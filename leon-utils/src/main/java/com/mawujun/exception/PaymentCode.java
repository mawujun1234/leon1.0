package com.mawujun.exception;

/**
 * 例子错误异常，其实可以建一个总类，往里面添加异常错误代码
 * @author mawujun
 *
 */
public enum PaymentCode implements ExceptionCode {
	
	SERVICE_TIMEOUT(101),
	CREDIT_CARD_EXPIRED(102),
	AMOUNT_TOO_HIGH(103),
	INSUFFICIENT_FUNDS(104);
	
	private final int number;

	private PaymentCode(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}

}
