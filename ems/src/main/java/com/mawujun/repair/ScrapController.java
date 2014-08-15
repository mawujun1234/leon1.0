package com.mawujun.repair;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/scrap")
public class ScrapController {

	@Resource
	private ScrapService scrapService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/scrap/query.do")
//	@ResponseBody
//	public List<Scrap> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Scrap.parent.id, "root".equals(id)?null:id);
//		List<Scrap> scrapes=scrapService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Scrap.class,M.Scrap.parent.name());
//		return scrapes;
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
//	@RequestMapping("/scrap/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Scrap.sampleName, "%"+sampleName+"%");
//		return scrapService.queryPage(page);
//	}

	@RequestMapping("/scrap/query.do")
	@ResponseBody
	public List<Scrap> query() {	
		List<Scrap> scrapes=scrapService.queryAll();
		return scrapes;
	}
	

	@RequestMapping("/scrap/load.do")
	public Scrap load(String id) {
		return scrapService.get(id);
	}
	
	@RequestMapping("/scrap/create.do")
	@ResponseBody
	public Scrap create(@RequestBody Scrap scrap) {
		scrapService.create(scrap);
		return scrap;
	}
	
	@RequestMapping("/scrap/update.do")
	@ResponseBody
	public  Scrap update(@RequestBody Scrap scrap) {
		scrapService.update(scrap);
		return scrap;
	}
	
	@RequestMapping("/scrap/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		scrapService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/scrap/destroy.do")
	@ResponseBody
	public Scrap destroy(@RequestBody Scrap scrap) {
		scrapService.delete(scrap);
		return scrap;
	}
	
	
}
