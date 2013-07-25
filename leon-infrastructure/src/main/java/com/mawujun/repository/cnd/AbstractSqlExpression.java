package com.mawujun.repository.cnd;

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
	
//	protected String _getEntityName(AbstractEntityPersister classMetadata) {
//		try {
//			classMetadata.getPropertyIndex(name);
//		} catch(Exception e){
//			String[] properties=name.split("\\.");
//			if(properties.length>1){
//				
//			}
//		}
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNot() {
		return not;
	}


}

