package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.CustomerService;
import com.mawujun.shiro.ShiroUtils;

@Controller
public class FrontEquipReportController {
	@Autowired
	private FrontEquipReportRepository frontEquipReportRepository;
	@Autowired
	private CustomerService customerService;
	
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
		CellStyle style2=getHeaderStyle(wb,(short)10);
		
		// 第一行列标题
		Row row_subtype = sheet.createRow(rownum++);
		Cell cell_subtype_0 = row_subtype.createCell(0);
		cell_subtype_0.setCellValue("");
		cell_subtype_0.setCellStyle(style);
		sheet.setColumnWidth(0, 700);
		Cell cell_subtype_1 = row_subtype.createCell(1);
		cell_subtype_1.setCellValue("");
		cell_subtype_1.setCellStyle(style);
		sheet.setColumnWidth(1, 4800);
		
		// 第二行列标题
		Row row_prod = sheet.createRow(rownum++);
		Cell cell_prod_0 = row_prod.createCell(0);
		cell_prod_0.setCellValue("序号");
		cell_prod_0.setCellStyle(style2);
		Cell cell_prod_1 = row_prod.createCell(1);
		cell_prod_1.setCellValue("客户名称");
		cell_prod_1.setCellStyle(style2);
		
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
				cell_prod.setCellValue(prod.getProd_name()+"("+prod.getProd_style()+")");
				cell_prod.setCellStyle(style2);
				cell_num++;
			}
			//对子类型进行横向的单元格合并
			sheet.addMergedRegion(new CellRangeAddress(1, (short)1, start_cell_num, (short) cell_num-1));
		}

		
		return rownum;
	}
	/**
	 * isMaching=true表示获取机房的设备，否则获取的就是前端的设备汇总
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param response
	 * @param customer_2
	 * @param customer_2_name
	 * @param customer_0or1
	 * @param isMaching
	 * @throws IOException
	 */
	@RequestMapping("/report/frontequip/exportFrontEquipSumReport.do")
	public void exportFrontEquipSumReport(HttpServletResponse response,String customer_2,String customer_0or1,Boolean isMaching) throws IOException {
		
		String customer_2_name=customerService.get(customer_2).getName();
		List<FrontEquipSumReport_subtype> list_subtype_prod=null;
		if(isMaching){
			 list_subtype_prod=frontEquipReportRepository.queryMachineroomEquipSumReport_header(customer_2, customer_0or1,ShiroUtils.getUserId());
		} else {
			 list_subtype_prod=frontEquipReportRepository.queryFrontEquipSumReport_header(customer_2, customer_0or1,ShiroUtils.getUserId());
		}
		List<FrontEquipSumReport> list=null;
		if(isMaching){
			list=frontEquipReportRepository.queryMachineroomEquipSumReport(customer_2, customer_0or1,ShiroUtils.getUserId());
		} else {
			list=frontEquipReportRepository.queryFrontEquipSumReport(customer_2, customer_0or1,ShiroUtils.getUserId());
		}
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		//标题
		Row title = sheet.createRow(rownum++);
		Cell title_cell = title.createCell(0);
		if(isMaching){
			title_cell.setCellValue(customer_2_name+"机房设备汇总表");
		} else {
			title_cell.setCellValue(customer_2_name+"前端设备汇总表");
		}
		
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
		//处理对应的品名在excel的哪一列
		Map<String,Integer> prod_col_index_map=new HashMap<String,Integer>();
		int cellIndex=2;
		for(FrontEquipSumReport_subtype subtype:list_subtype_prod){
			for(FrontEquipSumReport_prod prod:subtype.getProds()){
				prod_col_index_map.put(prod.getProd_id(), cellIndex++);
			}
		}
		
		if (list != null && list.size() > 0) {
			int i=1;
			for(FrontEquipSumReport customer:list){
				Row row = sheet.createRow(rownum++);
				Cell cell_prod_0 = row.createCell(0);
				cell_prod_0.setCellValue(i);
				//cell_prod_0.setCellStyle(style);
				Cell cell_prod_1 = row.createCell(1);
				cell_prod_1.setCellValue(customer.getCustomer_name());
				for(FrontEquipSumReport_prod prod:customer.getProdes()){
					Cell cell_prod=row.createCell(prod_col_index_map.get(prod.getProd_id()));
					cell_prod.setCellValue(prod.getNum());
					//cell_prod.setCellStyle(style);
				}
				i++;
			}
			//对标题行 进行单元格合并
			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) prod_col_index_map.size()));
		}
		//冻结2列3行
		sheet.createFreezePane(2, 3);
		
		//添加总计一行
		CellStyle style_sum=wb.createCellStyle();
		Font style_sum_font = wb.createFont();
		style_sum_font.setFontHeightInPoints((short) 12);
		// f.setColor(IndexedColors.RED.getIndex());
		style_sum_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style_sum.setFont(style_sum_font);
		style_sum.setAlignment(CellStyle.ALIGN_RIGHT);
		style_sum.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		Row row_sum = sheet.createRow(rownum++);
		Cell cell_sum_1 = row_sum.createCell(1);
		cell_sum_1.setCellValue("总计:");
		cell_sum_1.setCellStyle(style_sum);
		for(int i=2;i<cellIndex;i++){
			String col=CellReference.convertNumToColString(i);
			
			Cell cell_sum = row_sum.createCell(i);
			//=SUM(C4:C29)  从第4行开始到最后一样
			//cell_sum.setCellValue("SUM("+col+"4:"+col+(rownum-1)+")");
			cell_sum.setCellFormula("SUM("+col+"4:"+col+(rownum-1)+")");
			cell_sum.setCellStyle(style_sum);
		}
		
		
		String filename = customer_2_name+"前端设备汇总表.xlsx";
		if(isMaching){
			filename=customer_2_name+"机房设备汇总表.xlsx";
		}
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		//response.setContentType("application/vnd.ms-excel;charset=uft-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
		
	}
	
	//============================================================================================================================================
	private int exportFrontEquipListReport_header(List<FrontEquipListReport_subtype> subtypes,int rownum,XSSFWorkbook wb,Sheet sheet) {
		CellStyle style=getHeaderStyle(wb,(short)11);
		CellStyle style2=getHeaderStyle(wb,(short)10);
		
		
		// 第一行列标题
		Row row_subtype = sheet.createRow(rownum++);
		Cell cell_subtype_0 = row_subtype.createCell(0);
		cell_subtype_0.setCellValue("");
		cell_subtype_0.setCellStyle(style);
		sheet.setColumnWidth(0, 700);
		Cell cell_subtype_1 = row_subtype.createCell(1);
		cell_subtype_1.setCellValue("");
		cell_subtype_1.setCellStyle(style);
		sheet.setColumnWidth(1, 1800);
		Cell cell_subtype_2 = row_subtype.createCell(2);
		cell_subtype_2.setCellValue("");
		cell_subtype_2.setCellStyle(style);
		sheet.setColumnWidth(2, 4800);
		
		// 第二行列标题
		Row row_prod = sheet.createRow(rownum++);
		Cell cell_prod_0 = row_prod.createCell(0);
		cell_prod_0.setCellValue("序号");
		cell_prod_0.setCellStyle(style2);
		Cell cell_prod_1 = row_prod.createCell(1);
		cell_prod_1.setCellValue("点位编号");
		cell_prod_1.setCellStyle(style2);
		Cell cell_prod_2 = row_prod.createCell(2);
		cell_prod_2.setCellValue("点位名称");
		cell_prod_2.setCellStyle(style2);
		
		int cell_num=3;
		for(FrontEquipListReport_subtype subtype:subtypes){
			int start_cell_num=cell_num;
			Cell cell_subtype=row_subtype.createCell(cell_num);
			cell_subtype.setCellValue(subtype.getSubtype_name());
			cell_subtype.setCellStyle(style);
			
			for(FrontEquipListReport_prod prod:subtype.getProds()){
				if(start_cell_num!=cell_num){
					cell_subtype=row_subtype.createCell(cell_num);
					cell_subtype.setCellStyle(style);
				}
				
				
				Cell cell_prod=row_prod.createCell(cell_num);
				cell_prod.setCellValue(prod.getProd_name()+"("+prod.getProd_style()+")");
				cell_prod.setCellStyle(style2);
				cell_num++;
			}
			//对子类型进行横向的单元格合并
			sheet.addMergedRegion(new CellRangeAddress(1, (short)1, start_cell_num, (short) cell_num-1));
		}

		
		return rownum;
	}
	@RequestMapping("/report/frontequip/exportFrontEquipListReport.do")
	public void exportFrontEquipListReport(HttpServletResponse response,String customer_2,
			String customer_0or1) throws IOException {
		
		String customer_2_name=customerService.get(customer_2).getName();
		String customer_0or1_name=customerService.get(customer_0or1).getName();
		List<FrontEquipListReport> list=frontEquipReportRepository.queryFrontEquipListReport(customer_2, customer_0or1);
		
		List<FrontEquipListReport_subtype> list_subtype_prod=frontEquipReportRepository.queryFrontEquipListReport_header(customer_2, customer_0or1);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		//标题
		
		Row title = sheet.createRow(rownum++);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(customer_2_name+customer_0or1_name+"前端设备明细表");
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
		rownum=exportFrontEquipListReport_header(list_subtype_prod,rownum,wb,sheet);
		//=============================================================================================
		// 开始构建整个excel的文件
		//处理对应的品名在excel的哪一列
		Map<String,Integer> prod_col_index_map=new HashMap<String,Integer>();
		int cellIndex=3;
		for(FrontEquipListReport_subtype subtype:list_subtype_prod){
			for(FrontEquipListReport_prod prod:subtype.getProds()){
				prod_col_index_map.put(prod.getProd_id(), cellIndex++);
			}
		}
		
		if (list != null && list.size() > 0) {
			int i=1;
			for(FrontEquipListReport customer:list){
				Row row = sheet.createRow(rownum++);
				Cell cell_prod_0 = row.createCell(0);
				cell_prod_0.setCellValue(i);
				//cell_prod_0.setCellStyle(style);
				Cell cell_prod_1 = row.createCell(1);
				cell_prod_1.setCellValue(customer.getPole_code());
				Cell cell_prod_2 = row.createCell(2);
				cell_prod_2.setCellValue(customer.getPole_name());
				for(FrontEquipListReport_prod prod:customer.getProdes()){
					Cell cell_prod=row.createCell(prod_col_index_map.get(prod.getProd_id()));
					cell_prod.setCellValue(prod.getNum());
					//cell_prod.setCellStyle(style);
				}
				i++;
			}
			//对标题行 进行单元格合并
			sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) prod_col_index_map.size()));
		}
		sheet.createFreezePane(3, 3);
		
		// 添加总计一行
		CellStyle style_sum = wb.createCellStyle();
		Font style_sum_font = wb.createFont();
		style_sum_font.setFontHeightInPoints((short) 12);
		// f.setColor(IndexedColors.RED.getIndex());
		style_sum_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style_sum.setFont(style_sum_font);
		style_sum.setAlignment(CellStyle.ALIGN_RIGHT);
		style_sum.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		Row row_sum = sheet.createRow(rownum++);
		Cell cell_sum_2 = row_sum.createCell(2);
		cell_sum_2.setCellValue("小计:");
		cell_sum_2.setCellStyle(style_sum);
		for (int i = 3; i < cellIndex; i++) {
			String col = CellReference.convertNumToColString(i);

			Cell cell_sum = row_sum.createCell(i);
			// =SUM(C4:C29) 从第4行开始到最后一样
			// cell_sum.setCellValue("SUM("+col+"4:"+col+(rownum-1)+")");
			cell_sum.setCellFormula("SUM(" + col + "4:" + col + (rownum - 1) + ")");
			cell_sum.setCellStyle(style_sum);
		}
				
		String filename = customer_2_name+customer_0or1_name+"前端设备明细表.xlsx";
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
