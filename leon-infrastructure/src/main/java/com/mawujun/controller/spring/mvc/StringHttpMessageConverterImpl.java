package com.mawujun.controller.spring.mvc;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

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
	
}
