package com.mawujun.inventory;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;

@Controller
public class MonthInventoryController {
	//@Autowired
	//private DayInventoryService dayInventoryService;
	@Autowired
	private MonthInventoryService monthInventoryService;
	@Autowired
	private StoreService storeService;
	
	/**
	 * 在建仓库的月报表 计算
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param year
	 * @param month
	 * @param store_id
	 * @return
	 */
	@RequestMapping("/monthinventory/call_proc.do")
	public String call_proc(String store_id,boolean isbuild){
		monthInventoryService.call_proc(store_id,isbuild);
		return "success";	
	}
	
	
	/**
	 * 在建仓库的越报表
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param year
	 * @param month
	 * @param store_id
	 * @return
	 */
	@RequestMapping("/monthinventory/queryMonthReport.do")
	public List<MonthInventoryVO> queryMonthReport(String store_id,String year,String month){
		List<MonthInventoryVO> list=monthInventoryService.queryMonthReport(store_id,year+month);
		return list;	
	}
	
//	/**
//	 * 备品备件仓库的月报表
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param year
//	 * @param month
//	 * @param store_id
//	 * @return
//	 */
//	@RequestMapping("/monthinventory/querySparepartMonthReport.do")
//	public List<MonthInventoryVO> querySparepartMonthReport(String store_id,String year,String month){
//		//String sql="select * from report_buildmonthreport where month='"+month+"' and store_id='"+store_id+"'";
//		
//		List<MonthInventoryVO> list=monthInventoryService.querySparepartMonthReport(store_id, year+month);
//		return list;
//		
//	}
	
	@RequestMapping("/monthinventory/update.do")
	public String update(String field,String value,MonthInventory_PK key){
		Params params=Params.init().add(M.MonthInventory.monthkey, key.getMonthkey())
		.add(M.MonthInventory.subtype_id, key.getSubtype_id())
		.add(M.MonthInventory.prod_id, key.getProd_id())
		.add(M.MonthInventory.brand_id, key.getBrand_id())
		.add(M.MonthInventory.style, key.getStyle())
		.add(M.MonthInventory.store_id, key.getStore_id())
		.add("field", field)
		.add("value", value);
		
		
		monthInventoryService.updateField(params);
		return "success";
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
	@RequestMapping("/monthinventory/build/export.do")
	public void build_export(HttpServletResponse response,String store_id,String year,String month) throws IOException{
		
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
	
	/**
	 * =================================
	 * =================================
	 * =================================
	 * =================================  下面的是  备品备件仓库的导出等功能
	 * =================================
	 * =================================
	 * =================================
	 */
	
	
	private void sparepart_addRow1(XSSFWorkbook wb,Sheet sheet){
		 Row row = sheet.createRow(1);
		 
		 CellStyle black_style=getStyle(wb,IndexedColors.BLACK,null);
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
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*10*256);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
		 Cell prod_name=row.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*10*256);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
		 Cell store_name=row.createCell(cellnum++);
		 store_name.setCellValue("仓库");
		 store_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*2*256);
		 
		 Cell unit=row.createCell(cellnum++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*1*256);
		 
		 CellStyle fixednum_style=getStyle(wb,IndexedColors.BLACK,null);
		 fixednum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell fixednum=row.createCell(cellnum++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(fixednum_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle lastnum_style=getStyle(wb,IndexedColors.BLACK,null);
		 lastnum_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell lastnum=row.createCell(cellnum++);
		 lastnum.setCellValue("上月结余");
		 lastnum.setCellStyle(lastnum_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.LIGHT_BLUE,null);
		 Cell purchasenum=row.createCell(cellnum++);
		 purchasenum.setCellValue("本期采购新增");
		 purchasenum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, "列长长".getBytes().length*256);
		
		 Cell oldnum=row.createCell(cellnum++);
		 oldnum.setCellValue("旧品新增");
		 oldnum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED,null);
		 Cell installoutnum=row.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用");
		 installoutnum.setCellStyle(red_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle green_style=getStyle(wb,IndexedColors.GREEN,null);
		 Cell repairinnum=row.createCell(cellnum++);
		 repairinnum.setCellValue("本期维修返还数");
		 repairinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, "列长长".getBytes().length*256);
		 
