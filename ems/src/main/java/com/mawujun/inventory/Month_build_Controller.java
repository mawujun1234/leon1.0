package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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

import com.mawujun.baseinfo.Store;

@Controller
public class Month_build_Controller {
	/**
	 * 导出在建仓库月报表模板
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/inventory/month/build/excelTpl.do")
	public void excelTpl(HttpServletResponse response) throws IOException{
		Store store=storeService.get(store_id);
		List<MonthInventoryVO> list=queryMonthReport(store_id,year,month);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue(year+"年"+month+"月在建工程仓库("+store.getName()+")盘点月报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		//f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		//和并标题单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,build_month_freeze_num-1)); 
		
		//设置第一行,设置列标题
		build_addRow1(wb, sheet);

		CellStyle blue_style = getStyle(wb, IndexedColors.LIGHT_BLUE,null);
		
		CellStyle red_style = getStyle(wb, IndexedColors.RED,null);

		
		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.BLACK,null);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setWrapText(false);
		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle content_style =getContentStyle(wb,null);
		
		 CellStyle content_subtitle_style =getContentStyle(wb,null);
		 content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 //content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
		
		

		
		 //循环出数据
		int cellnum=0;
		int extra_row_num=2;
		String subtype_id_temp="";
		int fromRow=0;//开始分组的行
		int rownum=0;
		for(int i=0;i<list.size();i++){
			cellnum = 0;
			MonthInventoryVO buildDayReport = list.get(i);
			rownum = i + extra_row_num;
			//判断还是不是同个小类，如果不是同个小类就添加一行，只有小类名称的行
			if(!subtype_id_temp.equals(buildDayReport.getSubtype_id())){
				Row row = sheet.createRow(rownum);
				Cell subtype_name = row.createCell(cellnum);
				subtype_name.setCellValue(buildDayReport.getSubtype_name());
				subtype_name.setCellStyle(subtype_name_style);
				
				
				//Cell lastnum_cell=null;
				for(int j=1;j<11;j++){
					Cell cell = row.createCell(cellnum+j);
					cell.setCellStyle(content_subtitle_style);
//					if(j==6){
//						cell.setCellStyle(lastnum_subtitle_style);
//					}
//					if(j==9){
//						cell.setCellStyle(nownum_subtitle_style);
//					}
//					if(j==10){
//						cell.setCellStyle(memo_subtitle_style);
//					}
				}
				//同时合并单元格
				//sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,0,(short)10)); 
				
				//分组
				if(fromRow!=0){
					sheet.groupRow(fromRow, rownum-1);//-2是因为后面又++了
					sheet.setRowGroupCollapsed(fromRow, true);
				}
				fromRow=rownum+1;
				
				subtype_id_temp=buildDayReport.getSubtype_id();
				rownum++;
				extra_row_num++;
			}
			
			//这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum);
			//当循环到最后一行的时候，添加最后一个分组
			if(list.size()-1==i){
				sheet.groupRow(fromRow, rownum);
				sheet.setRowGroupCollapsed(fromRow, true);
			}
			
			
			

			//这个标题，真闷提取出一行进行显示了
			Cell subtype_name = row.createCell(cellnum++);
			subtype_name.setCellStyle(content_style);
			//subtype_name.setCellValue(buildDayReport.getSubtype_name());

			Cell brand_name = row.createCell(cellnum++);
			brand_name.setCellValue(buildDayReport.getBrand_name());
			brand_name.setCellStyle(content_style);

			Cell style = row.createCell(cellnum++);
			style.setCellValue(buildDayReport.getStyle());
			style.setCellStyle(content_style);

			Cell prod_name = row.createCell(cellnum++);
			prod_name.setCellValue(buildDayReport.getProd_name());
			prod_name.setCellStyle(content_style);

			Cell store_name = row.createCell(cellnum++);
			store_name.setCellValue(buildDayReport.getStore_name());
			store_name.setCellStyle(content_style);

			Cell unit = row.createCell(cellnum++);
			unit.setCellValue(buildDayReport.getUnit());
			unit.setCellStyle(content_style);

			Cell lastnum = row.createCell(cellnum++);
			lastnum.setCellValue(buildDayReport.getLastnum() == null ? 0
					: buildDayReport.getLastnum());
			//lastnum.setCellStyle(lastnum_style);

			Cell storeinnum = row.createCell(cellnum++);
			storeinnum.setCellValue(buildDayReport.getNowAdd() == null ? 0
					: buildDayReport.getNowAdd());
			storeinnum.setCellStyle(blue_style);
			//storeinnum.setCellStyle(content_style);

			Cell installoutnum = row.createCell(cellnum++);
			installoutnum.setCellValue(buildDayReport.getInstalloutnum() == null ? 0
							: buildDayReport.getInstalloutnum());
			installoutnum.setCellStyle(red_style);
			//installoutnum.setCellStyle(content_style);
			// 本月结余数
			Cell nownum = row.createCell(cellnum++);
			// nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
			nownum.setCellFormula("SUM("
					+ CellReference.convertNumToColString(6) + (rownum + 1)
					+ "," + CellReference.convertNumToColString(7)
					+ (rownum + 1) + ","
					+ CellReference.convertNumToColString(8) + (rownum + 1)
					+ ")");
			//nownum.setCellStyle(nownum_style);

			Cell memo = row.createCell(cellnum++);
			memo.setCellValue(buildDayReport.getMemo());
			memo.setCellStyle(content_style);
			
			
		}
		//定义收缩的方向
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		init_build_background(wb,sheet,++rownum);
		
		String filename = "在建工程仓库("+store.getName()+")盘点月报表.xlsx";
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
		 Cell subtype_name=row.createCell(cellnum++);
		 subtype_name.setCellValue("类别");
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
		 
		 Cell store_name=row.createCell(cellnum++);
		 store_name.setCellValue("仓库");
		 store_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*2*256);
		 
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
