package com.mawujun.controller.spring.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
	private String formatStr="yyyy-MM-dd HH:mm:ss";

	public void registerCustomEditors(PropertyEditorRegistry registry) {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        dateFormat.setLenient(false);
        registry.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));


	}

}
