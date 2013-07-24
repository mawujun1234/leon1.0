package com.mawujun.repository.page.sql;



public interface OrderBy  {
	OrderBy asc(String name);

	OrderBy desc(String name);
}
