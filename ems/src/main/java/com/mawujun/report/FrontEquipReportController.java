package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontEquipReportController {
	@Autowired
	private FrontEquipReportRepository frontEquipReportRepository;
	
	public CellStyle getHeaderStyle(XSSFWorkbook wb, Short fontSize) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if (fontSize != null) {
			font.setFontHeightInPoints(fontSize);
		} else {
			font.setFontHeightInPoints((short) 10);
		}

		//font.setColor(color.getIndex());
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
	
	
	private int exportFrontEquipSumReport_header(List<FrontEquipSumReport_subtype> subtypes,int rownum,XSSFWorkbook wb,Sheet sheet) {
		CellStyle style=getHeaderStyle(wb,(short)11);
		
		// 第一行列标题
		Row row_subtype = sheet.createRow(rownum++);
		Cell cell_subtype_0 = row_subtype.createCell(0);
		cell_subtype_0.setCellValue("");
		cell_subtype_0.setCellStyle(style);
		Cell cell_subtype_1 = row_subtype.createCell(1);
		cell_subtype_1.setCellValue("");
		cell_subtype_1.setCellStyle(style);
		
		// 第二行列标题
		Row row_prod = sheet.createRow(rownum++);
		Cell cell_prod_0 = row_prod.createCell(0);
		cell_prod_0.setCellValue("序号");
		cell_prod_0.setCellStyle(style);
		Cell cell_prod_1 = row_prod.createCell(1);
		cell_prod_1.setCellValue("客户名称");
		cell_prod_1.setCellStyle(style);
		
		int cell_num=2;
		for(FrontEquipSumReport_subtype subtype:subtypes){
			int start_cell_num=cell_num;
			Cell cell_subtype=row_subtype.createCell(cell_num);
			cell_subtype.setCellValue(subtype.getSubtype_name());
			cell_subtype.setCellStyle(style);
			
			for(FrontEquipSumReport_prod prod:subtype.getProds()){
				if(start_cell_num!=cell_num){
					cell_subtype=row_subtype.createCell(cell_num);
					cell_subtype.setCellStyle(style);
				}
				
				
				Cell cell_prod=row_prod.createCell(cell_num);
				cell_prod.setCellValue(prod.getProd_name());
				cell_prod.setCellStyle(style);
				cell_num++;
			}
			//对子类型进行横向的单元格合并
			sheet.addMergedRegion(new CellRangeAddress(1, (short)1, start_cell_num, (short) cell_num-1));
		}

		
		return rownum;
	}
	
	@RequestMapping("/report/frontequip/exportFrontEquipSumReport.do")
	public void exportFrontEquipSumReport(HttpServletResponse response,String customer_2,String customer_2_name,String customer_0or1) throws IOException {
		//List<FrontEquipSumReport> list=frontEquipReportRepository.queryFrontEquipSumReport(customer_2, customer_0or1);
		
		List<FrontEquipSumReport_subtype> list_subtype_prod=frontEquipReportRepository.queryFrontEquipSumReport_header(customer_2, customer_0or1);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		//标题
		Row title = sheet.createRow(rownum++);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(customer_2_name+"前端设备汇总表");
		CellStyle title_style = wb.createCellStyle();
		Font title_font = wb.createFont();
		title_font.setFontHeightInPoints((short) 16);
		// f.setColor(IndexedColors.RED.getIndex());
		title_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		title_style.setFont(title_font);
		title_style.setAlignment(CellStyle.ALIGN_CENTER);
		title_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(title_style);
		// 和并单元格
		//sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 15));
		//===========================================================================================
		rownum=exportFrontEquipSumReport_header(list_subtype_prod,rownum,wb,sheet);
		//=============================================================================================
		// 开始构建整个excel的文件
		if (list != null && list.size() > 0) {
			for(FrontEquipSumReport customer:list){
				
			}
		}
		String filename = customer_2_name+"前端设备汇总表.xlsx";
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
