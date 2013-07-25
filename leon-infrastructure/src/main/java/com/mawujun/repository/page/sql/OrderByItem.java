package com.mawujun.repository.page.sql;

import org.hibernate.persister.entity.AbstractEntityPersister;


public class OrderByItem  {//extends NoParamsPItem

	private String name;

	private String by;

	public OrderByItem(String name, String by) {
		this.name = name;
		this.by = by;
	}

//	public void joinSql(AbstractEntityPersister classMetadata, StringBuilder sb) {
//		sb.append(_fmtcolnm(en, name)).append(' ').append(by);
//	}
	public void joinHql(AbstractEntityPersister classMetadata, StringBuilder sb) {
		sb.append(name).append(' ').append(by);
	}
}
