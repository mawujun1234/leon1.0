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
public class ConstantItemController {

	@Autowired
	private ConstantItemService constantItemService;


	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/constantItem/queryAll")
	@ResponseBody
	public List<ConstantItem> queryAll(){		
		return constantItemService.queryAll();
	}
	@RequestMapping("/constantItem/get")
	@ResponseBody
	public ConstantItem get(String id){		
		return constantItemService.get(id);
	}
	
	@RequestMapping("/constantItem/create")
	@ResponseBody
	public ConstantItem create(@RequestBody ConstantItem constantItem){		
		constantItemService.create(constantItem);
		return constantItem;
	}
	
	@RequestMapping("/constantItem/update")
	@ResponseBody
	public ConstantItem update(@RequestBody ConstantItem constantItem){		
		constantItemService.update(constantItem);
		 return constantItem;
	}
	
	@RequestMapping("/constantItem/destroy")
	@ResponseBody
	public ConstantItem destroy(@RequestBody ConstantItem constantItem){		
		constantItemService.delete(constantItem);
		return constantItem;
	}
	
}
