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
	
	@RequestMapping("/equipment/update.do")
	@ResponseBody
	public  Equipment update(@RequestBody Equipment equipment) {
		equipmentService.update(equipment);
		return equipment;
	}
	
	@RequestMapping("/equipment/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		equipmentService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/equipment/destroy.do")
	@ResponseBody
	public Equipment destroy(@RequestBody Equipment equipment) {
		equipmentService.delete(equipment);
		return equipment;
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