		 CellStyle orange_style=getStyle(wb,IndexedColors.ORANGE,null);
		 Cell scrapoutnum=row.createCell(cellnum++);
		 scrapoutnum.setCellValue("报废出库数量");
		 scrapoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, "列长长".getBytes().length*256);
		 
		 Cell repairoutnum=row.createCell(cellnum++);
		 repairoutnum.setCellValue("维修出库数量");
		 repairoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, "列长长".getBytes().length*256);
		 
		 CellStyle plum_style=getStyle(wb,IndexedColors.PLUM,null);
		 Cell adjustoutnum=row.createCell(cellnum++);
		 adjustoutnum.setCellValue("本期借用数");
		 adjustoutnum.setCellStyle(plum_style);
		 sheet.setColumnWidth(cellnum-1, "列长长".getBytes().length*256);
		 
		 Cell adjustinnum=row.createCell(cellnum++);
		 adjustinnum.setCellValue("本期归还数");
		 adjustinnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长长".getBytes().length*256);
		 
		 CellStyle nownum_style=getStyle(wb,IndexedColors.BLACK,null);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余");
		 nownum.setCellStyle(nownum_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle supplementnum_style=getStyle(wb,IndexedColors.BLACK,null);
		 supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell supplementnum=row.createCell(cellnum++);
		 supplementnum.setCellValue("增补数量");
		 supplementnum.setCellStyle(supplementnum_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);

		 Cell memo=row.createCell(cellnum++);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 //sheet.createFreezePane(16, 2);
	}
	@RequestMapping("/monthinventory/sparepart/export.do")
	public void sparepart_export(HttpServletResponse response,String year,String month,String store_id) throws IOException{
		
		Store store=storeService.get(store_id);
		List<MonthInventoryVO> list=queryMonthReport(store_id,year,month);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue(year+"年"+month+"月备品备件仓库("+store.getName()+")盘点月报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		//f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		//和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)10)); 
		
		//设置第一行,设置列标题
		sparepart_addRow1(wb,sheet);
		
		
		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.BLACK,null);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setWrapText(false);
		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);
		
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
		int extra_row_num=2;
		String subtype_id_temp="";
		int fromRow=0;//开始分组的行
		int rownum=0;
		for(int i=0;i<list.size();i++){
			MonthInventoryVO sparepartMonthReport = list.get(i);
			rownum = i + extra_row_num;
			cellnum = 0;

			// 判断还是不是同个小类，如果不是同个小类就添加一行，只有小类名称的行
			if (!subtype_id_temp.equals(sparepartMonthReport.getSubtype_id())) {
				Row row = sheet.createRow(rownum);
				Cell subtype_name = row.createCell(cellnum);
				subtype_name.setCellValue(sparepartMonthReport
						.getSubtype_name());
				subtype_name.setCellStyle(subtype_name_style);
				for (int j = 1; j < 19; j++) {
					Cell cell = row.createCell(cellnum + j);
					cell.setCellStyle(content_subtitle_style);

				}
				// 同时合并单元格
				//sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,(short) 9));
				// 分组
				if (fromRow != 0) {
					sheet.groupRow(fromRow, rownum - 1);// -2是因为后面又++了
					sheet.setRowGroupCollapsed(fromRow, true);
				}
				fromRow = rownum + 1;

				subtype_id_temp = sparepartMonthReport.getSubtype_id();
				rownum++;
				extra_row_num++;
			}

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum);
			// 当循环到最后一行的时候，添加最后一个分组
			if (list.size() - 1 == i) {
				sheet.groupRow(fromRow, rownum);
				sheet.setRowGroupCollapsed(fromRow, true);
			}
			 
			 Cell subtype_name=row.createCell(cellnum++);
			 //subtype_name.setCellValue(sparepartMonthReport.getSubtype_name());
			 subtype_name.setCellStyle(content_style);
			 
			 Cell brand_name=row.createCell(cellnum++);
			 brand_name.setCellValue(sparepartMonthReport.getBrand_name());
			 brand_name.setCellStyle(content_style);
			 
			 Cell style=row.createCell(cellnum++);
			 style.setCellValue(sparepartMonthReport.getStyle());
			 style.setCellStyle(content_style);
			 
			 Cell prod_name=row.createCell(cellnum++);
			 prod_name.setCellValue(sparepartMonthReport.getProd_name());
			 prod_name.setCellStyle(content_style);
			 
			 Cell store_name=row.createCell(cellnum++);
			 store_name.setCellValue(sparepartMonthReport.getStore_name());
			 store_name.setCellStyle(content_style);
			 
			 Cell unit=row.createCell(cellnum++);
			 unit.setCellValue(sparepartMonthReport.getUnit());
			 unit.setCellStyle(content_style);
			 
			 Cell fixednum=row.createCell(cellnum++);
			 fixednum.setCellValue(sparepartMonthReport.getFixednum()==null?0:sparepartMonthReport.getFixednum());
			 //fixednum.setCellStyle(content_style);
			 
			 Cell lastnum=row.createCell(cellnum++);
			 lastnum.setCellValue(sparepartMonthReport.getLastnum()==null?0:sparepartMonthReport.getLastnum());
			 //lastnum.setCellStyle(content_style);
			 
			 Cell purchasenum=row.createCell(cellnum++);
			 purchasenum.setCellValue(sparepartMonthReport.getPurchasenum()==null?0:sparepartMonthReport.getPurchasenum());
			 purchasenum.setCellStyle(content_blue_style);
			 
			 Cell oldnum=row.createCell(cellnum++);
			 oldnum.setCellValue(sparepartMonthReport.getOldnum()==null?0:sparepartMonthReport.getOldnum());
			 oldnum.setCellStyle(content_blue_style);
			 
			 Cell installoutnum=row.createCell(cellnum++);
			 installoutnum.setCellValue(sparepartMonthReport.getInstalloutnum()==null?0:sparepartMonthReport.getInstalloutnum());
			 installoutnum.setCellStyle(content_red_style);
			 
			 Cell repairinnum=row.createCell(cellnum++);
			 repairinnum.setCellValue(sparepartMonthReport.getRepairinnum()==null?0:sparepartMonthReport.getRepairinnum());
			 repairinnum.setCellStyle(content_green_style);
			 
			 Cell scrapoutnum=row.createCell(cellnum++);
			 scrapoutnum.setCellValue(sparepartMonthReport.getScrapoutnum()==null?0:sparepartMonthReport.getScrapoutnum());
			 scrapoutnum.setCellStyle(content_orange_style);
			 
			 Cell repairoutnum=row.createCell(cellnum++);
			 repairoutnum.setCellValue(sparepartMonthReport.getRepairoutnum()==null?0:sparepartMonthReport.getRepairoutnum());
			 repairoutnum.setCellStyle(content_orange_style);
			 
			 Cell adjustoutnum=row.createCell(cellnum++);
			 adjustoutnum.setCellValue(sparepartMonthReport.getAdjustoutnum()==null?0:sparepartMonthReport.getAdjustoutnum());
			 adjustoutnum.setCellStyle(content_plum_style);
			 
			 Cell adjustinnum=row.createCell(cellnum++);
			 adjustinnum.setCellValue(sparepartMonthReport.getAdjustinnum()==null?0:sparepartMonthReport.getAdjustinnum());
			 adjustinnum.setCellStyle(content_style);
			 
			 Cell nownum=row.createCell(cellnum++);
			 nownum.setCellValue(sparepartMonthReport.getNownum()==null?0:sparepartMonthReport.getNownum());
			 //nownum.setCellStyle(content_style);
			 
			 Cell supplementnum=row.createCell(cellnum++);
			 supplementnum.setCellValue(sparepartMonthReport.getSupplementnum()==null?0:sparepartMonthReport.getSupplementnum());
			// supplementnum.setCellStyle(content_style);
		 
			 
			 Cell memo=row.createCell(cellnum++);
			 memo.setCellValue(sparepartMonthReport.getMemo()); 
			 memo.setCellStyle(content_style);
		 }
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		init_background(wb,sheet, ++rownum);
		
		 String filename = "备品备件仓库("+store.getName()+")盘点月报表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
	
	public void init_background(XSSFWorkbook wb ,Sheet sheet,int lastrownum){
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
//		 
		
		CellStyle fixednum_subtitle_style=getContentStyle(wb,null);
		 fixednum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 fixednum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 fixednum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		 fixednum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
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
		 
		 CellStyle supplementnum_subtitle_style=getContentStyle(wb,null);
		 supplementnum_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 supplementnum_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 supplementnum_subtitle_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		 supplementnum_subtitle_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle memo_subtitle_style=getContentStyle(wb,null);
		 memo_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 
		for(int i=2;i<lastrownum;i++){
			Row row=sheet.getRow(i);
			
			if(i==2){
				for(int j=0;j<18;j++){
					Cell cell=row.getCell(j);
					if(cell==null){
						cell=row.createCell(j);
					}
					if(j==6){
						cell.setCellStyle(fixednum_subtitle_style);
					}
					if(j==7){
						cell.setCellStyle(lastnum_subtitle_style);
					}
					if(j==16){
						cell.setCellStyle(nownum_subtitle_style);
					}
					if(j==17){
						cell.setCellStyle(supplementnum_subtitle_style);
					}
				}
				
			} else {
				for(int j=0;j<18;j++){
					Cell cell=row.getCell(j);
					if(cell==null){
						cell=row.createCell(j);
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
	}
	
	/**
	 * 导出在建仓库月报表模板
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/monthinventory/build/excelTpl.do")
	public void excelTpl(HttpServletResponse response) throws IOException{
		//Store store=storeService.get(store_id);
		//List<MonthInventoryVO> list=queryMonthReport(store_id,year,month);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue("在建仓库盘点月报表_模板");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		//f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		//和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)10)); 
		
		//设置第一行,设置列标题
		sparepart_addRow1(wb,sheet);
		
		
		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.BLACK,null);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setWrapText(false);
		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);
		
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
		 
		
		List<MonthInventoryVO> list=new ArrayList<MonthInventoryVO>();
		
		int cellnum=0;
		int extra_row_num=2;
		String subtype_id_temp="";
		int fromRow=0;//开始分组的行
		int rownum=0;
		for(int i=0;i<list.size();i++){
			//MonthInventoryVO sparepartMonthReport = list.get(i);
			rownum = i + extra_row_num;
			cellnum = 0;

			// 判断还是不是同个小类，如果不是同个小类就添加一行，只有小类名称的行
			if (!subtype_id_temp.equals(sparepartMonthReport.getSubtype_id())) {
				Row row = sheet.createRow(rownum);
				Cell subtype_name = row.createCell(cellnum);
				subtype_name.setCellValue(sparepartMonthReport.getSubtype_name());
				subtype_name.setCellStyle(subtype_name_style);
				for (int j = 1; j < 19; j++) {
					Cell cell = row.createCell(cellnum + j);
					cell.setCellStyle(content_subtitle_style);

				}
				// 同时合并单元格
				//sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,(short) 9));
				// 分组
				if (fromRow != 0) {
					sheet.groupRow(fromRow, rownum - 1);// -2是因为后面又++了
					sheet.setRowGroupCollapsed(fromRow, true);
				}
				fromRow = rownum + 1;

				subtype_id_temp = sparepartMonthReport.getSubtype_id();
				rownum++;
				extra_row_num++;
			}

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum);
			// 当循环到最后一行的时候，添加最后一个分组
			if (list.size() - 1 == i) {
				sheet.groupRow(fromRow, rownum);
				sheet.setRowGroupCollapsed(fromRow, true);
			}
			 
			 Cell subtype_name=row.createCell(cellnum++);
			 //subtype_name.setCellValue(sparepartMonthReport.getSubtype_name());
			 subtype_name.setCellStyle(content_style);
			 
			 Cell brand_name=row.createCell(cellnum++);
			 brand_name.setCellValue(sparepartMonthReport.getBrand_name());
			 brand_name.setCellStyle(content_style);
			 
			 Cell style=row.createCell(cellnum++);
			 style.setCellValue(sparepartMonthReport.getStyle());
			 style.setCellStyle(content_style);
			 
			 Cell prod_name=row.createCell(cellnum++);
			 prod_name.setCellValue(sparepartMonthReport.getProd_name());
			 prod_name.setCellStyle(content_style);
			 
			 Cell store_name=row.createCell(cellnum++);
			 store_name.setCellValue(sparepartMonthReport.getStore_name());
			 store_name.setCellStyle(content_style);
			 
			 Cell unit=row.createCell(cellnum++);
			 unit.setCellValue(sparepartMonthReport.getUnit());
			 unit.setCellStyle(content_style);
			 
			 Cell fixednum=row.createCell(cellnum++);
			 fixednum.setCellValue(sparepartMonthReport.getFixednum()==null?0:sparepartMonthReport.getFixednum());
			 //fixednum.setCellStyle(content_style);
			 
			 Cell lastnum=row.createCell(cellnum++);
			 lastnum.setCellValue(sparepartMonthReport.getLastnum()==null?0:sparepartMonthReport.getLastnum());
			 //lastnum.setCellStyle(content_style);
			 
			 Cell purchasenum=row.createCell(cellnum++);
			 purchasenum.setCellValue(sparepartMonthReport.getPurchasenum()==null?0:sparepartMonthReport.getPurchasenum());
			 purchasenum.setCellStyle(content_blue_style);
			 
			 Cell oldnum=row.createCell(cellnum++);
			 oldnum.setCellValue(sparepartMonthReport.getOldnum()==null?0:sparepartMonthReport.getOldnum());
			 oldnum.setCellStyle(content_blue_style);
			 
			 Cell installoutnum=row.createCell(cellnum++);
			 installoutnum.setCellValue(sparepartMonthReport.getInstalloutnum()==null?0:sparepartMonthReport.getInstalloutnum());
			 installoutnum.setCellStyle(content_red_style);
			 
			 Cell repairinnum=row.createCell(cellnum++);
			 repairinnum.setCellValue(sparepartMonthReport.getRepairinnum()==null?0:sparepartMonthReport.getRepairinnum());
			 repairinnum.setCellStyle(content_green_style);
			 
			 Cell scrapoutnum=row.createCell(cellnum++);
			 scrapoutnum.setCellValue(sparepartMonthReport.getScrapoutnum()==null?0:sparepartMonthReport.getScrapoutnum());
			 scrapoutnum.setCellStyle(content_orange_style);
			 
			 Cell repairoutnum=row.createCell(cellnum++);
			 repairoutnum.setCellValue(sparepartMonthReport.getRepairoutnum()==null?0:sparepartMonthReport.getRepairoutnum());
			 repairoutnum.setCellStyle(content_orange_style);
			 
			 Cell adjustoutnum=row.createCell(cellnum++);
			 adjustoutnum.setCellValue(sparepartMonthReport.getAdjustoutnum()==null?0:sparepartMonthReport.getAdjustoutnum());
			 adjustoutnum.setCellStyle(content_plum_style);
			 
			 Cell adjustinnum=row.createCell(cellnum++);
			 adjustinnum.setCellValue(sparepartMonthReport.getAdjustinnum()==null?0:sparepartMonthReport.getAdjustinnum());
			 adjustinnum.setCellStyle(content_style);
			 
			 Cell nownum=row.createCell(cellnum++);
			 nownum.setCellValue(sparepartMonthReport.getNownum()==null?0:sparepartMonthReport.getNownum());
			 //nownum.setCellStyle(content_style);
			 
			 Cell supplementnum=row.createCell(cellnum++);
			 supplementnum.setCellValue(sparepartMonthReport.getSupplementnum()==null?0:sparepartMonthReport.getSupplementnum());
			// supplementnum.setCellStyle(content_style);
		 
			 
			 Cell memo=row.createCell(cellnum++);
			 memo.setCellValue(sparepartMonthReport.getMemo()); 
			 memo.setCellStyle(content_style);
		 }
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		init_background(wb,sheet, ++rownum);
		
		 String filename = "在建仓库盘点月报表_模板.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
	}
}
