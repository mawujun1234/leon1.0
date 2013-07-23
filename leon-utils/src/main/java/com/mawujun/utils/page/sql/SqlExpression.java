package com.mawujun.utils.page.sql;

import org.nutz.dao.sql.PItem;



public interface SqlExpression  extends PItem{
	SqlExpression setNot(boolean not);
}
