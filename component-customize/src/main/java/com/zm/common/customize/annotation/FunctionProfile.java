package com.zm.common.customize.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.security.auth.login.Configuration;

import org.springframework.context.annotation.Conditional;

import com.zm.common.customize.FunctionApplicationContextInitializer;

/**
 * 功能Profile注解，提供功能选择。功能未选择时{@linkplain Configuration}
 * 下的所有bean不会加载到Spring容器。<br>
 * 需要配合 {@link FunctionApplicationContextInitializer}
 * 提供功能清单配置文件(classpath:functions.properties)，可选值
 * {@linkplain FunctionProfile#value()} 。<br>
 * 
 * 
 * <pre>
 * 
 * java代码：
 * 
 * &#064;Configuration
 * &#064;FunctionProfile(name = &quot;function1&quot;, value = &quot;sdzh&quot;)
 * public class FunctionProfileSdzhConfiguration {
 * }
 * 
 * &#064;Configuration
 * &#064;FunctionProfile(name = &quot;function1&quot;, value = &quot;zjlh&quot;)
 * public class FunctionProfileZjlhConfiguration {
 * }
 * 
 * function1功能可选值：sdzh和zjlh
 * 
 * functions.properties：
 * 
 * function1=zjlh 
 * or 
 * function1=sdzh
 * or
 * function1=sdzh,zjlh  --如果程序支持的话，可以采用逗号分隔。
 * 
 * </pre>
 * 
 * @see FunctionApplicationContextInitializer
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(FunctionProfileCondition.class)
public @interface FunctionProfile {

	/**
	 * profile名称。
	 * 
	 * @return
	 */
	String name();

	/**
	 * 匹配值，当Environment中对应{@link #name()}的值包含该指定值时，启用功能。
	 * 
	 * @return
	 */
	String value();

	/**
	 * 功能描述
	 * 
	 * @return
	 */
	String description() default "";
}
