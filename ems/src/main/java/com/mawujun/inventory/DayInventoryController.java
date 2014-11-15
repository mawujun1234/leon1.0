package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private MonthInventoryService monthInventoryService;
	@Autowired
	private StoreService storeService;
	
//	/**
//	 * 在建仓库的越报表
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param year
//	 * @param month
//	 * @param store_id
//	 * @return
//	 */
//	@RequestMapping("/dayinventory/queryMonthReport.do")
//	public List<DayInventoryVO> queryBuildMonthReport(String store_id,String year,String month){
//		List<DayInventoryVO> list=dayInventoryService.queryBuildMonthReport(store_id,year+month);
//		//还要进行处理，把两个数据合并后，才传到前台去
//		
//		return list;
//		
//	}
	
	
	SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
	public List<MonthInventoryVO> query(String store_id,String year,String month) throws IllegalAccessException, InvocationTargetException{
		//计算今天到目前为止的数据，就是计算当前为止的日报数据
//		buildDayReportService.createBuildDayReport();
		
		
		//这获取的是到今天为止的数据
		List<DayInventoryVO> list=dayInventoryService.queryDayInventory(store_id,year+month+"01",year+month+"31");
		
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
		cal.add(Calendar.MONTH, -1);
		List<MonthInventoryVO> list_last_monthInventory=monthInventoryService.queryMonthReport(store_id, format.format(cal.getTime()));
		//当第一次访问的时候，没有生产前一个月的数据的时候
		if(list_last_monthInventory==null || list_last_monthInventory.size()==0){
			list_last_monthInventory=monthInventoryService.queryNullMonthReport(store_id);
		}
		
		//Map<MonthInventoryVO,Map<String,Integer>> result=new HashMap<MonthInventoryVO,Map<String,Integer>>();
		//索引map,用来获取key
		Map<DayInventory_PK,MonthInventoryVO> temp=new HashMap<DayInventory_PK,MonthInventoryVO>();
		MonthInventoryVO vo=null;
		for(DayInventoryVO dayInventoryVO:list){
			DayInventory_PK pk_noday=dayInventoryVO.getId();
			pk_noday.setDaykey(null);
			if(temp.containsKey(pk_noday)){
				vo=temp.get(pk_noday);
			} else {
				//MonthInventory_PK m_pk=new MonthInventory_PK();
				//BeanUtils.copyProperties(pk_noday, m_pk);
				//MonthInventory monthInventory=monthInventoryService.get(m_pk);
				for(MonthInventoryVO aa:list_last_monthInventory){
					MonthInventory_PK m_pk=aa.getId();
					if(pk_noday.getBrand_id().equals(m_pk.getBrand_id())
							&& pk_noday.getProd_id().equals(m_pk.getProd_id())
							&&pk_noday.getStore_id().equals(m_pk.getStore_id())
							&&pk_noday.getStyle().equals(m_pk.getStyle())
							&&pk_noday.getSubtype_id().equals(m_pk.getSubtype_id())){
						vo=aa;
						break;
					}
				}
				
				temp.put(pk_noday, vo);
			}
			if(vo!=null){
				//然后把值设置到对应的字段中
				Integer day=Integer.parseInt((dayInventoryVO.getDaykey()+"").substring(6));
				vo.addDayInvertorys("day"+day, dayInventoryVO);
			}
			
		}
		
		return list_last_monthInventory;
		
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
	private StringBuilder[] build_addRow1(XSSFWorkbook wb,Sheet sheet){
		//final int cellstartnum=9;//其实应该从10开始，单后面用了++
		CellStyle black_style=getStyle_title(wb,IndexedColors.BLACK,null);
		//
		//合并单元格的行
		Row row = sheet.createRow(1);
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
		 lastnum.setCellValue("上期结余数");
		 lastnum.setCellStyle(black_style);
		 
		 CellStyle blue_style=getStyle_title(wb,IndexedColors.BLUE,null);
		 Cell storeinnum=row.createCell(cellnum++);
		 storeinnum.setCellValue("本期新增数");
		 storeinnum.setCellStyle(blue_style);
		 
		 CellStyle red_style=getStyle_title(wb,IndexedColors.RED,null);
		 Cell installoutnum=row.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(black_style);
		 
		 //====================================================		 
		// 新增数的样式
		CellStyle in_style = wb.createCellStyle();
		Font in_font = wb.createFont();
		//in_font.setFontHeightInPoints((short) 16);
		in_font.setColor(IndexedColors.BLUE.getIndex());
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
		
		//cellnum=cellstartnum;
		int cellnum_temp=cellnum;
		Row row2 = sheet.createRow(2);
		
		//
		StringBuilder in_formula=new StringBuilder("SUM(");
		StringBuilder out_formula=new StringBuilder("SUM(");
		for(int j=1;j<=31;j++){
			 Cell day_in=row2.createCell(cellnum_temp++);
			 day_in.setCellValue("新增数");
			 day_in.setCellStyle(in_style);
			 in_formula.append(CellReference.convertNumToColString(cellnum_temp-1)).append("=");
			
			 
			 
			 Cell day_out=row2.createCell(cellnum_temp++);
			 day_out.setCellValue("领用数");
			 day_out.setCellStyle(out_style);
			 out_formula.append(CellReference.convertNumToColString(cellnum_temp-1)).append("=");
			 
			 if(j!=31){
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
		
		
		//创建日期，并合并日期的两列
		//int cellnum=cellstartnum;
		 cellnum_temp=cellnum;
		for(int j=1;j<=31;j++){
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
	@RequestMapping("/dayinventory/build/export.do")
	public void build_export(HttpServletResponse response,String store_id,String year,String month) throws IOException, IllegalAccessException, InvocationTargetException{
		Store store=storeService.get(store_id);
		List<MonthInventoryVO> result=query(store_id,year,month);
		
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
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)9)); 
		
		//设置第一行,设置列标题
		StringBuilder[] formulas=build_addRow1(wb,sheet);
		
		CellStyle blue_style=getStyle_title(wb,IndexedColors.BLUE,null);
//		 blue_style.setBorderBottom(CellStyle.BORDER_NONE);
//		 blue_style.setBorderLeft(CellStyle.BORDER_NONE);
//		 blue_style.setBorderRight(CellStyle.BORDER_NONE);
//		 blue_style.setBorderTop(CellStyle.BORDER_NONE);
		 
		 CellStyle red_style=getStyle_title(wb,IndexedColors.RED,null);
//		 red_style.setBorderBottom(CellStyle.BORDER_NONE);
//		 red_style.setBorderLeft(CellStyle.BORDER_NONE);
//		 red_style.setBorderRight(CellStyle.BORDER_NONE);
//		 red_style.setBorderTop(CellStyle.BORDER_NONE);
		 
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
		
		//for(int i=0;i<list.size();i++){
		int i=0;
		int cellnum=0;
		int extra_row_num=3;
		String subtype_id_temp="";
		int fromRow=0;//开始分组的行
		StringBuilder builder=new StringBuilder();
		//for(Entry<DayInventoryVO,Map<String,Integer>> entry:result.entrySet()){
		for(MonthInventoryVO monthInventoryVO:result){
			//DayInventoryVO buildDayReport=entry.getKey();//list.get(i);
			int rownum=i+extra_row_num;
			cellnum=0;
			
			//判断还是不是同个小类，如果不是同个小类就添加一行，只有小类名称的行
			if(!subtype_id_temp.equals(monthInventoryVO.getSubtype_id())){
				Row row = sheet.createRow(rownum);
				Cell subtype_name = row.createCell(cellnum);
				subtype_name.setCellValue(monthInventoryVO.getSubtype_name());
				subtype_name.setCellStyle(black_style);
				for(int j=1;j<(10+31*2+1);j++){
					Cell cell = row.createCell(cellnum+j);
					cell.setCellStyle(black_style);
				}
				//同时合并单元格
				sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,0,9+21*2+1)); 
				//分组
				if(fromRow!=0){
					sheet.groupRow(fromRow, rownum-1);//-2是因为后面又++了
					sheet.setRowGroupCollapsed(fromRow, true);
				}
				fromRow=rownum+1;
				
				subtype_id_temp=monthInventoryVO.getSubtype_id();
				rownum++;
				extra_row_num++;
			}
			
			//这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum);
			//当循环到最后一行的时候，添加最后一个分组
			if(result.size()-1==i){
				sheet.groupRow(fromRow, rownum);
				sheet.setRowGroupCollapsed(fromRow, true);
			}
			i++;
			 
			 builder.append("SUM(");
			
			 
			 Cell subtype_name=row.createCell(cellnum++);
			 //subtype_name.setCellValue(monthInventoryVO.getSubtype_name());
			 subtype_name.setCellStyle(bord_style);
			 
			 Cell brand_name=row.createCell(cellnum++);
			 brand_name.setCellValue(monthInventoryVO.getBrand_name());
			 brand_name.setCellStyle(bord_style);
			 
			 Cell style=row.createCell(cellnum++);
			 style.setCellValue(monthInventoryVO.getStyle());
			 style.setCellStyle(bord_style);
			 
			 Cell prod_name=row.createCell(cellnum++);
			 prod_name.setCellValue(monthInventoryVO.getProd_name());
			 prod_name.setCellStyle(bord_style);
			 
			 Cell store_name=row.createCell(cellnum++);
			 store_name.setCellValue(monthInventoryVO.getStore_name());
			 store_name.setCellStyle(bord_style);
			 
			 Cell unit=row.createCell(cellnum++);
			 unit.setCellValue(monthInventoryVO.getUnit());
			 unit.setCellStyle(bord_style);
			 
			 //上期结余,获取上个月的当前值
			 Cell lastnum=row.createCell(cellnum++);
			 lastnum.setCellValue(monthInventoryVO.getNownum()==null?0:monthInventoryVO.getNownum());
			 builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 lastnum.setCellStyle(bord_style);
			 
			 //本期新增数
			 Cell storeinnum=row.createCell(cellnum++);
			 //storeinnum.setCellValue(buildDayReport.getStoreinnum()==null?0:buildDayReport.getStoreinnum());
			 storeinnum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum+1)+""));
			 storeinnum.setCellStyle(blue_style);
			 builder.append(",");
			 builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 
			 Cell installoutnum=row.createCell(cellnum++);
			 //installoutnum.setCellValue(buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
			 installoutnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum+1)+""));
			 installoutnum.setCellStyle(red_style);
			 builder.append(",");
			 builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 builder.append(")");
			 
			 //本月结余 上期+本期新增+本期领用
			 Cell nownum=row.createCell(cellnum++);
			 nownum.setCellFormula(builder.toString());
			 nownum.setCellStyle(bord_style);
			 builder=new StringBuilder();
			 //nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
