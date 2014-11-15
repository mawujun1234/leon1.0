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
		 lastnum.setCellStyle(black_style);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.BLUE,null);
		 Cell storeinnum=row.createCell(cellnum++);
		 storeinnum.setCellValue("本期新增数");
		 storeinnum.setCellStyle(blue_style);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED,null);
		 Cell installoutnum=row.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(black_style);
		 
		 Cell memo=row.createCell(cellnum++);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(black_style);
		 
		 sheet.createFreezePane(build_month_freeze_num, 2);
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

		CellStyle blue_style = getStyle(wb, IndexedColors.BLUE,null);
		blue_style.setBorderBottom(CellStyle.BORDER_NONE);
		blue_style.setBorderLeft(CellStyle.BORDER_NONE);
		blue_style.setBorderRight(CellStyle.BORDER_NONE);
		blue_style.setBorderTop(CellStyle.BORDER_NONE);
		
		CellStyle red_style = getStyle(wb, IndexedColors.RED,null);
		red_style.setBorderBottom(CellStyle.BORDER_NONE);
		red_style.setBorderLeft(CellStyle.BORDER_NONE);
		red_style.setBorderRight(CellStyle.BORDER_NONE);
		red_style.setBorderTop(CellStyle.BORDER_NONE);
		
		CellStyle black_style = this.getStyle(wb, IndexedColors.BLACK,null);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		black_style.setBorderLeft(CellStyle.BORDER_NONE);
		black_style.setBorderRight(CellStyle.BORDER_NONE);
		black_style.setBorderTop(CellStyle.BORDER_NONE);
		black_style.setAlignment(CellStyle.ALIGN_LEFT);
		
		CellStyle bord_style = wb.createCellStyle();
		//style.setAlignment(CellStyle.ALIGN_CENTER);
		//style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bord_style.setWrapText(true);//自动换行
		bord_style.setBorderTop(CellStyle.BORDER_THIN);
		bord_style.setBorderBottom(CellStyle.BORDER_THIN);
		bord_style.setBorderLeft(CellStyle.BORDER_THIN);
		bord_style.setBorderRight(CellStyle.BORDER_THIN);
		 
		 //循环出数据
		int cellnum=0;
		int extra_row_num=2;
		String subtype_id_temp="";
		int fromRow=0;//开始分组的行
		for(int i=0;i<list.size();i++){
			cellnum = 0;
			MonthInventoryVO buildDayReport = list.get(i);
			int rownum = i + extra_row_num;
			//判断还是不是同个小类，如果不是同个小类就添加一行，只有小类名称的行
			if(!subtype_id_temp.equals(buildDayReport.getSubtype_id())){
				Row row = sheet.createRow(rownum);
				Cell subtype_name = row.createCell(cellnum);
				subtype_name.setCellValue(buildDayReport.getSubtype_name());
				subtype_name.setCellStyle(black_style);
				for(int j=1;j<11;j++){
					Cell cell = row.createCell(cellnum+j);
					cell.setCellStyle(bord_style);
				}
				//同时合并单元格
				sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,0,(short)10)); 
				
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
			subtype_name.setCellStyle(bord_style);
			//subtype_name.setCellValue(buildDayReport.getSubtype_name());

			Cell brand_name = row.createCell(cellnum++);
			brand_name.setCellValue(buildDayReport.getBrand_name());
			brand_name.setCellStyle(bord_style);

			Cell style = row.createCell(cellnum++);
			style.setCellValue(buildDayReport.getStyle());
			style.setCellStyle(bord_style);

			Cell prod_name = row.createCell(cellnum++);
			prod_name.setCellValue(buildDayReport.getProd_name());
			prod_name.setCellStyle(bord_style);

			Cell store_name = row.createCell(cellnum++);
			store_name.setCellValue(buildDayReport.getStore_name());
			store_name.setCellStyle(bord_style);

			Cell unit = row.createCell(cellnum++);
			unit.setCellValue(buildDayReport.getUnit());
			unit.setCellStyle(bord_style);

			Cell lastnum = row.createCell(cellnum++);
			lastnum.setCellValue(buildDayReport.getLastnum() == null ? 0
					: buildDayReport.getLastnum());
			lastnum.setCellStyle(bord_style);

			Cell storeinnum = row.createCell(cellnum++);
			storeinnum.setCellValue(buildDayReport.getNowAdd() == null ? 0
					: buildDayReport.getNowAdd());
			storeinnum.setCellStyle(blue_style);
			storeinnum.setCellStyle(bord_style);

			Cell installoutnum = row.createCell(cellnum++);
			installoutnum
					.setCellValue(buildDayReport.getInstalloutnum() == null ? 0
							: buildDayReport.getInstalloutnum());
			installoutnum.setCellStyle(red_style);
			installoutnum.setCellStyle(bord_style);
			// 本月结余数
			Cell nownum = row.createCell(cellnum++);
			// nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
			nownum.setCellFormula("SUM("
					+ CellReference.convertNumToColString(6) + (rownum + 1)
					+ "," + CellReference.convertNumToColString(7)
					+ (rownum + 1) + ","
					+ CellReference.convertNumToColString(8) + (rownum + 1)
					+ ")");
			nownum.setCellStyle(bord_style);

			Cell memo = row.createCell(cellnum++);
			memo.setCellValue(buildDayReport.getMemo());
			memo.setCellStyle(bord_style);
			
			
		}
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		
		String filename = "在建工程仓库("+store.getName()+")盘点月报表.xlsx";
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
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*15*256);
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
		 
		 Cell fixednum=row.createCell(cellnum++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell lastnum=row.createCell(cellnum++);
		 lastnum.setCellValue("上月结余");
		 lastnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.BLUE,null);
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
		 
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余");
		 nownum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell supplementnum=row.createCell(cellnum++);
		 supplementnum.setCellValue("增补数量");
		 supplementnum.setCellStyle(black_style);
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
		
		
		CellStyle black_style = this.getStyle(wb, IndexedColors.BLACK,null);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		black_style.setBorderLeft(CellStyle.BORDER_NONE);
		black_style.setBorderRight(CellStyle.BORDER_NONE);
		black_style.setBorderTop(CellStyle.BORDER_NONE);
		black_style.setAlignment(CellStyle.ALIGN_LEFT);
		
		int cellnum=0;
		int extra_row_num=2;
		String subtype_id_temp="";
		int fromRow=0;//开始分组的行
		for(int i=0;i<list.size();i++){
			MonthInventoryVO sparepartMonthReport = list.get(i);
			int rownum = i + extra_row_num;
			cellnum = 0;

			// 判断还是不是同个小类，如果不是同个小类就添加一行，只有小类名称的行
			if (!subtype_id_temp.equals(sparepartMonthReport.getSubtype_id())) {
				Row row = sheet.createRow(rownum);
				Cell subtype_name = row.createCell(cellnum);
				subtype_name.setCellValue(sparepartMonthReport
						.getSubtype_name());
				subtype_name.setCellStyle(black_style);
				for (int j = 1; j < 19; j++) {
					Cell cell = row.createCell(cellnum + j);
					cell.setCellStyle(black_style);
				}
				// 同时合并单元格
				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,
						(short) 9));
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
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
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
