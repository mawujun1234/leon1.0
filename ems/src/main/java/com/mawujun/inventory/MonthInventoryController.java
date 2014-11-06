package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;

@Controller
public class MonthInventoryController {
	//@Autowired
	//private DayInventoryService dayInventoryService;
	@Autowired
	private MonthInventoryService monthInventoryService;
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
	@RequestMapping("/monthinventory/queryMonthReport.do")
	public List<MonthInventoryVO> queryBuildMonthReport(String store_id,String year,String month){
		List<MonthInventoryVO> list=monthInventoryService.queryBuildMonthReport(store_id,year+month);
		//还要进行处理，把两个数据合并后，才传到前台去
		
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
		monthInventoryService.update(Cnd.update().set(field, value)
				.andEquals(M.MonthInventory.monthkey, key.getMonthkey())
				.andEquals(M.MonthInventory.subtype_id, key.getSubtype_id())
				.andEquals(M.MonthInventory.prod_id, key.getProd_id())
				.andEquals(M.MonthInventory.brand_id, key.getBrand_id())
				.andEquals(M.MonthInventory.style, key.getStyle())
				.andEquals(M.MonthInventory.store_id, key.getStore_id()));
		return "success";
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
	private void build_addRow1(XSSFWorkbook wb,Sheet sheet){
		 Row row = sheet.createRow(1);
		 CellStyle black_style=getStyle(wb,IndexedColors.BLACK);
		 Cell subtype_name=row.createCell(0);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 
		 Cell brand_name=row.createCell(1);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 
		 Cell style=row.createCell(2);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 
		 Cell prod_name=row.createCell(3);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 
		 Cell store_name=row.createCell(4);
		 store_name.setCellValue("仓库");
		 store_name.setCellStyle(black_style);
		 
		 Cell unit=row.createCell(5);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 
		 Cell lastnum=row.createCell(6);
		 lastnum.setCellValue("上期结余数");
		 lastnum.setCellStyle(black_style);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.BLUE);
		 Cell storeinnum=row.createCell(7);
		 storeinnum.setCellValue("本期新增数");
		 storeinnum.setCellStyle(blue_style);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED);
		 Cell installoutnum=row.createCell(8);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 
		 Cell nownum=row.createCell(9);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(black_style);
		 
		 Cell memo=row.createCell(10);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
	}
	@RequestMapping("/monthinventory/build/export.do")
	public void build_export(HttpServletResponse response,String year,String month,String store_id) throws IOException{
		
		Store store=storeService.get(store_id);
		List<MonthInventoryVO> list=queryBuildMonthReport(year,month,store_id);
		
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
		//和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)10)); 
		
		//设置第一行,设置列标题
		build_addRow1(wb,sheet);
		
		 CellStyle blue_style=getStyle(wb,IndexedColors.BLUE);
		 blue_style.setBorderBottom(CellStyle.BORDER_NONE);
		 blue_style.setBorderLeft(CellStyle.BORDER_NONE);
		 blue_style.setBorderRight(CellStyle.BORDER_NONE);
		 blue_style.setBorderTop(CellStyle.BORDER_NONE);;
		 CellStyle red_style=getStyle(wb,IndexedColors.RED);
		 red_style.setBorderBottom(CellStyle.BORDER_NONE);
		 red_style.setBorderLeft(CellStyle.BORDER_NONE);
		 red_style.setBorderRight(CellStyle.BORDER_NONE);
		 red_style.setBorderTop(CellStyle.BORDER_NONE);;
		for(int i=0;i<list.size();i++){
			 MonthInventoryVO buildDayReport=list.get(i);
			 int rownum=i+2;
			 Row row = sheet.createRow(rownum);
			 
			 
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
			 
			 Cell lastnum=row.createCell(6);
			 lastnum.setCellValue(buildDayReport.getLastmonthnum()==null?0:buildDayReport.getLastmonthnum());
			 
			
			 Cell storeinnum=row.createCell(7);
			 storeinnum.setCellValue(buildDayReport.getNowAdd()==null?0:buildDayReport.getNowAdd());
			 storeinnum.setCellStyle(blue_style);
			 
			 Cell installoutnum=row.createCell(8);
			 installoutnum.setCellValue(buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
			 installoutnum.setCellStyle(red_style);
			 //本月结余数
			 Cell nownum=row.createCell(9);
			 //nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
			 nownum.setCellFormula("SUM("+CellReference.convertNumToColString(6)+(rownum+1)+
					 ","+CellReference.convertNumToColString(7)+(rownum+1)+
					 ","+CellReference.convertNumToColString(8)+(rownum+1)+")");
			 
			 Cell memo=row.createCell(10);
			 memo.setCellValue(buildDayReport.getMemo()); 
		 }
		 
		 String filename = "在建工程仓库("+store.getName()+")盘点月报表.xls";
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
	@RequestMapping("/monthinventory/sparepart/export.do")
	public void sparepart_export(HttpServletResponse response,String year,String month,String store_id) throws IOException{
		
		Store store=storeService.get(store_id);
		List<MonthInventoryVO> list=queryBuildMonthReport(year,month,store_id);
		
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
			MonthInventoryVO sparepartMonthReport=list.get(i);
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
			 
			 
//			 Cell storeinnum=row.createCell(7);
//			 storeinnum.setCellValue(sparepartMonthReport.getStoreinnum()==null?0:sparepartMonthReport.getStoreinnum());
//			 
//			 Cell installoutnum=row.createCell(8);
//			 installoutnum.setCellValue(sparepartMonthReport.getInstalloutnum()==null?0:sparepartMonthReport.getInstalloutnum());
//			 //本月结余数
//			 Cell nownum=row.createCell(9);
//			 //nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
//			 nownum.setCellFormula("SUM("+CellReference.convertNumToColString(6)+(rownum+1)+
//					 ","+CellReference.convertNumToColString(7)+(rownum+1)+
//					 ","+CellReference.convertNumToColString(8)+(rownum+1)+")");
//			 
			 Cell memo=row.createCell(cellnum++);
			 memo.setCellValue(sparepartMonthReport.getMemo()); 
		 }
		 
		 String filename = "备品备件仓库("+store.getName()+")盘点月报表.xls";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
}
