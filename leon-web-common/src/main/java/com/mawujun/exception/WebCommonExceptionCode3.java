package com.mawujun.exception;

public enum WebCommonExceptionCode3  implements ExceptionCode{

	EXISTS_CHILDREN(301);

	private final int number;

	private WebCommonExceptionCode3(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}

}
