package com.mawujun.install;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.install.Borrow;
import com.mawujun.install.BorrowService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/borrow")
public class BorrowController {

	@Resource
	private BorrowService borrowService;
	@Resource
	private EquipmentService equipmentService;

	/**
	 * 主要用于新品入库的时候
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/borrow/getEquipmentByEcode.do")
	@ResponseBody
	public EquipmentVO getEquipmentByEcode(String ecode,String store_id) {	
		EquipmentVO equipment= equipmentService.getEquipmentByEcode(ecode,store_id);
		if(equipment==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("对不起，该条码对应的设备不存在，或者该设备挂在其他仓库中!");
		}
		//设备返库的时候，设备如果不是手持或损坏状态的话，就不能进行返库，说明任务没有扫描或者没有提交
		if(equipment.getStatus()!=EquipmentStatus.in_storage){
			throw new BusinessException("设备状态不是\"已入库\",不能借用该设备!");
		}
		return equipment;
	}
	
	/**
	 * 设备出库，设备领用
	 * @author mawujun 16064988@qq.com 
	 * @param equipments
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/borrow/equipmentOutStore.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String equipOutStore(@RequestBody Equipment[] equipments, Borrow borrow) { 
		//inStoreService.newInStore(equipments);
		borrowService.borrow(equipments, borrow);
		return "success";
	}
	
	/**
	 * 
	 * @author mawujun 16064988@qq.com 
	 * @param start
	 * @param limit
	 * @param operateDate_start
	 * @param operateDate_end
	 * @param store_id
	 * @param type 是出库单还是入库单
	 * @return
	 */
	@RequestMapping("/borrow/queryMain.do")
	@ResponseBody
	public Page queryMain(Integer start,Integer limit,String operateDate_start,String operateDate_end,String store_id,String workUnit_id,String project_id,String isAllReturn) { 
		Page page=Page.getInstance(start, limit);
		page.addParam("operateDate_start", operateDate_start);
		page.addParam("operateDate_end", operateDate_end);
		page.addParam(M.Borrow.store_id, store_id);
		page.addParam(M.Borrow.workUnit_id, workUnit_id);
		page.addParam(M.Borrow.project_id, project_id);
		page.addParam(M.Borrow.isAllReturn, isAllReturn);
		page=borrowService.queryMain(page);
		return page;
	}
	
	@RequestMapping("/borrow/queryList.do")
	@ResponseBody
	public List<BorrowListVO> queryList(String borrow_id) { 
		if(!StringUtils.hasText(borrow_id)){
			throw new BusinessException("请先选择一条单据!");
		}
		List<BorrowListVO> page=borrowService.queryList(borrow_id);
		return page;
	}
	
	/**
	 * 在借用返还的时候，或旧设备返库的时候，获取设备信息的
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @param workunit_id
	 * @return
	 */
	@RequestMapping("/borrow/getBorrowListVOByEcode.do")
	@ResponseBody
	public BorrowListVO getBorrowListVOByEcode(String ecode,String store_id) {
		BorrowListVO borrowListVO= borrowService.getBorrowEquipmentByEcode(ecode,store_id);

		return borrowListVO;
	}
	
	@RequestMapping("/borrow/borrowReturn.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String borrowReturn(@RequestBody BorrowList borrowlists[],String store_id) { 
		borrowService.borrowReturn(borrowlists,store_id);
		return "success";
	}
	

}
