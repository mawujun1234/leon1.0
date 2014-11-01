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
		sparepartDayReportService.createSparepartDayReport();
		
		
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
			//vo.put("day"+day+"_in", buildDayReport.getStoreinnum()==null?0:buildDayReport.getStoreinnum());
			vo.put("day"+day+"_out", buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
		}
		
		return result;
		
	}
	
	private StringBuilder[] addRow1(HSSFWorkbook wb,Sheet sheet){
		final int cellstartnum=9;//其实应该从10开始，单后面用了++
		
		//=================================================================================
		//日期的样式
		CellStyle day_style = wb.createCellStyle();
		Font day_font = wb.createFont();
		//day_font.setFontHeightInPoints((short) 14);
		//day_font.setColor(IndexedColors.RED.getIndex());
		day_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		day_style.setFont(day_font);
		day_style.setAlignment(CellStyle.ALIGN_CENTER);
		day_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		day_style.setWrapText(true);//自动换行
		day_style.setBorderTop(CellStyle.BORDER_THIN);
		day_style.setBorderBottom(CellStyle.BORDER_THIN);
		day_style.setBorderLeft(CellStyle.BORDER_THIN);
		day_style.setBorderRight(CellStyle.BORDER_THIN);
		
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
			cell11.setCellStyle(day_style);
			
			Cell cell12=row1.createCell(cellnum);
			cell12.setCellStyle(day_style);
		}
		//=================================================================================== 
		
		//
		//合并0--9的单元格，纵向合并
		for(int j=0;j<=cellstartnum;j++){
			sheet.addMergedRegion(new CellRangeAddress(1,2,(short)j,(short)j)); 
		}
		 Cell subtype_name=row1.createCell(0);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(day_style);
		 
		 Cell brand_name=row1.createCell(1);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(day_style);
		 
		 Cell style=row1.createCell(2);
		 style.setCellValue("型号");
		 style.setCellStyle(day_style);
		 
		 Cell prod_name=row1.createCell(3);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(day_style);
		 
		 Cell store_name=row1.createCell(4);
		 store_name.setCellValue("仓库");
		 store_name.setCellStyle(day_style);
		 
		 Cell unit=row1.createCell(5);
		 unit.setCellValue("单位");
		 unit.setCellStyle(day_style);
		 
		 Cell lastnum=row1.createCell(6);
		 lastnum.setCellValue("上期结余数");
		 lastnum.setCellStyle(day_style);
		 
		 Cell storeinnum=row1.createCell(7);
		 storeinnum.setCellValue("本期新增数");
		 storeinnum.setCellStyle(day_style);
		 
		 Cell installoutnum=row1.createCell(8);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(day_style);
		 
		 Cell nownum=row1.createCell(9);
		 nownum.setCellValue("本期结余数");
		 nownum.setCellStyle(day_style);
		 
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
			 
			 if(j==20){
				 in_formula.append(")+SUM(");
				 out_formula.append(")+SUM(");
			 }
			 if(j!=20&&j!=31){
				 in_formula.append(",");
				 out_formula.append(",");
			 }
			 
			
		 }
		in_formula.append(")");
		out_formula.append(")");
		
		 Cell memo=row2.createCell(++cellnum);
		 memo.setCellValue("备注"); 
		 memo.setCellStyle(day_style);
		 
		 //冻结行和列
		 sheet.createFreezePane(cellstartnum+1, 3);
		 
		 //生成本期新增数公式
		 StringBuilder[] formulas=new StringBuilder[]{in_formula,out_formula};
		 return formulas;
		 
		 
	}
	//生成公司
	//String[] letters=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y",'Z'];
	//private String genFormula(){
		
	//}
	//
	@RequestMapping("/sparepartdayreport/export.do")
	public void export(HttpServletResponse response,String year,String month,String store_id) throws IOException, IllegalAccessException, InvocationTargetException{
		
		Store store=storeService.get(store_id);
		Map<SparepartDayReport,Map<String,Integer>> result=query(year,month,store_id);
		
		HSSFWorkbook wb =new HSSFWorkbook();
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
		
		
		//for(int i=0;i<list.size();i++){
		int i=0;
		for(Entry<SparepartDayReport,Map<String,Integer>> entry:result.entrySet()){
			SparepartDayReport buildDayReport=entry.getKey();//list.get(i);
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
			 //storeinnum.setCellValue(buildDayReport.getStoreinnum()==null?0:buildDayReport.getStoreinnum());
			 storeinnum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum+1)+""));
			 
			 Cell installoutnum=row.createCell(8);
			 installoutnum.setCellValue(buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
			 installoutnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum+1)+""));
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
				 
				 sheet.setColumnWidth(cellstartnum+1, 7 * 256);
				 Cell day_out=row.createCell(++cellstartnum);
				 Integer value_out=days_nums.get("day"+j+"_out");
				 day_out.setCellValue(value_out==null?0:value_out);
			 }
		 }
		 
		 String filename = "在建工程仓库("+store.getName()+")盘点日报表.xls";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
	
	
}
