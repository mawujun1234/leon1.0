package com.mawujun.utils.spring.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.mawujun.exception.AppException;

public class CustomerExceptionResolver extends SimpleMappingExceptionResolver {
	
	private String jsonMediaType="application/json";
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String acceptHeader=request.getHeader("Accept");
		ex.printStackTrace();
		if (StringUtils.hasText(acceptHeader) && acceptHeader.equals(this.getJsonMediaType())) {
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			if(ex instanceof AppException){
				writer.write("{success:false,message:'"+((AppException)ex).getDetailMessage()+"',errors:{}}");
			} else {
				writer.write("{success:false,message:'服务器端出现异常，请重新尝试或联系管理员',errors:{}}");
			}
			writer.close();
			return null;
		} else {
			return super.resolveException(request, response, handler, ex);
		}
	}
	public String getJsonMediaType() {
		return jsonMediaType;
	}
	public void setJsonMediaType(String jsonMediaType) {
		this.jsonMediaType = jsonMediaType;
	}

}
