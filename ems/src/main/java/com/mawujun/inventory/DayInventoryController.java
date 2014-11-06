package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

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

@Controller
public class DayInventoryController {
	//@Autowired
	//private DayInventoryService dayInventoryService;
	@Autowired
	private DayInventoryService dayInventoryService;
	@Autowired
	private StoreService storeService;
	
	/**
	 * 在建仓库的越报表
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param year
	 * @param month
	 * @param store_id
	 * @return
	 */
	@RequestMapping("/dayinventory/queryMonthReport.do")
	public List<DayInventoryVO> queryBuildMonthReport(String store_id,String year,String month){
		List<DayInventoryVO> list=dayInventoryService.queryBuildMonthReport(store_id,year+month);
		//还要进行处理，把两个数据合并后，才传到前台去
		
		return list;
		
	}

	
	public CellStyle getStyle(XSSFWorkbook wb,IndexedColors color){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		//day_font.setFontHeightInPoints((short) 14);
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
	private StringBuilder[] addRow1(XSSFWorkbook wb,Sheet sheet){
		final int cellstartnum=9;//其实应该从10开始，单后面用了++
		
		//=================================================================================
		//日期的样式
		
		CellStyle black_style=getStyle(wb,IndexedColors.BLACK);
		//合并单元格的行
		Row row1 = sheet.createRow(1);
		//创建日期，并合并日期的两列
		int cellnum=cellstartnum;
		for(int j=1;j<=31;j++){
			//合并这两个单元格
			sheet.addMergedRegion(new CellRangeAddress(1,1,++cellnum,++cellnum)); 
			//设置日期值
			Cell cell11=row1.createCell(cellnum-1);
			cell11.setCellValue(j);
			cell11.setCellStyle(black_style);
			
			Cell cell12=row1.createCell(cellnum);
			cell12.setCellStyle(black_style);
		}
		//=================================================================================== 
		
		//
		
		
		 Cell subtype_name=row1.createCell(0);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 
		 Cell brand_name=row1.createCell(1);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 
		 Cell style=row1.createCell(2);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 
		 Cell prod_name=row1.createCell(3);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 
		 Cell store_name=row1.createCell(4);
		 store_name.setCellValue("仓库");
		 store_name.setCellStyle(black_style);
		 
		 Cell unit=row1.createCell(5);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 
		 Cell lastnum=row1.createCell(6);
		 lastnum.setCellValue("上期结余数");
		 lastnum.setCellStyle(black_style);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.BLUE);
		 Cell storeinnum=row1.createCell(7);
		 storeinnum.setCellValue("本期新增数");
		 storeinnum.setCellStyle(blue_style);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED);
		 Cell installoutnum=row1.createCell(8);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 
		 Cell nownum=row1.createCell(9);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(black_style);
		 
		 //====================================================		 
		// 新增数的样式
		CellStyle in_style = wb.createCellStyle();
		Font in_font = wb.createFont();
		//in_font.setFontHeightInPoints((short) 16);
		in_font.setColor(IndexedColors.BLUE_GREY.getIndex());
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
		//out_font.setFontHeightInPoints((short) 16);
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
		
		cellnum=cellstartnum;
		Row row2 = sheet.createRow(2);
		
		//
		StringBuilder in_formula=new StringBuilder("SUM(");
		StringBuilder out_formula=new StringBuilder("SUM(");
		for(int j=1;j<=31;j++){
			 Cell day_in=row2.createCell(++cellnum);
			 day_in.setCellValue("新增数");
			 day_in.setCellStyle(in_style);
			 in_formula.append(CellReference.convertNumToColString(cellnum)).append("=");
			
			 
			 
			 Cell day_out=row2.createCell(++cellnum);
			 day_out.setCellValue("领用数");
			 day_out.setCellStyle(out_style);
			 out_formula.append(CellReference.convertNumToColString(cellnum)).append("=");
			 
			 if(j!=31){
				 in_formula.append(",");
				 out_formula.append(",");
			 } 
			
		 }
		in_formula.append(")");
		out_formula.append(")");
		
