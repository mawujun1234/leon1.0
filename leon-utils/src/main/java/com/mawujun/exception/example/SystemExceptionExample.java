package com.mawujun.exception.example;


import com.mawujun.exception.PaymentCode;
import com.mawujun.exception.BusinessException;

public class SystemExceptionExample {

	public static void main(String[] args) {
		try {
			throw new BusinessException(PaymentCode.CREDIT_CARD_EXPIRED);
		} catch (BusinessException e) {
			if (e.getErrorCode() == PaymentCode.CREDIT_CARD_EXPIRED) {
				System.out.println("Credit card expired");
			}
		}
	}

}
