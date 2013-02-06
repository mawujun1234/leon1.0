package com.mawujun.repository.sql;

public class DeleteWhere extends CE<DeleteWhere> 
{
	
	protected SQLKeyword getKeyword() {
		return SQLKeyword.WHERE;
	}
	
	
	public Delete parent() {
		return (Delete)super.parent();
	}
	
	
	public Delete top() {
		return (Delete)super.top();
	}
	
}