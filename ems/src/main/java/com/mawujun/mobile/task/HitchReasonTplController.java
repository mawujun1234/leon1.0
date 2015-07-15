package com.mawujun.mobile.task;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mawujun.mobile.task.HitchReasonTpl;
import com.mawujun.mobile.task.HitchReasonTplService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/hitchReasonTpl")
public class HitchReasonTplController {

	@Resource
	private HitchReasonTplService hitchReasonTplService;
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
//	@RequestMapping("/hitchReasonTpl/query.do")
//	@ResponseBody
//	public List<HitchReasonTpl> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.HitchReasonTpl.parent.id, "root".equals(id)?null:id);
//		List<HitchReasonTpl> hitchReasonTples=hitchReasonTplService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(HitchReasonTpl.class,M.HitchReasonTpl.parent.name());
//		return hitchReasonTples;
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
//	@RequestMapping("/hitchReasonTpl/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.HitchReasonTpl.sampleName, "%"+sampleName+"%");
//		return hitchReasonTplService.queryPage(page);
//	}

	@RequestMapping("/hitchReasonTpl/query.do")
	@ResponseBody
	public List<HitchReasonTpl> query(String hitchType_id) {	
//		List<HitchReasonTpl> hitchReasonTples=hitchReasonTplService.queryAll();
//		return hitchReasonTples;
		return hitchReasonTplService.query(Cnd.select().andEquals(M.HitchReasonTpl.hitchType_id, hitchType_id));
		
	}
	
	@RequestMapping("/hitchReasonTpl/mobile/query.do")
	@ResponseBody
	public List<HitchReasonTpl> mobile_query(Integer version,String hitchType_id) {	
		//List<HitchReasonTpl> hitchReasonTples=hitchReasonTplService.queryAll();
		List<HitchReasonTpl> hitchReasonTples=hitchReasonTplService.query(Cnd.select().andEquals(M.HitchReasonTpl.hitchType_id, hitchType_id));
		//MetaVersion metaVersion=metaVersionService.get(HitchReasonTpl.class.getSimpleName());
		//JsonConfigHolder.addProperty("version", metaVersion==null?0:metaVersion.getVersion());
		return hitchReasonTples;
	}
	
	/**
	 * 同时更新故障类型和原因模板
	 * @author mawujun 16064988@qq.com 
	 * @param version
	 * @return
	 */
	@RequestMapping("/hitchReasonTpl/mobile/queryAll.do")
	@ResponseBody
	public Map<String,Object> mobile_queryAll(Integer hitchType_version,Integer hitchReasonTpl_version) {
		Map<String,Object> map=new HashMap<String,Object>();
		
		MetaVersion metaVersion=metaVersionService.get(HitchType.class.getSimpleName());
		if(metaVersion!=null && metaVersion.getVersion()!=hitchType_version){
			List<HitchType> hitchTypes=hitchTypeService.queryAll();
			map.put("hitchTypes", hitchTypes);
			map.put("hitchType_version", metaVersion.getVersion());
		}
		
//		metaVersion=metaVersionService.get(HitchReasonTpl.class.getSimpleName());
//		if(metaVersion!=null && metaVersion.getVersion()!=hitchReasonTpl_version){
//			List<HitchReasonTpl> hitchReasonTples=hitchReasonTplService.queryAll();
//			map.put("hitchReasonTpls", hitchReasonTples);
//			map.put("hitchReasonTpl_version", metaVersion.getVersion());
//		}	
		
		return map;
	}
	

	@RequestMapping("/hitchReasonTpl/load.do")
	public HitchReasonTpl load(Integer id) {
		return hitchReasonTplService.get(id);
	}
	
	@RequestMapping("/hitchReasonTpl/create.do")
	@ResponseBody
	public HitchReasonTpl create(@RequestBody HitchReasonTpl hitchReasonTpl) {
		hitchReasonTplService.create(hitchReasonTpl);
		metaVersionService.update(HitchReasonTpl.class);
		return hitchReasonTpl;
	}
	
	@RequestMapping("/hitchReasonTpl/update.do")
	@ResponseBody
	public  HitchReasonTpl update(@RequestBody HitchReasonTpl hitchReasonTpl) {
		hitchReasonTplService.update(hitchReasonTpl);
		metaVersionService.update(HitchReasonTpl.class);
		return hitchReasonTpl;
	}
	
	@RequestMapping("/hitchReasonTpl/deleteById.do")
	@ResponseBody
	public Integer deleteById(Integer id) {
		hitchReasonTplService.deleteById(id);
		metaVersionService.update(HitchReasonTpl.class);
		return id;
	}
	
//	@RequestMapping("/hitchReasonTpl/destroy.do")
//	@ResponseBody
//	public HitchReasonTpl destroy(@RequestBody HitchReasonTpl hitchReasonTpl) {
//		hitchReasonTplService.delete(hitchReasonTpl);
//		return hitchReasonTpl;
//	}
	
	
}
