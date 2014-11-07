package com.mawujun.report;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.mawujun.inventory.DayInventory_PK;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.ReflectUtils;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/sparepartdayreport")
public class SparepartDayReportController {

	@Resource
	private SparepartDayReportService sparepartDayReportService;
	@Autowired
	private StoreService storeService;
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");

	
	public Map<SparepartDayReport,Map<String,Integer>> query(String year,String month,String store_id) throws IllegalAccessException, InvocationTargetException{
		//计算今天到目前为止的数据，就是计算当前为止的日报数据
		//sparepartDayReportService.createSparepartDayReport();
		
		
		//这获取的是到今天为止的数据
		List<SparepartDayReport> list=sparepartDayReportService.query(Cnd.select()
				.andGTE(M.SparepartDayReport.daykey, Long.parseLong(year+month+"01"))
				.andLTE(M.SparepartDayReport.daykey, Long.parseLong(year+month+"31"))
				.andEquals(M.SparepartDayReport.store_id, store_id)
				.asc(M.SparepartDayReport.subtype_id)
				.asc(M.SparepartDayReport.prod_id)
				.asc(M.SparepartDayReport.brand_id)
				.asc(M.SparepartDayReport.style));
		
		Map<SparepartDayReport,Map<String,Integer>> result=new HashMap<SparepartDayReport,Map<String,Integer>>();
		//索引map,用来获取key
		Map<DayInventory_PK,SparepartDayReport> temp=new HashMap<DayInventory_PK,SparepartDayReport>();
		Map<String,Integer> vo=null;
		for(SparepartDayReport buildDayReport:list){
			DayInventory_PK pk_noday=buildDayReport.getId();
			pk_noday.setDaykey(null);
			if(temp.containsKey(pk_noday)){
				vo=result.get(temp.get(pk_noday));
			} else {
				vo= new HashMap<String,Integer>();
				result.put(buildDayReport,vo);
				temp.put(pk_noday, buildDayReport);
			}
			//然后把值设置到对应的字段中
			Integer day=Integer.parseInt((buildDayReport.getDaykey()+"").substring(6));
			vo.put("purchasenum"+day, buildDayReport.getPurchasenum()==null?0:buildDayReport.getPurchasenum());
			vo.put("oldnum"+day, buildDayReport.getOldnum()==null?0:buildDayReport.getOldnum());
			vo.put("installoutnum"+day, buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
			vo.put("repairinnum"+day, buildDayReport.getRepairinnum()==null?0:buildDayReport.getRepairinnum());
			vo.put("scrapoutnum"+day, buildDayReport.getScrapoutnum()==null?0:buildDayReport.getScrapoutnum());
			vo.put("repairoutnum"+day, buildDayReport.getRepairoutnum()==null?0:buildDayReport.getRepairoutnum());
			vo.put("adjustoutnum"+day, buildDayReport.getAdjustoutnum()==null?0:buildDayReport.getAdjustoutnum());
			vo.put("adjustinnum"+day, buildDayReport.getAdjustinnum()==null?0:buildDayReport.getAdjustinnum());
		}
		
		return result;
		
	}
	public CellStyle getStyle(XSSFWorkbook wb,IndexedColors color,Short fontSize){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if(fontSize!=null){
			font.setFontHeightInPoints(fontSize);
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
	private StringBuilder[] addRow1(XSSFWorkbook wb,Sheet sheet){
		
		int cellnum=0;
		Row row1 = sheet.createRow(1);
		
		 CellStyle black_style=getStyle(wb,IndexedColors.BLACK,null);
		 Cell subtype_name=row1.createCell(cellnum++);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 
		 Cell brand_name=row1.createCell(cellnum++);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 
		 Cell style=row1.createCell(cellnum++);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 sheet.autoSizeColumn(cellnum-1, true);
		 
		 Cell prod_name=row1.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.autoSizeColumn(cellnum-1, true);
		 
		 Cell store_name=row1.createCell(cellnum++);
		 store_name.setCellValue("仓库");
		 store_name.setCellStyle(black_style);
		 
		 Cell unit=row1.createCell(cellnum++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 
		 Cell fixednum=row1.createCell(cellnum++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell lastnum=row1.createCell(cellnum++);
		 lastnum.setCellValue("上月结余");
		 lastnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.BLUE,null);
		 Cell purchasenum=row1.createCell(cellnum++);
		 purchasenum.setCellValue("本期采购新增");
		 purchasenum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		
		 Cell oldnum=row1.createCell(cellnum++);
		 oldnum.setCellValue("旧品新增");
		 oldnum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED,null);
		 Cell installoutnum=row1.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用");
		 installoutnum.setCellStyle(red_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle green_style=getStyle(wb,IndexedColors.GREEN,null);
		 Cell repairinnum=row1.createCell(cellnum++);
		 repairinnum.setCellValue("本期维修返还数");
		 repairinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle orange_style=getStyle(wb,IndexedColors.ORANGE,null);
		 Cell scrapoutnum=row1.createCell(cellnum++);
		 scrapoutnum.setCellValue("报废出库数量");
		 scrapoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell repairoutnum=row1.createCell(cellnum++);
		 repairoutnum.setCellValue("维修出库数量");
		 repairoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 CellStyle plum_style=getStyle(wb,IndexedColors.PLUM,null);
		 Cell adjustoutnum=row1.createCell(cellnum++);
		 adjustoutnum.setCellValue("本期借用数");
		 adjustoutnum.setCellStyle(plum_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell adjustinnum=row1.createCell(cellnum++);
		 adjustinnum.setCellValue("本期归还数");
		 adjustinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell nownum=row1.createCell(cellnum++);
		 nownum.setCellValue("本月结余");
		 nownum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);
		 
		 Cell supplementnum=row1.createCell(cellnum++);
		 supplementnum.setCellValue("增补数量");
		 supplementnum.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, "列长".getBytes().length*256);

		 
		 
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
		StringBuilder in_formula=new StringBuilder("SUM(");
		StringBuilder out_formula=new StringBuilder("SUM(");
		for(int j=1;j<=31;j++){
			 
			blue_style=getStyle(wb,IndexedColors.BLUE,(short)10);
			 Cell purchasenum_repeat=row2.createCell(cellnum_repeat++);
			 purchasenum_repeat.setCellValue("采购新增");
			 purchasenum_repeat.setCellStyle(blue_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 
			 Cell oldnum_repeat=row2.createCell(cellnum_repeat++);
			 oldnum_repeat.setCellValue("旧品新增");
			 oldnum_repeat.setCellStyle(blue_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 
			 red_style=getStyle(wb,IndexedColors.RED,(short)10);
			 Cell installoutnum_repeat=row2.createCell(cellnum_repeat++);
			 installoutnum_repeat.setCellValue("本期领用");
			 installoutnum_repeat.setCellStyle(red_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 
			 green_style=getStyle(wb,IndexedColors.GREEN,(short)10);
			 Cell repairinnum_repeat=row2.createCell(cellnum_repeat++);
			 repairinnum_repeat.setCellValue("维修返还数");
			 repairinnum_repeat.setCellStyle(green_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 
			 orange_style=getStyle(wb,IndexedColors.ORANGE,(short)10);
			 Cell scrapoutnum_repeat=row2.createCell(cellnum_repeat++);
			 scrapoutnum_repeat.setCellValue("报废出库");
			 scrapoutnum_repeat.setCellStyle(orange_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 
			 Cell repairoutnum_repeat=row2.createCell(cellnum_repeat++);
			 repairoutnum_repeat.setCellValue("维修出库");
			 repairoutnum_repeat.setCellStyle(orange_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 
			 plum_style=getStyle(wb,IndexedColors.PLUM,(short)10);
			 Cell adjustoutnum_repeat=row2.createCell(cellnum_repeat++);
			 adjustoutnum_repeat.setCellValue("借用数");
			 adjustoutnum_repeat.setCellStyle(plum_style);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);
			 
			 green_style=getStyle(wb,IndexedColors.GREEN,(short)10);
			 Cell adjustinnum_repeat=row2.createCell(cellnum_repeat++);
			 adjustinnum_repeat.setCellValue("归还数");
			 adjustinnum_repeat.setCellStyle(green_style);
			 green_style.setBorderRight(CellStyle.BORDER_DOUBLE);
			 sheet.setColumnWidth(cellnum_repeat-1, "列长".getBytes().length*256);

	
//			 Cell day_out=row2.createCell(++cellnum_repeat);
//			 day_out.setCellValue("领用数");
//			 day_out.setCellStyle(out_style);
//			 out_formula.append(CellReference.convertNumToColString(cellnum_repeat)).append("=");
			 if(j!=31){
				 in_formula.append(",");
				 out_formula.append(",");
			 }
			 
			
		 }
		in_formula.append(")");
		out_formula.append(")");
		

		Cell memo = row2.createCell(cellnum_repeat++);
		memo.setCellValue("备注");
		memo.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, "列长".getBytes().length * 256);
		
		//合并单元格的行
		//Row row1 = sheet.createRow(1);
		//创建日期，并合并日期的两列
		black_style=getStyle(wb,IndexedColors.BLACK,null);
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
		 StringBuilder[] formulas=new StringBuilder[]{in_formula,out_formula};
		 return formulas;
		 
		 
	}

	//
	@RequestMapping("/sparepartdayreport/export.do")
	public void export(HttpServletResponse response,String year,String month,String store_id) throws IOException, IllegalAccessException, InvocationTargetException{
		
		Store store=storeService.get(store_id);
		Map<SparepartDayReport,Map<String,Integer>> result=query(year,month,store_id);
		
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
		
		
//		//for(int i=0;i<list.size();i++){
//		int i=0;
//		for(Entry<SparepartDayReport,Map<String,Integer>> entry:result.entrySet()){
//			SparepartDayReport buildDayReport=entry.getKey();//list.get(i);
//			int rownum=i+3;
//			 Row row = sheet.createRow(rownum);
//			 i++;
//			 
//			 Cell subtype_name=row.createCell(0);
//			 subtype_name.setCellValue(buildDayReport.getSubtype_name());
//			 
//			 Cell brand_name=row.createCell(1);
//			 brand_name.setCellValue(buildDayReport.getBrand_name());
//			 
//			 Cell style=row.createCell(2);
//			 style.setCellValue(buildDayReport.getStyle());
//			 
//			 Cell prod_name=row.createCell(3);
//			 prod_name.setCellValue(buildDayReport.getProd_name());
//			 
//			 Cell store_name=row.createCell(4);
//			 store_name.setCellValue(buildDayReport.getStore_name());
//			 
//			 Cell unit=row.createCell(5);
//			 unit.setCellValue(buildDayReport.getUnit());
//			 
//			 //上期结余
//			 Cell lastnum=row.createCell(6);
//			 lastnum.setCellValue(buildDayReport.getLastnum()==null?0:buildDayReport.getLastnum());
//			 
//			 
//			 //本期新增数
//			 Cell storeinnum=row.createCell(7);
//			 //storeinnum.setCellValue(buildDayReport.getStoreinnum()==null?0:buildDayReport.getStoreinnum());
//			 storeinnum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum+1)+""));
//			 
//			 Cell installoutnum=row.createCell(8);
//			 installoutnum.setCellValue(buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
//			 installoutnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum+1)+""));
//			 //本月结余 上期+本期新增+本期领用
//			 Cell nownum=row.createCell(9);
//			 //nownum.setCellValue(buildDayReport.getNownum()==null?0:buildDayReport.getNownum());
//			 nownum.setCellFormula("SUM("+CellReference.convertNumToColString(6)+(rownum+1)+
//					 ","+CellReference.convertNumToColString(7)+(rownum+1)+
//					 ","+CellReference.convertNumToColString(8)+(rownum+1)+")");
//			 
//
//			 //循环出31天的数据
//			 Map<String,Integer> days_nums=entry.getValue();
//			 int cellstartnum=9;
//			 for(int j=1;j<=31;j++){
//				 sheet.setColumnWidth(cellstartnum+1, 7 * 256);
//				 Cell day_in=row.createCell(++cellstartnum);
//				 Integer value_in=days_nums.get("day"+j+"_in");
//				 day_in.setCellValue(value_in==null?0:value_in);
//				 
//				 sheet.setColumnWidth(cellstartnum+1, 7 * 256);
//				 Cell day_out=row.createCell(++cellstartnum);
//				 Integer value_out=days_nums.get("day"+j+"_out");
//				 day_out.setCellValue(value_out==null?0:value_out);
//			 }
//		 }
		 
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
