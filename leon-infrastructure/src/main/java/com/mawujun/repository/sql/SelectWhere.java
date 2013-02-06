package com.mawujun.repository.sql;

public class SelectWhere extends Where
{
	
	protected SQLKeyword getKeyword() {
		return SQLKeyword.WHERE;
	}
 
	public Select parent() {
		return (Select)super.parent();
	}
	
	 
	public Select top() {
		return (Select)super.top();
	}
}
