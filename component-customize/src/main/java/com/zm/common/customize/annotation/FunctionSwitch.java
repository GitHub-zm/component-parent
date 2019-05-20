package com.zm.common.customize.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 功能开关注解。实现功能的启用或关闭，功能关闭时{@linkplain Configuration} 下的所有bean不会加载到Spring容器。<br>
 * 需要配合 {@link FunctionApplicationContextInitializer}
 * 提供功能清单配置文件(classpath:functions.properties)，可选值{@code true}或 {@code  false}
 * ，未配置时，等价于false。<br>
 * 
 * <pre>
 * <strong>java代码</strong>
 * 
 * &#064;Configuration
 * &#064;FunctionSwitch(value = &quot;functionSwitch1&quot;, description = &quot;功能1&quot;)
 * public class FunctionSwitchConfiguration {
 * 
 * }
 * 
 * <strong>functions.properties</strong>
 * 
 * functionSwitch1=true
 * or
 * functionSwitch1=false
 * 
 * 
 * </pre>
 * 
 * @see FunctionApplicationContextInitializer
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(FunctionSwitchCondition.class)
public @interface FunctionSwitch {
	/**
	 * 功能名称。<br>
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
