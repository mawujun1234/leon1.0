package com.mawujun.baseinfo;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/equipment")
public class EquipmentController {

	@Resource
	private EquipmentService equipmentService;
	


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/equipment/query.do")
//	@ResponseBody
//	public List<Equipment> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Equipment.parent.id, "root".equals(id)?null:id);
//		List<Equipment> equipmentes=equipmentService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Equipment.class,M.Equipment.parent.name());
//		return equipmentes;
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
//	@RequestMapping("/equipment/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Equipment.sampleName, "%"+sampleName+"%");
//		return equipmentService.queryPage(page);
//	}

	@RequestMapping("/equipment/query.do")
	@ResponseBody
	public List<Equipment> query() {	
		List<Equipment> equipmentes=equipmentService.queryAll();
		return equipmentes;
	}
	

	@RequestMapping("/equipment/load.do")
	public Equipment load(String id) {
		return equipmentService.get(id);
	}
	
	@RequestMapping("/equipment/create.do")
	@ResponseBody
	public Equipment create(@RequestBody Equipment equipment) {
		equipmentService.create(equipment);
		return equipment;
	}
	
//	@RequestMapping("/equipment/update.do")
//	@ResponseBody
//	public  Equipment update(@RequestBody Equipment equipment) {
//		equipmentService.update(equipment);
//		return equipment;
//	}
//	
//	@RequestMapping("/equipment/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		equipmentService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/equipment/destroy.do")
//	@ResponseBody
//	public Equipment destroy(@RequestBody Equipment equipment) {
//		equipmentService.delete(equipment);
//		return equipment;
//	}
	
	/**主要用于当某个设备入库的时候，本来是坏件但是最后不小心好件入库的时候，用来手工修改为坏件的
	 * 
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipment
	 * @return
	 */
	@RequestMapping("/equipment/wait_for_repair.do")
	@ResponseBody
	public String wait_for_repair(String ecode,String reason) {
//		Equipment equip=equipmentService.get(ecode);
//		if(equip.getStatus()== EquipmentStatus.wait_for_repair){
//			//return "已经是损坏设备，不需要重复设置为损坏设备!";
//			throw new BusinessException("已经是损坏设备，不需要重复设置为损坏设备!");
//		} else if(equip.getStatus() != EquipmentStatus.in_storage){
//			throw new BusinessException("设备不在仓库，不能修改为待维修状态!");
//		} else {
//
//			
//			EquipmentStoreVO equipmentStoreVO=equipmentService.getEquipmentStoreVO(ecode);
//			if(equipmentStoreVO==null){
//				throw new BusinessException("设备不在仓库中，请注意!");
//			}
//			equip.setStatus(EquipmentStatus.wait_for_repair);
//			equipmentService.update(equip);
//			EquipmentCycle equipmentCycle=equipmentCycleService.logEquipmentCycle(ecode, OperateType.manual_wait_for_repair, ShiroUtils.getLoginName(), TargetType.store,equipmentStoreVO.getStore_id());
//			equipmentCycle.setMemo(reason);
//			equipmentCycleService.update(equipmentCycle);
//		}
		equipmentService.wait_for_repair(ecode, reason);
		return "success";
	}
	
	/**
	 * 主要用来手工修改设备从新件变成老件，例如被借去非作业单位使用的时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipment
	 * @return
	 */
	@RequestMapping("/equipment/to_old.do")
	@ResponseBody
	public String to_old(String ecode,String reason) {
//		Equipment equip=equipmentService.get(ecode);
//		if(equip.getIsnew()==false){
//			throw new BusinessException("已经是旧设备，不需要重复设置为旧设备!");
//		} else {
//			equip.setIsnew(false);
//			equipmentService.update(equip);
//			
//			EquipmentWorkunitVO aa=equipmentService.getEquipmentWorkunitVO(ecode);
//			EquipmentStoreVO bb=null;
//			EquipmentPoleVO cc=null;
//			EquipmentRepairVO dd=null;
//			
//			TargetType targetType=TargetType.workunit;
//			String targetType_id=null;
//			if(aa==null){
//				bb=equipmentService.getEquipmentStoreVO(ecode);
//
//				if(bb!=null){
//					targetType=TargetType.store;
//					targetType_id=bb.getStore_id();
//					
//				} else {
//					cc=equipmentService.getEquipmentPoleVO(ecode);
//					
//					if(cc!=null){
//						targetType=TargetType.pole;	
//						targetType_id=cc.getPole_id();
//						
//					} else {
//						dd=equipmentService.getEquipmentRepairVO(ecode);
//						
//						if(dd!=null){
//							targetType=TargetType.repair;
//							targetType_id=dd.getRepair_id();		
//						} else {
//							throw new BusinessException("该设备不在仓库，点位，作业单位和维修中心,不能进行手工设旧处理!");
//						}
//					}
//				}
//			} else {
//				targetType_id=aa.getWorkunit_id();
//				targetType=TargetType.workunit;
//			}
//			
//			
//			
//			
//			EquipmentCycle equipmentCycle=equipmentCycleService.logEquipmentCycle(ecode, OperateType.manual_to_old, ShiroUtils.getLoginName(), targetType,targetType_id);
//			equipmentCycle.setMemo(reason);
//			equipmentCycleService.update(equipmentCycle);
//		}
//		//equipmentService.update(Cnd.update().set(M.Equipment.isnew, false).andEquals(M.Equipment.ecode, ecode));
		equipmentService.to_old(ecode, reason);
		return "success";
	}
	
	/**
	 * 导出条码进行重打
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/equipment/exportEcode.do")
	@ResponseBody
	public void exportEcode(HttpServletResponse response,String ecode) throws IOException {
		EquipmentVO equipmentVO=equipmentService.getEquipmentInfo(ecode);
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		
		Row hssfRow0 = sheet.createRow(0);
    	Cell cell00 = hssfRow0.createCell(0);
    	cell00.setCellValue("条码");
    	Cell cell011 = hssfRow0.createCell(1);
    	cell011.setCellValue("型号");
    	Cell cell012= hssfRow0.createCell(2);
    	cell012.setCellValue("品牌");
    	Cell cell013 = hssfRow0.createCell(3);
    	cell013.setCellValue("供应商");
    	Cell cell014 = hssfRow0.createCell(4);
    	cell014.setCellValue("小类");
    	Cell cell015 = hssfRow0.createCell(5);
    	cell015.setCellValue("品名");
    	
//        for(int i=1;i<=results.size();i++){
//        	BarcodeVO barcodeVO=results.get(i-1);
        	
    	Row hssfRow = sheet.createRow(1);
        	Cell cell0 = hssfRow.createCell(0);
        	cell0.setCellValue(equipmentVO.getEcode());
        	Cell cell1 = hssfRow.createCell(1);
        	cell1.setCellValue(equipmentVO.getStyle());
        	
        	Cell cell2 = hssfRow.createCell(2);
        	cell2.setCellValue(equipmentVO.getBrand_name());
        	Cell cell3 = hssfRow.createCell(3);
        	cell3.setCellValue(equipmentVO.getSupplier_name());
        	Cell cell4 = hssfRow.createCell(4);
        	cell4.setCellValue(equipmentVO.getSubtype_name());
        	Cell cell5 = hssfRow.createCell(5);
        	cell5.setCellValue(equipmentVO.getProd_name());
        	 
//        }
		
		String filename = "条码重打.xlsx";

		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		//response.setContentType("application/vnd.ms-excel;charset=uft-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
	}
	
	
}
