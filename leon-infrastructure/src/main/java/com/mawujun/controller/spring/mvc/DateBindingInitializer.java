package com.mawujun.controller.spring.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 属性编辑器，间日期格式转换成Date.class对象 将格式为yyyy-MM-dd的字符串转换成Date对象
 * 注意类PropertyEditorRegistrySupport
 * 
 * 使用CustomPropertyEditorRegistrar替换了
 * @author mawujun
 *
 */
@Deprecated
public class DateBindingInitializer implements WebBindingInitializer {
	private String formatStr="yyyy-MM-dd";

	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,  new CustomDateEditor(dateFormat, true));//true表示允许时间字段为空
        //binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));//将空字符串转换成null
       // binder.registerCustomEditor(int.class, new MyCustomNumberEditor(Integer.class, true));//当不能获取到基本类型的时候，就是用bea中的默认值
       // binder.registerCustomEditor(long.class, new MyCustomNumberEditor(Long.class, false));
        //binder.registerCustomEditor(boolean.class, new CustomBooleanEditor(true));
        //binder.registerCustomEditor(Menu.class, new MenuEditor());   
	}

	public String getFormatStr() {
		return formatStr;
	}

	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}

}
