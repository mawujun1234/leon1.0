package com.mawujun.exception;

/**
 * 默认的异常处理，也可以往这里面添加异常信息。然后再往异常的properties中添加相应的异常错误信息
 * 1开头的都算是系统抛出的异常
 * @author mawujun
 *
 */
public enum DefaulExceptionCode implements ExceptionCode {
	SYSTEM_EXCEPTION(101),//系统异常，后台报出的异常
	SYSTEM_PROPERTY_IS_NOT_EXISTS(102),//属性不存在
	SYSTEM_MYBATIS_STATEMENT_CAN_NOT_NULL(103),//Mybatis的statement id不能为空
	BUSSINESS_EXCEPTION(104);
	
	
	private final int number;

	private DefaulExceptionCode(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}

}
