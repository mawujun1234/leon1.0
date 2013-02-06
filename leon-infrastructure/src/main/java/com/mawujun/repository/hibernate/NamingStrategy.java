package com.mawujun.repository.hibernate;

import org.hibernate.cfg.DefaultComponentSafeNamingStrategy;

/**
 * 或者可以继承ImprovedNamingStrategy
 * 在字段前加c可以防止出现，和数据库关键字冲突的问题
 * 不加c的话，映射起来更简单
 * @author mawujun
 *
 */
public class NamingStrategy extends DefaultComponentSafeNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static  String columnPrefix = "c_";
	
//	@Override
//	public String collectionTableName(String ownerEntity,
//			String ownerEntityTable, String associatedEntity,
//			String associatedEntityTable, String propertyName) {
//		return tableName(ownerEntityTable + "_" + associatedEntityTable);
//	}
	

	@Override
	public String propertyToColumnName(String propertyName) {
//		if("id".equalsIgnoreCase(propertyName)) {
//			return super.propertyToColumnName(propertyName).toUpperCase();
//		}
		return columnPrefix + super.propertyToColumnName(propertyName).toUpperCase();
	}

	public  String getColumnPrefix() {
		return NamingStrategy.columnPrefix;
	}

	public void setColumnPrefix(String columnPrefix) {
		NamingStrategy.columnPrefix = columnPrefix;
	}

//	@Override
//	public String classToTableName(String className) {
//		return "tbl_" + super.classToTableName(className).toUpperCase();
//	}

}
