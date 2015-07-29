package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.mobile.task.Task;
import com.mawujun.mobile.task.TaskService;

@Controller
public class TaskReportController {
	@Resource
	private TaskService taskService;
	
	@RequestMapping("/report/task/exportUnrepairPoleReport.do")
	@ResponseBody
	public void exportUnrepairPoleReport(HttpServletResponse response,String workunit_id,String customer_id) throws IOException {
		List<Task> taskes=taskService.exportUnrepairPoleReport(workunit_id,customer_id);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		build_addColumnName(wb,sheet,rownum);
		
		// 开始构建整个excel的文件
		if (taskes != null && taskes.size() > 0) {
			rownum++;
			build_content(taskes, wb, sheet, rownum);
		}
		String filename = "维修任务明细.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		//response.setContentType("application/vnd.ms-excel;charset=uft-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
	}
	public CellStyle getStyle(XSSFWorkbook wb, IndexedColors color, Short fontSize) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if (fontSize != null) {
			font.setFontHeightInPoints(fontSize);
		} else {
			font.setFontHeightInPoints((short) 10);
		}

		font.setColor(color.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);// 自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}
	private void build_addColumnName(XSSFWorkbook wb,Sheet sheet,int rowInt){
		CellStyle black_style=getStyle(wb,IndexedColors.BLACK,(short)11);
		
		
		 
		Row row = sheet.createRow(rowInt);
		int cellnum=0;
		
		Cell id=row.createCell(cellnum++);
		id.setCellValue("序号");
		id.setCellStyle(black_style);
		
		Cell customer_name=row.createCell(cellnum++);
		customer_name.setCellValue("派出所");
		customer_name.setCellStyle(black_style);
		
		Cell pole_code=row.createCell(cellnum++);
		pole_code.setCellValue("点位编号");
		pole_code.setCellStyle(black_style);
		
		Cell pole_name=row.createCell(cellnum++);
		pole_name.setCellValue("点位名称");
		pole_name.setCellStyle(black_style);
		
		Cell workunit_name=row.createCell(cellnum++);
		workunit_name.setCellValue("作业单位");
		workunit_name.setCellStyle(black_style);
		
		Cell task_id=row.createCell(cellnum++);
		task_id.setCellValue("任务编号");
		task_id.setCellStyle(black_style);
		
		Cell task_memo=row.createCell(cellnum++);
		task_memo.setCellValue("任务描述");
		task_memo.setCellStyle(black_style);
		
		Cell createDate=row.createCell(cellnum++);
		createDate.setCellValue("任务下派时间");
		createDate.setCellStyle(black_style);
		
		Cell startHandDate=row.createCell(cellnum++);
		startHandDate.setCellValue("维修到达时间");
		startHandDate.setCellStyle(black_style);
		
//		Cell completeDate=row.createCell(cellnum++);
//		completeDate.setCellValue("完成时间");
//		
//		Cell finishTime=row.createCell(cellnum++);
//		finishTime.setCellValue("总耗时");
//		
//		Cell repairTime=row.createCell(cellnum++);
//		repairTime.setCellValue("修复耗时");
//		
//		Cell isOverTime=row.createCell(cellnum++);
//		isOverTime.setCellValue("是否超时");
		
		Cell hitchType=row.createCell(cellnum++);
		hitchType.setCellValue("故障类型");
		hitchType.setCellStyle(black_style);
		
		Cell hitchReason=row.createCell(cellnum++);
		hitchReason.setCellValue("故障原因");
		hitchReason.setCellStyle(black_style);
			
	}
	SimpleDateFormat yMdHms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void build_content(List<Task> taskes,XSSFWorkbook wb,Sheet sheet,int rownum){
		int cellnum=0;

		int i=1;
		for(Task task:taskes){
			cellnum=0;
			Row row = sheet.createRow(rownum++);
			
			Cell id=row.createCell(cellnum++);
			id.setCellValue(i);
			i++;
			
			Cell customer_name=row.createCell(cellnum++);
			customer_name.setCellValue(task.getCustomer_name());
			
			Cell pole_code=row.createCell(cellnum++);
			pole_code.setCellValue(task.getPole_code());
			
			Cell pole_name=row.createCell(cellnum++);
			pole_name.setCellValue(task.getPole_name());
			
			Cell workunit_name=row.createCell(cellnum++);
			workunit_name.setCellValue(task.getWorkunit_name());
			
			Cell task_id=row.createCell(cellnum++);
			task_id.setCellValue(task.getId());
			
			Cell task_memo=row.createCell(cellnum++);
			task_memo.setCellValue(task.getMemo());
			
			Cell createDate=row.createCell(cellnum++);
			createDate.setCellValue(yMdHms.format(task.getCreateDate()));
			
			Cell startHandDate=row.createCell(cellnum++);
			startHandDate.setCellValue(task.getStartHandDate()!=null?yMdHms.format(task.getStartHandDate()):"");
			
//			Cell completeDate=row.createCell(cellnum++);
//			completeDate.setCellValue(task.getCompleteDate()!=null?yMdHms.format(task.getCompleteDate()):"");//==null?"":task.getCompleteDate());
//			
//			Cell finishTime=row.createCell(cellnum++);
//			finishTime.setCellValue(task.getFinishTime());
//			
//			Cell repairTime=row.createCell(cellnum++);
//			repairTime.setCellValue(task.getRepairTime());
//			
//			Cell isOverTime=row.createCell(cellnum++);
//			isOverTime.setCellValue(task.isOverTime?"超时":"否");
			
			Cell hitchType=row.createCell(cellnum++);
			hitchType.setCellValue(task.getHitchType());
			
			Cell hitchReason=row.createCell(cellnum++);
			hitchReason.setCellValue(task.getHitchReason());
		}
	}
}
