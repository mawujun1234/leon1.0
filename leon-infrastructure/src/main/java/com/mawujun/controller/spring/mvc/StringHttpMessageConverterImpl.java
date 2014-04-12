package com.mawujun.controller.spring.mvc;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;

/**
 * 用来处理返回string的时候，使用了@ResponseBody的方法
 * @author mawujun email:160649888@163.com qq:16064988
 *
 */
public class StringHttpMessageConverterImpl extends StringHttpMessageConverter {
	/**
	 * A default constructor that uses {@code "ISO-8859-1"} as the default charset.
	 * @see #StringHttpMessageConverter(Charset)
	 */
	public StringHttpMessageConverterImpl() {
		super(DEFAULT_CHARSET);
	}

	/**
	 * A constructor accepting a default charset to use if the requested content
	 * type does not specify one.
	 */
	public StringHttpMessageConverterImpl(Charset defaultCharset) {
		super(defaultCharset);
	}
	
	
	@Override
	protected Long getContentLength(String s, MediaType contentType) {
		if(JsonConfigHolder.getAutoWrap()){
			s= FastJsonToStringUtils.getJsonString(s);
		}
		return super.getContentLength(s, contentType);
	}
	
	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
		if(JsonConfigHolder.getAutoWrap()){
			s= FastJsonToStringUtils.getJsonString(s);
		}
		super.writeInternal(s, outputMessage);
	}
	
}
