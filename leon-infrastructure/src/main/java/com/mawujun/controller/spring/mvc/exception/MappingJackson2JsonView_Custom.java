package com.mawujun.controller.spring.mvc.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.mawujun.controller.spring.mvc.HttpMessageConverter_FastJson;
import com.mawujun.exception.BussinessException;
import com.mawujun.exception.ExceptionCode;

/**
 * 添加了构建异常处理信息的model
 * @author mawujun
 *
 */
public class MappingJackson2JsonView_Custom extends MappingJackson2JsonView {
	final static Logger logger = LoggerFactory.getLogger(MappingJackson2JsonView_Custom.class);   
	ResourceBundle bundle = ResourceBundle.getBundle("com.mawujun.exception.exceptions");
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(model.get(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE)!=null){
			Exception exception=(Exception)model.get(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE);
			Map<String,Object> map=new  HashMap<String, Object>();
			map.put("success", false);
			map.put("detailMsg", exception.getMessage());
			
			if(exception instanceof BussinessException){
				ExceptionCode errorCode=((BussinessException) exception).getErrorCode();
				String key = errorCode.getClass().getSimpleName() + "." + errorCode;
				map.put("message", bundle.getString(key));
				
				//还要监听验证异常，从hibernate后台抛出来的
			} else if(exception instanceof ConstraintViolationException ){
				ConstraintViolationException ex=(ConstraintViolationException)exception;
				//map.put("message",ex.getMessage());
				
				StringBuffer detailMsg=new StringBuffer();
				Set<? extends ConstraintViolation> constraintViolations=ex.getConstraintViolations();
				for (ConstraintViolation violation : constraintViolations) {
					detailMsg.append(violation.getPropertyPath().toString()+"字段"+violation.getMessage()+":"+violation.getInvalidValue()+";");
				}
				map.put("message",detailMsg);
			} else {
				map.put("message", exception.getMessage());
				
			}
			model=map;
			//exception.printStackTrace();
			logger.info(exception.getMessage());
		}
		super.renderMergedOutputModel(model, request, response);
	}
}
