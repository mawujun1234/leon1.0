package com.mawujun.parameter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * 用来获取菜单数据做为参数数据源，这样就可以为用户设置特定的菜单
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class MenuDataSource implements JavaBeanDataSource {

	@Override
	public List<JavaBeanKeyName> getData(JdbcTemplate jdbcTemplate) {
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
