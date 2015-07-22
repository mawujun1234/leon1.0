package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.CustomerService;
import com.mawujun.baseinfo.ProjectService;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.utils.StringUtils;

/**
 * 查询一些仓库报表用的
 * @author mawujun 16064988@qq.com  
 *
 */
@Controller
public class StoreReportController {
	@Resource
	private StoreReportRepository storeReportRepository;
	@Resource
	private StoreService storeService;
	@Resource
	private ProjectService projectService;
	@Resource
	private CustomerService customerService;
	
	private void gen_InstalloutListReport_header(XSSFWorkbook wb, Sheet sheet,
			int rownum) {
		// 创建表头行
		int cellnum = 0;
		Row header_row = sheet.createRow(rownum);
		CellStyle head_style = getHeaderStyle(wb, (short) 10);
		Cell seq_cell = header_row.createCell(cellnum++);
		seq_cell.setCellValue("序号");
		seq_cell.setCellStyle(head_style);

		Cell operatedate_cell = header_row.createCell(cellnum++);
		operatedate_cell.setCellValue("日期");
		operatedate_cell.setCellStyle(head_style);

		Cell ecode_cell = header_row.createCell(cellnum++);
		ecode_cell.setCellValue("二维码");
		ecode_cell.setCellStyle(head_style);

		Cell subtype_cell = header_row.createCell(cellnum++);
		subtype_cell.setCellValue("类别");
		subtype_cell.setCellStyle(head_style);

		Cell brand_name_cell = header_row.createCell(cellnum++);
		brand_name_cell.setCellValue("品牌");
		brand_name_cell.setCellStyle(head_style);

		Cell prod_name_cell = header_row.createCell(cellnum++);
		prod_name_cell.setCellValue("品名");
		prod_name_cell.setCellStyle(head_style);

		Cell prod_style_cell = header_row.createCell(cellnum++);
		prod_style_cell.setCellValue("规格型号");
		prod_style_cell.setCellStyle(head_style);

		Cell prod_unit_cell = header_row.createCell(cellnum++);
		prod_unit_cell.setCellValue("单位");
		prod_unit_cell.setCellStyle(head_style);

		Cell project_name_cell = header_row.createCell(cellnum++);
		project_name_cell.setCellValue("项目");
		project_name_cell.setCellStyle(head_style);

		Cell store_name_cell = header_row.createCell(cellnum++);
		store_name_cell.setCellValue("仓库");
		store_name_cell.setCellStyle(head_style);

		Cell workunit_name_cell = header_row.createCell(cellnum++);
		workunit_name_cell.setCellValue("领用单位");
		workunit_name_cell.setCellStyle(head_style);
		
		Cell installouttype_cell = header_row.createCell(cellnum++);
		installouttype_cell.setCellValue("领用类型");
		installouttype_cell.setCellStyle(head_style);

		Cell customer_name_cell = header_row.createCell(cellnum++);
		customer_name_cell.setCellValue("派出所");
		customer_name_cell.setCellStyle(head_style);

		Cell pole_name_cell = header_row.createCell(cellnum++);
		pole_name_cell.setCellValue("设备去向");
		pole_name_cell.setCellStyle(head_style);

		Cell installout_id_cell = header_row.createCell(cellnum++);
		installout_id_cell.setCellValue("领用单号");
		installout_id_cell.setCellStyle(head_style);

		Cell memo_cell = header_row.createCell(cellnum++);
		memo_cell.setCellValue("备注");
		memo_cell.setCellStyle(head_style);
	}
	
