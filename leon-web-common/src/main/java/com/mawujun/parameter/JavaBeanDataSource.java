package com.mawujun.parameter;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 使用java获取参数数据源,用于动态获取数据源的地方，
 * 主要用于当参数值是动态的时候，在界面上定义
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
