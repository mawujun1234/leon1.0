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
public class ConstantController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ConstantService constantService;


	/**
	 * 一次性读取出所有的节点数据
	 * @return
	 */
	@RequestMapping("/constant/query")
	@ResponseBody
	public List<Constant> query(String constanType_id){		
		WhereInfo whereinfo=WhereInfo.parse("constantType.id", constanType_id);
		return constantService.query(whereinfo);
	}
	@RequestMapping("/constant/load")
	@ResponseBody
	public Constant load(String id){		
		return constantService.get(id);
	}
	
	@RequestMapping("/constant/create")
	@ResponseBody
	public Constant create(@RequestBody Constant constant){		
		constantService.create(constant);
		return constant;
	}
	
	@RequestMapping("/constant/update")
	@ResponseBody
	public Constant update(@RequestBody Constant constant){		
		constantService.createOrUpdate(constant);
		 return constant;
	}
	
	@RequestMapping("/constant/destroy")
	@ResponseBody
	public Constant destroy(@RequestBody Constant constant){		
		constantService.delete(constant);
		return constant;
	}
	
}
