package com.mawujun.repository.mybatis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sql="select count (*) "
				+ "from (select x.*,1 as num from ems_equipment x where x.store_id='2c90838447a984ce0147a9d67db60000' and x.prod_id='23') a "
				+ "inner join ems_equipmentsubtype b on a.subtype_id=b.id inner join ems_equipmentprod c on a.prod_id=c.id inner join ems_brand d on a.brand_id=d.id "
				+ "inner join ems_supplier e on a.supplier_id=e.id WHERE c.id='23' "
				+ " group by a.style,b.name ,c.name,d.name,e.name ";
		String aa=removeGroupBys(sql);
		System.out.println(aa);
	}

	private static String removeGroupBys(String hql) {
		//Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		//Pattern p = Pattern.compile("order\\s*by([\\s*|,]\\w+(asc|desc|\\s*)*)+", Pattern.CASE_INSENSITIVE);
		Pattern p = Pattern.compile("group\\s*by([\\s*|,].+*)+", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
