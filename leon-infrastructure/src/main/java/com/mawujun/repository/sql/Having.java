package com.mawujun.repository.sql;

public class Having extends CE<Having>
{

	
	protected SQLKeyword getKeyword() {
		return SQLKeyword.GROUP$HAVING;
	}
	
	
	public GroupBy parent()
	{
		return (GroupBy)super.parent();
	}

}
