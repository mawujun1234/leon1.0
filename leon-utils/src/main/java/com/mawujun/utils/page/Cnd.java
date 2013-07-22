package com.mawujun.utils.page;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpression;

public class Cnd {

	public static Cnd where(SqlExpression e) {
		return new Cnd(e);
	}

}
