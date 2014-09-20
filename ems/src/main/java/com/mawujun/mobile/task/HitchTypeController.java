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
import com.mawujun.meta.MetaVersion;
import com.mawujun.meta.MetaVersionService;
import com.mawujun.mobile.task.HitchType;
import com.mawujun.mobile.task.HitchTypeService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/hitchType")
public class HitchTypeController {

	@Resource
	private HitchTypeService hitchTypeService;
	@Resource
	private MetaVersionService metaVersionService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/hitchType/query.do")
//	@ResponseBody
//	public List<HitchType> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.HitchType.parent.id, "root".equals(id)?null:id);
//		List<HitchType> hitchTypees=hitchTypeService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(HitchType.class,M.HitchType.parent.name());
//		return hitchTypees;
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
//	@RequestMapping("/hitchType/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.HitchType.sampleName, "%"+sampleName+"%");
//		return hitchTypeService.queryPage(page);
//	}

	@RequestMapping("/hitchType/query.do")
	@ResponseBody
	public List<HitchType> query() {	
		List<HitchType> hitchTypees=hitchTypeService.queryAll();
		return hitchTypees;
	}
	
	@RequestMapping("/hitchType/mobile/query.do")
	@ResponseBody
	public List<HitchType> mobile_query() {	
		List<HitchType> hitchTypees=hitchTypeService.queryAll();
		
		MetaVersion metaVersion=metaVersionService.get(HitchType.class.getSimpleName());
		JsonConfigHolder.addProperty("version", metaVersion==null?0:metaVersion.getVersion());
		return hitchTypees;
	}
	

	@RequestMapping("/hitchType/load.do")
	public HitchType load(Integer id) {
		return hitchTypeService.get(id);
	}
	
	@RequestMapping("/hitchType/create.do")
	@ResponseBody
	public HitchType create(@RequestBody HitchType hitchType) {
		hitchTypeService.create(hitchType);
		metaVersionService.update(HitchType.class);
		return hitchType;
	}
	
	@RequestMapping("/hitchType/update.do")
	@ResponseBody
	public  HitchType update(@RequestBody HitchType hitchType) {
		hitchTypeService.update(hitchType);
		metaVersionService.update(HitchType.class);
		return hitchType;
	}
	
	@RequestMapping("/hitchType/deleteById.do")
	@ResponseBody
	public Integer deleteById(Integer id) {
		hitchTypeService.deleteById(id);
		metaVersionService.update(HitchType.class);
		return id;
	}
	
//	@RequestMapping("/hitchType/destroy.do")
//	@ResponseBody
//	public HitchType destroy(@RequestBody HitchType hitchType) {
//		hitchTypeService.delete(hitchType);
//		return hitchType;
//	}
	
	
}
