package com.zm.common.core.biz.validator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.util.Assert;

public class BeanValidator {

	public BeanValidator() {
	}

	/**
	 * 构造属性验证器，指定验证配置。
	 * 
	 * @param config
	 *            配置，not null
	 * @throws IllegalArgumentException
	 *             参数不合法时抛出此异常。
	 */
	public BeanValidator(ValidateConfig config) throws IllegalArgumentException {
		Assert.notNull(config, "config");
		this.config = config;
	}

	/**
	 * 验证指定JavaBean属性是否合法。
	 * 
	 * @param bean
	 *            待验证的JavaBean，如果传入null，将返回空列表。
	 * @return 验证结果列表，保证不返回null，验证通过也返回空列表。
	 * @throws ValidatorException
	 *             验证过程中的未知异常时抛出。
	 */
	public List<ConstraintViolation> validate(Object bean) throws ValidatorException {
		return validateNested(null, bean);
	}

	private List<ConstraintViolation> validateNested(String name, Object bean) throws ValidatorException {
		// 循环检查
		if (needCheck(bean) == false)
			return new ArrayList();

		// 对集合、数组等的验证
		if (bean instanceof Collection) {
			return validateCollection(name, (Collection) bean);
		} else if (bean instanceof Map) {
			return validateMap(name, (Map) bean);
		} else if (bean != null && bean.getClass().isArray()) {
			return validateArray(name, bean);
		}

		List<ConstraintViolation> result = new ArrayList();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
		} catch (IntrospectionException e) {
			throw new ValidatorException(e, MessageFormat.format(R.R.getBeanInfoError(), bean.getClass().getName()));
		}
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors) {
			if (descriptor.getReadMethod() == null)
				continue;

			Method method = descriptor.getReadMethod();
			Object nestedBean = getProperty(bean, method);
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (Valid.class.equals(annotation.annotationType())) {
					String propertyName = getNestedPropertyName(name, getPropertyName(descriptor));
					result.addAll(validateNested(propertyName, nestedBean));
					continue;
				}

				ValidatorClass validatorClass = annotation.annotationType().getAnnotation(ValidatorClass.class);
				if (validatorClass == null)
					continue;

				Validator validator = getValidator(validatorClass);
				String message = validator.validate(annotation, nestedBean);
				if (message == null)
					continue;

				ConstraintViolation violation = new ConstraintViolation();
				violation.setInvalidValue(nestedBean);
				violation.setMessage(message);
				String propertyName = getNestedPropertyName(name, getPropertyName(descriptor));
				violation.setPropertyName(propertyName);
				result.add(violation);
			}
		}

		return result;
	}

	private Object getProperty(Object bean, Method readMethod) throws ValidatorException {
		try {
			return readMethod.invoke(bean, EMPTY_ARRAYS);
		} catch (IllegalArgumentException e) {
			throw new ValidatorException(e,
					MessageFormat.format(R.R.invokeMethodError(), bean.getClass().getName(), readMethod.getName()));
		} catch (IllegalAccessException e) {
			throw new ValidatorException(e,
					MessageFormat.format(R.R.invokeMethodError(), bean.getClass().getName(), readMethod.getName()));
		} catch (InvocationTargetException e) {
			throw new ValidatorException(e,
					MessageFormat.format(R.R.invokeMethodError(), bean.getClass().getName(), readMethod.getName()));
		}
	}

	private List<ConstraintViolation> validateCollection(String name, Collection bean) throws ValidatorException {
		List<ConstraintViolation> result = new ArrayList();
		int index = 0;
		Iterator iter = bean.iterator();
		while (iter.hasNext()) {
			String propertyName = getIndexedPropertyName(name, index++);
			result.addAll(validateNested(propertyName, iter.next()));
		}
		return result;
	}

	private List<ConstraintViolation> validateMap(String name, Map bean) throws ValidatorException {
		List<ConstraintViolation> result = new ArrayList();
		Set<Map.Entry> entries = bean.entrySet();
		for (Map.Entry entry : entries) {
			String propertyName = getMappedPropertyName(name, entry.getKey().toString());
			result.addAll(validateNested(propertyName, entry.getValue()));
		}
		return result;
	}

	private List<ConstraintViolation> validateArray(String name, Object bean) throws ValidatorException {
		List<ConstraintViolation> result = new ArrayList();
		int len = Array.getLength(bean);
		for (int index = 0; index < len; index++) {
			Object nestedBean = Array.get(bean, index);
			String propertyName = getIndexedPropertyName(name, index);
			result.addAll(validateNested(propertyName, nestedBean));
		}
		return result;
	}

	private String getPropertyName(PropertyDescriptor descriptor) {
		assert descriptor != null;

		if (config.isEnabled(ValidateConfig.USE_JAXB_PROPERTY_NAME)) {
			Method m = descriptor.getReadMethod();
			if (m != null) {
				XmlElement elem = m.getAnnotation(XmlElement.class);
				if (elem != null)
					return elem.name();
			}
		}

		return descriptor.getName();
	}

	private String getIndexedPropertyName(String name, int index) {
		return name + "[" + index + "]";
	}

	private String getMappedPropertyName(String name, String key) {
		return name + "(" + key + ")";
	}

	private String getNestedPropertyName(String name, String nestedName) {
		if (name != null)
			return name + "." + nestedName;
		else
			return nestedName;
	}

	/**
	 * 实例化Validator，考虑到同一种类型的Validator可能会多次处理，这里进行了缓存，以减少重复创建对象的开销。
	 */
	private Validator getValidator(ValidatorClass validatorClass) throws ValidatorException {
		Class clazz = validatorClass.value();
		Validator validator = validatorCache.get(clazz.getName());
		if (validator == null) {
			try {
				validator = (Validator) clazz.newInstance();
			} catch (InstantiationException e) {
				throw new ValidatorException(e, MessageFormat.format(R.R.newValidatorInstanceError(), clazz.getName()));
			} catch (IllegalAccessException e) {
				throw new ValidatorException(e, MessageFormat.format(R.R.newValidatorInstanceError(), clazz.getName()));
			}
			validatorCache.put(clazz.getName(), validator);
		}
		return validator;
	}

	private boolean needCheck(Object bean) {
		if (bean == null)
			return false;
		for (Object o : cycleChecker)
			if (o == bean)
				return false;
		cycleChecker.add(bean);
		return true;
	}

	private WeakHashMap<String, Validator> validatorCache = new WeakHashMap();
	private List cycleChecker = new ArrayList();
	private static final Object[] EMPTY_ARRAYS = new Object[0];
	private ValidateConfig config = new ValidateConfig();

}
