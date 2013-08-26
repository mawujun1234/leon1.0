package com.mawujun.parameter;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class JavaBeanDataSourceSample implements JavaBeanDataSource {

	@Override
	public List<JavaBeanKeyName> getData(JdbcTemplate jdbcTemplate) {
//		// TODO Auto-generated method stub
//		List<JavaBeanKeyName> list=new ArrayList<JavaBeanKeyName>();
//		for(int i=0;i<10;i++){
//			JavaBeanKeyName keyName=new JavaBeanKeyName();
//			keyName.setKey(i+"");
//			keyName.setName(i+"");
//			list.add(keyName);
//		}
//		return list;
		List<JavaBeanKeyName> list=null;
		d
		list=(List<JavaBeanKeyName>) jdbcTemplate.queryForList("select id as key,text as name from leon_menu", JavaBeanKeyName.class);
		
		return list;
	}

}
