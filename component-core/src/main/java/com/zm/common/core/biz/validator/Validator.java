package com.zm.common.core.biz.validator;

import java.lang.annotation.Annotation;

/**
 * 验证器接口
 * 
 */
public interface Validator<A extends Annotation> {

	/**
	 * 验证指定的属性是否合法。
	 * 
	 * @param annotation
	 *            属性上标记的验证注释
	 * @param va
	 *            lue 属性值
	 * @return 如果验证错误，返回错误信息，否则返回null。
	 * @throws RuntimeException
	 *             验证过程中产生的意外错误。
	 */
	String validate(A annotation, Object value) throws RuntimeException;

}