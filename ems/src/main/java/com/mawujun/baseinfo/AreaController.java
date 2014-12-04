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
import com.mawujun.baseinfo.Area;
import com.mawujun.baseinfo.AreaService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/area")
public class AreaController {

	@Resource
	private AreaService areaService;
	
	@Resource
	private PoleService poleService;



//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/area/query.do")
//	@ResponseBody
//	public List<Area> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Area.parent.id, "root".equals(id)?null:id);
//		List<Area> areaes=areaService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Area.class,M.Area.parent.name());
//		return areaes;
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
//	@RequestMapping("/area/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Area.sampleName, "%"+sampleName+"%");
//		return areaService.queryPage(page);
//	}

	@RequestMapping("/area/query.do")
	@ResponseBody
	public List<Area> query() {	
		List<Area> areaes=areaService.queryAllWithWorkunit();
		return areaes;
	}
	

	@RequestMapping("/area/load.do")
	public Area load(String id) {
		return areaService.get(id);
	}
	
	@RequestMapping("/area/create.do")
	@ResponseBody
	public Area create(@RequestBody Area area) {
		areaService.create(area);
		return area;
	}
	
	@RequestMapping("/area/update.do")
	@ResponseBody
	public  Area update(@RequestBody Area area) {
		areaService.update(area);
		return area;
	}
	
	@RequestMapping("/area/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		areaService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/area/destroy.do")
	@ResponseBody
	public Area destroy(@RequestBody Area area) {
		areaService.delete(area);
		return area;
	}
	
	@RequestMapping("/area/queryPoles.do")
	@ResponseBody
	public List<Pole> queryPoles(String area_id) {	
		List<Pole> poles=poleService.query(Cnd.where().andEquals(M.Pole.area_id, area_id).asc(M.Pole.code));
		return poles;
	}
	
	@RequestMapping("/area/savePoles.do")
	@ResponseBody
	public String savePoles(String area_id,String[] pole_ids) {	
		if(pole_ids==null){
			return "sucess";
		}
		poleService.savePoles(area_id, pole_ids);
		return "success";
	}
	
	@RequestMapping("/area/deletePoles.do")
	@ResponseBody
	public String deletePoles(String area_id,String[] pole_ids) {	
		if(pole_ids==null){
			return "sucess";
		}
		poleService.deletePoles(area_id, pole_ids);
		return "success";
	}
	
	@RequestMapping("/area/queryCombo.do")
	@ResponseBody
	public List<Area> queryCombo(String name) {	
		
		return areaService.query(Cnd.select().andLike(M.Area.name, name));	
	}
	
}
