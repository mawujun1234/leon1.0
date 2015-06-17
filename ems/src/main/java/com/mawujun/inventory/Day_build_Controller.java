package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.EquipmentSubtype;
import com.mawujun.baseinfo.EquipmentType;
import com.mawujun.baseinfo.EquipmentTypeRepository;

@Controller
public class Day_build_Controller {
	@Resource
	private EquipmentTypeRepository equipmentTypeRepository;
	private CellStyle getContentStyle(XSSFWorkbook wb,IndexedColors color){
		CellStyle style = wb.createCellStyle();
		//style.setAlignment(CellStyle.ALIGN_CENTER);
		//style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font bord_font = wb.createFont();
		bord_font.setFontHeightInPoints((short)10);
		if(color!=null){
			bord_font.setColor(color.getIndex());
		}
		//
		//bord_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		style.setFont(bord_font);
		style.setWrapText(true);//自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}
	
	public CellStyle getStyle(XSSFWorkbook wb,IndexedColors color,Short fontSize){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if(fontSize!=null){
			font.setFontHeightInPoints(fontSize);
		} else {
			font.setFontHeightInPoints((short)10);
		}
		
		font.setColor(color.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);//自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}
	public CellStyle getStyle_title(XSSFWorkbook wb,IndexedColors color,Short fontSize){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if(fontSize!=null){
			font.setFontHeightInPoints(fontSize);
		} else {
			font.setFontHeightInPoints((short)10);
		}
		
		font.setColor(color.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);//自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}
	
	//int build_month_freeze_num=6;//在建仓库月冻结的列数
	//int build_day_freeze_num=9;//在建仓库日报表冻结的列数
	int day_of_month_num=31;
	int type_group_end_num=9;//小类和大类分组的结束列
	private StringBuilder[] build_addRow1(XSSFWorkbook wb,Sheet sheet){
		 Row row = sheet.createRow(1);
		 CellStyle black_style=getStyle(wb,IndexedColors.BLACK,null);
		 CellStyle lastnum_style=getStyle(wb,IndexedColors.BLACK,null);
		 lastnum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle nownum_style=getStyle(wb,IndexedColors.BLACK,null);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 int cellnum=0;
		 Cell type_name=row.createCell(cellnum++);
		 type_name.setCellValue("大类");
		 type_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*1*256);
		 
		 Cell subtype_name=row.createCell(cellnum++);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*1*256);
		 
		 Cell brand_name=row.createCell(cellnum++);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 
		 Cell style=row.createCell(cellnum++);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*6*256);
		 
		 Cell prod_name=row.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*6*256);
		 
//		 Cell store_name=row.createCell(cellnum++);
//		 store_name.setCellValue("仓库");
//		 store_name.setCellStyle(black_style);
//		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*2*256);
		 
		 Cell unit=row.createCell(cellnum++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*1*256);
		 
		 Cell lastnum=row.createCell(cellnum++);
		 lastnum.setCellValue("上月结余数");
		 lastnum.setCellStyle(lastnum_style);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.LIGHT_BLUE,null);
		 Cell storeinnum=row.createCell(cellnum++);
		 storeinnum.setCellValue("本期新增数");
		 storeinnum.setCellStyle(blue_style);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED,null);
		 Cell installoutnum=row.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(nownum_style);
		 
