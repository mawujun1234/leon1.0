package com.mawujun.repository.sql;

public abstract class DML extends SubSQL {
	public  static Select select()
	{
		return new Select();
	}
	
	public  static Insert insert()
	{
		return new Insert();
	}
	
	public  static Update update()
	{
		return new Update();
	}
	
	public  static Delete delete()
	{
		return new Delete();
	}
}
