package com.mawujun.repair;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
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
//@RequestMapping("/repair")
public class RepairController {

	@Resource
	private RepairService repairService;
	//@Resource
	//private EquipmentService equipmentService;


	/**
	 * 搜索仓库中所有坏掉的设备
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @param store_id
	 * @return
	 */
	@RequestMapping("/repair/queryBrokenEquipment.do")
	@ResponseBody
	public List<EquipmentVO> queryBrokenEquipment(String store_id) {
		if(!StringUtils.hasText(store_id)){
			throw new BusinessException("请先选择一个仓库!");
		}
		return repairService.queryBrokenEquipment(store_id);
		
	}
	/**
	 * 仓库中所有损坏的设备一次性全部生成维修单
	 * @author mawujun 16064988@qq.com 
	 * @param store_id
	 * @return
	 */
	@RequestMapping("/repair/brokenEquipment2Repair.do")
	@ResponseBody
	public String brokenEquipment2Repair(String store_id,String rpa_id) {
		if(!StringUtils.hasText(store_id)){
			throw new BusinessException("请先选择一个仓库!");
		}
		if(!StringUtils.hasText(rpa_id)){
			throw new BusinessException("请先选择一个维修中心!");
		}
		repairService.brokenEquipment2Repair(store_id,rpa_id);
		return "success";
	}

	
	@RequestMapping("/repair/update.do")
	@ResponseBody
	public  Repair update(@RequestBody Repair repair) {
//		if("".equals(repair.getRpa_type())){
//			
//		}
		repairService.update(repair);
		return repair;
	}
	
//	@RequestMapping("/repair/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		repairService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/repair/destroy.do")
//	@ResponseBody
//	public Repair destroy(@RequestBody Repair repair) {
//		repairService.delete(repair);
//		return repair;
//	}
	
	
	@RequestMapping("/repair/getRepairVOByEcode.do")
	@ResponseBody
	public RepairVO getRepairVOByEcode(String ecode,String store_id) {	
		RepairVO repairvo= repairService.getRepairVOByEcode(ecode,store_id);
		if(repairvo==null){
			throw new BusinessException("对不起，该条码对应的设备不存在，或者该设备挂在其他仓库中!");
		}
		if(repairvo.getEquipment_status()!=EquipmentStatus.wait_for_repair){
			throw new BusinessException("该设备为非\"入库待维修\"状态,不能添加到列表");
		}
		repairvo.setStatus(RepairStatus.to_repair);
		return repairvo;
	}
	
	
	
	/**
	 * 创建信的维修单
	 * @author mawujun 16064988@qq.com 
	 * @param createBatch
	 * @return
	 */
	@RequestMapping("/repair/newRepair.do")
	@ResponseBody
	public String newRepair(@RequestBody Repair[] repairs) {
		//repairService.createBatch(createBatch);
		repairService.newRepair(repairs);
		return "success";
	}
	
	@RequestMapping("/repair/storeMgrQuery.do")
	@ResponseBody
	public Page storeMgrQuery(Integer start,Integer limit, String str_out_id,String rpa_id,String str_out_date_start,String str_out_date_end
			,String ecode,RepairStatus status,Boolean only_have_scap){
		Page page=Page.getInstance(start,limit);//.addParam(M.Repair.sampleName, "%"+sampleName+"%");
		page.addParam("str_out_id", str_out_id).addParam("rpa_id", rpa_id).addParam("str_out_date_start", str_out_date_start).addParam("str_out_date_end", str_out_date_end)
		.addParam("ecode", ecode).addParam("status", status)
		.addParam("only_have_scap", only_have_scap);
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		//page.addParam("edit", true);
		return repairService.storeMgrQuery(page);
	}
	
