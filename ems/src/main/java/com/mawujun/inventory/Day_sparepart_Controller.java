package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
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
public class Day_sparepart_Controller {
	@Resource
	private EquipmentTypeRepository equipmentTypeRepository;
	
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
	
	private CellStyle getContentStyle(XSSFWorkbook wb,IndexedColors color,short fontSize){
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
	
	public void init_background(XSSFWorkbook wb ,Sheet sheet,int lastrownum){
		CellStyle fixednum_style=getContentStyle(wb,null,(short)9);
		 fixednum_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		 fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		CellStyle lastnum_style=getContentStyle(wb,null,(short)9);
		 lastnum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle nownum_style=getContentStyle(wb,null,(short)9);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		 CellStyle supplementnum_style=getContentStyle(wb,null,(short)9);
		 supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		 
		
		CellStyle fixednum_subtitle_style=getContentStyle(wb,null,(short)9);
		 fixednum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 fixednum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 fixednum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		 fixednum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle lastnum_subtitle_style=getContentStyle(wb,null,(short)9);
		 lastnum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 lastnum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 lastnum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		 lastnum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle nownum_subtitle_style=getContentStyle(wb,null,(short)9);
		 nownum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 nownum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 nownum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);
		 nownum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
//		 CellStyle supplementnum_subtitle_style=getContentStyle(wb,null);
//		 supplementnum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
//		 supplementnum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
//		 supplementnum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
//		 supplementnum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
//		 CellStyle memo_subtitle_style=getContentStyle(wb,null);
//		 memo_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 
		for(int i=2;i<lastrownum;i++){
			Row row=sheet.getRow(i);

				for(int j=0;j<type_group_end_num;j++){
					Cell cell=row.getCell(j);
					if(cell==null){
						//cell=row.createCell(j);
						continue;
					}
					if(j==6){
						cell.setCellStyle(fixednum_style);
					}
					if(j==7){
						cell.setCellStyle(lastnum_style);
					}
					if(j==16){
						cell.setCellStyle(nownum_style);
					}
					if(j==17){
						cell.setCellStyle(supplementnum_style);
					}
				}
			}
	}
	private StringBuilder[] sparepart_addRow1(XSSFWorkbook wb,Sheet sheet){
		 Row row = sheet.createRow(1);
		 
		 CellStyle black_style=getStyle(wb,IndexedColors.BLACK,null);
		 int cellnum=0;
		 Cell type_name=row.createCell(cellnum++);
		 type_name.setCellValue("大类");
		 type_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 600);
		 
		 Cell subtype_name=row.createCell(cellnum++);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 600);
		 
		 Cell brand_name=row.createCell(cellnum++);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 2400);
		 
		 Cell style=row.createCell(cellnum++);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 3600);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
		 Cell prod_name=row.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 3600);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
