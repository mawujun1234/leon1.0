package com.mawujun.parameter;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 使用java获取参数数据源
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public interface JavaBeanDataSource {
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param connection 数据库连接
	 * @return
	 */
	public List<JavaBeanKeyName> getData(JdbcTemplate jdbcTemplate);

}
