package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;

@Controller
public class BuildMonthReportController {
	//@Autowired
	//private JdbcTemplate jdbcTemplate;
	@Autowired
	private BuildMonthReportService buildMonthReportService;
	@Autowired
	private StoreService storeService;
	@RequestMapping("/buildmonthreport/query.do")
	public List<BuildMonthReport> query(String year,String month,String store_id){
		//String sql="select * from report_buildmonthreport where month='"+month+"' and store_id='"+store_id+"'";
		
		List<BuildMonthReport> list=buildMonthReportService.query(Cnd.select().andEquals(M.BuildMonthReport.monthkey, year+month)
				.andEquals(M.BuildMonthReport.store_id, store_id)
				.asc(M.BuildMonthReport.subtype_id)
				.asc(M.BuildMonthReport.prod_id)
				.asc(M.BuildMonthReport.brand_id)
				.asc(M.BuildMonthReport.style)
				);
		return list;
		
	}
	
	@RequestMapping("/buildmonthreport/updateMemo.do")
	public String updateMemo(BuildMonthReport buildDayReport){
		buildMonthReportService.update(buildDayReport);
		return "success";
	}
	
	
	private void addRow1(Sheet sheet){
		 Row row = sheet.createRow(1);
		 
		 Cell subtype_name=row.createCell(0);
		 subtype_name.setCellValue("小类");
		 
		 Cell brand_name=row.createCell(1);
		 brand_name.setCellValue("品牌");
		 
		 Cell style=row.createCell(2);
		 style.setCellValue("型号");
		 
		 Cell prod_name=row.createCell(3);
		 prod_name.setCellValue("品名");
		 
		 Cell store_name=row.createCell(4);
		 store_name.setCellValue("仓库");
		 
		 Cell unit=row.createCell(5);
		 unit.setCellValue("单位");
		 
		 Cell lastnum=row.createCell(6);
		 lastnum.setCellValue("上期结余数");
		 
		 Cell storeinnum=row.createCell(7);
		 storeinnum.setCellValue("本期新增数");
		 
		 Cell installoutnum=row.createCell(8);
		 installoutnum.setCellValue("本期领用数");
		 
		 Cell nownum=row.createCell(9);
		 nownum.setCellValue("本月结余数");
		 
		 Cell memo=row.createCell(10);
		 memo.setCellValue("备注"); 
	}
	@RequestMapping("/buildmonthreport/export.do")
	public void export(HttpServletResponse response,String year,String month,String store_id) throws IOException{
		
		Store store=storeService.get(store_id);
		List<BuildMonthReport> list=query(year,month,store_id);
		
		HSSFWorkbook wb =new HSSFWorkbook();
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
		addRow1(sheet);
		
		
		for(int i=0;i<list.size();i++){
			 BuildMonthReport buildDayReport=list.get(i);
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
			 lastnum.setCellValue(buildDayReport.getLastnum()==null?0:buildDayReport.getLastnum());
			 
			 Cell storeinnum=row.createCell(7);
			 storeinnum.setCellValue(buildDayReport.getStoreinnum()==null?0:buildDayReport.getStoreinnum());
			 
			 Cell installoutnum=row.createCell(8);
			 installoutnum.setCellValue(buildDayReport.getInstalloutnum()==null?0:buildDayReport.getInstalloutnum());
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
}
