package com.mawujun.controller.spring.mvc.json;

import java.util.Map;


public interface Trans2JsonObject {
	/**
	 * 要支持的类的处理
	 * @author mawujun 16064988@qq.com 
	 * @param t
	 * @return
	 */
	public boolean supports(Object object);
	public Map convert(Object object);
}