//		 Cell store_name=row.createCell(cellnum++);
//		 store_name.setCellValue("仓库");
//		 store_name.setCellStyle(black_style);
//		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*2*256);
		 
		 Cell unit=row.createCell(cellnum++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1,600);
		 
		 CellStyle fixednum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
		 fixednum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell fixednum=row.createCell(cellnum++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(fixednum_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		 
		 CellStyle lastnum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
		 lastnum_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell lastnum=row.createCell(cellnum++);
		 lastnum.setCellValue("上月结余数");
		 lastnum.setCellStyle(lastnum_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.LIGHT_BLUE,(short)9);
		 Cell purchasenum=row.createCell(cellnum++);
		 purchasenum.setCellValue("本期采购新增");
		 purchasenum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		
		 Cell oldnum=row.createCell(cellnum++);
		 oldnum.setCellValue("本期旧品新增");
		 oldnum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED,(short)9);
		 Cell installoutnum=row.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle green_style=getStyle(wb,IndexedColors.GREEN,(short)9);
		 Cell repairinnum=row.createCell(cellnum++);
		 repairinnum.setCellValue("本期维修返还数");
		 repairinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle orange_style=getStyle(wb,IndexedColors.ORANGE,(short)9);
		 Cell scrapoutnum=row.createCell(cellnum++);
		 scrapoutnum.setCellValue("报废出库数量");
		 scrapoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 Cell repairoutnum=row.createCell(cellnum++);
		 repairoutnum.setCellValue("维修出库数量");
		 repairoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle plum_style=getStyle(wb,IndexedColors.PLUM,(short)9);
		 Cell adjustoutnum=row.createCell(cellnum++);
		 adjustoutnum.setCellValue("本期借用数");
		 adjustoutnum.setCellStyle(plum_style);
		 sheet.setColumnWidth(cellnum-1,1800);
		 
		 Cell adjustinnum=row.createCell(cellnum++);
		 adjustinnum.setCellValue("本期归还数");
		 adjustinnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle nownum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(nownum_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
//		 CellStyle supplementnum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
//		 supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//		 supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		 Cell supplementnum=row.createCell(cellnum++);
//		 supplementnum.setCellValue("增补数量");
//		 supplementnum.setCellStyle(supplementnum_style);
//		 sheet.setColumnWidth(cellnum-1,1200);

		 Row row2 = sheet.createRow(2);
		for (int j = 0; j < cellnum; j++) {

			sheet.addMergedRegion(new CellRangeAddress(1, 2, (short) j, (short) j));
			// 同时设置第二行的单元格的样式
			Cell temp = row2.createCell(j);
			temp.setCellStyle(black_style);
		}
			
		
		 

			int cellnum_repeat=cellnum;
			//Row row2 = sheet.createRow(2);
			
			//
			StringBuilder purchasenum_formula=new StringBuilder("SUM(");
			StringBuilder oldnum_formula=new StringBuilder("SUM(");
			StringBuilder installoutnum_formula=new StringBuilder("SUM(");
			StringBuilder repairinnum_formula=new StringBuilder("SUM(");
			StringBuilder scrapoutnum_formula=new StringBuilder("SUM(");
			StringBuilder repairoutnum_formula=new StringBuilder("SUM(");
			StringBuilder adjustoutnum_formula=new StringBuilder("SUM(");
			StringBuilder adjustinnum_formula=new StringBuilder("SUM(");
			for(int j=1;j<=31;j++){
				 
				blue_style=getStyle_title(wb,IndexedColors.PALE_BLUE,(short)8);
				 Cell purchasenum_repeat=row2.createCell(cellnum_repeat++);
				 purchasenum_repeat.setCellValue("采购新增");
				 purchasenum_repeat.setCellStyle(blue_style);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 purchasenum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 Cell oldnum_repeat=row2.createCell(cellnum_repeat++);
				 oldnum_repeat.setCellValue("旧品新增");
				 oldnum_repeat.setCellStyle(blue_style);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 oldnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 red_style=getStyle_title(wb,IndexedColors.RED,(short)8);
				 Cell installoutnum_repeat=row2.createCell(cellnum_repeat++);
				 installoutnum_repeat.setCellValue("本期领用");
				 installoutnum_repeat.setCellStyle(red_style);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 installoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 green_style=getStyle_title(wb,IndexedColors.GREEN,(short)8);
				 Cell repairinnum_repeat=row2.createCell(cellnum_repeat++);
				 repairinnum_repeat.setCellValue("维修返还数");
				 repairinnum_repeat.setCellStyle(green_style);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 repairinnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 orange_style=getStyle_title(wb,IndexedColors.ORANGE,(short)8);
				 Cell scrapoutnum_repeat=row2.createCell(cellnum_repeat++);
				 scrapoutnum_repeat.setCellValue("报废出库");
				 scrapoutnum_repeat.setCellStyle(orange_style);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 scrapoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 Cell repairoutnum_repeat=row2.createCell(cellnum_repeat++);
				 repairoutnum_repeat.setCellValue("维修出库");
				 repairoutnum_repeat.setCellStyle(orange_style);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 repairoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 plum_style=getStyle_title(wb,IndexedColors.PLUM,(short)8);
				 Cell adjustoutnum_repeat=row2.createCell(cellnum_repeat++);
				 adjustoutnum_repeat.setCellValue("借用数");
				 adjustoutnum_repeat.setCellStyle(plum_style);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 adjustoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 green_style=getStyle_title(wb,IndexedColors.GREEN,(short)8);
				 Cell adjustinnum_repeat=row2.createCell(cellnum_repeat++);
				 adjustinnum_repeat.setCellValue("归还数");
				 adjustinnum_repeat.setCellStyle(green_style);
				 green_style.setBorderRight(CellStyle.BORDER_DOUBLE);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 adjustinnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");


				 if(j!=31){
					 purchasenum_formula.append(",");
					 oldnum_formula.append(",");
					 installoutnum_formula.append(",");
					 repairinnum_formula.append(",");
					 scrapoutnum_formula.append(",");
					 repairoutnum_formula.append(",");
					 adjustoutnum_formula.append(",");
					 adjustinnum_formula.append(",");
				 }
				 
				
			 }
			purchasenum_formula.append(")");
			oldnum_formula.append(")");
			installoutnum_formula.append(")");
			repairinnum_formula.append(")");
			scrapoutnum_formula.append(")");
			repairoutnum_formula.append(")");
			adjustoutnum_formula.append(")");
			adjustinnum_formula.append(")");

			

//			Cell memo = row2.createCell(cellnum_repeat++);
//			memo.setCellValue("备注");
//			memo.setCellStyle(black_style);
//			sheet.setColumnWidth(cellnum - 1, "列长".getBytes().length * 256);
			
			//合并单元格的行
			//Row row1 = sheet.createRow(1);
			//创建日期，并合并日期的两列
			black_style=getStyle_title(wb,IndexedColors.BLACK,null);
			cellnum_repeat=cellnum;
			for(int j=1;j<=31;j++){
				//合并这两个单元格
				int cellnum_repeat_temp=cellnum_repeat;
				cellnum_repeat=	cellnum_repeat+8;
				sheet.addMergedRegion(new CellRangeAddress(1,1,cellnum_repeat_temp,cellnum_repeat-1)); 
				//设置日期值
				Cell cell11=row.createCell(cellnum_repeat_temp);
				cell11.setCellValue(j);
				cell11.setCellStyle(black_style);
				//设置单个元样式
				cellnum_repeat_temp++;//第一格已经创建过了，不用再创建了
				for(;cellnum_repeat_temp<cellnum_repeat;cellnum_repeat_temp++){
					Cell cell12=row.createCell(cellnum_repeat_temp);
					cell12.setCellStyle(black_style);
					if(cellnum_repeat_temp==(cellnum_repeat-1)){
						black_style.setBorderRight(CellStyle.BORDER_DOUBLE);
					}
				}
				

			}

			 
			 //冻结行和列
			 sheet.createFreezePane(sparepart_month_freeze_num, 3);
			 
			 //生成本期新增数公式
			 StringBuilder[] formulas=new StringBuilder[]{purchasenum_formula,oldnum_formula,installoutnum_formula,repairinnum_formula,scrapoutnum_formula,repairoutnum_formula,adjustoutnum_formula,adjustinnum_formula};
			 return formulas;
		 
		 //sheet.createFreezePane(16, 2);
		
	}
	int sparepart_month_freeze_num=17;//在建仓库月冻结的列数
	int type_group_end_num=17;//小类和大类分组的结束列
	int day_of_month_num=31;
	@RequestMapping("/inventory/day/sparepart/excelTpl.do")
	public void excelTpl(HttpServletResponse response) throws IOException{
		
		//首先获取大类，小类的内容，然后按照格式输出，最后，设置压缩问题
		List<EquipmentType> equipmentTypes=equipmentTypeRepository.queryTypeAndSubtype();
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue("__________项目________年_________月备品备件仓库(仓库名称)盘点月报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		//f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_LEFT);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		//和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)type_group_end_num-1)); 
		
		
		//设置第一行,设置列标题
		StringBuilder[] formulas=sparepart_addRow1(wb,sheet);
		
		
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
		
		CellStyle content_style =getContentStyle(wb,null,(short)9);
		CellStyle content_blue_style =getContentStyle(wb,IndexedColors.LIGHT_BLUE,(short)9);	
		CellStyle content_red_style =getContentStyle(wb,IndexedColors.RED,(short)9);	
		CellStyle content_green_style =getContentStyle(wb,IndexedColors.GREEN,(short)9);
		CellStyle content_orange_style =getContentStyle(wb,IndexedColors.ORANGE,(short)9);
		CellStyle content_plum_style =getContentStyle(wb,IndexedColors.PLUM,(short)9);
		
		

		 
		 CellStyle content_subtitle_style =getContentStyle(wb,null,(short)9);
		 content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 //content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
		 
		
		
		
		int cellnum=0;
		int rownum=3;
		for(int i=0;i<equipmentTypes.size();i++){
			cellnum = 0;
			EquipmentType equipmentType = equipmentTypes.get(i);

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);
			
			Cell type_name = row.createCell(cellnum++);
			type_name.setCellValue(equipmentType.getName());
			type_name.setCellStyle(type_name_style);
			//subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,0,(short)type_group_end_num-1)); 
			sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,type_group_end_num,(short)type_group_end_num+2));
			 
			//描绘小类
			StringBuilder nownum_formule_builder=new StringBuilder();
			//StringBuilder supplementnum_formule_builder=new StringBuilder();
			if(equipmentType.getSubtypes()!=null) {
				int fromRow_type=rownum;
				for(EquipmentSubtype equipmentSubtype:equipmentType.getSubtypes()){
					cellnum=0;
					Row row_subtype = sheet.createRow(rownum++);
					
					cellnum++;
					
					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getName());
					subtype_name.setCellStyle(subtype_name_style);
					
					sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,1,(short)type_group_end_num-1)); 
					sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,type_group_end_num,(short)type_group_end_num+2));

					//弄几行模拟品名的数据，即几个空行
					int fromRow_subtype=rownum;
					for(int k=0;k<5;k++){
						nownum_formule_builder=new StringBuilder();
						nownum_formule_builder.append("SUM(");
						
						//supplementnum_formule_builder=new StringBuilder();
						
						cellnum=2;//从第3个单元格开始
						Row row_prod = sheet.createRow(rownum++);
						
						Cell brand_name=row_prod.createCell(cellnum++);
						 brand_name.setCellValue("测试");
						 brand_name.setCellStyle(content_style);
						 
						 Cell style=row_prod.createCell(cellnum++);
						 style.setCellValue("测试");
						 style.setCellStyle(content_style);
						 
						 Cell prod_name=row_prod.createCell(cellnum++);
						 prod_name.setCellValue("测试");
						 prod_name.setCellStyle(content_style);
						 
//						 Cell store_name=row_prod.createCell(cellnum++);
//						 store_name.setCellValue(sparepartMonthReport.getStore_name());
//						 store_name.setCellStyle(content_style);
						 
						 Cell unit=row_prod.createCell(cellnum++);
						 unit.setCellValue("台");
						 unit.setCellStyle(content_style);
						 
						 //额定数量
						 Cell fixednum=row_prod.createCell(cellnum++);
						 fixednum.setCellValue(1);
						 //supplementnum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 //fixednum.setCellStyle(content_style);
						 
						 //上月结余
						 Cell lastnum=row_prod.createCell(cellnum++);
						 lastnum.setCellValue(2);
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //lastnum.setCellStyle(content_style);
						 
						 //本期采购新增
						 Cell purchasenum=row_prod.createCell(cellnum++);
						// purchasenum.setCellValue(3);
						 purchasenum.setCellStyle(content_blue_style);
						 purchasenum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 
						 //本期旧品新增
						 Cell oldnum=row_prod.createCell(cellnum++);
						 //oldnum.setCellValue(4);
						 oldnum.setCellStyle(content_blue_style);
						 oldnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //本期领用数
						 Cell installoutnum=row_prod.createCell(cellnum++);
						 //installoutnum.setCellValue(5);
						 installoutnum.setCellStyle(content_red_style);
						 installoutnum.setCellFormula(formulas[2].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //本期维修返还数
						 Cell repairinnum=row_prod.createCell(cellnum++);
						 //repairinnum.setCellValue(6);
						 repairinnum.setCellStyle(content_green_style);
						 repairinnum.setCellFormula(formulas[3].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //报废出库
						 Cell scrapoutnum=row_prod.createCell(cellnum++);
						 //scrapoutnum.setCellValue(7);
						 scrapoutnum.setCellStyle(content_orange_style);
						 scrapoutnum.setCellFormula(formulas[4].toString().replaceAll("=", (rownum)+""));
						 //维修出库
						 Cell repairoutnum=row_prod.createCell(cellnum++);
						 //repairoutnum.setCellValue(8);
						 repairoutnum.setCellStyle(content_orange_style);
						 repairoutnum.setCellFormula(formulas[5].toString().replaceAll("=", (rownum)+""));
						 
						 //本期借用数
						 Cell adjustoutnum=row_prod.createCell(cellnum++);
						 //adjustoutnum.setCellValue(9);
						 adjustoutnum.setCellStyle(content_plum_style);
						 adjustoutnum.setCellFormula(formulas[6].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //本期归还数
						 Cell adjustinnum=row_prod.createCell(cellnum++);
						 //adjustinnum.setCellValue(10);
						 adjustinnum.setCellStyle(content_style);
						 adjustinnum.setCellFormula(formulas[7].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(")");
						 
						 Cell nownum=row_prod.createCell(cellnum++);
						 nownum.setCellFormula(nownum_formule_builder.toString());
						 //supplementnum_formule_builder.append("-"+CellReference.convertNumToColString(cellnum-1)+(rownum));
						 //nownum.setCellStyle(content_style);
						 
					 
						for (int j = 1; j <= day_of_month_num; j++) {
							CellStyle blue_style = getStyle(wb, IndexedColors.PALE_BLUE, null);
							Cell purchasenum_mx = row_prod.createCell(cellnum++);
							purchasenum_mx.setCellStyle(blue_style);
							purchasenum_mx.setCellValue(1);

							Cell oldnum_mx = row_prod.createCell(cellnum++);
							oldnum_mx.setCellStyle(blue_style);
							oldnum_mx.setCellValue(2);

							CellStyle red_style = getStyle(wb, IndexedColors.RED, null);
							Cell installoutnum_mx = row_prod.createCell(cellnum++);
							installoutnum_mx.setCellStyle(red_style);
							installoutnum_mx.setCellValue(3);

							CellStyle green_style = getStyle(wb, IndexedColors.GREEN, null);
							Cell repairinnum_mx = row_prod.createCell(cellnum++);
							repairinnum_mx.setCellStyle(green_style);
							repairinnum_mx.setCellValue(4);

							CellStyle orange_style = getStyle(wb, IndexedColors.ORANGE, null);
							Cell scrapoutnum_mx = row_prod.createCell(cellnum++);
							scrapoutnum_mx.setCellStyle(orange_style);
							scrapoutnum_mx.setCellValue(5);

							Cell repairoutnum_mx = row_prod.createCell(cellnum++);
							repairoutnum_mx.setCellStyle(orange_style);
							repairoutnum_mx.setCellValue(6);

							CellStyle plum_style = getStyle(wb, IndexedColors.PLUM, null);
							Cell adjustoutnum_mx = row_prod.createCell(cellnum++);
							adjustoutnum_mx.setCellStyle(plum_style);
							adjustoutnum_mx.setCellValue(7);

							green_style = getStyle(wb, IndexedColors.GREEN, null);
							Cell adjustinnum_mx = row_prod.createCell(cellnum++);
							green_style.setBorderRight(CellStyle.BORDER_DOUBLE);
							adjustinnum_mx.setCellStyle(green_style);
							adjustinnum_mx.setCellValue(8);		
							
						}
						 
						
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
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		init_background(wb,sheet, ++rownum);
		
		 String filename = "备品备件仓库盘点月报表_样式表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
	
}