//		 Cell memo=row.createCell(cellnum++);
//		 memo.setCellValue("备注"); 
//		 memo.setCellStyle(black_style);
		 
		// sheet.createFreezePane(build_month_freeze_num, 2);
		 
		 //====================================================		 
		// 新增数的样式
		CellStyle in_style = wb.createCellStyle();
		Font in_font = wb.createFont();
		in_font.setFontHeightInPoints((short) 8);
		in_font.setColor(IndexedColors.PALE_BLUE.getIndex());
		in_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		in_style.setFont(in_font);
		in_style.setAlignment(CellStyle.ALIGN_CENTER);
		in_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		in_style.setBorderTop(CellStyle.BORDER_THIN);
		in_style.setBorderBottom(CellStyle.BORDER_THIN);
		in_style.setBorderLeft(CellStyle.BORDER_THIN);
		in_style.setBorderRight(CellStyle.BORDER_THIN);
		// title_cell.setCellStyle(in_style);

		// 领用数的样式
		CellStyle out_style = wb.createCellStyle();
		Font out_font = wb.createFont();
		out_font.setFontHeightInPoints((short) 8);
		out_font.setColor(IndexedColors.RED.getIndex());
		out_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		out_style.setFont(out_font);
		out_style.setAlignment(CellStyle.ALIGN_CENTER);
		out_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		out_style.setBorderTop(CellStyle.BORDER_THIN);
		out_style.setBorderBottom(CellStyle.BORDER_THIN);
		out_style.setBorderLeft(CellStyle.BORDER_THIN);
		out_style.setBorderRight(CellStyle.BORDER_THIN);
		// title_cell.setCellStyle(out_style);
		
		//cellnum=cellstartnum;
		int cellnum_temp=cellnum;
		Row row2 = sheet.createRow(2);
		
		//
		StringBuilder in_formula=new StringBuilder("SUM(");
		StringBuilder out_formula=new StringBuilder("SUM(");
		for(int j=1;j<=day_of_month_num;j++){
			 Cell day_in=row2.createCell(cellnum_temp++);
			 day_in.setCellValue("新增数");
			 day_in.setCellStyle(in_style);
			 in_formula.append(CellReference.convertNumToColString(cellnum_temp-1)).append("=");
			 sheet.setColumnWidth(cellnum_temp-1, "列".getBytes().length*2*200);
			
			 
			 
			 Cell day_out=row2.createCell(cellnum_temp++);
			 day_out.setCellValue("领用数");
			 day_out.setCellStyle(out_style);
			 out_formula.append(CellReference.convertNumToColString(cellnum_temp-1)).append("=");
			 sheet.setColumnWidth(cellnum_temp-1, "列".getBytes().length*2*200);
			 
			 if(j!=day_of_month_num){
				 in_formula.append(",");
				 out_formula.append(",");
			 } 
			
		 }
		in_formula.append(")");
		out_formula.append(")");
		
		
		 Cell memo=row2.createCell(cellnum_temp++);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
		 
		
		//合并0--9的单元格，纵向合并
		for(int j=0;j<=cellnum-1;j++){
			sheet.addMergedRegion(new CellRangeAddress(1,2,(short)j,(short)j)); 
				
			Cell cell12=row2.createCell(j);
			cell12.setCellStyle(black_style);
		}
		
		
		//创建日期，并合并日期的两列，横向合并
		//int cellnum=cellstartnum;
		 cellnum_temp=cellnum;
		for(int j=1;j<=day_of_month_num;j++){
			//合并这两个单元格
			sheet.addMergedRegion(new CellRangeAddress(1,1,cellnum_temp++,cellnum_temp++)); 
			//设置日期值
			Cell cell11=row.createCell(cellnum_temp-2);
			cell11.setCellValue(j);
			cell11.setCellStyle(black_style);
			
			Cell cell12=row.createCell(cellnum_temp-1);
			cell12.setCellStyle(black_style);
		}
		 
		 //冻结行和列
		cellnum_temp=cellnum;
		sheet.createFreezePane(cellnum_temp, 3);
		 
		 //生成本期新增数公式
		 StringBuilder[] formulas=new StringBuilder[]{in_formula,out_formula};
		 return formulas;	 
		 
	}
	
	@RequestMapping("/inventory/day/build/excelTpl.do")
	public void excelTpl(HttpServletResponse response) throws IOException, IllegalAccessException, InvocationTargetException{

		
		
		//首先获取大类，小类的内容，然后按照格式输出，最后，设置压缩问题
		List<EquipmentType> equipmentTypes=equipmentTypeRepository.queryTypeAndSubtype();
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue("_______年_______月在建工程仓库(仓库名称)盘点日报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		//f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_LEFT);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		//和并标题单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,type_group_end_num)); 
		
		
		//设置第一行,设置列标题
		StringBuilder[] formulas=build_addRow1(wb, sheet);
		
		
		//---------------------------具体数据德样式
		// 新增数的样式
		CellStyle in_style = wb.createCellStyle();
		Font in_font = wb.createFont();
		in_font.setFontHeightInPoints((short) 10);
		in_font.setColor(IndexedColors.PALE_BLUE.getIndex());
		//in_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		in_style.setFont(in_font);
		in_style.setAlignment(CellStyle.ALIGN_CENTER);
		in_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		in_style.setBorderTop(CellStyle.BORDER_THIN);
		in_style.setBorderBottom(CellStyle.BORDER_THIN);
		in_style.setBorderLeft(CellStyle.BORDER_THIN);
		in_style.setBorderRight(CellStyle.BORDER_THIN);
		// title_cell.setCellStyle(in_style);

		// 领用数的样式
		CellStyle out_style = wb.createCellStyle();
		Font out_font = wb.createFont();
		out_font.setFontHeightInPoints((short) 10);
		out_font.setColor(IndexedColors.RED.getIndex());
		//out_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		out_style.setFont(out_font);
		out_style.setAlignment(CellStyle.ALIGN_CENTER);
		out_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		out_style.setBorderTop(CellStyle.BORDER_THIN);
		out_style.setBorderBottom(CellStyle.BORDER_THIN);
		out_style.setBorderLeft(CellStyle.BORDER_THIN);
		out_style.setBorderRight(CellStyle.BORDER_THIN);
		//---------------------------具体数据德样式

		CellStyle blue_style = getStyle(wb, IndexedColors.LIGHT_BLUE,null);
		
		CellStyle red_style = getStyle(wb, IndexedColors.RED,null);

		
		CellStyle type_name_style = this.getStyle(wb, IndexedColors.BLACK,(short)12);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		type_name_style.setWrapText(false);
		type_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		type_name_style.setBorderRight(CellStyle.BORDER_NONE);
		type_name_style.setBorderTop(CellStyle.BORDER_NONE);
		type_name_style.setAlignment(CellStyle.ALIGN_LEFT);
		type_name_style.setBorderBottom(CellStyle.BORDER_NONE);
		
		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.GREY_80_PERCENT,(short)11);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setWrapText(false);
		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle content_style =getContentStyle(wb,null);
		
		 CellStyle content_subtitle_style =getContentStyle(wb,null);
		 content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 //content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
		
		

		
		 //循环出数据
		int cellnum=0;
		//int extra_row_num=2;
		//String subtype_id_temp="";
		//int fromRow=0;//开始分组的行
		int rownum=3;
		for(int i=0;i<equipmentTypes.size();i++){
			cellnum = 0;
			EquipmentType equipmentType = equipmentTypes.get(i);
			
			//这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);	

			//这个标题，真闷提取出一行进行显示了
			Cell type_name = row.createCell(cellnum++);
			type_name.setCellValue(equipmentType.getName());
			type_name.setCellStyle(type_name_style);
			//subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,0,(short)type_group_end_num)); 
			//第二个+1是备注列
			sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,type_group_end_num+1,(short)(type_group_end_num+day_of_month_num*2+1)));
	
			//创建子类型的excel行
			StringBuilder nownum_formule_builder=new StringBuilder();
			if(equipmentType.getSubtypes()!=null) {
				int fromRow_type=rownum;
				for(EquipmentSubtype equipmentSubtype:equipmentType.getSubtypes()){
					cellnum=0;
					
					Row row_subtype = sheet.createRow(rownum++);
					
					
					cellnum++;//空出第一列，因为第一列放的是大类的名称
					//Cell type_name_2 = row_subtype.createCell(cellnum++);
					//type_name.setCellValue("    "+equipmentSubtype.getName());
					//type_name.setCellStyle(subtype_name_style);
					
					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getName());
					subtype_name.setCellStyle(subtype_name_style);
					
					sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,1,(short)type_group_end_num)); 
					//第二个+1是备注列
					sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,type_group_end_num+1,(short)(type_group_end_num+day_of_month_num*2+1)));
					
					
					//----------------------------------------------------------------------------------
					//弄几行模拟品名的数据，即几个空行
					int fromRow_subtype=rownum;
					for(int k=0;k<5;k++){
						nownum_formule_builder=new StringBuilder();
						nownum_formule_builder.append("SUM(");
						
						cellnum=2;
						Row row_prod = sheet.createRow(rownum++);
						Cell brand_name = row_prod.createCell(cellnum++);
						brand_name.setCellValue("测试");
						brand_name.setCellStyle(content_style);
			
						Cell style = row_prod.createCell(cellnum++);
						style.setCellValue("测试");
						style.setCellStyle(content_style);
			
						Cell prod_name = row_prod.createCell(cellnum++);
						prod_name.setCellValue("测试");
						prod_name.setCellStyle(content_style);
			
//						Cell store_name = row_prod.createCell(cellnum++);
//						store_name.setCellValue("测试");
//						store_name.setCellStyle(content_style);
			
						Cell unit = row_prod.createCell(cellnum++);
						unit.setCellValue("台");
						unit.setCellStyle(content_style);
			
						Cell lastnum = row_prod.createCell(cellnum++);
						lastnum.setCellValue(1);
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						//lastnum.setCellStyle(lastnum_style);
			
						Cell storeinnum = row_prod.createCell(cellnum++);
						storeinnum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum)+""));
						storeinnum.setCellStyle(blue_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
			
						Cell installoutnum = row_prod.createCell(cellnum++);
						installoutnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum)+""));
						installoutnum.setCellStyle(red_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						nownum_formule_builder.append(")");
						// 本月结余数
						Cell nownum = row_prod.createCell(cellnum++);
//						nownum.setCellFormula("SUM("
//								+ CellReference.convertNumToColString(6) + (rownum )
//								+ "," + CellReference.convertNumToColString(7)+(rownum ) 
//								+ ","+ CellReference.convertNumToColString(8) + (rownum )
//								+ ")");
						 nownum.setCellFormula(nownum_formule_builder.toString());
						 //nownum.setCellStyle(bord_style);
						//nownum.setCellStyle(nownum_style);
						
						for(int j=1;j<=day_of_month_num;j++){
							 Cell day_in=row_prod.createCell(cellnum++);
							 day_in.setCellValue(1);
							 day_in.setCellStyle(in_style);
							 //in_formula.append(CellReference.convertNumToColString(cellnum_temp-1)).append("=");
							 //sheet.setColumnWidth(cellnum_temp-1, "列".getBytes().length*2*200);
							
							 
							 
							 Cell day_out=row_prod.createCell(cellnum++);
							 day_out.setCellValue(2);
							 day_out.setCellStyle(out_style);
							 //out_formula.append(CellReference.convertNumToColString(cellnum_temp-1)).append("=");
							// sheet.setColumnWidth(cellnum_temp-1, "列".getBytes().length*2*200);
//							 
//							 if(j!=day_of_month_num){
//								 in_formula.append(",");
//								 out_formula.append(",");
//							 } 
							
						 }
			
						Cell memo = row_prod.createCell(cellnum++);
						//memo.setCellValue(buildDayReport.getMemo());
						memo.setCellStyle(content_style);
					}
					
					//小类的收缩
					sheet.groupRow(fromRow_subtype, rownum);
					sheet.setRowGroupCollapsed(fromRow_subtype, true);
					
				}
				//设置最挖曾的收缩,就是类型的收缩
				sheet.groupRow(fromRow_type, rownum);
				sheet.setRowGroupCollapsed(fromRow_type, true);
			}	
			
		}
		//定义收缩的方向
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		init_build_background(wb,sheet,rownum);
		
		String filename = "在建仓库日报表_样式表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
	
	int start_row=3;
	public void init_build_background(XSSFWorkbook wb ,Sheet sheet,int lastrownum){
		 CellStyle lastnum_style=getContentStyle(wb,null);
		 lastnum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle nownum_style=getContentStyle(wb,null);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 

		 
		 CellStyle lastnum_subtitle_style=getContentStyle(wb,null);
		 lastnum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 lastnum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 lastnum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		 lastnum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle nownum_subtitle_style=getContentStyle(wb,null);
		 nownum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 nownum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 nownum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
		 nownum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle memo_subtitle_style=getContentStyle(wb,null);
		 memo_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 //memo_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		
		for(int i=3;i<lastrownum;i++){
			Row row=sheet.getRow(i);
			
			for(int j=0;j<11;j++){
				Cell cell=row.getCell(j);
				if(cell==null){
					//cell=row.createCell(j);
					continue;
				}
				if(j==6){
					cell.setCellStyle(lastnum_style);
				}
				if(j==9){
					cell.setCellStyle(nownum_style);
				}
			}
			
		}
	}
	
	
}
