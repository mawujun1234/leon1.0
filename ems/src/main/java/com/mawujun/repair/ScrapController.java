package com.mawujun.repair;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/scrap")
public class ScrapController {

	@Resource
	private ScrapService scrapService;
	

//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/scrap/query.do")
//	@ResponseBody
//	public List<Scrap> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Scrap.parent.id, "root".equals(id)?null:id);
//		List<Scrap> scrapes=scrapService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Scrap.class,M.Scrap.parent.name());
//		return scrapes;
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
//	@RequestMapping("/scrap/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Scrap.sampleName, "%"+sampleName+"%");
//		return scrapService.queryPage(page);
//	}

//	@RequestMapping("/scrap/query.do")
//	@ResponseBody
//	public List<Scrap> query() {	
//		List<Scrap> scrapes=scrapService.queryAll();
//		return scrapes;
//	}
//	
//
//	@RequestMapping("/scrap/load.do")
//	public Scrap load(String id) {
//		return scrapService.get(id);
//	}
//	
	@RequestMapping("/scrap/create.do")
	@ResponseBody
	public Scrap create(@RequestBody Scrap scrap) {
		
		scrapService.create(scrap);
		return scrap;
	}
	
	@RequestMapping("/scrap/update.do")
	@ResponseBody
	public  Scrap update(@RequestBody Scrap scrap) {
		scrapService.update(scrap);
		return scrap;
	}
//	
//	@RequestMapping("/scrap/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		scrapService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/scrap/destroy.do")
//	@ResponseBody
//	public Scrap destroy(@RequestBody Scrap scrap) {
//		scrapService.delete(scrap);
//		return scrap;
//	}
	
	@RequestMapping("/scrap/loadByRepair_id.do")
	public Scrap loadByRepair_id(String repair_id) {
		Scrap scrap= scrapService.queryUnique(Cnd.select().andEquals(M.Scrap.repair_id, repair_id));
		if(scrap==null){
			//throw new BusinessException("当前维修单还没有创建报废单");
			JsonConfigHolder.setSuccessValue(false);
			JsonConfigHolder.setMsg("当前维修单还没有创建报废单");
		}
		return scrap;
	}
	/**
	 * 维修中心确认报废单，开始走报废流程
	 * @author mawujun 16064988@qq.com 
	 * @param scrap
	 * @return
	 */
	@RequestMapping("/scrap/scrap.do")
	public Scrap scrap(Scrap scrap) {
		return scrapService.scrap(scrap);
	}
	
	/**
	 * 仓管确认报废单，结束报废流程和维修流程
	 * @author mawujun 16064988@qq.com 
	 * @param scrap
	 * @return
	 */
	@RequestMapping("/scrap/makeSureScrap.do")
	public Scrap makeSureScrap(Scrap scrap) {
		return scrapService.makeSureScrap(scrap.getId());
	}
	
	
	@RequestMapping("/scrap/queryScrapReport.do")
	@ResponseBody
	public Page queryScrapReport(Integer start,Integer limit,String date_start,String date_end){
		Page page=Page.getInstance(start,limit);
		page.addParam("date_start", date_start);
		page.addParam("date_end", date_end);

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		Page result=scrapService.queryScrapReport(page);
		return result;
	}
	
	@RequestMapping("/scrap/exportScrapReport.do")
	@ResponseBody
	public void exportRepairReport(HttpServletResponse response,String date_start,String date_end) throws IOException{

		Params params=Params.init().add("date_start", date_start).add("date_end", date_end);

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		List<RepairVO> result=scrapService.exportRepairReport(params);
		
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		build_addColumnName(wb,sheet,rownum);
		
		// 开始构建整个excel的文件
		if (result != null && result.size() > 0) {
			rownum++;
			build_content(result, wb, sheet, rownum);
		}
		String filename = "设备报废明细报表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		//response.setContentType("application/vnd.ms-excel;charset=uft-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
	}
	
