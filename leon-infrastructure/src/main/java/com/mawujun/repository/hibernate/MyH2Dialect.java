package com.mawujun.repository.hibernate;

import org.hibernate.dialect.H2Dialect;

/**
 * 取消了cross join
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class MyH2Dialect extends H2Dialect {

	public String getCrossJoinSeparator() {
		return ", ";
	}

}
