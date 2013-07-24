package com.mawujun.repository.page.sql;

public class IntRange extends NumberRange {

	IntRange(String name, int... ids) {
		super(name);
		this.not = false;
		this.ids = new long[ids.length];
		for (int i = 0; i < ids.length; i++)
			this.ids[i] = ids[i];
	}
	
}
