package com.mawujun.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.annotation.Label;
import com.mawujun.fun.Fun;
import com.mawujun.fun.FunService;
import com.mawujun.repository.idEntity.AutoIdEntity;
import com.mawujun.repository.idEntity.UUIDEntity;
import com.mawujun.utils.page.WhereInfo;

/**
 * 
 * @author mawujun email:mawujun1234@163.com qq:16064988
 *
 */
@Controller
@Transactional
public class ConstantTypeController  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ConstantTypeService constantTypeService;
	@Autowired
	private ConstantService constantService;
	@Autowired
	private ConstantItemService constantItemService;


	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/constantType/queryAll")
	@ResponseBody
	public List<Map<String,String>> queryAll(String id,String discriminator){	
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		if("root".equalsIgnoreCase(id) || id==null){
			List<ConstantType> types=constantTypeService.queryAll();
			for(ConstantType type:types){
				Map<String,String> map=new HashMap<String,String>();
				map.put("id", type.getId());
				map.put("text", type.getText());
				map.put("discriminator", type.getDiscriminator());
				map.put("remark", type.getRemark());
				list.add(map);
			}
		} else if("ConstantType".equals(discriminator)){
			WhereInfo whereinfo=WhereInfo.parse("constantType.id", id);
			
			List<Constant> types=constantService.query(whereinfo);
			for(Constant constant:types){
				Map<String,String> map=new HashMap<String,String>();
				map.put("id", constant.getId());
				map.put("text", constant.getText());
				map.put("discriminator", constant.getDiscriminator());
				map.put("remark", constant.getRemark());
				list.add(map);
			}
		} else if("Constant".equals(discriminator)){
			WhereInfo whereinfo=WhereInfo.parse("constant.id", id);
			List<ConstantItem> types=constantItemService.query(whereinfo);
			for(ConstantItem constant:types){
				Map<String,String> map=new HashMap<String,String>();
				map.put("id", constant.getId());
				map.put("text", constant.getText());
				map.put("discriminator", constant.getDiscriminator());
				map.put("remark", constant.getRemark());
				list.add(map);
			}
		}
		return list;
	}
	@RequestMapping("/constantType/query")
	@ResponseBody
	public List<ConstantType> query(){	
		return constantTypeService.queryAll();
	}
	@RequestMapping("/constantType/load")
	@ResponseBody
	public ConstantType load(String id){		
		return constantTypeService.get(id);
	}
	
	@RequestMapping("/constantType/create")
	@ResponseBody
	public ConstantType create(@RequestBody ConstantType constantType){		
		constantTypeService.create(constantType);
		return constantType;
	}
	
	@RequestMapping("/constantType/update")
	@ResponseBody
	public ConstantType update(@RequestBody ConstantType constantType){		
		constantTypeService.update(constantType);
		 return constantType;
	}
	
	@RequestMapping("/constantType/destroy")
	@ResponseBody
	public ConstantType destroy(@RequestBody ConstantType constantType){		
		constantTypeService.delete(constantType);
		return constantType;
	}

}
