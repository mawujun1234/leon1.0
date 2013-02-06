package com.mawujun.repository.sql;

public class UpdateWhere extends CE<UpdateWhere> 
{
	
	protected SQLKeyword getKeyword() {
		return SQLKeyword.WHERE;
	}
	
	
	public Update parent() {
		return (Update)super.parent();
	}
	
	
	public Update top() {
		return (Update)super.top();
	}
	
}
