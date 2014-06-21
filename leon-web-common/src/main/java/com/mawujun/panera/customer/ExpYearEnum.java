package com.mawujun.panera.customer;

public enum ExpYearEnum {
	zero(1),one(2),two(4),threeMore(5);
	private int score;
	
	private ExpYearEnum(int score){
		this.score=score;
	}
	
	public int getScore() {
		return score;
	}
	

}
