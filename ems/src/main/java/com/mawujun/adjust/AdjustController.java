package com.mawujun.adjust;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/adjust")
public class AdjustController {

	@Resource
	private AdjustService adjustService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/adjust/query.do")
//	@ResponseBody
//	public List<Adjust> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Adjust.parent.id, "root".equals(id)?null:id);
//		List<Adjust> adjustes=adjustService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Adjust.class,M.Adjust.parent.name());
//		return adjustes;
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
//	@RequestMapping("/adjust/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Adjust.sampleName, "%"+sampleName+"%");
//		return adjustService.queryPage(page);
//	}


	
	/**
	 * 入库仓库查询调拨单,之后进行入库
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/adjust/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String str_out_id,String str_in_id,String str_out_date_start,String str_out_date_end,String status) {	
		Page page=Page.getInstance(start,limit);	
		page.addParam("str_out_id", str_out_id);
		page.addParam("str_in_id", str_in_id);
		page.addParam("str_out_date_start", str_out_date_start);
		page.addParam("str_out_date_end", str_out_date_end);
		page.addParam("status", status);
		
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		return adjustService.queryPage(page);
	}
	
	/**
	 * 调拨单查询的时候，查询明细数据
	 * @author mawujun 16064988@qq.com 
	 * @param adjust_id
	 * @return
	 */
	@RequestMapping("/adjust/queryList.do")
	@ResponseBody
	public List<AdjustListVO> queryList(String adjust_id) {	
		return adjustService.queryList(adjust_id);
	}
	
	
	@RequestMapping("/adjust/getAdjustListVOByEcode.do")
	@ResponseBody
	public AdjustListVO getAdjustListVOByEcode(String ecode,String store_id) {	
		AdjustListVO repairvo= adjustService.getAdjustListVOByEcode(ecode,store_id);
		if(repairvo==null){
			throw new BusinessException("对不起，该条码对应的设备不存在，或者该设备挂在其他仓库中!");
		}

		//repairvo.setStatus(AdjustStatus.edit);
		return repairvo;
	}
	
	@RequestMapping("/adjust/newAdjuest.do")
	@ResponseBody
	public String newAdjuest(Adjust adjust,@RequestBody AdjustList[] adjuestLists) {
		adjustService.newAdjuest(adjust,adjuestLists);
		return "success";
	}
	
	/**
	 * 入库仓库查询调拨单
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/adjust/query4InStr.do")
	@ResponseBody
	public Page query4InStr(Integer start,Integer limit) {	
		Page page=Page.getInstance(start,limit);	
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		return adjustService.query4InStore(page);
	}
	/**
	 * 在调拨入库的时候，获取调拨单明细
	 * @author mawujun 16064988@qq.com 
	 * @param adjust_id
	 * @return
	 */
	@RequestMapping("/adjust/query4InStrList.do")
	@ResponseBody
	public List<AdjustListVO> query4InStrList(String adjust_id) {	
		
		return adjustService.query4InStoreList(adjust_id);
	}
	
	/**
	 * 当按部分入库按钮的时候，并且判断当入库数和实际要入库数一样的时候，就当做事全部入库，走的是就是全部入库的路线了
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/adjust/adjustInStore.do")
	@ResponseBody
	public String adjustInStore(@RequestBody AdjustList[] adjustLists,String adjust_id) {	
		adjustService.adjustInStore(adjustLists,adjust_id);	
		return "success";
	}
	
	/**
	 * 把借用单转换成领用单
	 * @author mawujun 16064988@qq.com 
	 * @param adjust_id
	 * @return
	 */
	@RequestMapping("/adjust/change2installout.do")
	@ResponseBody
	public String change2installout(String adjust_id) {	
		
		 adjustService.change2installout(adjust_id);
		 return "成功";
	}
	
	/**
	 * 调拨单归还，把设备归还给原来的仓库
	 * @author mawujun 16064988@qq.com 
	 * @param adjust
	 * @param adjuestLists
	 * @param adjust_id_borrow 被归还的调拨单id
	 * @return
	 */
	@RequestMapping("/adjust/adjuestReturn.do")
	@ResponseBody
	public String adjuestReturn(Adjust adjust,@RequestBody AdjustList[] adjuestLists,String adjust_id_borrow) {
		adjustService.adjuestReturn(adjust,adjuestLists,adjust_id_borrow);
		return "success";
	}
}
