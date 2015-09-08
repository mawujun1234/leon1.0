package com.mawujun.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.PoleRepository;
import com.mawujun.utils.page.Page;

@Controller
public class MapController {
	@Autowired
	private MapService mapService;
	@Autowired
	private PoleRepository poleRepository;
	/**
	 * 初始化所有不存在经纬度的点位
	 * @author mawujun email:160649888@163.com qq:16064988
	 */
	@RequestMapping("/map/initAllPoleNoLngLat.do")
	@ResponseBody
	public String initAllPoleNoLngLat(){
		String result=mapService.initAllPoleNoLngLat();
		//return "".equals(result)?"nodata":result;
		return result;
	}
	
	
	/**
	 * 查询某个客户下的下的杆位
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param area_id
	 * @return
	 */
	@RequestMapping("/map/queryPoles.do")
	@ResponseBody
	public Page queryPoles(Integer start,Integer limit,String customer_2_id,String customer_0or1_id,String workunit_id,Boolean queryNoLngLatPole) {	
		Page page=Page.getInstance(start,limit);
		//查询所有没有设置过经纬度的数据
		if(queryNoLngLatPole==true){	
			return poleRepository.queryNoLngLatPole(page);
		}
		
		page.addParam("customer_2_id", customer_2_id);
		page.addParam("customer_0or1_id", customer_0or1_id);
		page.addParam("workunit_id", workunit_id);
		page.addParam("novalue", "novalue");
		
		return poleRepository.queryPoles4Map(page);
		
//		List<Pole> poles=poleService.query(Cnd.where().andEquals(M.Pole.area_id, area_id).asc(M.Pole.code));
//		return poles;
	}
//	/**
//	 * 查询所有还未设置了经纬度的点位
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param pole_id
//	 */
//	@RequestMapping("/map/initAllPoleNoLngLat.do")
//	public Page queryNoLngLatPole(Integer start,Integer limit){
//		Page page=Page.getInstance(start,limit);
//		
//		return poleRepository.queryNoLngLatPole(page);
//	}

}
