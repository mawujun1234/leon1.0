package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.exception.BusinessException;
/**
 * 零星项目报表
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Controller
public class InstallOutReportController {
	SimpleDateFormat yyyy_MM_dd_formater=new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat MM_dd_formater=new SimpleDateFormat("MM-dd");
	@Resource
	private StoreService storeService;
	@Resource
	private StoreReportRepository storeReportRepository;
	
	int sparepart_month_freeze_num = 7;// 在建仓库月冻结的列数
	int type_group_end_num = 6;// 小类和大类分组的结束列
	
	/**
	 * 零星项目领用报表
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param response
	 * @param store_id
	 * @param date_start
	 * @param date_end
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/report/installout/excelExport.do")
	public void excelExport(HttpServletResponse response, String store_id, String date_start, String date_end) throws IOException, ParseException {
		long diff = yyyy_MM_dd_formater.parse(date_end).getTime() - yyyy_MM_dd_formater.parse(date_start).getTime();	
		long day_length = diff / (1000 * 60 * 60 * 24)+1;//一共显示多少明细数据
		if(day_length<=0){
			throw new BusinessException("日期选错了,请重新选择!");
		}
		
		//零星报表都是备品备件报表
		String store_name_title="备品备件仓库";
//		if (store_type == 1) {
//			store_name_title = "在建仓库";
//		}
		if(store_id!=null && !"".equals(store_id) ){
			Store store=storeService.get(store_id);
			store_name_title=store.getName();
		}
		
		
		//List<InstallOutReport_type> types = storeReportRepository.queryInstalloutReport(store_id,date_start,date_end);
		List<InstallOutReport_type> types =new ArrayList<InstallOutReport_type>();
		List<InstallOutReport_title> installOutReport_titles=new ArrayList<InstallOutReport_title>();
		if(!StringUtils.hasText(store_id)){
			List<Store> stores=storeService.queryCombo(new Integer[]{3},true,true);
			StringBuilder builder=new StringBuilder();
			for(Store store:stores){
				builder.append(",'");
				builder.append(store.getId());
				builder.append("'");
				
			}
			//查询所以可以编辑的仓库
			types = storeReportRepository.queryInstalloutReport(builder.substring(1),true, date_start,date_end);
			installOutReport_titles=storeReportRepository.queryInstalloutReport_title(builder.substring(1),true, date_start,date_end);
		} else {
			//查询具体某个一仓库
			types = storeReportRepository.queryInstalloutReport(store_id,false, date_start,date_end);
			installOutReport_titles=storeReportRepository.queryInstalloutReport_title(store_id,false, date_start,date_end);
		}

		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);// 一共有11列
		title.setHeight((short) 660);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(store_name_title+"零星项目领用报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		// f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		// 和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) sparepart_month_freeze_num-1));
		
		Row row_date = sheet.createRow(1);
		Cell cell_date=row_date.createCell(0);
		CellStyle cell_date_style = wb.createCellStyle();
		Font cell_date_f = wb.createFont();
		cell_date_f.setFontHeightInPoints((short) 12);
		cell_date_f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cell_date_style.setFont(cell_date_f);
		cell_date_style.setAlignment(CellStyle.ALIGN_LEFT);
		cell_date_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cell_date.setCellStyle(cell_date_style);
		cell_date.setCellValue("统计日期:"+date_start+"到"+date_end);
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 0, (short) type_group_end_num+1));

		// 设置第一行,设置列标题
		sparepart_addRow1(wb, sheet);

		CellStyle type_name_style = this.getStyle(wb, IndexedColors.BLACK, (short) 12);
		// black_style.setBorderBottom(CellStyle.BORDER_NONE);
		type_name_style.setWrapText(false);
		type_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		type_name_style.setBorderRight(CellStyle.BORDER_NONE);
		type_name_style.setBorderTop(CellStyle.BORDER_NONE);
		type_name_style.setAlignment(CellStyle.ALIGN_LEFT);
		type_name_style.setBorderBottom(CellStyle.BORDER_NONE);

		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.GREY_80_PERCENT, (short) 11);
		// black_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setWrapText(false);
		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle fixednum_style = getContentStyle(wb, null);
		fixednum_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle yesterdaynum_style = getContentStyle(wb, null);
		yesterdaynum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		yesterdaynum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle nownum_style = getContentStyle(wb, null);
		nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle supplementnum_style = getContentStyle(wb, null);
		supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle content_style = getContentStyle(wb, null);
//		CellStyle content_blue_style = getContentStyle(wb, IndexedColors.LIGHT_BLUE);
//		CellStyle content_red_style = getContentStyle(wb, IndexedColors.RED);
//		CellStyle content_green_style = getContentStyle(wb, IndexedColors.GREEN);
//		CellStyle content_orange_style = getContentStyle(wb, IndexedColors.ORANGE);
//		CellStyle content_plum_style = getContentStyle(wb, IndexedColors.PLUM);

//		CellStyle content_subtitle_style = getStyle(wb, IndexedColors.BLACK, null);
//		//content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
//		//content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
//		// content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
		
		//添加动态标题，即一级，二级领用类型
		int exists_cellnum=addInstallouttype_name_title(wb,sheet,installOutReport_titles);

		int cellnum = 0;
		int rownum = 4;//从第3行开始
		////用来存放领用类型所属的单元格第一个key是第一级的name，第二个map的key是第二级的name
		//Map<String,Map<String,Integer>> installtype_map=new LinkedHashMap<String,Map<String,Integer>>();
		Set<Integer> haveDataRow=new HashSet<Integer>();

//		Row row1 = sheet.getRow(2);
//		Row row2 = sheet.getRow(3);
		for (int i = 0; i < types.size(); i++) {
			cellnum = 0;
			InstallOutReport_type equipmentType = types.get(i);

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);

			Cell type_name = row.createCell(cellnum++);
			type_name.setCellValue(equipmentType.getType_name());
			type_name.setCellStyle(type_name_style);
			// subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, (short) type_group_end_num ));
			//sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));

			
			if (equipmentType.getSubtypes() != null) {
				int fromRow_type = rownum;
				for (InstallOutReport_subtype equipmentSubtype : equipmentType.getSubtypes()) {
					cellnum = 0;
					Row row_subtype = sheet.createRow(rownum++);

					cellnum++;

					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getSubtype_name());
					subtype_name.setCellStyle(subtype_name_style);

					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, (short) type_group_end_num ));
					//sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));

					// 
					int fromRow_subtype = rownum;
					List<InstallOutReport_prod> prods=equipmentSubtype.getProdes();
			
					for (InstallOutReport_prod prod:prods) {


						cellnum = 2;// 从第3个单元格开始
						Row row_prod = sheet.createRow(rownum++);
						haveDataRow.add(rownum-1);

						Cell brand_name = row_prod.createCell(cellnum++);
						brand_name.setCellValue(prod.getBrand_name());
						brand_name.setCellStyle(content_style);

						Cell style = row_prod.createCell(cellnum++);
						style.setCellValue(prod.getProd_style());
						style.setCellStyle(content_style);

						Cell prod_name = row_prod.createCell(cellnum++);
						prod_name.setCellValue(prod.getProd_name());
						prod_name.setCellStyle(content_style);

						Cell store_name = row_prod.createCell(cellnum++);
						if(storeService.get(prod.getStore_id())!=null){
							store_name.setCellValue(storeService.get(prod.getStore_id()).getName());
						}
						store_name.setCellStyle(content_style);

						Cell unit = row_prod.createCell(cellnum++);
						unit.setCellValue(prod.getProd_unit());
						unit.setCellStyle(content_style);

//						if(!installtype_map.containsKey(prod.getInstallouttype_name())){
//							Map<String,Integer> content_map=new LinkedHashMap<String,Integer>();
//							content_map.put(prod.getInstallouttype_content(), exists_cellnum);
//							content_map.put("first_cellnum", exists_cellnum);//第一级领用类型第一个单元格的位置
//							//content_map.put("first_cellvalue", prod.getInstallouttype_name());
//							//第一行表头--第一级领用类型
//							Cell installtype_cell=row1.createCell(exists_cellnum);
//							installtype_cell.setCellValue(prod.getInstallouttype_name());//
//							installtype_cell.setCellStyle(content_subtitle_style);
//							//二级领用类型，单元格合并在后面进行合并
//							Cell content_cell=row2.createCell(exists_cellnum);
//							content_cell.setCellValue(prod.getInstallouttype_content());
//							content_cell.setCellStyle(content_subtitle_style);
//							
//							installtype_map.put(prod.getInstallouttype_name(), content_map);
//							exists_cellnum++;
//							
//							
//						} else {
//							Map<String,Integer> content_map=installtype_map.get(prod.getInstallouttype_name());
//							if(!content_map.containsKey(prod.getInstallouttype_content())){
//								content_map.put(prod.getInstallouttype_content(), exists_cellnum);
//								//表头
//								Cell content_cell=row2.createCell(exists_cellnum);
//								content_cell.setCellValue(prod.getInstallouttype_content());
//								content_cell.setCellStyle(content_subtitle_style);
//								
//								
//								exists_cellnum++;
//							}
//						}
						
						for(InstallOutReport_title installOutReport_title:installOutReport_titles){
							if(installOutReport_title.getInstallouttype_content().equals(prod.getInstallouttype_content())){
								Cell content_cell = row_prod.createCell(installOutReport_title.getCell_index());
								content_cell.setCellValue(prod.getInstalloutnum());
								content_cell.setCellStyle(content_style);
							}
						}
						
					}
					// 小类的收缩
					sheet.groupRow(fromRow_subtype, rownum);
					sheet.setRowGroupCollapsed(fromRow_subtype, true);
				}
				// 设置最挖曾的收缩,就是类型的收缩
				sheet.groupRow(fromRow_type, rownum);
				sheet.setRowGroupCollapsed(fromRow_type, true);
			}
		}
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		//对没有数据的单元格加上黑色边框
		for(Integer rownum_temp:haveDataRow){
			Row row_temp=sheet.getRow(rownum_temp);
			for(int i=sparepart_month_freeze_num;i<sparepart_month_freeze_num+exists_cellnum;i++){
				Cell cell=row_temp.getCell(i);
				if(cell==null){
					cell=row_temp.createCell(i);
					cell.setCellStyle(content_style);
				}
			}
		}


		String filename = "零星项目领用报表.xlsx";

		// FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);

		out.flush();
		out.close();
	}
	
	private void sparepart_addRow1(XSSFWorkbook wb, Sheet sheet) {
		Row row = sheet.createRow(2);
		Row row2 = sheet.createRow(3);
		Row row3 = sheet.createRow(4);

		CellStyle black_style = getStyle(wb, IndexedColors.BLACK, null);
		int cellnum = 0;
		Cell type_name = row.createCell(cellnum++);
		
		type_name.setCellValue("大类");
		type_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 600);
		row2.createCell(cellnum-1).setCellStyle(black_style);	
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, cellnum - 1, (short) cellnum - 1));
		

		Cell subtype_name = row.createCell(cellnum++);
		subtype_name.setCellValue("小类");
		subtype_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 600);
		row2.createCell(cellnum-1).setCellStyle(black_style);	
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, cellnum - 1, (short) cellnum - 1));

		Cell brand_name = row.createCell(cellnum++);
		brand_name.setCellValue("品牌");
		brand_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 3000);
		row2.createCell(cellnum-1).setCellStyle(black_style);	
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, cellnum - 1, (short) cellnum - 1));

		Cell style = row.createCell(cellnum++);
		style.setCellValue("型号");
		style.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, "列".getBytes().length * 8 * 256);
		row2.createCell(cellnum-1).setCellStyle(black_style);	
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, cellnum - 1, (short) cellnum - 1));
		// sheet.autoSizeColumn(cellint-1, true);

		Cell prod_name = row.createCell(cellnum++);
		prod_name.setCellValue("品名");
		prod_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, "列".getBytes().length * 8 * 256);
		row2.createCell(cellnum-1).setCellStyle(black_style);	
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, cellnum - 1, (short) cellnum - 1));
		// sheet.autoSizeColumn(cellint-1, true);

		Cell store_name = row.createCell(cellnum++);
		store_name.setCellValue("仓库");
		store_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 2400);
		row2.createCell(cellnum-1).setCellStyle(black_style);	
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, cellnum - 1, (short) cellnum - 1));

		Cell unit = row.createCell(cellnum++);
		unit.setCellValue("单位");
		unit.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 600);
		row2.createCell(cellnum-1).setCellStyle(black_style);	
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 3, cellnum - 1, (short) cellnum - 1));


//		Cell memo = row.createCell(cellnum++);
//		memo.setCellValue("备注");
//		memo.setCellStyle(black_style);
//		sheet.setColumnWidth(cellnum - 1, 2400);

		// sheet.createFreezePane(16, 2);
		sheet.createFreezePane(sparepart_month_freeze_num, 4);
	}
	
	public CellStyle getStyle(XSSFWorkbook wb, IndexedColors color, Short fontSize) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		if (fontSize != null) {
			font.setFontHeightInPoints(fontSize);
		} else {
			font.setFontHeightInPoints((short) 10);
		}

		font.setColor(color.getIndex());
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

	private CellStyle getContentStyle(XSSFWorkbook wb, IndexedColors color) {
		CellStyle style = wb.createCellStyle();
		// style.setAlignment(CellStyle.ALIGN_CENTER);
		// style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font bord_font = wb.createFont();
		bord_font.setFontHeightInPoints((short) 10);
		if (color != null) {
			bord_font.setColor(color.getIndex());
		}
		//
		// bord_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		style.setFont(bord_font);
		style.setWrapText(true);// 自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}
	/**
	 * 添加动态的标题
	 * @param wb
	 * @param sheet
	 */
	private int addInstallouttype_name_title(XSSFWorkbook wb, Sheet sheet,List<InstallOutReport_title> installOutReport_titles) {
		Row row1 = sheet.getRow(2);
		Row row2 = sheet.getRow(3);
		
		CellStyle content_subtitle_style = getStyle(wb, IndexedColors.BLACK, null);
		
		int exists_cellnum=sparepart_month_freeze_num;//领用类型的初始迁移量
		int first_cellnum=exists_cellnum;
		
		Set<String> temp_first=new HashSet<String>();//一级领用各类型
		Set<String> temp_second=new HashSet<String>();//二级领用各类型
		for(InstallOutReport_title installOutReport_title:installOutReport_titles){
			
			if(!temp_first.contains(installOutReport_title.getInstallouttype_name())){
				Cell installtype_cell=row1.createCell(exists_cellnum);
				installtype_cell.setCellValue(installOutReport_title.getInstallouttype_name());//
				installtype_cell.setCellStyle(content_subtitle_style);
				
				temp_first.add(installOutReport_title.getInstallouttype_name());
			}
			
			
			//二级领用类型，单元格合并在后面进行合并
			Cell content_cell=row2.createCell(exists_cellnum);
			content_cell.setCellValue(installOutReport_title.getInstallouttype_content());
			content_cell.setCellStyle(content_subtitle_style);
			installOutReport_title.setCell_index(exists_cellnum);
			
			if(!temp_second.contains(installOutReport_title.getInstallouttype_content())){	
				if(first_cellnum!=exists_cellnum){
					sheet.addMergedRegion(new CellRangeAddress(2, 2, first_cellnum,exists_cellnum));
					first_cellnum=exists_cellnum+1;
				}
				
				
				temp_second.add(installOutReport_title.getInstallouttype_content());
				exists_cellnum++;
				
				
				
			}
		}
		//返回一共有多少个二级类型的个数
		return temp_second.size();
		
	}
	
	/**
	 * 添加动态的标题,包含净值的
	 * @param wb
	 * @param sheet
	 */
	private int addInstallouttype_name_title_assetclean(XSSFWorkbook wb, Sheet sheet,List<InstallOutReport_title> installOutReport_titles) {
		Row row1 = sheet.getRow(2);
		Row row2 = sheet.getRow(3);
		Row row3 = sheet.getRow(4);
		
		CellStyle content_subtitle_style = getStyle(wb, IndexedColors.BLACK, null);
		
		int exists_cellnum=sparepart_month_freeze_num;//领用类型的初始迁移量
		int first_first_cellnum=exists_cellnum;
		int second_first_cellnum=exists_cellnum;
		
		
		Set<String> temp_first=new HashSet<String>();//一级领用各类型
		Set<String> temp_second=new HashSet<String>();//二级领用各类型
		for(InstallOutReport_title installOutReport_title:installOutReport_titles){
			
			if(!temp_first.contains(installOutReport_title.getInstallouttype_name())){
				Cell installtype_cell=row1.createCell(exists_cellnum);
				installtype_cell.setCellValue(installOutReport_title.getInstallouttype_name());//
				installtype_cell.setCellStyle(content_subtitle_style);
				
				temp_first.add(installOutReport_title.getInstallouttype_name());
				
				if(first_first_cellnum!=exists_cellnum){
					sheet.addMergedRegion(new CellRangeAddress(2, 2, first_first_cellnum,exists_cellnum-1));
					first_first_cellnum=exists_cellnum;
				}
				
			}
			
			
			//二级领用类型，单元格合并在后面进行合并
			Cell content_cell=row2.createCell(exists_cellnum);
			content_cell.setCellValue(installOutReport_title.getInstallouttype_content());
			content_cell.setCellStyle(content_subtitle_style);
			installOutReport_title.setCell_index(exists_cellnum);
			
			
			//后面的数量和净值
			Cell cell=row3.createCell(exists_cellnum);
			cell.setCellValue("数量");
			cell.setCellStyle(content_subtitle_style);
//			//边框
//			cell=row2.createCell(exists_cellnum);
//			cell.setCellStyle(content_subtitle_style);
			
			exists_cellnum++;
			cell=row3.createCell(exists_cellnum);
			cell.setCellValue("净值");
			cell.setCellStyle(content_subtitle_style);
//			//边框
//			cell=row2.createCell(exists_cellnum);
//			cell.setCellStyle(content_subtitle_style);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, second_first_cellnum,second_first_cellnum+1));
			
			
			
			if(!temp_second.contains(installOutReport_title.getInstallouttype_content())){	
				if(second_first_cellnum!=exists_cellnum){
					//第一行标题 合并单元格
					//sheet.addMergedRegion(new CellRangeAddress(2, 2, first_cellnum,exists_cellnum));
					//sheet.addMergedRegion(new CellRangeAddress(2, 2, first_cellnum,first_cellnum+(exists_cellnum-first_cellnum+1)*2));
					second_first_cellnum=exists_cellnum+1;
				}
				
				
				temp_second.add(installOutReport_title.getInstallouttype_content());
				exists_cellnum++;
				
				
				
			}
		}
		sheet.addMergedRegion(new CellRangeAddress(2, 2, first_first_cellnum,exists_cellnum-1));
		//返回一共有多少个二级类型的个数
		return temp_second.size()*2;
		
	}
	
	/**
	 * 零星项目领用报表
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param response
	 * @param store_id
	 * @param date_start
	 * @param date_end
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/report/installout/excelExport_assetclean.do")
	public void excelExport_assetclean(HttpServletResponse response, String store_id, String date_start, String date_end) throws IOException, ParseException {
		long diff = yyyy_MM_dd_formater.parse(date_end).getTime() - yyyy_MM_dd_formater.parse(date_start).getTime();	
		long day_length = diff / (1000 * 60 * 60 * 24)+1;//一共显示多少明细数据
		if(day_length<=0){
			throw new BusinessException("日期选错了,请重新选择!");
		}
		
		//零星报表都是备品备件报表
		String store_name_title="备品备件仓库";
//		if (store_type == 1) {
//			store_name_title = "在建仓库";
//		}
		if(store_id!=null && !"".equals(store_id) ){
			Store store=storeService.get(store_id);
			store_name_title=store.getName();
		}
		
		
		//List<InstallOutReport_type> types = storeReportRepository.queryInstalloutReport(store_id,date_start,date_end);
		List<InstallOutReport_type> types =new ArrayList<InstallOutReport_type>();
		List<InstallOutReport_title> installOutReport_titles=new ArrayList<InstallOutReport_title>();
		if(!StringUtils.hasText(store_id)){
			List<Store> stores=storeService.queryCombo(new Integer[]{3},true,true);
			StringBuilder builder=new StringBuilder();
			for(Store store:stores){
				builder.append(",'");
				builder.append(store.getId());
				builder.append("'");
				
			}
			//查询所以可以编辑的仓库
			types = storeReportRepository.queryInstalloutReport(builder.substring(1),true, date_start,date_end);
			installOutReport_titles=storeReportRepository.queryInstalloutReport_title(builder.substring(1),true, date_start,date_end);
		} else {
			//查询具体某个一仓库
			types = storeReportRepository.queryInstalloutReport(store_id,false, date_start,date_end);
			installOutReport_titles=storeReportRepository.queryInstalloutReport_title(store_id,false, date_start,date_end);
		}

		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);// 一共有11列
		title.setHeight((short) 660);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(store_name_title+"零星项目领用报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		// f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		// 和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) sparepart_month_freeze_num-1));
		
		Row row_date = sheet.createRow(1);
		Cell cell_date=row_date.createCell(0);
		CellStyle cell_date_style = wb.createCellStyle();
		Font cell_date_f = wb.createFont();
		cell_date_f.setFontHeightInPoints((short) 12);
		cell_date_f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cell_date_style.setFont(cell_date_f);
		cell_date_style.setAlignment(CellStyle.ALIGN_LEFT);
		cell_date_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cell_date.setCellStyle(cell_date_style);
		cell_date.setCellValue("统计日期:"+date_start+"到"+date_end);
		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 0, (short) type_group_end_num+1));

		// 设置第一行,设置列标题
		sparepart_addRow1(wb, sheet);

		CellStyle type_name_style = this.getStyle(wb, IndexedColors.BLACK, (short) 12);
		// black_style.setBorderBottom(CellStyle.BORDER_NONE);
		type_name_style.setWrapText(false);
		type_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		type_name_style.setBorderRight(CellStyle.BORDER_NONE);
		type_name_style.setBorderTop(CellStyle.BORDER_NONE);
		type_name_style.setAlignment(CellStyle.ALIGN_LEFT);
		type_name_style.setBorderBottom(CellStyle.BORDER_NONE);

		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.GREY_80_PERCENT, (short) 11);
		// black_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setWrapText(false);
		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);

		CellStyle fixednum_style = getContentStyle(wb, null);
		fixednum_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle yesterdaynum_style = getContentStyle(wb, null);
		yesterdaynum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		yesterdaynum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle nownum_style = getContentStyle(wb, null);
		nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle supplementnum_style = getContentStyle(wb, null);
		supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle content_style = getContentStyle(wb, null);
//		CellStyle content_blue_style = getContentStyle(wb, IndexedColors.LIGHT_BLUE);
//		CellStyle content_red_style = getContentStyle(wb, IndexedColors.RED);
//		CellStyle content_green_style = getContentStyle(wb, IndexedColors.GREEN);
//		CellStyle content_orange_style = getContentStyle(wb, IndexedColors.ORANGE);
//		CellStyle content_plum_style = getContentStyle(wb, IndexedColors.PLUM);

//		CellStyle content_subtitle_style = getStyle(wb, IndexedColors.BLACK, null);
//		//content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
//		//content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
//		// content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
		
		//添加动态标题，即一级，二级领用类型
		int exists_cellnum=addInstallouttype_name_title_assetclean(wb,sheet,installOutReport_titles);

		int cellnum = 0;
		int rownum = 5;//从第3行开始
		////用来存放领用类型所属的单元格第一个key是第一级的name，第二个map的key是第二级的name
		//Map<String,Map<String,Integer>> installtype_map=new LinkedHashMap<String,Map<String,Integer>>();
		Set<Integer> haveDataRow=new HashSet<Integer>();

//		Row row1 = sheet.getRow(2);
//		Row row2 = sheet.getRow(3);
		for (int i = 0; i < types.size(); i++) {
			cellnum = 0;
			InstallOutReport_type equipmentType = types.get(i);

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);

			Cell type_name = row.createCell(cellnum++);
			type_name.setCellValue(equipmentType.getType_name());
			type_name.setCellStyle(type_name_style);
			// subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, (short) type_group_end_num ));
			//sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));

			
			if (equipmentType.getSubtypes() != null) {
				int fromRow_type = rownum;
				for (InstallOutReport_subtype equipmentSubtype : equipmentType.getSubtypes()) {
					cellnum = 0;
					Row row_subtype = sheet.createRow(rownum++);

					cellnum++;

					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getSubtype_name());
					subtype_name.setCellStyle(subtype_name_style);

					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, (short) type_group_end_num ));
					//sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));

					// 
					int fromRow_subtype = rownum;
					List<InstallOutReport_prod> prods=equipmentSubtype.getProdes();
			
					for (InstallOutReport_prod prod:prods) {


						cellnum = 2;// 从第3个单元格开始
						Row row_prod = sheet.createRow(rownum++);
						haveDataRow.add(rownum-1);

						Cell brand_name = row_prod.createCell(cellnum++);
						brand_name.setCellValue(prod.getBrand_name());
						brand_name.setCellStyle(content_style);

						Cell style = row_prod.createCell(cellnum++);
						style.setCellValue(prod.getProd_style());
						style.setCellStyle(content_style);

						Cell prod_name = row_prod.createCell(cellnum++);
						prod_name.setCellValue(prod.getProd_name());
						prod_name.setCellStyle(content_style);

						Cell store_name = row_prod.createCell(cellnum++);
						if(storeService.get(prod.getStore_id())!=null){
							store_name.setCellValue(storeService.get(prod.getStore_id()).getName());
						}
						store_name.setCellStyle(content_style);

						Cell unit = row_prod.createCell(cellnum++);
						unit.setCellValue(prod.getProd_unit());
						unit.setCellStyle(content_style);

//						if(!installtype_map.containsKey(prod.getInstallouttype_name())){
//							Map<String,Integer> content_map=new LinkedHashMap<String,Integer>();
//							content_map.put(prod.getInstallouttype_content(), exists_cellnum);
//							content_map.put("first_cellnum", exists_cellnum);//第一级领用类型第一个单元格的位置
//							//content_map.put("first_cellvalue", prod.getInstallouttype_name());
//							//第一行表头--第一级领用类型
//							Cell installtype_cell=row1.createCell(exists_cellnum);
//							installtype_cell.setCellValue(prod.getInstallouttype_name());//
//							installtype_cell.setCellStyle(content_subtitle_style);
//							//二级领用类型，单元格合并在后面进行合并
//							Cell content_cell=row2.createCell(exists_cellnum);
//							content_cell.setCellValue(prod.getInstallouttype_content());
//							content_cell.setCellStyle(content_subtitle_style);
//							
//							installtype_map.put(prod.getInstallouttype_name(), content_map);
//							exists_cellnum++;
//							
//							
//						} else {
//							Map<String,Integer> content_map=installtype_map.get(prod.getInstallouttype_name());
//							if(!content_map.containsKey(prod.getInstallouttype_content())){
//								content_map.put(prod.getInstallouttype_content(), exists_cellnum);
//								//表头
//								Cell content_cell=row2.createCell(exists_cellnum);
//								content_cell.setCellValue(prod.getInstallouttype_content());
//								content_cell.setCellStyle(content_subtitle_style);
//								
//								
//								exists_cellnum++;
//							}
//						}
						
						for(InstallOutReport_title installOutReport_title:installOutReport_titles){
							if(installOutReport_title.getInstallouttype_content().equals(prod.getInstallouttype_content())){
								Cell content_cell = row_prod.createCell(installOutReport_title.getCell_index());
								content_cell.setCellValue(prod.getInstalloutnum());
								content_cell.setCellStyle(content_style);
								
								content_cell = row_prod.createCell(installOutReport_title.getCell_index()+1);
								content_cell.setCellValue(prod.getValue_net().toString());
								content_cell.setCellStyle(content_style);
							}
						}
						
					}
					// 小类的收缩
					sheet.groupRow(fromRow_subtype, rownum);
					sheet.setRowGroupCollapsed(fromRow_subtype, true);
				}
				// 设置最挖曾的收缩,就是类型的收缩
				sheet.groupRow(fromRow_type, rownum);
				sheet.setRowGroupCollapsed(fromRow_type, true);
			}
		}
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		//对没有数据的单元格加上黑色边框
		for(Integer rownum_temp:haveDataRow){
			Row row_temp=sheet.getRow(rownum_temp);
			for(int i=sparepart_month_freeze_num;i<sparepart_month_freeze_num+exists_cellnum;i++){
				Cell cell=row_temp.getCell(i);
				if(cell==null){
					cell=row_temp.createCell(i);
					cell.setCellStyle(content_style);
				}
			}
		}


		String filename = "零星项目领用报表-净值.xlsx";

		// FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);

		out.flush();
		out.close();
	}
}
