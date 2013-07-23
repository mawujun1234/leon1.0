package com.mawujun.utils.page.sql;



public interface OrderBy  {
	OrderBy asc(String name);

	OrderBy desc(String name);
}
