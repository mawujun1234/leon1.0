package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repair.RepairVO;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Controller
public class ScrapReportController {
	@Autowired
	private RepairReportRepository repairReportRepository;
	
	@RequestMapping("/report/scrap/queryScrapReport.do")
	@ResponseBody
	public Page queryScrapReport(Integer start,Integer limit,String date_start,String date_end){
		Page page=Page.getInstance(start,limit);
		page.addParam("date_start", date_start);
		page.addParam("date_end", date_end);

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		Page result=repairReportRepository.queryScrapReport(page);
		return result;
	}
	
	@RequestMapping("/report/scrap/exportScrapReport.do")
	@ResponseBody
	public void exportRepairReport(HttpServletResponse response,String date_start,String date_end) throws IOException{

		Params params=Params.init().add("date_start", date_start).add("date_end", date_end);

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		List<RepairVO> result=repairReportRepository.queryScrapReport(params);
		
		
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
		
		Cell str_out_name=row.createCell(cellnum++);
		str_out_name.setCellValue("出库仓库");
		
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
			
			Cell str_out_name=row.createCell(cellnum++);
			str_out_name.setCellValue(repairVO.getStr_out_name());
			
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
