package com.mawujun.exception;

/**
 * 例子错误异常
 * @author mawujun
 *
 */
public enum ValidationCode implements ExceptionCode {
	
	VALUE_REQUIRED(201),
	INVALID_FORMAT(202),
	VALUE_TOO_SHORT(203),
	VALUE_TOO_LONGS(204);

	private final int number;

	private ValidationCode(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}

}
