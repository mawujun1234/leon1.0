package com.mawujun.utils.jsonLib;

import net.sf.json.util.PropertyFilter;
/**
 * 过滤掉所有的属性，只获取id属性
 * @author Administrator
 *
 */
public class OnlyIdPropertyFilter implements PropertyFilter {

	public boolean apply(Object source, String name, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

}
