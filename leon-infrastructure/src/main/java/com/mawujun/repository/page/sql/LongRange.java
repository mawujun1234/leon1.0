package com.mawujun.repository.page.sql;

public class LongRange extends NumberRange {

	LongRange(String name, long... ids) {
		super(name);
		this.ids = ids;
		this.not = false;
	}

}