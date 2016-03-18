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
import com.mawujun.utils.page.Page;

@Controller
public class TaskRepairReportController {
	@Autowired
	private TaskReportRepository taskReportRepository;
	
	
	@RequestMapping("/report/taskrepair/queryRepairReport.do")
	@ResponseBody
	public Page queryRepairReport(Integer start,Integer limit,String date_start,String date_end,String pole_code,String hitchType_id) {
		Page page=Page.getInstance(start, limit).addParam("date_start", date_start)
				.addParam("date_end", date_end)
				.addParam("pole_code", pole_code)
				.addParam("hitchType_id", hitchType_id);
		
		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return taskReportRepository.queryTaskRepairReport(page);
	}
	
	
	
	@RequestMapping("/report/taskrepair/exportRepairReport.do")
	@ResponseBody
	public void exportRepairReport(HttpServletResponse response,String date_start,String date_end,String pole_code,String hitchType_id) throws IOException {
		List<TaskRepairReport> result= taskReportRepository.queryTaskRepairReport(date_start, date_end, pole_code, hitchType_id);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		build_addColumnName(wb,sheet,rownum);
		
		// 开始构建整个excel的文件
		if (result != null && result.size() > 0) {
			rownum++;
			build_content(result, wb, sheet, rownum);
		}
		
		String filename = "新维修任务明细报表.xlsx";
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
		
		Cell num=row.createCell(cellnum++);
		num.setCellValue("序号");
		Cell customer_name=row.createCell(cellnum++);
		customer_name.setCellValue("客户名称");
		Cell pole_code=row.createCell(cellnum++);
		pole_code.setCellValue("点位编号");
		Cell pole_name=row.createCell(cellnum++);
		pole_name.setCellValue("点位名称");
		Cell workunit_name=row.createCell(cellnum++);
		workunit_name.setCellValue("作业单位");
		Cell memo=row.createCell(cellnum++);
		memo.setCellValue("故障现象");
		Cell hitchDate=row.createCell(cellnum++);
		hitchDate.setCellValue("故障时间");
		Cell createDate=row.createCell(cellnum++);
		createDate.setCellValue("任务下发时间");
		Cell startHandDate=row.createCell(cellnum++);
		startHandDate.setCellValue("开始处理时间");
		Cell submitDate=row.createCell(cellnum++);
		submitDate.setCellValue("提交时间");
		Cell completeDate=row.createCell(cellnum++);
		completeDate.setCellValue("完成时间");
		Cell usedTime=row.createCell(cellnum++);
		usedTime.setCellValue("总耗时");
		Cell repairUsedTime=row.createCell(cellnum++);
		repairUsedTime.setCellValue("维修耗时");
		Cell result=row.createCell(cellnum++);
		result.setCellValue("结果");
		Cell overtime=row.createCell(cellnum++);
		overtime.setCellValue("超时");
		Cell hitchType=row.createCell(cellnum++);
		hitchType.setCellValue("故障类型");
		Cell hitchReason=row.createCell(cellnum++);
		hitchReason.setCellValue("故障原因");
		Cell handleMethod_name=row.createCell(cellnum++);
		handleMethod_name.setCellValue("处理方法");
		Cell handle_contact=row.createCell(cellnum++);
		handle_contact.setCellValue("备注");	
		
	}
	
	SimpleDateFormat yMdHms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void build_content(List<TaskRepairReport> list,XSSFWorkbook wb,Sheet sheet,int rownum){
		int cellnum=0;

		int i=0;
		for(TaskRepairReport repairVO:list){
			i++;
			cellnum=0;
			Row row = sheet.createRow(rownum++);

			Cell num=row.createCell(cellnum++);
			num.setCellValue(i);
			Cell customer_name=row.createCell(cellnum++);
			customer_name.setCellValue(repairVO.getCustomer_name());
			Cell pole_code=row.createCell(cellnum++);
			pole_code.setCellValue(repairVO.getPole_code());
			Cell pole_name=row.createCell(cellnum++);
			pole_name.setCellValue(repairVO.getPole_name());
			Cell workunit_name=row.createCell(cellnum++);
			workunit_name.setCellValue(repairVO.getWorkunit_name());
			Cell memo=row.createCell(cellnum++);
			memo.setCellValue(repairVO.getMemo());
			Cell hitchDate=row.createCell(cellnum++);
			hitchDate.setCellValue(repairVO.getHitchDate()==null?"":yMdHms.format(repairVO.getHitchDate()));
			Cell createDate=row.createCell(cellnum++);
			createDate.setCellValue(repairVO.getCreateDate()==null?"":yMdHms.format(repairVO.getCreateDate()));
			Cell startHandDate=row.createCell(cellnum++);
			startHandDate.setCellValue(repairVO.getStartHandDate()==null?"":yMdHms.format(repairVO.getStartHandDate()));
			Cell submitDate=row.createCell(cellnum++);
			submitDate.setCellValue(repairVO.getSubmitDate()==null?"":yMdHms.format(repairVO.getSubmitDate()));
			Cell completeDate=row.createCell(cellnum++);
			completeDate.setCellValue(repairVO.getCompleteDate()==null?"":yMdHms.format(repairVO.getCompleteDate()));
			Cell usedTime=row.createCell(cellnum++);
			usedTime.setCellValue(repairVO.getUsedTime());
			Cell repairUsedTime=row.createCell(cellnum++);
			repairUsedTime.setCellValue(repairVO.getRepairUsedTime());
			Cell result=row.createCell(cellnum++);
			result.setCellValue(repairVO.getResult());
			Cell overtime=row.createCell(cellnum++);
			overtime.setCellValue(repairVO.getOvertime());
			Cell hitchType=row.createCell(cellnum++);
			hitchType.setCellValue(repairVO.getHitchType());
			Cell hitchReason=row.createCell(cellnum++);
			hitchReason.setCellValue(repairVO.getHitchReason());
			Cell handleMethod_name=row.createCell(cellnum++);
			handleMethod_name.setCellValue(repairVO.getHandleMethod_name());
			Cell handle_contact=row.createCell(cellnum++);
			handle_contact.setCellValue(repairVO.getHandle_contact());
		}
	}

}
