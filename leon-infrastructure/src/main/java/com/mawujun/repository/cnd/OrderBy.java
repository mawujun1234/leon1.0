package com.mawujun.repository.cnd;



public interface OrderBy  {
	OrderBy asc(String name);

	OrderBy desc(String name);
}
