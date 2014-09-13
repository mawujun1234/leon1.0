package com.mawujun.mobile.task;
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

import com.mawujun.mobile.task.Overtime;
import com.mawujun.mobile.task.OvertimeService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/overtime")
public class OvertimeController {

	@Resource
	private OvertimeService overtimeService;

//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/overtime/query.do")
//	@ResponseBody
//	public List<Overtime> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Overtime.parent.id, "root".equals(id)?null:id);
//		List<Overtime> overtimees=overtimeService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Overtime.class,M.Overtime.parent.name());
//		return overtimees;
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
//	@RequestMapping("/overtime/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Overtime.sampleName, "%"+sampleName+"%");
//		return overtimeService.queryPage(page);
//	}
//
//	@RequestMapping("/overtime/query.do")
//	@ResponseBody
//	public List<Overtime> query() {	
//		List<Overtime> overtimees=overtimeService.queryAll();
//		return overtimees;
//	}
	

	@RequestMapping("/overtime/load.do")
	public Overtime load(String id) {
		return overtimeService.get(id);
	}
	
	
	
	@RequestMapping("/overtime/update.do")
	@ResponseBody
	public  Overtime update(Overtime overtime) {
		overtimeService.update(overtime);
		return overtime;
	}
	
//	@RequestMapping("/overtime/create.do")
//	@ResponseBody
//	public Overtime create(@RequestBody Overtime overtime) {
//		overtimeService.create(overtime);
//		return overtime;
//	}
//	@RequestMapping("/overtime/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		overtimeService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/overtime/destroy.do")
//	@ResponseBody
//	public Overtime destroy(@RequestBody Overtime overtime) {
//		overtimeService.delete(overtime);
//		return overtime;
//	}
//	
	
}
