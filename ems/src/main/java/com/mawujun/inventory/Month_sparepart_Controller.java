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
import com.mawujun.baseinfo.Store;

@Controller
public class Month_sparepart_Controller {
	@Resource
	private EquipmentTypeRepository equipmentTypeRepository;
	
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
	

	private void sparepart_addRow1(XSSFWorkbook wb,Sheet sheet){
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
		 sheet.setColumnWidth(cellnum-1, 3000);
		 
		 Cell style=row.createCell(cellnum++);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*8*256);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
		 Cell prod_name=row.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*8*256);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
//		 Cell store_name=row.createCell(cellnum++);
//		 store_name.setCellValue("仓库");
//		 store_name.setCellStyle(black_style);
//		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*2*256);
		 
		 Cell unit=row.createCell(cellnum++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1,600);
		 
		 CellStyle fixednum_style=getStyle(wb,IndexedColors.BLACK,null);
		 fixednum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell fixednum=row.createCell(cellnum++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(fixednum_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		 
		 CellStyle lastnum_style=getStyle(wb,IndexedColors.BLACK,null);
		 lastnum_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell lastnum=row.createCell(cellnum++);
		 lastnum.setCellValue("上月结余数");
		 lastnum.setCellStyle(lastnum_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.LIGHT_BLUE,null);
		 Cell purchasenum=row.createCell(cellnum++);
		 purchasenum.setCellValue("本期采购新增");
		 purchasenum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		
		 Cell oldnum=row.createCell(cellnum++);
		 oldnum.setCellValue("本期旧品新增");
		 oldnum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED,null);
		 Cell installoutnum=row.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle green_style=getStyle(wb,IndexedColors.GREEN,null);
		 Cell repairinnum=row.createCell(cellnum++);
		 repairinnum.setCellValue("本期维修返还数");
		 repairinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle orange_style=getStyle(wb,IndexedColors.ORANGE,null);
		 Cell scrapoutnum=row.createCell(cellnum++);
		 scrapoutnum.setCellValue("报废出库数量");
		 scrapoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 Cell repairoutnum=row.createCell(cellnum++);
		 repairoutnum.setCellValue("维修出库数量");
		 repairoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle plum_style=getStyle(wb,IndexedColors.PLUM,null);
		 Cell adjustoutnum=row.createCell(cellnum++);
		 adjustoutnum.setCellValue("本期借用数");
		 adjustoutnum.setCellStyle(plum_style);
		 sheet.setColumnWidth(cellnum-1,1800);
		 
		 Cell adjustinnum=row.createCell(cellnum++);
		 adjustinnum.setCellValue("本期归还数");
		 adjustinnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle nownum_style=getStyle(wb,IndexedColors.BLACK,null);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(nownum_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle supplementnum_style=getStyle(wb,IndexedColors.BLACK,null);
		 supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell supplementnum=row.createCell(cellnum++);
		 supplementnum.setCellValue("增补数量");
		 supplementnum.setCellStyle(supplementnum_style);
		 sheet.setColumnWidth(cellnum-1,1200);

		 Cell memo=row.createCell(cellnum++);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 2400);
		 
		 //sheet.createFreezePane(16, 2);
		 sheet.createFreezePane(sparepart_month_freeze_num, 2);
	}
	int sparepart_month_freeze_num=15;//在建仓库月冻结的列数
	int type_group_end_num=15;//小类和大类分组的结束列
	@RequestMapping("/inventory/month/sparepart/excelTpl.do")
	public void excelTpl(HttpServletResponse response) throws IOException{
		
		//首先获取大类，小类的内容，然后按照格式输出，最后，设置压缩问题
		List<EquipmentType> equipmentTypes=equipmentTypeRepository.queryTypeAndSubtype();
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue("____________项目________年_________月备品备件仓库(仓库名称)盘点月报表");
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
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)type_group_end_num)); 
		
		
		//设置第一行,设置列标题
		sparepart_addRow1(wb,sheet);
		
		
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
		
		CellStyle fixednum_style=getContentStyle(wb,null);
		 fixednum_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		 fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		CellStyle lastnum_style=getContentStyle(wb,null);
		 lastnum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle nownum_style=getContentStyle(wb,null);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		 CellStyle supplementnum_style=getContentStyle(wb,null);
		 supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		CellStyle content_style =getContentStyle(wb,null);
		CellStyle content_blue_style =getContentStyle(wb,IndexedColors.LIGHT_BLUE);	
		CellStyle content_red_style =getContentStyle(wb,IndexedColors.RED);	
		CellStyle content_green_style =getContentStyle(wb,IndexedColors.GREEN);
		CellStyle content_orange_style =getContentStyle(wb,IndexedColors.ORANGE);
		CellStyle content_plum_style =getContentStyle(wb,IndexedColors.PLUM);
		
		

		 
		 CellStyle content_subtitle_style =getContentStyle(wb,null);
		 content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 //content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
		 
		
		
		
		int cellnum=0;
		int rownum=2;
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
			StringBuilder supplementnum_formule_builder=new StringBuilder();
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
						
						supplementnum_formule_builder=new StringBuilder();
						//nownum_formule_builder.append("SUM(");
						
						cellnum=2;//从第3个单元格开始
						Row row_prod = sheet.createRow(rownum++);
						
						Cell brand_name=row_prod.createCell(cellnum++);
						 //brand_name.setCellValue("测试");
						 brand_name.setCellStyle(content_style);
						 
						 Cell style=row_prod.createCell(cellnum++);
						 //style.setCellValue("测试");
						 style.setCellStyle(content_style);
						 
						 Cell prod_name=row_prod.createCell(cellnum++);
						// prod_name.setCellValue("测试");
						 prod_name.setCellStyle(content_style);
						 
