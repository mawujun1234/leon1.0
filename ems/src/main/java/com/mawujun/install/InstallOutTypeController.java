package com.mawujun.install;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;

import com.mawujun.install.InstallOutType;
import com.mawujun.install.InstallOutTypeService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/installOutType")
public class InstallOutTypeController {

	@Resource
	private InstallOutTypeService installOutTypeService;

//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/installOutType/query.do")
//	@ResponseBody
//	public List<InstallOutType> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.InstallOutType.parent.id, "root".equals(id)?null:id);
//		List<InstallOutType> installOutTypees=installOutTypeService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(InstallOutType.class,M.InstallOutType.parent.name());
//		return installOutTypees;
//	}
//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/installOutType/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.InstallOutType.sampleName, "%"+sampleName+"%");
//		return installOutTypeService.queryPage(page);
//	}

	@RequestMapping("/installOutType/query.do")
	@ResponseBody
	public List<InstallOutType> query() {	
		List<InstallOutType> installOutTypees=installOutTypeService.queryAll();
		return installOutTypees;
	}
	

	@RequestMapping("/installOutType/load.do")
	public InstallOutType load(String id) {
		return installOutTypeService.get(id);
	}
	
	@RequestMapping("/installOutType/create.do")
	@ResponseBody
	public InstallOutType create(@RequestBody InstallOutType installOutType) {
		installOutTypeService.create(installOutType);
		return installOutType;
	}
	
	@RequestMapping("/installOutType/update.do")
	@ResponseBody
	public  InstallOutType update(@RequestBody InstallOutType installOutType) {
		installOutTypeService.update(installOutType);
		return installOutType;
	}
	
//	@RequestMapping("/installOutType/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		installOutTypeService.deleteById(id);
//		return id;
//	}
	
	@RequestMapping("/installOutType/destroy.do")
	@ResponseBody
	public InstallOutType destroy(@RequestBody InstallOutType installOutType) {
		installOutTypeService.delete(installOutType);
		return installOutType;
	}
	
	
}