	@RequestMapping("/report/storereport/queryInstalloutListReport")
	public void queryInstalloutListReport(HttpServletResponse response,String store_id,String project_id,String date_start,String date_end) throws IOException{
		
		String store_name="所有仓库";
		if(StringUtils.hasText(store_id)){
			store_name=storeService.get(store_id).getName();
		}

		String project_name="所有项目";
		if(StringUtils.hasText(project_id)){
			project_name=projectService.get(project_id).getName();
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("领用明细报表");
		int rownum=0;
		Row title = sheet.createRow(rownum++);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(store_name+"--领用明细报表");
		CellStyle title_style = wb.createCellStyle();
		Font title_font = wb.createFont();
		title_font.setFontHeightInPoints((short) 16);
		// f.setColor(IndexedColors.RED.getIndex());
		title_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		title_style.setFont(title_font);
		title_style.setAlignment(CellStyle.ALIGN_CENTER);
		title_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(title_style);
		// 和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 15));
		
		//创建第2行，放置项目信息 和日期
		Row subtitle = sheet.createRow(rownum++);
		Cell subtitle_project_cell = subtitle.createCell(0);
		subtitle_project_cell.setCellValue("项目:"+project_name);
		Cell subtitle_date_cell = subtitle.createCell(12);
		subtitle_date_cell.setCellValue("日期范围:"+date_start+"到"+date_end);
		CellStyle subtitle_style = wb.createCellStyle();
		Font subtitle_font = wb.createFont();
		subtitle_font.setFontHeightInPoints((short) 11);
		// f.setColor(IndexedColors.RED.getIndex());
		subtitle_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		subtitle_style.setFont(subtitle_font);
		subtitle_style.setAlignment(CellStyle.ALIGN_LEFT);
		subtitle_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		subtitle_project_cell.setCellStyle(subtitle_style);
		subtitle_date_cell.setCellStyle(subtitle_style);
		
		
		//产生表格的表头
		gen_InstalloutListReport_header(wb,sheet,rownum++);
		
		//--------------------------------------
		int cellnum=0;
		CellStyle content_style =getContentStyle(wb,(short)10);
		List<InstalloutListReport> result=storeReportRepository.queryInstalloutListReport(store_id,project_id,date_start,date_end);
		if(result!=null){
			int seq=1;
			for(InstalloutListReport installoutListReport:result){
				cellnum=0;
				Row row = sheet.createRow(rownum++);
				Cell seq_cell = row.createCell(cellnum++);
				seq_cell.setCellValue(seq);
				seq_cell.setCellStyle(content_style);
				seq++;
				
				Cell operatedate_cell = row.createCell(cellnum++);
				operatedate_cell.setCellValue(installoutListReport.getOperatedate());
				operatedate_cell.setCellStyle(content_style);
				
				Cell ecode_cell = row.createCell(cellnum++);
				ecode_cell.setCellValue(installoutListReport.getEcode());
				ecode_cell.setCellStyle(content_style);
				
				Cell subtype_cell = row.createCell(cellnum++);
				subtype_cell.setCellValue(installoutListReport.getSubtype_name());
				subtype_cell.setCellStyle(content_style);
				
				Cell brand_name_cell = row.createCell(cellnum++);
				brand_name_cell.setCellValue(installoutListReport.getBrand_name());
				brand_name_cell.setCellStyle(content_style);
				
				Cell prod_name_cell = row.createCell(cellnum++);
				prod_name_cell.setCellValue(installoutListReport.getProd_name());
				prod_name_cell.setCellStyle(content_style);
				
				Cell prod_style_cell = row.createCell(cellnum++);
				prod_style_cell.setCellValue(installoutListReport.getProd_style());
				prod_style_cell.setCellStyle(content_style);
				
				Cell prod_unit_cell = row.createCell(cellnum++);
				prod_unit_cell.setCellValue(installoutListReport.getProd_unit());
				prod_unit_cell.setCellStyle(content_style);
				
				Cell project_name_cell = row.createCell(cellnum++);
				if(projectService.get(installoutListReport.getProject_id())!=null){
					project_name_cell.setCellValue(projectService.get(installoutListReport.getProject_id()).getName());
				}
				project_name_cell.setCellStyle(content_style);
				
				Cell store_name_cell = row.createCell(cellnum++);
				if(storeService.get(installoutListReport.getStore_id())!=null){
					store_name_cell.setCellValue(storeService.get(installoutListReport.getStore_id()).getName());
				}
				store_name_cell.setCellStyle(content_style);
				
				Cell workunit_name_cell = row.createCell(cellnum++);
				workunit_name_cell.setCellValue(installoutListReport.getWorkunit_name());
				workunit_name_cell.setCellStyle(content_style);
				
				Cell installouttype_cell = row.createCell(cellnum++);
				installouttype_cell.setCellValue(installoutListReport.getInstallouttype());
				installouttype_cell.setCellStyle(content_style);
				
				Cell customer_name_cell = row.createCell(cellnum++);
				if(customerService.get(installoutListReport.getCustomer_id())!=null){
					customer_name_cell.setCellValue(customerService.get(installoutListReport.getCustomer_id()).getName());
				}
				customer_name_cell.setCellStyle(content_style);
				
				Cell pole_name_cell = row.createCell(cellnum++);
				pole_name_cell.setCellValue(installoutListReport.getPole_name());
				pole_name_cell.setCellStyle(content_style);
				
				Cell installout_id_cell = row.createCell(cellnum++);
				installout_id_cell.setCellValue(installoutListReport.getInstallout_id());
				installout_id_cell.setCellStyle(content_style);
				
				Cell memo_cell = row.createCell(cellnum++);
				memo_cell.setCellValue("备注");
				memo_cell.setCellStyle(content_style);
			}
		}
		
		
		String filename = "领用明细报表.xlsx";

		// FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);

		out.flush();
		out.close();
	}
	
	
	private void gen_borrowListReport_header(XSSFWorkbook wb, Sheet sheet,
			int rownum) {
		// 创建表头行
		int cellnum = 0;
		Row header_row = sheet.createRow(rownum);
		CellStyle head_style = getHeaderStyle(wb, (short) 10);
		Cell seq_cell = header_row.createCell(cellnum++);
		seq_cell.setCellValue("序号");
		seq_cell.setCellStyle(head_style);

		Cell operatedate_cell = header_row.createCell(cellnum++);
		operatedate_cell.setCellValue("日期");
		operatedate_cell.setCellStyle(head_style);

		Cell ecode_cell = header_row.createCell(cellnum++);
		ecode_cell.setCellValue("二维码");
		ecode_cell.setCellStyle(head_style);

		Cell subtype_cell = header_row.createCell(cellnum++);
		subtype_cell.setCellValue("类别");
		subtype_cell.setCellStyle(head_style);

		Cell brand_name_cell = header_row.createCell(cellnum++);
		brand_name_cell.setCellValue("品牌");
		brand_name_cell.setCellStyle(head_style);

		Cell prod_name_cell = header_row.createCell(cellnum++);
		prod_name_cell.setCellValue("品名");
		prod_name_cell.setCellStyle(head_style);

		Cell prod_style_cell = header_row.createCell(cellnum++);
		prod_style_cell.setCellValue("规格型号");
		prod_style_cell.setCellStyle(head_style);

		Cell prod_unit_cell = header_row.createCell(cellnum++);
		prod_unit_cell.setCellValue("单位");
		prod_unit_cell.setCellStyle(head_style);

		Cell project_name_cell = header_row.createCell(cellnum++);
		project_name_cell.setCellValue("项目");
		project_name_cell.setCellStyle(head_style);

		Cell store_name_cell = header_row.createCell(cellnum++);
		store_name_cell.setCellValue("仓库");
		store_name_cell.setCellStyle(head_style);

		Cell workunit_name_cell = header_row.createCell(cellnum++);
		workunit_name_cell.setCellValue("借用人");
		workunit_name_cell.setCellStyle(head_style);

		Cell borrowtype_cell = header_row.createCell(cellnum++);
		borrowtype_cell.setCellValue("借用类型");
		borrowtype_cell.setCellStyle(head_style);
		
		Cell status_cell = header_row.createCell(cellnum++);
		status_cell.setCellValue("借用状态");
		status_cell.setCellStyle(head_style);

		Cell borrow_id_cell = header_row.createCell(cellnum++);
		borrow_id_cell.setCellValue("借用单号");
		borrow_id_cell.setCellStyle(head_style);

		Cell memo_cell = header_row.createCell(cellnum++);
		memo_cell.setCellValue("备注");
		memo_cell.setCellStyle(head_style);
	}
	@RequestMapping("/report/storereport/queryBorrowListReport")
	public void queryBorrowListReport(HttpServletResponse response,String store_id,String project_id,String date_start,String date_end) throws IOException{
		
		String store_name="所有仓库";
		if(StringUtils.hasText(store_id)){
			store_name=storeService.get(store_id).getName();
		}

		String project_name="所有项目";
		if(StringUtils.hasText(project_id)){
			project_name=projectService.get(project_id).getName();
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("借用明细报表");
		int rownum=0;
		Row title = sheet.createRow(rownum++);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(store_name+"--借用明细报表");
		CellStyle title_style = wb.createCellStyle();
		Font title_font = wb.createFont();
		title_font.setFontHeightInPoints((short) 16);
		// f.setColor(IndexedColors.RED.getIndex());
		title_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		title_style.setFont(title_font);
		title_style.setAlignment(CellStyle.ALIGN_CENTER);
		title_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(title_style);
		// 和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 14));
		
		//创建第2行，放置项目信息 和日期
		Row subtitle = sheet.createRow(rownum++);
		Cell subtitle_project_cell = subtitle.createCell(0);
		subtitle_project_cell.setCellValue("项目:"+project_name);
		Cell subtitle_date_cell = subtitle.createCell(11);
		subtitle_date_cell.setCellValue("日期范围:"+date_start+"到"+date_end);
		CellStyle subtitle_style = wb.createCellStyle();
		Font subtitle_font = wb.createFont();
		subtitle_font.setFontHeightInPoints((short) 10);
		// f.setColor(IndexedColors.RED.getIndex());
		subtitle_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		subtitle_style.setFont(subtitle_font);
		subtitle_style.setAlignment(CellStyle.ALIGN_LEFT);
		subtitle_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		subtitle_project_cell.setCellStyle(subtitle_style);
		subtitle_date_cell.setCellStyle(subtitle_style);
		
		
		//产生表格的表头
		gen_borrowListReport_header(wb,sheet,rownum++);
		
		//--------------------------------------
		int cellnum=0;
		CellStyle content_style =getContentStyle(wb,(short)10);
		List<BorrowListReport> result=storeReportRepository.queryBorrowListReport(store_id,project_id,date_start,date_end);
		if(result!=null){
			int seq=1;
			for(BorrowListReport borrowListReport:result){
				cellnum=0;
				Row row = sheet.createRow(rownum++);
				Cell seq_cell = row.createCell(cellnum++);
				seq_cell.setCellValue(seq);
				seq_cell.setCellStyle(content_style);
				seq++;
				
				Cell operatedate_cell = row.createCell(cellnum++);
				operatedate_cell.setCellValue(borrowListReport.getOperatedate());
				operatedate_cell.setCellStyle(content_style);
				
				Cell ecode_cell = row.createCell(cellnum++);
				ecode_cell.setCellValue(borrowListReport.getEcode());
				ecode_cell.setCellStyle(content_style);
				
				Cell subtype_cell = row.createCell(cellnum++);
				subtype_cell.setCellValue(borrowListReport.getSubtype_name());
				subtype_cell.setCellStyle(content_style);
				
				Cell brand_name_cell = row.createCell(cellnum++);
				brand_name_cell.setCellValue(borrowListReport.getBrand_name());
				brand_name_cell.setCellStyle(content_style);
				
				Cell prod_name_cell = row.createCell(cellnum++);
				prod_name_cell.setCellValue(borrowListReport.getProd_name());
				prod_name_cell.setCellStyle(content_style);
				
				Cell prod_style_cell = row.createCell(cellnum++);
				prod_style_cell.setCellValue(borrowListReport.getProd_style());
				prod_style_cell.setCellStyle(content_style);
				
				Cell prod_unit_cell = row.createCell(cellnum++);
				prod_unit_cell.setCellValue(borrowListReport.getProd_unit());
				prod_unit_cell.setCellStyle(content_style);
				
				Cell project_name_cell = row.createCell(cellnum++);
				if(projectService.get(borrowListReport.getProject_id())!=null){
					project_name_cell.setCellValue(projectService.get(borrowListReport.getProject_id()).getName());
				}
				project_name_cell.setCellStyle(content_style);
				
				Cell store_name_cell = row.createCell(cellnum++);
				if(storeService.get(borrowListReport.getStore_id())!=null){
					store_name_cell.setCellValue(storeService.get(borrowListReport.getStore_id()).getName());
				}
				store_name_cell.setCellStyle(content_style);
				
				Cell workunit_name_cell = row.createCell(cellnum++);
				workunit_name_cell.setCellValue(borrowListReport.getWorkunit_name());
				workunit_name_cell.setCellStyle(content_style);
				
				Cell borrowtype_cell = row.createCell(cellnum++);
				borrowtype_cell.setCellValue(borrowListReport.getBorrowtype());
				borrowtype_cell.setCellStyle(content_style);
				
				Cell status_cell = row.createCell(cellnum++);
				status_cell.setCellValue(borrowListReport.getStatus());
				status_cell.setCellStyle(content_style);
				
				Cell borrow_id_cell = row.createCell(cellnum++);
				borrow_id_cell.setCellValue(borrowListReport.getBorrow_id());
				borrow_id_cell.setCellStyle(content_style);
				
				Cell memo_cell = row.createCell(cellnum++);
				//memo_cell.setCellValue("备注");
				memo_cell.setCellStyle(content_style);
			}
		}
		
		
		String filename = "借用明细报表.xlsx";

		// FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);

		out.flush();
		out.close();
	}
	
	private void gen_instorelistReport_header(XSSFWorkbook wb, Sheet sheet,int rownum) {
		// 创建表头行
		int cellnum = 0;
		Row header_row = sheet.createRow(rownum);
		CellStyle head_style = getHeaderStyle(wb, (short) 10);
		Cell seq_cell = header_row.createCell(cellnum++);
		seq_cell.setCellValue("序号");
		seq_cell.setCellStyle(head_style);

		Cell operatedate_cell = header_row.createCell(cellnum++);
		operatedate_cell.setCellValue("日期");
		operatedate_cell.setCellStyle(head_style);

		Cell ecode_cell = header_row.createCell(cellnum++);
		ecode_cell.setCellValue("二维码");
		ecode_cell.setCellStyle(head_style);

		Cell subtype_cell = header_row.createCell(cellnum++);
		subtype_cell.setCellValue("类别");
		subtype_cell.setCellStyle(head_style);

		Cell brand_name_cell = header_row.createCell(cellnum++);
		brand_name_cell.setCellValue("品牌");
		brand_name_cell.setCellStyle(head_style);

		Cell prod_name_cell = header_row.createCell(cellnum++);
		prod_name_cell.setCellValue("品名");
		prod_name_cell.setCellStyle(head_style);

		Cell prod_style_cell = header_row.createCell(cellnum++);
		prod_style_cell.setCellValue("规格型号");
		prod_style_cell.setCellStyle(head_style);

		Cell prod_unit_cell = header_row.createCell(cellnum++);
		prod_unit_cell.setCellValue("单位");
		prod_unit_cell.setCellStyle(head_style);

		Cell project_name_cell = header_row.createCell(cellnum++);
		project_name_cell.setCellValue("项目");
		project_name_cell.setCellStyle(head_style);

		Cell store_name_cell = header_row.createCell(cellnum++);
		store_name_cell.setCellValue("入库仓库");
		store_name_cell.setCellStyle(head_style);

		Cell instoretype_cell = header_row.createCell(cellnum++);
		instoretype_cell.setCellValue("入库类型");
		instoretype_cell.setCellStyle(head_style);
		
		Cell instore_id_cell = header_row.createCell(cellnum++);
		instore_id_cell.setCellValue("入库单号");
		instore_id_cell.setCellStyle(head_style);

		Cell memo_cell = header_row.createCell(cellnum++);
		memo_cell.setCellValue("备注");
		memo_cell.setCellStyle(head_style);
	}
	@RequestMapping("/report/storereport/queryInstoreListReport.do")
	public void queryInstoreListReport(HttpServletResponse response,String store_id,String project_id,String date_start,String date_end) throws IOException{
		String store_name="所有仓库";
		if(StringUtils.hasText(store_id)){
			store_name=storeService.get(store_id).getName();
		}

		String project_name="所有项目";
		if(StringUtils.hasText(project_id)){
			project_name=projectService.get(project_id).getName();
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("入库明细报表");
		int rownum=0;
		Row title = sheet.createRow(rownum++);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(store_name+"--入库明细报表");
		CellStyle title_style = wb.createCellStyle();
		Font title_font = wb.createFont();
		title_font.setFontHeightInPoints((short) 16);
		// f.setColor(IndexedColors.RED.getIndex());
		title_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		title_style.setFont(title_font);
		title_style.setAlignment(CellStyle.ALIGN_CENTER);
		title_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(title_style);
		// 和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 14));
		
		//创建第2行，放置项目信息 和日期
		Row subtitle = sheet.createRow(rownum++);
		Cell subtitle_project_cell = subtitle.createCell(0);
		subtitle_project_cell.setCellValue("项目:"+project_name);
		Cell subtitle_date_cell = subtitle.createCell(11);
		subtitle_date_cell.setCellValue("日期范围:"+date_start+"到"+date_end);
		CellStyle subtitle_style = wb.createCellStyle();
		Font subtitle_font = wb.createFont();
		subtitle_font.setFontHeightInPoints((short) 10);
		// f.setColor(IndexedColors.RED.getIndex());
		subtitle_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		subtitle_style.setFont(subtitle_font);
		subtitle_style.setAlignment(CellStyle.ALIGN_LEFT);
		subtitle_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		subtitle_project_cell.setCellStyle(subtitle_style);
		subtitle_date_cell.setCellStyle(subtitle_style);
		
		
		//产生表格的表头
		gen_instorelistReport_header(wb,sheet,rownum++);
		
		
		//--------------------------------------
				int cellnum=0;
				CellStyle content_style =getContentStyle(wb,(short)10);
				List<InstoreListReport> result=storeReportRepository.queryInstoreListReport(store_id,project_id,date_start,date_end);
				if(result!=null){
					int seq=1;
					for(InstoreListReport instoreListReport:result){
						cellnum=0;
						Row row = sheet.createRow(rownum++);
						Cell seq_cell = row.createCell(cellnum++);
						seq_cell.setCellValue(seq);
						seq_cell.setCellStyle(content_style);
						seq++;
						
						Cell operatedate_cell = row.createCell(cellnum++);
						operatedate_cell.setCellValue(instoreListReport.getOperatedate());
						operatedate_cell.setCellStyle(content_style);
						
						Cell ecode_cell = row.createCell(cellnum++);
						ecode_cell.setCellValue(instoreListReport.getEcode());
						ecode_cell.setCellStyle(content_style);
						
						Cell subtype_cell = row.createCell(cellnum++);
						subtype_cell.setCellValue(instoreListReport.getSubtype_name());
						subtype_cell.setCellStyle(content_style);
						
						Cell brand_name_cell = row.createCell(cellnum++);
						brand_name_cell.setCellValue(instoreListReport.getBrand_name());
						brand_name_cell.setCellStyle(content_style);
						
						Cell prod_name_cell = row.createCell(cellnum++);
						prod_name_cell.setCellValue(instoreListReport.getProd_name());
						prod_name_cell.setCellStyle(content_style);
						
						Cell prod_style_cell = row.createCell(cellnum++);
						prod_style_cell.setCellValue(instoreListReport.getProd_style());
						prod_style_cell.setCellStyle(content_style);
						
						Cell prod_unit_cell = row.createCell(cellnum++);
						prod_unit_cell.setCellValue(instoreListReport.getProd_unit());
						prod_unit_cell.setCellStyle(content_style);
						
						Cell project_name_cell = row.createCell(cellnum++);
						if("修复入库".equals(instoreListReport.getProject_id())){
							project_name_cell.setCellValue(instoreListReport.getProject_id());
						} else {
							if(projectService.get(instoreListReport.getProject_id())!=null){
								project_name_cell.setCellValue(projectService.get(instoreListReport.getProject_id()).getName());
							}
						}
						project_name_cell.setCellStyle(content_style);
						
						Cell store_name_cell = row.createCell(cellnum++);
						if(storeService.get(instoreListReport.getStore_id())!=null){
							store_name_cell.setCellValue(storeService.get(instoreListReport.getStore_id()).getName());
						}
						store_name_cell.setCellStyle(content_style);
						
						
						Cell instoretype_cell = row.createCell(cellnum++);
						instoretype_cell.setCellValue(instoreListReport.getInstoretype());
						instoretype_cell.setCellStyle(content_style);
						
						Cell instore_id_cell = row.createCell(cellnum++);
						instore_id_cell.setCellValue(instoreListReport.getInstore_id());
						instore_id_cell.setCellStyle(content_style);
						
						Cell memo_cell = row.createCell(cellnum++);
						//memo_cell.setCellValue("备注");
						memo_cell.setCellStyle(content_style);
					}
				}
		
		
		
		
		String filename = "入库明细报表.xlsx";

		// FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);

		out.flush();
		out.close();
	}
	
	public CellStyle getContentStyle(XSSFWorkbook wb, Short fontSize) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if (fontSize != null) {
			font.setFontHeightInPoints(fontSize);
		} else {
			font.setFontHeightInPoints((short) 10);
		}

		//font.setColor(color.getIndex());
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);// 自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}
	public CellStyle getHeaderStyle(XSSFWorkbook wb, Short fontSize) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if (fontSize != null) {
			font.setFontHeightInPoints(fontSize);
		} else {
			font.setFontHeightInPoints((short) 10);
		}

		//font.setColor(color.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);// 自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}

}