//			 nownum.setCellFormula("SUM("+CellReference.convertNumToColString(6)+(rownum+1)+
//					 ","+CellReference.convertNumToColString(7)+(rownum+1)+
//					 ","+CellReference.convertNumToColString(8)+(rownum+1)+")");
			 

			 //循环出31天的数据
			 Map<String,DayInventory> days_nums=monthInventoryVO.getDayInvertorys();//entry.getValue();
			 int cellstartnum=9;
			 for(int j=1;j<=31;j++){
				 sheet.setColumnWidth(cellstartnum+1, 7 * 256);
				 Cell day_in=row.createCell(++cellstartnum);
				 day_in.setCellStyle(blue_style);
				 
				 sheet.setColumnWidth(cellstartnum+1, 7 * 256);
				 Cell day_out=row.createCell(++cellstartnum);
				 day_out.setCellStyle(red_style);
				 
				 DayInventory dayInventory=days_nums.get("day"+j);
				 if(dayInventory==null){
					 continue;
				 }		 
				 
				 Integer value_in=dayInventory.getNow_in();//      days_nums.get("day"+j+"_in");
				 day_in.setCellValue(value_in==null?0:value_in);

				 Integer value_out=dayInventory.getNow_out();//days_nums.get("day"+j+"_out");
				 day_out.setCellValue(value_out==null?0:value_out);
				 
			 }
		 }
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
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
	
	
	private StringBuilder[] sparepart_addRow1(XSSFWorkbook wb,Sheet sheet){

		
		
		int cellnum=0;
		Row row1 = sheet.createRow(1);
		
		 CellStyle black_style=getStyle_title(wb,IndexedColors.BLACK,null);
		 Cell subtype_name=row1.createCell(cellnum++);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "长".getBytes().length*1*256);
		 
		 Cell brand_name=row1.createCell(cellnum++);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 
		 
		 Cell style=row1.createCell(cellnum++);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 sheet.autoSizeColumn(cellnum-1, true);
		 sheet.setColumnWidth(cellnum-1, "长".getBytes().length*6*256);
		 
		 Cell prod_name=row1.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.autoSizeColumn(cellnum-1, true);
		 sheet.setColumnWidth(cellnum-1, "长".getBytes().length*6*256);
		 
