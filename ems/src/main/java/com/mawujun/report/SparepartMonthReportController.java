package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
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
/**
 * 备品备件库的控制器
 * @author mawujun 16064988@qq.com  
 *
 */
@Controller
public class SparepartMonthReportController {
	@Autowired
	private SparepartMonthReportService sparepartMonthReportService;
	@Autowired
	private StoreService storeService;
	
	@RequestMapping("/sparepartmonthreport/query.do")
	public List<SparepartMonthReport> query(String year,String month,String store_id){
		//String sql="select * from report_buildmonthreport where month='"+month+"' and store_id='"+store_id+"'";
		
		List<SparepartMonthReport> list=sparepartMonthReportService.query(Cnd.select().andEquals(M.SparepartMonthReport.monthkey, year+month)
				.andEquals(M.SparepartMonthReport.store_id, store_id)
				.asc(M.SparepartMonthReport.subtype_id)
				.asc(M.SparepartMonthReport.prod_id)
				.asc(M.SparepartMonthReport.brand_id)
				.asc(M.SparepartMonthReport.style));
		return list;
		
	}
	@RequestMapping("/sparepartmonthreport/update.do")
	public String update(SparepartMonthReport sparepartMonthReport){
		sparepartMonthReportService.update(sparepartMonthReport);
		return "success";
	}
	
	private void addRow1(Sheet sheet){
		 Row row = sheet.createRow(1);
		 
		 int cellint=0;
		 Cell subtype_name=row.createCell(cellint++);
		 subtype_name.setCellValue("小类");
		 
		 Cell brand_name=row.createCell(cellint++);
		 brand_name.setCellValue("品牌");
		 
		 Cell style=row.createCell(cellint++);
		 style.setCellValue("型号");
		 
		 Cell prod_name=row.createCell(cellint++);
		 prod_name.setCellValue("品名");
		 
		 Cell store_name=row.createCell(cellint++);
		 store_name.setCellValue("仓库");
		 
		 Cell unit=row.createCell(cellint++);
		 unit.setCellValue("单位");
		 
		 Cell fixednum=row.createCell(cellint++);
		 fixednum.setCellValue("额定数量");
		 
		 Cell lastnum=row.createCell(cellint++);
		 lastnum.setCellValue("上月结余");
		 
		 Cell purchasenum=row.createCell(cellint++);
		 purchasenum.setCellValue("采购新增");
		 
		 Cell oldnum=row.createCell(cellint++);
		 oldnum.setCellValue("旧品新增");
		 
		 Cell installoutnum=row.createCell(cellint++);
		 installoutnum.setCellValue("本期领用");
		 
		 Cell repairinnum=row.createCell(cellint++);
		 repairinnum.setCellValue("本期维修返还数");
		 
		 Cell scrapoutnum=row.createCell(cellint++);
		 scrapoutnum.setCellValue("报废出库数量");
		 
		 Cell repairoutnum=row.createCell(cellint++);
		 repairoutnum.setCellValue("维修出库数量");
		 
		 Cell adjustoutnum=row.createCell(cellint++);
		 adjustoutnum.setCellValue("借用数");
		 
		 Cell adjustinnum=row.createCell(cellint++);
		 adjustinnum.setCellValue("返还数");
		 
		 Cell nownum=row.createCell(cellint++);
		 nownum.setCellValue("本月结余");
		 
		 Cell supplementnum=row.createCell(cellint++);
		 supplementnum.setCellValue("增部数");

		 Cell memo=row.createCell(cellint++);
		 memo.setCellValue("备注"); 
	}
	@RequestMapping("/sparepartmonthreport/export.do")
	public void export(HttpServletResponse response,String year,String month,String store_id) throws IOException{
		
		Store store=storeService.get(store_id);
		List<SparepartMonthReport> list=query(year,month,store_id);
		
		HSSFWorkbook wb =new HSSFWorkbook();
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
		addRow1(sheet);
		
		
		for(int i=0;i<list.size();i++){
			SparepartMonthReport sparepartMonthReport=list.get(i);
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