	/**
	 * 仓库进行管理的时候进行的查询
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairInQuery.do")
	@ResponseBody
	public Page repairInQuery(Integer start,Integer limit, String str_out_id,String rpa_id,String str_out_date_start,String str_out_date_end
			,String ecode,RepairStatus status){
		Page page=Page.getInstance(start,limit);//.addParam(M.Repair.sampleName, "%"+sampleName+"%");
		page.addParam("str_out_id", str_out_id).addParam("rpa_id", rpa_id).addParam("str_out_date_start", str_out_date_start).addParam("str_out_date_end", str_out_date_end)
		.addParam("ecode", ecode).addParam("status", RepairStatus.to_repair);
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		return repairService.repairInQuery(page);
	}

	
	/**
	 * w维修中心进行管理的时候进行的查询
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairMgrQuery.do")
	@ResponseBody
	public Page repairMgrQuery(Integer start,Integer limit, String str_out_id,String rpa_id,String rpa_in_date_start,String rpa_in_date_end
			,String ecode,RepairStatus status,Boolean only_have_scap){
		Page page=Page.getInstance(start,limit);//.addParam(M.Repair.sampleName, "%"+sampleName+"%");
		page.addParam("str_out_id", str_out_id).addParam("rpa_id", rpa_id).addParam("rpa_in_date_start", rpa_in_date_start).addParam("rpa_in_date_end", rpa_in_date_end)
		.addParam("ecode", ecode).addParam("status", status)
		.addParam("only_have_scap", only_have_scap);
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		
		//JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return repairService.repairMgrQuery(page);
	}
	
	/**
	 * 当维修中心入库的时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @param store_id
	 * @return
	 */
	@RequestMapping("/repair/getRepairVOByEcodeAtTo_repair.do")
	@ResponseBody
	public RepairVO getRepairVOByEcodeAtTo_repair(String ecode,String rpa_id) {	
		RepairVO repairvo= repairService.getRepairVOByEcodeStatus(ecode,RepairStatus.to_repair);
		
		if(repairvo==null){
			throw new BusinessException("该设备状态不对或者该设备不是维修设备!");
		}
		if(!repairvo.getRpa_id().equals(rpa_id)){
			throw new BusinessException("该设备入库能入库这个维修中心!");
		}
		return repairvo;
	}
	/**
	 * 维修中心入库
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairInStore.do")
	@ResponseBody
	public String repairInStore(@RequestBody Repair[] repairs){
		repairService.repairInStore(repairs);
		return "success";
	}
	
	/**
	 * 维修中心出库
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/repairOutStore.do")
	@ResponseBody
	public String repairOutStore(@RequestBody Repair[] repairs){
		for(Repair repair:repairs){
			//如果是外修，检查回收字段是否已经填好了
			if(repair.getRpa_type()==RepairType.outrpa){
				if(repair.getReceive_date()==null){
					throw new BusinessException(repair.getId()+"单据的'收到日期'没有填写!");
				}
			}
		}
		repairService.repairOutStore(repairs);
		return "success";
	}
	
	/**
	 * 当维修好后，仓库入库的时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @param store_id
	 * @return
	 */
	@RequestMapping("/repair/getRepairVOByEcodeAtBack_store.do")
	@ResponseBody
	public RepairVO getRepairVOByEcodeAtBack_store(String ecode,String store_id) {	
		RepairVO repairvo= repairService.getRepairVOByEcodeStatus(ecode,RepairStatus.back_store);
		
		if(repairvo==null){
			throw new BusinessException("该设备还没维修好或者该设备不是维修设备!");
		}
		if(!repairvo.getStr_in_id().equals(store_id)){
			throw new BusinessException("该设备入库能入库这个仓库，不是从这里出去的!");
		}
		return repairvo;
	}
	