		 Cell memo=row2.createCell(++cellnum);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
		 
		//合并0--9的单元格，纵向合并
		for(int j=0;j<=cellstartnum;j++){
			sheet.addMergedRegion(new CellRangeAddress(1,2,(short)j,(short)j)); 
				
			Cell cell12=row2.createCell(j);
			cell12.setCellStyle(black_style);
		}
		 
		 //冻结行和列
		 sheet.createFreezePane(cellstartnum+1, 3);
		 
		 //生成本期新增数公式
		 StringBuilder[] formulas=new StringBuilder[]{in_formula,out_formula};
		 return formulas;
		 
		 
	}
	@RequestMapping("/dayinventory/build/export.do")
	public void build_export(HttpServletResponse response,String store_id,String year,String month) throws IOException{
		Store store=storeService.get(store_id);
		Map<DayInventoryVO,Map<String,Integer>> result=query(year,month,store_id);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue(year+"年"+month+"月在建工程仓库("+store.getName()+")盘点日报表");
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
		StringBuilder[] formulas=addRow1(wb,sheet);
		
		CellStyle blue_style=getStyle(wb,IndexedColors.BLUE);
		 blue_style.setBorderBottom(CellStyle.BORDER_NONE);
		 blue_style.setBorderLeft(CellStyle.BORDER_NONE);
		 blue_style.setBorderRight(CellStyle.BORDER_NONE);
		 blue_style.setBorderTop(CellStyle.BORDER_NONE);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED);
		 red_style.setBorderBottom(CellStyle.BORDER_NONE);
		 red_style.setBorderLeft(CellStyle.BORDER_NONE);
		 red_style.setBorderRight(CellStyle.BORDER_NONE);
		 red_style.setBorderTop(CellStyle.BORDER_NONE);;
		
		//for(int i=0;i<list.size();i++){
		int i=0;
		for(Entry<DayInventoryVO,Map<String,Integer>> entry:result.entrySet()){
			DayInventoryVO buildDayReport=entry.getKey();//list.get(i);
			int rownum=i+3;
			 Row row = sheet.createRow(rownum);
			 i++;
			 
			 Cell subtype_name=row.createCell(0);
			 subtype_name.setCellValue(buildDayReport.getSubtype_name());
			 
			 Cell brand_name=row.createCell(1);
			 brand_name.setCellValue(buildDayReport.getBrand_name());
			 
			 Cell style=row.createCell(2);
			 style.setCellValue(buildDayReport.getStyle());
			 
			 Cell prod_name=row.createCell(3);
			 prod_name.setCellValue(buildDayReport.getProd_name());
			 
			 Cell store_name=row.createCell(4);
			 store_name.setCellValue(buildDayReport.getStore_name());
			 
			 Cell unit=row.createCell(5);
			 unit.setCellValue(buildDayReport.getUnit());
			 
			 //上期结余
			 Cell lastnum=row.createCell(6);
			 lastnum.setCellValue(buildDayReport.getLastnum()==null?0:buildDayReport.getLastnum());
			 
			 
			 //本期新增数
			 Cell storeinnum=row.createCell(7);
			 storeinnum.setCellValue(buildDayReport.getStoreinnum()==null?0:buildDayReport.getStoreinnum());
			 storeinnum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum+1)+""));
			 storeinnum.setCellStyle(blue_style);
			 
			 Cell installoutnum=row.createCell(8);
			 installoutnum.setCellValue(buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
			 installoutnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum+1)+""));
			 installoutnum.setCellStyle(red_style);
			 
			 //本月结余 上期+本期新增+本期领用
			 Cell nownum=row.createCell(9);
			 //nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
			 nownum.setCellFormula("SUM("+CellReference.convertNumToColString(6)+(rownum+1)+
					 ","+CellReference.convertNumToColString(7)+(rownum+1)+
					 ","+CellReference.convertNumToColString(8)+(rownum+1)+")");
			 

			 //循环出31天的数据
			 Map<String,Integer> days_nums=entry.getValue();
			 int cellstartnum=9;
			 for(int j=1;j<=31;j++){
				 sheet.setColumnWidth(cellstartnum+1, 7 * 256);
				 Cell day_in=row.createCell(++cellstartnum);
				 Integer value_in=days_nums.get("day"+j+"_in");
				 day_in.setCellValue(value_in==null?0:value_in);
				 day_in.setCellStyle(blue_style);
				 
				 sheet.setColumnWidth(cellstartnum+1, 7 * 256);
				 Cell day_out=row.createCell(++cellstartnum);
				 Integer value_out=days_nums.get("day"+j+"_out");
				 day_out.setCellValue(value_out==null?0:value_out);
				 day_out.setCellStyle(red_style);
			 }
		 }
		 
		 String filename = "在建工程仓库("+store.getName()+")盘点日报表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
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
		 
		 CellStyle black_style=getStyle(wb,IndexedColors.BLACK);
		 int cellint=0;
		 Cell subtype_name=row.createCell(cellint++);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 
		 Cell brand_name=row.createCell(cellint++);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 
		 Cell style=row.createCell(cellint++);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
		 Cell prod_name=row.createCell(cellint++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
		 Cell store_name=row.createCell(cellint++);
		 store_name.setCellValue("仓库");
		 store_name.setCellStyle(black_style);
		 
		 Cell unit=row.createCell(cellint++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 
		 Cell fixednum=row.createCell(cellint++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellint-1, "列长".getBytes().length*256);
		 
		 Cell lastnum=row.createCell(cellint++);
		 lastnum.setCellValue("上月结余");
		 lastnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellint-1, "列长".getBytes().length*256);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.BLUE);
		 Cell purchasenum=row.createCell(cellint++);
		 purchasenum.setCellValue("本期采购新增");
		 purchasenum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellint-1, "列长长".getBytes().length*256);
		
		 Cell oldnum=row.createCell(cellint++);
		 oldnum.setCellValue("旧品新增");
		 oldnum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellint-1, "列长".getBytes().length*256);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED);
		 Cell installoutnum=row.createCell(cellint++);
		 installoutnum.setCellValue("本期领用");
		 installoutnum.setCellStyle(red_style);
		 sheet.setColumnWidth(cellint-1, "列长".getBytes().length*256);
		 
		 CellStyle green_style=getStyle(wb,IndexedColors.GREEN);
		 Cell repairinnum=row.createCell(cellint++);
		 repairinnum.setCellValue("本期维修返还数");
		 repairinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellint-1, "列长长".getBytes().length*256);
		 
		 CellStyle orange_style=getStyle(wb,IndexedColors.ORANGE);
		 Cell scrapoutnum=row.createCell(cellint++);
		 scrapoutnum.setCellValue("报废出库数量");
		 scrapoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellint-1, "列长长".getBytes().length*256);
		 
		 Cell repairoutnum=row.createCell(cellint++);
		 repairoutnum.setCellValue("维修出库数量");
		 repairoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellint-1, "列长长".getBytes().length*256);
		 
		 CellStyle plum_style=getStyle(wb,IndexedColors.PLUM);
		 Cell adjustoutnum=row.createCell(cellint++);
		 adjustoutnum.setCellValue("本期借用数");
		 adjustoutnum.setCellStyle(plum_style);
		 sheet.setColumnWidth(cellint-1, "列长长".getBytes().length*256);
		 
		 Cell adjustinnum=row.createCell(cellint++);
		 adjustinnum.setCellValue("本期归还数");
		 adjustinnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellint-1, "列长长".getBytes().length*256);
		 
		 Cell nownum=row.createCell(cellint++);
		 nownum.setCellValue("本月结余");
		 nownum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellint-1, "列长".getBytes().length*256);
		 
		 Cell supplementnum=row.createCell(cellint++);
		 supplementnum.setCellValue("增补数量");
		 supplementnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellint-1, "列长".getBytes().length*256);

		 Cell memo=row.createCell(cellint++);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
		 sheet.setColumnWidth(cellint-1, "列长".getBytes().length*256);
	}
	@RequestMapping("/dayinventory/sparepart/export.do")
	public void sparepart_export(HttpServletResponse response,String year,String month,String store_id) throws IOException{
		
		Store store=storeService.get(store_id);
		List<DayInventoryVO> list=queryBuildMonthReport(store_id,year,month);
		
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
		
		
		for(int i=0;i<list.size();i++){
			DayInventoryVO sparepartMonthReport=list.get(i);
			 int rownum=i+2;
			 int cellnum=0;
			 Row row = sheet.createRow(rownum);
			 
			 Cell subtype_name=row.createCell(cellnum++);
			 subtype_name.setCellValue(sparepartMonthReport.getSubtype_name());
			 
			 Cell brand_name=row.createCell(cellnum++);
			 brand_name.setCellValue(sparepartMonthReport.getBrand_name());
			 
			 Cell style=row.createCell(cellnum++);
			 style.setCellValue(sparepartMonthReport.getStyle());
			 
			 Cell prod_name=row.createCell(cellnum++);
			 prod_name.setCellValue(sparepartMonthReport.getProd_name());
			 
			 Cell store_name=row.createCell(cellnum++);
			 store_name.setCellValue(sparepartMonthReport.getStore_name());
			 
			 Cell unit=row.createCell(cellnum++);
			 unit.setCellValue(sparepartMonthReport.getUnit());
			 
			 Cell fixednum=row.createCell(cellnum++);
			 fixednum.setCellValue(sparepartMonthReport.getFixednum()==null?0:sparepartMonthReport.getFixednum());
			 
			 Cell lastnum=row.createCell(cellnum++);
			 lastnum.setCellValue(sparepartMonthReport.getLastnum()==null?0:sparepartMonthReport.getLastnum());
			 
			 Cell purchasenum=row.createCell(cellnum++);
			 purchasenum.setCellValue(sparepartMonthReport.getPurchasenum()==null?0:sparepartMonthReport.getPurchasenum());
			 
			 Cell oldnum=row.createCell(cellnum++);
			 oldnum.setCellValue(sparepartMonthReport.getOldnum()==null?0:sparepartMonthReport.getOldnum());
			 
			 Cell installoutnum=row.createCell(cellnum++);
			 installoutnum.setCellValue(sparepartMonthReport.getInstalloutnum()==null?0:sparepartMonthReport.getInstalloutnum());
			 
			 Cell repairinnum=row.createCell(cellnum++);
			 repairinnum.setCellValue(sparepartMonthReport.getRepairinnum()==null?0:sparepartMonthReport.getRepairinnum());
			 
			 Cell scrapoutnum=row.createCell(cellnum++);
			 scrapoutnum.setCellValue(sparepartMonthReport.getScrapoutnum()==null?0:sparepartMonthReport.getScrapoutnum());
			 
			 Cell repairoutnum=row.createCell(cellnum++);
			 repairoutnum.setCellValue(sparepartMonthReport.getRepairoutnum()==null?0:sparepartMonthReport.getRepairoutnum());
			 
			 Cell adjustoutnum=row.createCell(cellnum++);
			 adjustoutnum.setCellValue(sparepartMonthReport.getAdjustoutnum()==null?0:sparepartMonthReport.getAdjustoutnum());
			 
			 Cell adjustinnum=row.createCell(cellnum++);
			 adjustinnum.setCellValue(sparepartMonthReport.getAdjustinnum()==null?0:sparepartMonthReport.getAdjustinnum());
			 
			 Cell nownum=row.createCell(cellnum++);
			 nownum.setCellValue(sparepartMonthReport.getNownum()==null?0:sparepartMonthReport.getNownum());
			 
			 Cell supplementnum=row.createCell(cellnum++);
			 supplementnum.setCellValue(sparepartMonthReport.getSupplementnum()==null?0:sparepartMonthReport.getSupplementnum());
		 
			 
			 Cell memo=row.createCell(cellnum++);
			 memo.setCellValue(sparepartMonthReport.getMemo()); 
		 }
		 
		 String filename = "备品备件仓库("+store.getName()+")盘点月报表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
}