//						 Cell store_name=row_prod.createCell(cellnum++);
//						 store_name.setCellValue(sparepartMonthReport.getStore_name());
//						 store_name.setCellStyle(content_style);
						 
						 Cell unit=row_prod.createCell(cellnum++);
						 //unit.setCellValue("台");
						 unit.setCellStyle(content_style);
						 
						 //额定数量
						 Cell fixednum=row_prod.createCell(cellnum++);
						 //fixednum.setCellValue(1);
						 fixednum.setCellStyle(fixednum_style);
						 supplementnum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						
						 
						 //上月结余
						 Cell lastnum=row_prod.createCell(cellnum++);
						 //lastnum.setCellValue(2);
						 lastnum.setCellStyle(lastnum_style);
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 
						 
						 //本期采购新增
						 Cell purchasenum=row_prod.createCell(cellnum++);
						 //purchasenum.setCellValue(3);
						 purchasenum.setCellStyle(content_blue_style);
						 nownum_formule_builder.append(",");
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 
						 //本期旧品新增
						 Cell oldnum=row_prod.createCell(cellnum++);
						 //oldnum.setCellValue(4);
						 oldnum.setCellStyle(content_blue_style);
						 nownum_formule_builder.append(",");
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 //本期领用数
						 Cell installoutnum=row_prod.createCell(cellnum++);
						 //installoutnum.setCellValue(5);
						 installoutnum.setCellStyle(content_red_style);
						 nownum_formule_builder.append(",");
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 //本期维修返还数
						 Cell repairinnum=row_prod.createCell(cellnum++);
						 //repairinnum.setCellValue(6);
						 repairinnum.setCellStyle(content_green_style);
						 nownum_formule_builder.append(",");
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 //报废出库
						 Cell scrapoutnum=row_prod.createCell(cellnum++);
						 //scrapoutnum.setCellValue(7);
						 scrapoutnum.setCellStyle(content_orange_style);
						 //维修出库
						 Cell repairoutnum=row_prod.createCell(cellnum++);
						 //repairoutnum.setCellValue(8);
						 repairoutnum.setCellStyle(content_orange_style);
						 //本期借用数
						 Cell adjustoutnum=row_prod.createCell(cellnum++);
						// adjustoutnum.setCellValue(9);
						 adjustoutnum.setCellStyle(content_plum_style);
						 nownum_formule_builder.append(",");
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 //本期归还数
						 Cell adjustinnum=row_prod.createCell(cellnum++);
						 //adjustinnum.setCellValue(10);
						 adjustinnum.setCellStyle(content_style);
						 nownum_formule_builder.append(",");
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(")");
						 
						 Cell nownum=row_prod.createCell(cellnum++);
						 nownum.setCellFormula(nownum_formule_builder.toString());
						 nownum.setCellStyle(nownum_style);
						 supplementnum_formule_builder.append("-"+CellReference.convertNumToColString(cellnum-1)+(rownum));
						 
						 
						 Cell supplementnum=row_prod.createCell(cellnum++);
						 //supplementnum.setCellValue(12);
						 supplementnum.setCellStyle(supplementnum_style);
						 supplementnum.setCellFormula(supplementnum_formule_builder.toString());
						 
					 
						 
						 Cell memo=row_prod.createCell(cellnum++);
						 memo.setCellValue(""); 
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
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
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