	private void build_addColumnName(XSSFWorkbook wb,Sheet sheet,int rowInt){
		//CellStyle black_style=getStyle(wb,IndexedColors.BLACK,(short)11);
		 
		Row row = sheet.createRow(rowInt);
		int cellnum=0;
		
		Cell id=row.createCell(cellnum++);
		id.setCellValue("维修单号");
		
		Cell rpa_in_date=row.createCell(cellnum++);
		rpa_in_date.setCellValue("坏件领料时间");
		
		Cell rpa_user_name=row.createCell(cellnum++);
		rpa_user_name.setCellValue("维修人员");
		
		Cell ecode=row.createCell(cellnum++);
		ecode.setCellValue("条码");
		
		Cell prod_name=row.createCell(cellnum++);
		prod_name.setCellValue("品名");
		
		Cell equipment_style=row.createCell(cellnum++);
		equipment_style.setCellValue("型号");
		
		Cell broken_memo=row.createCell(cellnum++);
		broken_memo.setCellValue("故障现象");
		
		//Cell broken_reson=row.createCell(cellnum++);
		//broken_reson.setCellValue("故障原因");
		
		Cell handler_method=row.createCell(cellnum++);
		handler_method.setCellValue("处理方法");
		
		Cell scrap_reason=row.createCell(cellnum++);
		scrap_reason.setCellValue("报废原因");
		
		Cell scrap_residual=row.createCell(cellnum++);
		scrap_residual.setCellValue("坏件残值");
		
		Cell scrap_operateDate=row.createCell(cellnum++);
		scrap_operateDate.setCellValue("报废确认");
		
		Cell memo=row.createCell(cellnum++);
		memo.setCellValue("信息反馈及备注");
		
	}
	
	SimpleDateFormat yMdHms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void build_content(List<RepairVO> list,XSSFWorkbook wb,Sheet sheet,int rownum){
		int cellnum=0;

		for(RepairVO repairVO:list){
			cellnum=0;
			Row row = sheet.createRow(rownum++);
			
			Cell id=row.createCell(cellnum++);
			id.setCellValue(repairVO.getId());
			
			Cell rpa_in_date=row.createCell(cellnum++);
			rpa_in_date.setCellValue(repairVO.getRpa_in_date()!=null?yMdHms.format(repairVO.getRpa_in_date()):"");
			
			Cell rpa_user_name=row.createCell(cellnum++);
			rpa_user_name.setCellValue(repairVO.getRpa_user_name());
			
			Cell ecode=row.createCell(cellnum++);
			ecode.setCellValue(repairVO.getEcode());
			
			Cell prod_name=row.createCell(cellnum++);
			prod_name.setCellValue(repairVO.getProd_name());
			
			Cell equipment_style=row.createCell(cellnum++);
			equipment_style.setCellValue(repairVO.getEquipment_style());
			
			Cell broken_memo=row.createCell(cellnum++);
			broken_memo.setCellValue(repairVO.getBroken_memo());
			
			//Cell broken_reson=row.createCell(cellnum++);
			//broken_reson.setCellValue(repairVO.getBroken_reson());
			
			Cell handler_method=row.createCell(cellnum++);
			handler_method.setCellValue(repairVO.getHandler_method());
			
			Cell scrap_reason=row.createCell(cellnum++);
			scrap_reason.setCellValue(repairVO.getScrap_reason());
			
			Cell scrap_residual=row.createCell(cellnum++);
			scrap_residual.setCellValue(repairVO.getScrap_residual());
			
			Cell scrap_operateDate=row.createCell(cellnum++);
			scrap_operateDate.setCellValue(repairVO.getScrap_operateDate()!=null?yMdHms.format(repairVO.getScrap_operateDate()):"");
			
			Cell memo=row.createCell(cellnum++);
			memo.setCellValue(repairVO.getMemo());

		}
	}
}
