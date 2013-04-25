package com.mawujun.controller.spring.mvc;

import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import com.mawujun.utils.jsonLib.JSONObjectUtils;

/**
 * 
 * @author Administrator
 *
 */
public class JSONView_JsonLib extends AbstractView {
	
	private boolean disableCaching = true;
	
	public static final String DEFAULT_CONTENT_TYPE = "application/json";

	private String encoding = "UTF-8";
	
	private Set<String> modelKeys;

	private boolean extractValueFromSingleKeyModel = false;
	
	/**
	 * Set the attribute in the model that should be rendered by this view.
	 * When set, all other model attributes will be ignored.
	 */
	public void setModelKey(String modelKey) {
		this.modelKeys = Collections.singleton(modelKey);
	}
	
	/**
	 * Set the attributes in the model that should be rendered by this view.
	 * When set, all other model attributes will be ignored.
	 */
	public void setModelKeys(Set<String> modelKeys) {
		this.modelKeys = modelKeys;
	}

	/**
	 * Return the attributes in the model that should be rendered by this view.
	 */
	public Set<String> getModelKeys() {
		return this.modelKeys;
	}
	/**
	 * Set whether to serialize models containing a single attribute as a map or whether to
	 * extract the single value from the model and serialize it directly.
	 * <p>The effect of setting this flag is similar to using {@code MappingJacksonHttpMessageConverter}
	 * with an {@code @ResponseBody} request-handling method.
	 * <p>Default is {@code false}.
	 */
	public void setExtractValueFromSingleKeyModel(boolean extractValueFromSingleKeyModel) {
		this.extractValueFromSingleKeyModel = extractValueFromSingleKeyModel;
	}
	
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(getContentType());
		response.setCharacterEncoding(this.getEncoding());
		if (disableCaching) {
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
			response.addDateHeader("Expires", 1L);
		}
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		1:把jsonobject转换成了key-value键值对，所以这里转换变错了，
//		2:还要测试直接返回实体类
		
		Object o=filterModel(model);
		JSONObject json=JSONObjectUtils.toJson(o);
		//JSONObject reslt=new JSONObject()
		FileCopyUtils.copy(json.toString(), new OutputStreamWriter(response.getOutputStream(), this.getEncoding()));

	}
	
//	@Override
//	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		Object value = filterModel(model);
//		JsonGenerator generator =
//				this.objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), this.encoding);
//		if (this.prefixJson) {
//			generator.writeRaw("{} && ");
//		}
//		this.objectMapper.writeValue(generator, value);
//	}
	
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes = (!CollectionUtils.isEmpty(this.modelKeys) ? this.modelKeys : model.keySet());
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		if(result.size() == 1){
			return result.values().iterator().next();
		} else {
			return (this.extractValueFromSingleKeyModel && result.size() == 1 ? result.values().iterator().next() : result);
		}
		
	}

	public boolean isDisableCaching() {
		return disableCaching;
	}

	public void setDisableCaching(boolean disableCaching) {
		this.disableCaching = disableCaching;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getContentType() {
		return JSONView_JsonLib.DEFAULT_CONTENT_TYPE;
	}

	


}
