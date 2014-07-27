package com.mawujun.generator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.PropertiesUtils;

/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/propertyConfig")
public class PropertyConfigController {

	@Resource
	private PropertyConfigService propertyConfigService;
	@Autowired
	JavaEntityMetaDataService javaEntityMetaDataService;

	@RequestMapping("/query.do")
	@ResponseBody
	public List<PropertyConfig> query(String subjectName) throws ClassNotFoundException {	
		List<PropertyConfig> list=propertyConfigService.query(Cnd.select().andEquals("subjectName", subjectName));
		
		if(list==null || list.size()==0){
			//PropertiesUtils utils=PropertiesUtils.load("templates/templates.properties");
			SubjectRoot root=javaEntityMetaDataService.prepareDate(subjectName);
			
			List<PropertyConfig> result=new ArrayList<PropertyConfig>();
			for(PropertyColumn pc:root.getPropertyColumns()){
				PropertyConfig config=new PropertyConfig();
				config.setSubjectName(subjectName);
				config.setProperty(pc.getProperty());
				config.setLabel(pc.getProperty());
				if(pc.getJsType()=="date"){
					config.setShowModel("datefield");
				} else if(pc.getJsType()=="int" || pc.getJsType()=="float"){
					config.setShowModel("numberfield");
				} else if(pc.getIsConstantType()){
					config.setShowModel("combobox");
				}else {
					config.setShowModel("textfield");
				}
				result.add(config);
			}
			propertyConfigService.saveBatch(result);
			return result;
		} else {
			return list;
		}
	}

	@RequestMapping("/load.do")
	public PropertyConfig load(String id) {
		return propertyConfigService.get(id);
	}
	
	@RequestMapping("/create.do")
	@ResponseBody
	public PropertyConfig create(@RequestBody PropertyConfig propertyConfig) {
		propertyConfigService.create(propertyConfig);
		return propertyConfig;
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public  PropertyConfig update(@RequestBody PropertyConfig propertyConfig) {
		propertyConfigService.update(propertyConfig);
		return propertyConfig;
	}
	
	@RequestMapping("/destroy.do")
	@ResponseBody
	public String destroy(String id) {
		propertyConfigService.delete(id);
		return id;
	}
	
//	@RequestMapping("/destroy")
//	@ResponseBody
//	public PropertyConfig destroy(@RequestBody PropertyConfig propertyConfig) {
//		propertyConfigService.delete(propertyConfig);
//		return propertyConfig;
//	}
	
	
}

