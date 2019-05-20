package com.zm.common.core.biz.validator;

import com.zm.common.core.i18n.Resources;

public interface R {

	public static final R R = Resources.create(R.class);

	/** 必须是未来的时间（包含今天） */
	String validatorFutureIncludeToday();

	/** 必须是未来的时间。 */
	String validatorFuture();

	/** 必须是过去的时间（包含今天） */
	String validatorPastIncludeToday();

	/** 必须是过去的时间 */
	String validatorPast();

	/** 必须大于{0} */
	String validatorMin();

	/** 必须大于等于{0} */
	String validatorMinEqual();

	/** 必须小于{0} */
	String validatorMax();

	/** 必须小于等于{0} */
	String validatorMaxEqual();

	/** 不能为空 */
	String validatorNotNull();

	/** 不能为空 */
	String validatorNotEmpty();

	/** 必须符合规则{0} */
	String validatorPattern();

	/** 长度必须大于等于 */
	String validatorLengthMin();

	/** 长度必须小于等于 */
	String validatorLengthMax();

	/** 读取{0}的元数据出错 */
	String getBeanInfoError();

	/** 调用{0}的方法{1}出错 */
	String invokeMethodError();

	/** 实例化验证器{0}出错。 */
	String newValidatorInstanceError();

}
