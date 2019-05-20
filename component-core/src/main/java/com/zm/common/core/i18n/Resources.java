package com.zm.common.core.i18n;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * 能够为资源访问接口动态创建实现类，能够实现读取指定的资源包（.properties），且符合国际化编码的规范。
 * 
 */
public class Resources {

	private static final Logger logger = LoggerFactory.getLogger(Resources.class);

	private static final Map<String, Object> objectMap = new HashMap<String, Object>();

	/**
	 * 创建对象，其实现了参数指定的资源方位接口。调用此方法等价于调用：<br>
	 * <code>create(constIntf, null, null, null);</code>
	 * 
	 * @see #create(Class, String, Locale, ClassLoader)
	 */
	public static <T> T create(Class<T> constIntf) {
		return create(constIntf, null, null, null);
	}

	/**
	 * 创建对象，其实现了参数指定的资源方位接口。调用此方法等价于调用：<br>
	 * <code>create(constIntf, resourceBaseName, null, null);</code>
	 * 
	 * @see #create(Class, String, Locale, ClassLoader)
	 */
	public static <T> T create(Class<T> constIntf, String resourceBaseName) {
		return create(constIntf, resourceBaseName, null, null);
	}

	/**
	 * 创建对象，其实现了参数指定的资源方位接口。
	 * 
	 * @param <T>
	 * @param constIntf
	 *            资源访问接口类。传入null将导致返回null。
	 * @param resourceBaseName
	 *            资源包的基本名称。传入null等价于在constIntf相同包路径下查找与constIntf同名的资源包。
	 * @param locale
	 *            资源包所需的语言环境。传入null表示使用当前默认语言环境。
	 * @param classLoader
	 *            加载资源包的类加载器。若传入null将使用constIntf。当传入非null时，resourceBaseName必须使用绝对路径。
	 * @return 返回实现了constIntf的对象。若指定的资源包不存在将返回null。
	 */
	public static <T> T create(Class<T> constIntf, String resourceBaseName, Locale locale, ClassLoader classLoader) {
		if (constIntf == null) {
			return null;
		}
		if (resourceBaseName == null) {
			resourceBaseName = constIntf.getName().replace('.', '/');
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		if (classLoader == null) {
			classLoader = constIntf.getClassLoader();
		}

		// 从缓存中取得。
		String key = constIntf.getName() + "." + resourceBaseName;
		Object object = objectMap.get(key);
		if (object != null) {
			return (T) object;
		}

		ResourceBundle bundle = getResourceBundle(resourceBaseName, locale, classLoader);

		T impl = createImpl(constIntf, bundle, resourceBaseName);
		if (impl != null) {
			objectMap.put(key, impl);
		}
		return impl;
	}

	private static ResourceBundle getResourceBundle(String resourceBaseName, Locale locale, ClassLoader classLoader) {
		try {
			return ResourceBundle.getBundle(resourceBaseName, locale, classLoader);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	private static <T> T createImpl(Class<T> constIntf, ResourceBundle bundle, String resourceBaseName) {
		assert constIntf != null;

		try {
			// 创建实现类
			ClassPool cp = ClassPool.getDefault();

			String className = constIntf.getName() + "$" + resourceBaseName.replace('/', '_') + "$javassist_impl";
			CtClass impl = cp.makeClass(className);
			CtClass intf = getCtClass(cp, constIntf);
			CtClass assist = cp.get(ResourcesAssist.class.getName());
			impl.setSuperclass(assist);
			impl.addInterface(intf);

			// 逐个实现接口的每个方法
			Method[] ims = constIntf.getMethods();
			for (Method im : ims) {
				if (im.getDeclaringClass().equals(BaseResources.class)) {
					continue;
				}
				String value = ensureValue(bundle, im);
				CtMethod m = new CtMethod(cp.get(String.class.getName()), im.getName(), new CtClass[] {}, impl);
				m.setModifiers(Modifier.PUBLIC);
				m.setBody("{return \"" + value + "\";}");
				impl.addMethod(m);
			}

			Class cls = cp.toClass(impl);
			T object = (T) cls.newInstance();
			((ResourcesAssist) object).setResourceBundle(bundle);
			return object;
		} catch (Exception e) {
			logger.error("创建资源访问类失败。", e);
			return null;
		}
	}

	private static CtClass getCtClass(ClassPool cp, Class cls) throws NotFoundException {
		assert cp != null;
		assert cls != null;
		try {
			return cp.get(cls.getName());
		} catch (NotFoundException e) {
			// lxm 2012-8-17
			// 可能由于是ClassLoader的原因，直接调用cp.get()会抛出NotFoundException，解决方法是尝试修改classpath。
			// 参见：http://blog.sina.com.cn/s/blog_40ea56450100qarw.html
			cp.insertClassPath(new ClassClassPath(cls));
			return cp.get(cls.getName());
		}
	}

	private static String ensureValue(ResourceBundle bundle, Method im) {
		String key = im.getName();
		String value = key;
		if (bundle == null || !bundle.containsKey(key)) {
			if (im.isAnnotationPresent(DefaultStringValue.class)) {
				DefaultStringValue def = im.getAnnotation(DefaultStringValue.class);
				value = def.value();
			}
		} else {
			value = bundle.getString(key);
		}

		// 将“"”和“\"”转换为“\\"”
		StringBuffer sb = new StringBuffer();
		char last = 0;
		for (int index = 0; index < value.length(); index++) {
			char ch = value.charAt(index);
			if (ch == '"') {
				if (index == 0 || last != '\\') {
					sb.append('\\');
				}
			}
			sb.append(ch);
			last = ch;
		}

		return sb.toString();
	}

}