//		 Cell store_name=row1.createCell(cellnum++);
//		 store_name.setCellValue("仓库");
//		 store_name.setCellStyle(black_style);
//		 
		 Cell unit=row1.createCell(cellnum++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*256);
		 
		 Cell fixednum=row1.createCell(cellnum++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell lastnum=row1.createCell(cellnum++);
		 lastnum.setCellValue("上月结余");
		 lastnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle blue_style=getStyle_title(wb,IndexedColors.BLUE,null);
		 Cell purchasenum=row1.createCell(cellnum++);
		 purchasenum.setCellValue("本期采购新增");
		 purchasenum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		
		 Cell oldnum=row1.createCell(cellnum++);
		 oldnum.setCellValue("旧品新增");
		 oldnum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle red_style=getStyle_title(wb,IndexedColors.RED,null);
		 Cell installoutnum=row1.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用");
		 installoutnum.setCellStyle(red_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle green_style=getStyle_title(wb,IndexedColors.GREEN,null);
		 Cell repairinnum=row1.createCell(cellnum++);
		 repairinnum.setCellValue("本期维修返还数");
		 repairinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle orange_style=getStyle_title(wb,IndexedColors.ORANGE,null);
		 Cell scrapoutnum=row1.createCell(cellnum++);
		 scrapoutnum.setCellValue("报废出库数量");
		 scrapoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell repairoutnum=row1.createCell(cellnum++);
		 repairoutnum.setCellValue("维修出库数量");
		 repairoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle plum_style=getStyle_title(wb,IndexedColors.PLUM,null);
		 Cell adjustoutnum=row1.createCell(cellnum++);
		 adjustoutnum.setCellValue("本期借用数");
		 adjustoutnum.setCellStyle(plum_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell adjustinnum=row1.createCell(cellnum++);
		 adjustinnum.setCellValue("本期归还数");
		 adjustinnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell nownum=row1.createCell(cellnum++);
		 nownum.setCellValue("本月结余");
		 nownum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
//		 Cell supplementnum=row1.createCell(cellnum++);
//		 supplementnum.setCellValue("增补数量");
//		 supplementnum.setCellStyle(black_style);
//		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);

		 
		 
//		 Cell supplementnum=row1.createCell(cellnum++);
//		 supplementnum.setCellValue("增补数");
//		 supplementnum.setCellStyle(day_style);
		 Row row2 = sheet.createRow(2);
		 //
		//合并0--9的单元格，纵向合并
		
		for (int j = 0; j < cellnum; j++) {
			
			sheet.addMergedRegion(new CellRangeAddress(1, 2, (short) j,(short) j));
			// 同时设置第二行的单元格的样式
			Cell temp = row2.createCell( j);
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
			 
			blue_style=getStyle_title(wb,IndexedColors.BLUE,(short)8);
			 Cell purchasenum_repeat=row2.createCell(cellnum_repeat++);
			 purchasenum_repeat.setCellValue("采购新增");
			 purchasenum_repeat.setCellStyle(blue_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 purchasenum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
			 
			 Cell oldnum_repeat=row2.createCell(cellnum_repeat++);
			 oldnum_repeat.setCellValue("旧品新增");
			 oldnum_repeat.setCellStyle(blue_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 oldnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
			 
			 red_style=getStyle_title(wb,IndexedColors.RED,(short)8);
			 Cell installoutnum_repeat=row2.createCell(cellnum_repeat++);
			 installoutnum_repeat.setCellValue("本期领用");
			 installoutnum_repeat.setCellStyle(red_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 installoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
			 
			 green_style=getStyle_title(wb,IndexedColors.GREEN,(short)8);
			 Cell repairinnum_repeat=row2.createCell(cellnum_repeat++);
			 repairinnum_repeat.setCellValue("维修返还数");
			 repairinnum_repeat.setCellStyle(green_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 repairinnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
			 
			 orange_style=getStyle_title(wb,IndexedColors.ORANGE,(short)8);
			 Cell scrapoutnum_repeat=row2.createCell(cellnum_repeat++);
			 scrapoutnum_repeat.setCellValue("报废出库");
			 scrapoutnum_repeat.setCellStyle(orange_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 scrapoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
			 
			 Cell repairoutnum_repeat=row2.createCell(cellnum_repeat++);
			 repairoutnum_repeat.setCellValue("维修出库");
			 repairoutnum_repeat.setCellStyle(orange_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 repairoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
			 
			 plum_style=getStyle_title(wb,IndexedColors.PLUM,(short)8);
			 Cell adjustoutnum_repeat=row2.createCell(cellnum_repeat++);
			 adjustoutnum_repeat.setCellValue("借用数");
			 adjustoutnum_repeat.setCellStyle(plum_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 adjustoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
			 
			 green_style=getStyle_title(wb,IndexedColors.GREEN,(short)10);
			 Cell adjustinnum_repeat=row2.createCell(cellnum_repeat++);
			 adjustinnum_repeat.setCellValue("归还数");
			 adjustinnum_repeat.setCellStyle(green_style);
			 green_style.setBorderRight(CellStyle.BORDER_DOUBLE);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 adjustinnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");

	
//			 Cell day_out=row2.createCell(++cellnum_repeat);
//			 day_out.setCellValue("领用数");
//			 day_out.setCellStyle(out_style);
//			 out_formula.append(CellReference.convertNumToColString(cellnum_repeat)).append("=");
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

		

		Cell memo = row2.createCell(cellnum_repeat++);
		memo.setCellValue("备注");
		memo.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, "列长".getBytes().length * 256);
		
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
			Cell cell11=row1.createCell(cellnum_repeat_temp);
			cell11.setCellValue(j);
			cell11.setCellStyle(black_style);
			//设置单个元样式
			cellnum_repeat_temp++;//第一格已经创建过了，不用再创建了
			for(;cellnum_repeat_temp<cellnum_repeat;cellnum_repeat_temp++){
				Cell cell12=row1.createCell(cellnum_repeat_temp);
				cell12.setCellStyle(black_style);
				if(cellnum_repeat_temp==(cellnum_repeat-1)){
					black_style.setBorderRight(CellStyle.BORDER_DOUBLE);
				}
			}
			

		}

		 
		 //冻结行和列
		 sheet.createFreezePane(cellnum, 3);
		 
		 //生成本期新增数公式
		 StringBuilder[] formulas=new StringBuilder[]{purchasenum_formula,oldnum_formula,installoutnum_formula,repairinnum_formula,scrapoutnum_formula,repairoutnum_formula,adjustoutnum_formula,adjustinnum_formula};
		 return formulas;
	}
	@RequestMapping("/dayinventory/sparepart/export.do")
	public void sparepart_export(HttpServletResponse response,String year,String month,String store_id) throws IOException, IllegalAccessException, InvocationTargetException{
		Store store=storeService.get(store_id);
		List<MonthInventoryVO> result=query(store_id,year,month);
		
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
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)15)); 
		
		//设置第一行,设置列标题
		StringBuilder[] formulas=sparepart_addRow1(wb,sheet);
		
		CellStyle black_style=getStyle_title(wb,IndexedColors.BLACK,null);
		black_style.setWrapText(false);
		black_style.setAlignment(CellStyle.ALIGN_LEFT);
		 
		int i=0;
		int cellnum=0;
		int extra_row_num=3;
		String subtype_id_temp="";
		int fromRow=0;//开始分组的行
		//for(Entry<DayInventoryVO,Map<String,Integer>> entry:result.entrySet()){
		for(MonthInventoryVO monthInventoryVO:result){
			// int rownum=i+3;
			// i++;
			// int cellnum=0;

			int rownum = i + extra_row_num;
			cellnum = 0;

			// 判断还是不是同个小类，如果不是同个小类就添加一行，只有小类名称的行
			if (!subtype_id_temp.equals(monthInventoryVO.getSubtype_id())) {
				Row row = sheet.createRow(rownum);
				Cell subtype_name = row.createCell(cellnum);
				subtype_name.setCellValue(monthInventoryVO.getSubtype_name());
				subtype_name.setCellStyle(black_style);
				for (int j = 1; j < 15+31*7+1; j++) {
					Cell cell = row.createCell(cellnum + j);
					cell.setCellStyle(black_style);
				}
				// 同时合并单元格
				sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0,15+31*7+1));
				// 分组
				if (fromRow != 0) {
					sheet.groupRow(fromRow, rownum - 1);// -2是因为后面又++了
					sheet.setRowGroupCollapsed(fromRow, true);
				}
				fromRow = rownum + 1;

				subtype_id_temp = monthInventoryVO.getSubtype_id();
				rownum++;
				extra_row_num++;
			}

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum);
			// 当循环到最后一行的时候，添加最后一个分组
			if (result.size() - 1 == i) {
				sheet.groupRow(fromRow, rownum);
				sheet.setRowGroupCollapsed(fromRow, true);
			}
			i++;
				
			 StringBuilder nownum_formulas=new StringBuilder("SUM(");
			 
			 Cell subtype_name=row.createCell(cellnum++);
			 //subtype_name.setCellValue(monthInventoryVO.getSubtype_name());
			 subtype_name.setCellStyle(black_style);
			 
			 Cell brand_name=row.createCell(cellnum++);
			 brand_name.setCellValue(monthInventoryVO.getBrand_name());
			 brand_name.setCellStyle(black_style);
			 
			 Cell style=row.createCell(cellnum++);
			 style.setCellValue(monthInventoryVO.getStyle());
			 style.setCellStyle(black_style);
			 
			 Cell prod_name=row.createCell(cellnum++);
			 prod_name.setCellValue(monthInventoryVO.getProd_name());
			 prod_name.setCellStyle(black_style);
			 
//			 Cell store_name=row.createCell(cellnum++);
//			 store_name.setCellValue(monthInventoryVO.getStore_name());
//			 store_name.setCellStyle(black_style);
			 
			 Cell unit=row.createCell(cellnum++);
			 unit.setCellValue(monthInventoryVO.getUnit());
			 unit.setCellStyle(black_style);
			 
			 Cell fixednum=row.createCell(cellnum++);
			 fixednum.setCellValue(monthInventoryVO.getFixednum()==null?0:monthInventoryVO.getFixednum());
			 fixednum.setCellStyle(black_style);
			 
			 Cell lastnum=row.createCell(cellnum++);
			 lastnum.setCellValue(monthInventoryVO.getLastnum()==null?0:monthInventoryVO.getLastnum());
			 lastnum.setCellStyle(black_style);
			 nownum_formulas.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 nownum_formulas.append(",");
			 
			 CellStyle blue_style=getStyle_title(wb,IndexedColors.BLUE,null);
			 Cell purchasenum=row.createCell(cellnum++);
			 //purchasenum.setCellValue(monthInventoryVO.getPurchasenum()==null?0:monthInventoryVO.getPurchasenum());
			 purchasenum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum+1)+""));
			 purchasenum.setCellStyle(blue_style); 
			 nownum_formulas.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 nownum_formulas.append(",");
			 
			 Cell oldnum=row.createCell(cellnum++);
			 oldnum.setCellValue(monthInventoryVO.getOldnum()==null?0:monthInventoryVO.getOldnum());
			 oldnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum+1)+""));
			 oldnum.setCellStyle(blue_style);
			 nownum_formulas.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 nownum_formulas.append(",");
			 
			 CellStyle red_style=getStyle_title(wb,IndexedColors.RED,null);
			 Cell installoutnum=row.createCell(cellnum++);
			 installoutnum.setCellValue(monthInventoryVO.getInstalloutnum()==null?0:monthInventoryVO.getInstalloutnum());
			 installoutnum.setCellFormula(formulas[2].toString().replaceAll("=", (rownum+1)+""));
			 installoutnum.setCellStyle(red_style);
			 nownum_formulas.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 nownum_formulas.append(",");
			 
			 CellStyle green_style=getStyle_title(wb,IndexedColors.GREEN,null);
			 Cell repairinnum=row.createCell(cellnum++);
			 repairinnum.setCellValue(monthInventoryVO.getRepairinnum()==null?0:monthInventoryVO.getRepairinnum());
			 repairinnum.setCellFormula(formulas[3].toString().replaceAll("=", (rownum+1)+""));
			 repairinnum.setCellStyle(green_style);
			 nownum_formulas.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 nownum_formulas.append(",");
			 
			 CellStyle orange_style=getStyle_title(wb,IndexedColors.ORANGE,null);
			 Cell scrapoutnum=row.createCell(cellnum++);
			 //scrapoutnum.setCellValue(monthInventoryVO.getScrapoutnum()==null?0:monthInventoryVO.getScrapoutnum());
			 scrapoutnum.setCellFormula(formulas[4].toString().replaceAll("=", (rownum+1)+""));
			 scrapoutnum.setCellStyle(orange_style);
			 
			 Cell repairoutnum=row.createCell(cellnum++);
			 //repairoutnum.setCellValue(monthInventoryVO.getRepairoutnum()==null?0:monthInventoryVO.getRepairoutnum());
			 repairoutnum.setCellFormula(formulas[5].toString().replaceAll("=", (rownum+1)+""));
			 repairoutnum.setCellStyle(orange_style);
			 
			 CellStyle plum_style=getStyle_title(wb,IndexedColors.PLUM,null);
			 Cell adjustoutnum=row.createCell(cellnum++);
			 //adjustoutnum.setCellValue(monthInventoryVO.getAdjustoutnum()==null?0:monthInventoryVO.getAdjustoutnum());
			 adjustoutnum.setCellFormula(formulas[6].toString().replaceAll("=", (rownum+1)+""));
			 adjustoutnum.setCellStyle(plum_style);
			 nownum_formulas.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 nownum_formulas.append(",");
			 
			 Cell adjustinnum=row.createCell(cellnum++);
			 adjustinnum.setCellValue(monthInventoryVO.getAdjustinnum()==null?0:monthInventoryVO.getAdjustinnum());
			 adjustinnum.setCellFormula(formulas[7].toString().replaceAll("=", (rownum+1)+""));
			 adjustinnum.setCellStyle(green_style);
			 nownum_formulas.append(CellReference.convertNumToColString(cellnum-1)+(rownum+1));
			 
			 nownum_formulas.append(")");
			 Cell nownum=row.createCell(cellnum++);
			 nownum.setCellFormula(nownum_formulas.toString());
			 nownum.setCellStyle(black_style);
