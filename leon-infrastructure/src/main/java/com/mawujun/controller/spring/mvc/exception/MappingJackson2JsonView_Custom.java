package com.mawujun.controller.spring.mvc.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
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

import com.mawujun.controller.spring.mvc.MappingFastJson2HttpMessageConverter;
import com.mawujun.exception.BussinessException;
import com.mawujun.exception.ExceptionCode;

/**
 * 添加了构建异常处理信息的model，这是json视图
 * 注意这里使用的是jackson，没有解决循环依赖，hibernate等问题，有空的时候最好使用fastjson进行整合下
 * @author mawujun
 *
 */
@Deprecated
public class MappingJackson2JsonView_Custom extends MappingJackson2JsonView {
	final static Logger logger = LoggerFactory.getLogger(MappingJackson2JsonView_Custom.class);   
	ResourceBundle bundle = ResourceBundle.getBundle("com.mawujun.exception.exceptions");
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//处理异常的json视图
		if(model.get(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE)!=null){
			Exception exception=(Exception)model.get(SimpleMappingExceptionResolver.DEFAULT_EXCEPTION_ATTRIBUTE);
			Map<String,Object> map=new  HashMap<String, Object>();
			map.put("success", false);

			if(exception instanceof BussinessException){
				if( exception.getMessage()!=null){
					map.put("message", exception.getMessage());
				} else {
					ExceptionCode errorCode=((BussinessException) exception).getErrorCode();
					String key = errorCode.getClass().getSimpleName() + "." + errorCode;
					try {
						map.put("message", bundle.getString(key));
					} catch(MissingResourceException e){
						map.put("message",exception.getMessage());
					}
				}
				
				

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
				
				//map.put("message", exception.getMessage());
				map.put("message", "后台发生系统异常，请联系管理员或重试!");
			}
			model=map;
			//exception.printStackTrace();
			logger.debug(exception.getMessage(),exception);
		}
		
		//正常情况的 视图解析
		super.renderMergedOutputModel(model, request, response);
	}
}
