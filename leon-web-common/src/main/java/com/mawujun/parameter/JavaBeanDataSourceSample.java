package com.mawujun.parameter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
		
		list=(List<JavaBeanKeyName>) jdbcTemplate.query("select id as key,text as name from leon_menu", new RowMapper<JavaBeanKeyName>(){

			@Override
			public JavaBeanKeyName mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				JavaBeanKeyName keName=new JavaBeanKeyName();
				keName.setKey((String)rs.getString("key"));  
				keName.setName((String)rs.getString("name"));  
				return keName;
			}

			
			
		});
		
		return list;
	}

}
