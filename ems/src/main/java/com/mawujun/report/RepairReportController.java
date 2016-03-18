package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;

@Controller
public class RepairReportController {
	@Resource
	private RepairReportRepository repairReportRepository;
	
	@RequestMapping("/report/repair/queryRepairReport.do")
	@ResponseBody
	public Page queryRepairReport(Integer start,Integer limit,String date_start,String date_end){
		Page page=Page.getInstance(start,limit);
		page.addParam("date_start", date_start);
		page.addParam("date_end", date_end);

		//JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		Page result=repairReportRepository.queryRepairReport(page);
		return result;
	}
	
	@RequestMapping("/report/repair/exportRepairReport.do")
	@ResponseBody
	public void exportRepairReport(HttpServletResponse response,String date_start,String date_end) throws IOException{

		Params params=Params.init().add("date_start", date_start).add("date_end", date_end);

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		List<RepairReport> result=repairReportRepository.queryRepairReport(params);
		
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		build_addColumnName(wb,sheet,rownum);
		
		// 开始构建整个excel的文件
		if (result != null && result.size() > 0) {
			rownum++;
			build_content(result, wb, sheet, rownum);
		}
		String filename = "设备维修明细报表.xlsx";
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
		
		Cell str_out_name=row.createCell(cellnum++);
		str_out_name.setCellValue("出库仓库");
		
		Cell brand_name=row.createCell(cellnum++);
		brand_name.setCellValue("品牌");
		
		Cell subtype_name=row.createCell(cellnum++);
		subtype_name.setCellValue("类型");
		
		Cell prod_style=row.createCell(cellnum++);
		prod_style.setCellValue("型号");
		
		Cell ecode=row.createCell(cellnum++);
		ecode.setCellValue("条码");
		
		Cell str_out_date=row.createCell(cellnum++);
		str_out_date.setCellValue("送修时间");
		
		Cell repair_take_time=row.createCell(cellnum++);
		repair_take_time.setCellValue("维修时间");
		
		Cell broken_reson=row.createCell(cellnum++);
		broken_reson.setCellValue("故障原因");
		
		Cell handler_method=row.createCell(cellnum++);
		handler_method.setCellValue("处理方法");
		
		Cell status_name=row.createCell(cellnum++);
		status_name.setCellValue("维修结果");
		
		Cell send_date=row.createCell(cellnum++);
		send_date.setCellValue("返厂时间");
		
		Cell receive_date=row.createCell(cellnum++);
		receive_date.setCellValue("返回时间");
		
		Cell str_in_date=row.createCell(cellnum++);
		str_in_date.setCellValue("入库时间");
		
		Cell memo=row.createCell(cellnum++);
		memo.setCellValue("备注");
		
	}
	
	SimpleDateFormat yMdHms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void build_content(List<RepairReport> list,XSSFWorkbook wb,Sheet sheet,int rownum){
		int cellnum=0;

		for(RepairReport repairVO:list){
			cellnum=0;
			Row row = sheet.createRow(rownum++);
			
			Cell str_out_name=row.createCell(cellnum++);
			str_out_name.setCellValue(repairVO.getStr_out_name());
			
			Cell brand_name=row.createCell(cellnum++);
			brand_name.setCellValue(repairVO.getBrand_name());
			
			Cell subtype_name=row.createCell(cellnum++);
			subtype_name.setCellValue(repairVO.getSubtype_name());
			
			Cell prod_style=row.createCell(cellnum++);
			prod_style.setCellValue(repairVO.getProd_style());
			
			Cell ecode=row.createCell(cellnum++);
			ecode.setCellValue(repairVO.getEcode());
			
			Cell str_out_date=row.createCell(cellnum++);
			str_out_date.setCellValue(yMdHms.format(repairVO.getStr_out_date()));
			
			Cell repair_take_time=row.createCell(cellnum++);
			repair_take_time.setCellValue(repairVO.getRepair_take_time());
			
			Cell broken_reson=row.createCell(cellnum++);
			broken_reson.setCellValue(repairVO.getBroken_reson());
			
			Cell handler_method=row.createCell(cellnum++);
			handler_method.setCellValue(repairVO.getHandler_method());
			
			Cell status_name=row.createCell(cellnum++);
			status_name.setCellValue(repairVO.getStatus_name());
			
			Cell send_date=row.createCell(cellnum++);
			if(repairVO.getSend_date()!=null){
				send_date.setCellValue(yMdHms.format(repairVO.getSend_date()));
			}
			
			
			Cell receive_date=row.createCell(cellnum++);
			if(repairVO.getReceive_date()!=null){
				receive_date.setCellValue(yMdHms.format(repairVO.getReceive_date()));
			}
			
			
			Cell str_in_date=row.createCell(cellnum++);
			if(repairVO.getStr_in_date()!=null){
				str_in_date.setCellValue(yMdHms.format(repairVO.getStr_in_date()));
			}
			
			
			Cell memo=row.createCell(cellnum++);
			memo.setCellValue("");
		}
	}
}
