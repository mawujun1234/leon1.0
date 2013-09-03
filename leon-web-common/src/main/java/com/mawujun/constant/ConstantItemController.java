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
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.repository.idEntity.AutoIdEntity;
import com.mawujun.repository.idEntity.UUIDEntity;
import com.mawujun.utils.page.WhereInfo;

/**
 * 
 * @author mawujun email:mawujun1234@163.com qq:16064988
 *
 */
@Controller
@RequestMapping("/app")
public class ConstantItemController {

	@Autowired
	private ConstantItemService constantItemService;


	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/constantItem/query")
	@ResponseBody
	public List<ConstantItem> query(String constan_id){	
		//WhereInfo whereinfo=WhereInfo.parse("constant.id", constan_id);
		return constantItemService.query(Cnd.select().andEquals("constant.id", constan_id));
	}
	@RequestMapping("/constantItem/queryByCode")
	@ResponseBody
	public List<ConstantItem> queryByCode(String code){	
		//WhereInfo whereinfo=WhereInfo.parse("constant.code", code);
		//WhereInfo whereinfo1=WhereInfo.parse("constant.id", "402881e53f0f187d013f0f1f2bd00000");
		List<ConstantItem> list=constantItemService.query(Cnd.select().andEquals("constant.code", code));
		//这样写是为了解决 fastjson生成jsonpath的问题
		for(ConstantItem item:list){
			item.setConstant(null);
		}
		return list;
	}
	@RequestMapping("/constantItem/load")
	@ResponseBody
	public ConstantItem load(String id){		
		return constantItemService.get(id);
	}
	
	@RequestMapping("/constantItem/create")
	@ResponseBody
	public ConstantItem[] create(@RequestBody ConstantItem[] constantItem){	
		constantItemService.createBatch(constantItem);
		//constantItemServic.(constantItem);
		return constantItem;
	}
	
	@RequestMapping("/constantItem/update")
	@ResponseBody
	public ConstantItem[] update(@RequestBody ConstantItem[] constantItem){		
		constantItemService.updateBatch(constantItem);
		return constantItem;
	}
	
	@RequestMapping("/constantItem/destroy")
	@ResponseBody
	public ConstantItem[] destroy(@RequestBody ConstantItem[] constantItem){		
		constantItemService.deleteBatch(constantItem);
		return constantItem;
	}
	
}