//			 Cell supplementnum=row.createCell(cellnum++);
//			 supplementnum.setCellValue(monthInventoryVO.getSupplementnum()==null?0:monthInventoryVO.getSupplementnum());
			 

			 

			 //循环出31天的数据
			 Map<String,DayInventory> days_nums=monthInventoryVO.getDayInvertorys();//entry.getValue();
			 for(int j=1;j<=31;j++){
				 

				 blue_style=getStyle(wb,IndexedColors.BLUE,null);
				 Cell purchasenum_mx=row.createCell(cellnum++);
				 purchasenum_mx.setCellStyle(blue_style);
 
				 Cell oldnum_mx=row.createCell(cellnum++);			
				 oldnum_mx.setCellStyle(blue_style);
				 
				 red_style=getStyle(wb,IndexedColors.RED,null);
				 Cell installoutnum_mx=row.createCell(cellnum++);				 
				 installoutnum_mx.setCellStyle(red_style);
				 
				 green_style=getStyle(wb,IndexedColors.GREEN,null);
				 Cell repairinnum_mx=row.createCell(cellnum++);				
				 repairinnum_mx.setCellStyle(green_style);
				 
				 orange_style=getStyle(wb,IndexedColors.ORANGE,null);
				 Cell scrapoutnum_mx=row.createCell(cellnum++);				 
				 scrapoutnum_mx.setCellStyle(orange_style);
				 
				
				 Cell repairoutnum_mx=row.createCell(cellnum++);				 
				 repairoutnum_mx.setCellStyle(orange_style);
				 
				 plum_style=getStyle(wb,IndexedColors.PLUM,null);
				 Cell adjustoutnum_mx=row.createCell(cellnum++);				 
				 adjustoutnum_mx.setCellStyle(plum_style);
				 
				 green_style=getStyle(wb,IndexedColors.GREEN,null);
				 Cell adjustinnum_mx=row.createCell(cellnum++);
				 
				 green_style.setBorderRight(CellStyle.BORDER_DOUBLE);
				 adjustinnum_mx.setCellStyle(green_style);
				 

				 DayInventory dayInventory=days_nums.get("day"+j);
				 if(dayInventory==null){
					 continue;
				 }
				 purchasenum_mx.setCellValue(dayInventory.getPurchasenum());
				 oldnum_mx.setCellValue(dayInventory.getOldnum());
				 installoutnum_mx.setCellValue(dayInventory.getInstalloutnum());
				 repairinnum_mx.setCellValue(dayInventory.getRepairinnum());
				 scrapoutnum_mx.setCellValue(dayInventory.getRepairoutnum());
				 repairoutnum_mx.setCellValue(dayInventory.getRepairoutnum());
				 adjustoutnum_mx.setCellValue(dayInventory.getAdjustoutnum());
				 adjustinnum_mx.setCellValue(dayInventory.getAdjustinnum());
			 }
		 }
		 
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		 String filename = "备品备件仓库("+store.getName()+")盘点日报表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
}
