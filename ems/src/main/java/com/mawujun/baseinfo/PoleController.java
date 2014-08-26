package com.mawujun.baseinfo;
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

import com.mawujun.baseinfo.Pole;
import com.mawujun.baseinfo.PoleService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/pole")
public class PoleController {

	@Resource
	private PoleService poleService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/pole/query.do")
//	@ResponseBody
//	public List<Pole> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Pole.parent.id, "root".equals(id)?null:id);
//		List<Pole> polees=poleService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Pole.class,M.Pole.parent.name());
//		return polees;
//	}

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/pole/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String sampleName,String customer_id,Boolean filterContainArea){
		Page page=Page.getInstance(start,limit).addParam(M.Pole.customer_id, customer_id);//.addParam(M.Pole.sampleName, "%"+sampleName+"%");
		if(filterContainArea!=null && filterContainArea==true){
			//filterContainArea=true表示过滤掉已经选择了片区的杆位,主要用在为area选择杆位的时候
			page.addParam("filterContainArea", true);
			
		}
		return poleService.queryPage(page);
	}

//	@RequestMapping("/pole/query.do")
//	@ResponseBody
//	public List<Pole> query() {	
//		List<Pole> polees=poleService.queryAll();
//		return polees;
//	}
	

	@RequestMapping("/pole/load.do")
	public Pole load(String id) {
		return poleService.get(id);
	}
	
	@RequestMapping("/pole/create.do")
	@ResponseBody
	public Pole create(@RequestBody Pole pole) {
		pole.setStatus(PoleStatus.uninstall);
		poleService.create(pole);
		return pole;
	}
	
	@RequestMapping("/pole/update.do")
	@ResponseBody
	public  Pole update(@RequestBody Pole pole) {
		poleService.update(pole);
		return pole;
	}
	
	@RequestMapping("/pole/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		poleService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/pole/destroy.do")
	@ResponseBody
	public Pole destroy(Pole pole) {
		poleService.delete(pole);
		return pole;
	}
	
	
}
