package com.mawujun.exception.example;


import com.mawujun.exception.PaymentCode;
import com.mawujun.exception.BussinessException;

public class SystemExceptionExample {

	public static void main(String[] args) {
		try {
			throw new BussinessException(PaymentCode.CREDIT_CARD_EXPIRED);
		} catch (BussinessException e) {
			if (e.getErrorCode() == PaymentCode.CREDIT_CARD_EXPIRED) {
				System.out.println("Credit card expired");
			}
		}
	}

}
