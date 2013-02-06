package com.mawujun.exception;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String detailMessage;
	
	public AppException() {
		// TODO Auto-generated constructor stub
	}

	public AppException(String message) {
		this.detailMessage=message;
	}

	@Override
    public Throwable fillInStackTrace() {
	   //return this;
		return super.fillInStackTrace();
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}


}
