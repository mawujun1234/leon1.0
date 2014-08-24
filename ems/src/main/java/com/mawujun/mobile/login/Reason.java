package com.mawujun.mobile.login;

public class Reason {
	private String code;
	private String reason;
	
	public static Reason getInstance(){
		return new Reason();
	}
	

	public String getReason() {
		return reason;
	}
	public Reason setReason(String reason) {
		this.reason = reason;
		return this;
	}


	public String getCode() {
		return code;
	}


	public Reason setCode(String code) {
		this.code = code;
		return this;
	}

}
