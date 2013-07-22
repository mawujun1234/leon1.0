package com.mawujun.utils.page.sql;

import org.hibernate.persister.entity.AbstractEntityPersister;



public abstract class AbstractSqlExpression implements SqlExpression {

	protected boolean not;

	private String name;

	protected AbstractSqlExpression(String name) {
		this.name = name;
	}

	AbstractSqlExpression not() {
		this.not = true;
		return this;
	}

	public SqlExpression setNot(boolean not) {
		this.not = not;
		return this;
	}

	/**
	 * 获取某个属性对应的
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param classMetadata
	 * @return
	 */
	protected String _fmtcol(AbstractEntityPersister classMetadata) {
		String[] columns=classMetadata.getPropertyColumnNames(name);

		if(columns==null){
			return null;
		} else{
			return columns[0];
		}
	}

//	protected MappingField _field(Entity<?> en) {
//		en = _en(en);
//		return null == en ? null : en.getField(name);
//	}

}

