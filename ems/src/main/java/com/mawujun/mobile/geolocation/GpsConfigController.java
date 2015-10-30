package com.mawujun.mobile.geolocation;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/gpsConfig")
public class GpsConfigController {

	@Resource
	private GpsConfigService gpsConfigService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/gpsConfig/query.do")
//	@ResponseBody
//	public List<GpsConfig> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.GpsConfig.parent.id, "root".equals(id)?null:id);
//		List<GpsConfig> gpsConfiges=gpsConfigService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(GpsConfig.class,M.GpsConfig.parent.name());
//		return gpsConfiges;
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
//	@RequestMapping("/gpsConfig/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.GpsConfig.sampleName, "%"+sampleName+"%");
//		return gpsConfigService.queryPage(page);
//	}
//
//	@RequestMapping("/gpsConfig/query.do")
//	@ResponseBody
//	public List<GpsConfig> query() {	
//		List<GpsConfig> gpsConfiges=gpsConfigService.queryAll();
//		return gpsConfiges;
//	}
//	

	int id=1;
	@RequestMapping("/gpsConfig/get.do")
	public GpsConfig get() {
		return gpsConfigService.get();
	}
	
//	@RequestMapping("/gpsConfig/create.do")
//	@ResponseBody
//	public GpsConfig create(@RequestBody GpsConfig gpsConfig) {
//		gpsConfigService.create(gpsConfig);
//		return gpsConfig;
//	}
	
	@RequestMapping("/gpsConfig/update.do")
	@ResponseBody
	public  GpsConfig update(GpsConfig gpsConfig) {
		gpsConfig.setId(id);
		gpsConfigService.update(gpsConfig);
		return gpsConfig;
	}
	
//	@RequestMapping("/gpsConfig/deleteById.do")
//	@ResponseBody
//	public Integer deleteById(Integer id) {
//		gpsConfigService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/gpsConfig/destroy.do")
//	@ResponseBody
//	public GpsConfig destroy(@RequestBody GpsConfig gpsConfig) {
//		gpsConfigService.delete(gpsConfig);
//		return gpsConfig;
//	}
	
	
}
