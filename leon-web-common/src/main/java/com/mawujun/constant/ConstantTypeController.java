package com.mawujun.constant;

import java.util.List;
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


	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/constantType/queryAll")
	@ResponseBody
	public List<ConstantType> queryAll(String id){		
		return constantTypeService.queryAll();
	}
	@RequestMapping("/constantType/get")
	@ResponseBody
	public ConstantType get(String id){		
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
