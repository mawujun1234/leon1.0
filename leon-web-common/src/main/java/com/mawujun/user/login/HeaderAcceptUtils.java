package com.mawujun.user.login;

import javax.servlet.http.HttpServletRequest;

public class HeaderAcceptUtils {

	/**
	 * 判断是不是请求 返回json数据
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param request
	 * @return
	 */
	public static boolean isAcceptJson(HttpServletRequest request){
		 String accept=request.getHeader("Accept");
		 if("application/json;".equalsIgnoreCase(accept)){
			 return true;
		 } else {
			 return false;
		 }
	}

}