	/**
	 * 维修完返库，仓库入库
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/repair/storeInStore.do")
	@ResponseBody
	public String storeInStore(@RequestBody Repair[] repairs){
//		for(Repair repair:repairs){
//			if(str_in_id==null || !str_in_id.equalsIgnoreCase(repair.getStr_in_id())){
//				throw new BusinessException("产品要发往的仓库和要入库的仓库部一致!");
//			}
//		}
		repairService.storeInStore(repairs);
		return "success";
	}
	

	SimpleDateFormat yMdHms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	/**
//	 * 已完成（自修）的维修单明细和已完成（外修）维修单明细
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param date_start
//	 * @param date_end
//	 * @return
//	 */
//	@RequestMapping("/repair/queryCompleteRepairReport.do")
//	@ResponseBody
//	public Page queryCompleteRepairReport(Integer start,Integer limit,String date_start,String date_end){
//		Page page=Page.getInstance(start,limit);
//		page.addParam("date_start", date_start);
//		page.addParam("date_end", date_end);
//
//		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
//		Page result=repairService.queryCompleteRepairReport(page);
//		return result;
//	}
//	
//	@RequestMapping("/repair/exportCompleteRepairReport.do")
//	@ResponseBody
//	public void exportCompleteRepairReport(HttpServletResponse response,String date_start,String date_end) throws IOException{
//
//		Params params=Params.init().add("date_start", date_start).add("date_end", date_end);
//
//		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
//		List<RepairVO> result=repairService.exportRepairReport(params);
//		
//		
//		XSSFWorkbook wb =new XSSFWorkbook();
//		Sheet sheet = wb.createSheet();
//		int rownum=0;
//		
//		build_addColumnName_complete(wb,sheet,rownum);
//		
//		// 开始构建整个excel的文件
//		if (result != null && result.size() > 0) {
//			rownum++;
//			build_content_complete(result, wb, sheet, rownum);
//		}
//		String filename = "已完成维修明细报表.xlsx";
//		 //FileOutputStream out = new FileOutputStream(filename);
//		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
//		//response.setContentType("application/vnd.ms-excel;charset=uft-8");
//		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");
//
//		OutputStream out = response.getOutputStream();
//		wb.write(out);
//		
//		out.flush();
//		out.close();
//	}
//	
//	private void build_addColumnName_complete(XSSFWorkbook wb,Sheet sheet,int rowInt){
//		//CellStyle black_style=getStyle(wb,IndexedColors.BLACK,(short)11);
//		 
//		Row row = sheet.createRow(rowInt);
//		int cellnum=0;
//		
//		Cell id=row.createCell(cellnum++);
//		id.setCellValue("维修单号");
//	
//		Cell ecode=row.createCell(cellnum++);
//		ecode.setCellValue("条码");
//		
//		Cell prod_name=row.createCell(cellnum++);
//		prod_name.setCellValue("品名");
//		
//		Cell equipment_style=row.createCell(cellnum++);
//		equipment_style.setCellValue("型号");
//		
//		Cell str_out_name=row.createCell(cellnum++);
//		str_out_name.setCellValue("发货仓库");
//		
//		Cell rpa_type_name=row.createCell(cellnum++);
//		rpa_type_name.setCellValue("维修类型");
//		
//		Cell str_out_date=row.createCell(cellnum++);
//		str_out_date.setCellValue("开单日期");
//		
//		Cell str_in_date=row.createCell(cellnum++);
//		str_in_date.setCellValue("结单日期");
//		
//		Cell rpa_user_name=row.createCell(cellnum++);
//		rpa_user_name.setCellValue("维修人员");
//		
//		Cell rpa_in_date=row.createCell(cellnum++);
//		rpa_in_date.setCellValue("入库时间");
//		
//	}
//
//	private void build_content_complete(List<RepairVO> list,XSSFWorkbook wb,Sheet sheet,int rownum){
//		int cellnum=0;
//
//		for(RepairVO repairVO:list){
//			cellnum=0;
//			Row row = sheet.createRow(rownum++);
//			
//			Cell id=row.createCell(cellnum++);
//			id.setCellValue(repairVO.getId());
//
//			Cell ecode=row.createCell(cellnum++);
//			ecode.setCellValue(repairVO.getEcode());
//			
//			Cell prod_name=row.createCell(cellnum++);
//			prod_name.setCellValue(repairVO.getProd_name());
//			
//			Cell equipment_style=row.createCell(cellnum++);
//			equipment_style.setCellValue(repairVO.getEquipment_style());
//			
//			
//			Cell str_out_name=row.createCell(cellnum++);
//			str_out_name.setCellValue(repairVO.getStr_out_name());
//			
//			Cell rpa_type_name=row.createCell(cellnum++);
//			rpa_type_name.setCellValue(repairVO.getRpa_type_name());
//			
//			Cell str_out_date=row.createCell(cellnum++);
//			str_out_date.setCellValue(repairVO.getStr_out_date());
//			
//			Cell rpa_out_date=row.createCell(cellnum++);
//			rpa_out_date.setCellValue(repairVO.getRpa_out_date()!=null?yMdHms.format(repairVO.getRpa_out_date()):"");
//			
//			Cell rpa_user_name=row.createCell(cellnum++);
//			rpa_user_name.setCellValue(repairVO.getRpa_user_name());
//			
//			Cell str_in_date=row.createCell(cellnum++);
//			str_in_date.setCellValue(repairVO.getStr_in_date()!=null?yMdHms.format(repairVO.getStr_in_date()):"");
//
//			
//			
//			
////			Cell broken_memo=row.createCell(cellnum++);
////			broken_memo.setCellValue(repairVO.getBroken_memo());
////			
////			Cell broken_reson=row.createCell(cellnum++);
////			broken_reson.setCellValue(repairVO.getBroken_reson());
////			
////			Cell handler_method=row.createCell(cellnum++);
////			handler_method.setCellValue(repairVO.getHandler_method());
////			
////			Cell rpa_out_date=row.createCell(cellnum++);
////			rpa_out_date.setCellValue(repairVO.getRpa_out_date()!=null?yMdHms.format(repairVO.getRpa_out_date()):"");
////			
////			Cell str_in_date=row.createCell(cellnum++);
////			str_in_date.setCellValue(repairVO.getStr_in_date()!=null?yMdHms.format(repairVO.getStr_in_date()):"");
////			
////			Cell memo=row.createCell(cellnum++);
////			memo.setCellValue(repairVO.getMemo());
//		}
//	}
	
}
