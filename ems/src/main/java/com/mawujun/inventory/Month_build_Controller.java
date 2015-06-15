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
public class Month_build_Controller {
	@Resource
	private EquipmentTypeRepository equipmentTypeRepository;
	/**
	 * 导出在建仓库月报表模板
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/inventory/month/build/excelTpl.do")
	public void excelTpl(HttpServletResponse response) throws IOException{
		//首先获取大类，小类的内容，然后按照格式输出，最后，设置压缩问题
		List<EquipmentType> equipmentTypes=equipmentTypeRepository.queryTypeAndSubtype();
		
		
		XSSFWorkbook wb =new XSSFWorkbook();

		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue("_______年_______月在建工程仓库(仓库名称)盘点月报表");
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
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,build_month_freeze_num-1)); 
		
		
		//设置第一行,设置列标题
		build_addRow1(wb, sheet);

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
		int rownum=2;
		for(int i=0;i<equipmentTypes.size();i++){
			cellnum = 0;
			EquipmentType equipmentType = equipmentTypes.get(i);
			
			//这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);	

			//这个标题，真闷提取出一行进行显示了
			Cell name = row.createCell(cellnum++);
			name.setCellValue(equipmentType.getName());
			name.setCellStyle(type_name_style);
			//subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,0,(short)10)); 
	
			//创建子类型的excel行
			
			if(equipmentType.getSubtypes()!=null) {
				int fromRow_type=rownum;
				for(EquipmentSubtype equipmentSubtype:equipmentType.getSubtypes()){
					cellnum=0;
					
					Row row_subtype = sheet.createRow(rownum++);
					
					//这个标题，真闷提取出一行进行显示了
					Cell type_name = row_subtype.createCell(cellnum++);
					//type_name.setCellValue("    "+equipmentSubtype.getName());
					//type_name.setCellStyle(subtype_name_style);
					
					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getName());
					subtype_name.setCellStyle(subtype_name_style);
					
					sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,1,(short)10)); 
					
					//弄几行模拟品名的数据，即几个空行
					int fromRow_subtype=rownum;
					for(int k=0;k<5;k++){
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
						//lastnum.setCellStyle(lastnum_style);
			
						Cell storeinnum = row_prod.createCell(cellnum++);
						storeinnum.setCellValue(2);
						storeinnum.setCellStyle(blue_style);
						//storeinnum.setCellStyle(content_style);
			
						Cell installoutnum = row_prod.createCell(cellnum++);
						installoutnum.setCellValue(3);
						installoutnum.setCellStyle(red_style);
						installoutnum.setCellStyle(content_style);
						// 本月结余数
						Cell nownum = row_prod.createCell(cellnum++);
						// nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
						nownum.setCellFormula("SUM("
								+ CellReference.convertNumToColString(6) + (rownum )
								+ "," + CellReference.convertNumToColString(7)+(rownum ) 
								+ ","+ CellReference.convertNumToColString(8) + (rownum )
								+ ")");
						//nownum.setCellStyle(nownum_style);
			
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
		
		String filename = "在建仓库月报表_样式表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
	}
	
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
		
		for(int i=2;i<lastrownum;i++){
			Row row=sheet.getRow(i);
			
			if(i==2){
				for(int j=0;j<11;j++){
					Cell cell=row.getCell(j);
					if(cell==null){
						//cell=row.createCell(j);
						continue;
					}
					if(j==6){
						cell.setCellStyle(lastnum_subtitle_style);
					}
					if(j==9){
						cell.setCellStyle(nownum_subtitle_style);
					}
					if(j==10){
						cell.setCellStyle(memo_subtitle_style);
					}
				}
				
			} else {
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
	
	int build_month_freeze_num=6;//在建仓库月冻结的列数
	int build_day_freeze_num=6;//在建仓库日报表冻结的列数
	private void build_addRow1(XSSFWorkbook wb,Sheet sheet){
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
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*15*256);
		 
		 Cell prod_name=row.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*10*256);
		 
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
		 
		 Cell memo=row.createCell(cellnum++);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
		 
		 sheet.createFreezePane(build_month_freeze_num, 2);
	}
}
