package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.baseinfo.EquipmentSubtype;
import com.mawujun.baseinfo.EquipmentType;
import com.mawujun.baseinfo.EquipmentTypeRepository;
import com.mawujun.baseinfo.Store;
import com.mawujun.baseinfo.StoreService;
import com.mawujun.exception.BusinessException;

@Controller
public class Month_sparepart_assetclean_Controller {
	@Resource
	private EquipmentTypeRepository equipmentTypeRepository;
	@Resource
	private Day_sparepart_Service day_sparepart_Service;
	@Resource
	private StoreService storeService;
	
	SimpleDateFormat yyyy_MM_dd_formater=new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat MM_dd_formater=new SimpleDateFormat("MM-dd");

	@RequestMapping("/inventory/month/sparepart/excelExport_assetclean.do")
	public void excelExport_assetclean(HttpServletResponse response, String store_id, Integer store_type, String date_start, String date_end) throws IOException, ParseException {
		long diff = yyyy_MM_dd_formater.parse(date_end).getTime() - yyyy_MM_dd_formater.parse(date_start).getTime();	
		long day_length = diff / (1000 * 60 * 60 * 24)+1;//一共显示多少明细数据
		if(day_length<=0){
			throw new BusinessException("日期选错了,请重新选择!");
		}
		
		
		List<Month_sparepart_type> types = day_sparepart_Service.queryMonth_sparepartVO(store_id, store_type,date_start.replaceAll("-", ""),date_end.replaceAll("-", ""));
		Map<String,Object> yesterdaynum_map=day_sparepart_Service.queryMonth_yesterdaynum(store_id, store_type, date_start.replaceAll("-", ""));
		String store_name_title="备品备件仓库";
		if (store_type == 1) {
			store_name_title = "在建仓库";
		}
		if(store_id!=null && !"".equals(store_id) ){
			Store store=storeService.get(store_id);
			store_name_title=store.getName();
		}

		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("盘点汇总表");
		Row title = sheet.createRow(0);// 一共有11列
		title.setHeight((short) 660);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(store_name_title+"盘点汇总表");
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
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) type_group_end_num+1));
		
		// 设置第一行,设置列标题
		sparepart_addRow1(wb, sheet,store_type);
		
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
		CellStyle content_blue_style = getContentStyle(wb, IndexedColors.LIGHT_BLUE);
		CellStyle content_red_style = getContentStyle(wb, IndexedColors.RED);
		CellStyle content_green_style = getContentStyle(wb, IndexedColors.GREEN);
		CellStyle content_orange_style = getContentStyle(wb, IndexedColors.ORANGE);
		CellStyle content_plum_style = getContentStyle(wb, IndexedColors.PLUM);

		CellStyle content_subtitle_style = getContentStyle(wb, null);
		content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		// content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);

		int cellnum = 0;
		int rownum = 4;
		for (int i = 0; i < types.size(); i++) {
			cellnum = 0;
			Month_sparepart_type equipmentType = types.get(i);

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);

			Cell type_name = row.createCell(cellnum++);
			type_name.setCellValue(equipmentType.getType_name());
			type_name.setCellStyle(type_name_style);
			// subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, (short) type_group_end_num+1 ));
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num+2, (short) type_group_end_num + 4));

			// 描绘小类
			StringBuilder nownum_formule_builder = new StringBuilder();
			StringBuilder supplementnum_formule_builder = new StringBuilder();
			if (equipmentType.getSubtypes() != null) {
				int fromRow_type = rownum;
				for (Month_sparepart_subtype equipmentSubtype : equipmentType.getSubtypes()) {
					cellnum = 0;
					Row row_subtype = sheet.createRow(rownum++);

					cellnum++;

					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getSubtype_name());
					subtype_name.setCellStyle(subtype_name_style);

					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, (short) type_group_end_num+1));
					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num+2, (short) type_group_end_num + 4));

					// 弄几行模拟品名的数据，即几个空行
					int fromRow_subtype = rownum;
					List<Month_sparepart_prod> prods=equipmentSubtype.getProdes();
					for (Month_sparepart_prod prod:prods) {
						nownum_formule_builder = new StringBuilder();
						nownum_formule_builder.append("SUM(");

						supplementnum_formule_builder = new StringBuilder();

						cellnum = 2;// 从第3个单元格开始
						Row row_prod = sheet.createRow(rownum++);

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
						store_name.setCellValue(prod.getStore_name());
						store_name.setCellStyle(content_style);

						Cell unit = row_prod.createCell(cellnum++);
						unit.setCellValue(prod.getProd_unit());
						unit.setCellStyle(content_style);

//						if (store_type == 3) {
//							// 额定数量
//							Cell fixednum = row_prod.createCell(cellnum++);
//							//fixednum.setCellValue();
//							fixednum.setCellStyle(fixednum_style);
//							supplementnum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//						}

						// 上月结余
						Cell yesterdaynum = row_prod.createCell(cellnum++);
						String prod_key=prod.getProd_id()+"_"+prod.getStore_id();
						if(yesterdaynum_map.get(prod_key)!=null){
							yesterdaynum.setCellValue((Integer)yesterdaynum_map.get(prod_key));
						} else {
							yesterdaynum.setCellValue(0);
						}
						//yesterdaynum.setCellValue(prod.getYesterdaynum());
						yesterdaynum.setCellStyle(yesterdaynum_style);
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						
						Cell yesterdaynum_net = row_prod.createCell(cellnum++);
						yesterdaynum_net.setCellStyle(yesterdaynum_style);
						if(yesterdaynum_map.get(prod_key+"_net")!=null){	
							yesterdaynum_net.setCellValue(((Double)yesterdaynum_map.get(prod_key+"_net")));
							
						}
						

						// 本期采购新增
						Cell purchasenum = row_prod.createCell(cellnum++);
						purchasenum.setCellValue(prod.getPurchasenum());
						purchasenum.setCellStyle(content_blue_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));

						// 本期旧品新增
						Cell oldnum = row_prod.createCell(cellnum++);
						oldnum.setCellValue(prod.getOldnum());
						oldnum.setCellStyle(content_blue_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						// 本期领用数
						Cell installoutnum = row_prod.createCell(cellnum++);
						installoutnum.setCellValue(prod.getInstalloutnum());
						installoutnum.setCellStyle(content_red_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						// 本期维修返还数
						Cell repairinnum = row_prod.createCell(cellnum++);
						repairinnum.setCellValue(prod.getRepairinnum());
						repairinnum.setCellStyle(content_green_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						// 报废出库
						Cell scrapoutnum = row_prod.createCell(cellnum++);
						scrapoutnum.setCellValue(prod.getScrapoutnum());
						scrapoutnum.setCellStyle(content_orange_style);
						// 维修出库
						Cell repairoutnum = row_prod.createCell(cellnum++);
						repairoutnum.setCellValue(prod.getRepairoutnum());
						repairoutnum.setCellStyle(content_orange_style);
						// 本期借用数
						Cell borrownum = row_prod.createCell(cellnum++);
						borrownum.setCellValue(prod.getBorrownum());
						borrownum.setCellStyle(content_plum_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						// 本期归还数
						Cell borrowreturnnum = row_prod.createCell(cellnum++);
						borrowreturnnum.setCellValue(prod.getBorrowreturnnum());
						borrowreturnnum.setCellStyle(content_style);
						nownum_formule_builder.append(",");
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(")");
						//本期结余
						Cell nownum = row_prod.createCell(cellnum++);
						nownum.setCellFormula(nownum_formule_builder.toString());
						nownum.setCellStyle(nownum_style);
						supplementnum_formule_builder.append("-" + CellReference.convertNumToColString(cellnum - 1) + (rownum));
						Cell nownum_net = row_prod.createCell(cellnum++);
						nownum_net.setCellStyle(nownum_style);
						if(prod.getValue_net()!=null){
							nownum_net.setCellValue(prod.getValue_net().doubleValue());
						}
						
						if (store_type == 3) {
							//增补数量
							Cell supplementnum = row_prod.createCell(cellnum++);
							// supplementnum.setCellValue(12);
							supplementnum.setCellStyle(supplementnum_style);
							supplementnum.setCellFormula(supplementnum_formule_builder.toString());
						}

						Cell memo = row_prod.createCell(cellnum++);
						memo.setCellValue("");
						memo.setCellStyle(content_style);

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

		String filename = "备品备件仓库盘点汇总表.xlsx";
		if (store_type == 1) {
			filename = "在建仓库盘点汇总表.xlsx";
		}
		// FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);

		out.flush();
		out.close();
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

	private void sparepart_addRow1(XSSFWorkbook wb, Sheet sheet,int store_type) {
		Row row = sheet.createRow(2);
		Row row3 = sheet.createRow(3);

		CellStyle black_style = getStyle(wb, IndexedColors.BLACK, null);
		int cellnum = 0;
		Cell type_name = row.createCell(cellnum++);
		type_name.setCellValue("大类");
		type_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 600);

		Cell subtype_name = row.createCell(cellnum++);
		subtype_name.setCellValue("小类");
		subtype_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 600);

		Cell brand_name = row.createCell(cellnum++);
		brand_name.setCellValue("品牌");
		brand_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 3000);

		Cell style = row.createCell(cellnum++);
		style.setCellValue("型号");
		style.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, "列".getBytes().length * 8 * 256);
		// sheet.autoSizeColumn(cellint-1, true);

		Cell prod_name = row.createCell(cellnum++);
		prod_name.setCellValue("品名");
		prod_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, "列".getBytes().length * 8 * 256);
		// sheet.autoSizeColumn(cellint-1, true);

		Cell store_name = row.createCell(cellnum++);
		store_name.setCellValue("仓库");
		store_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 2400);

		Cell unit = row.createCell(cellnum++);
		unit.setCellValue("单位");
		unit.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 600);

//		if (store_type == 3) {
//			CellStyle fixednum_style = getStyle(wb, IndexedColors.BLACK, null);
//			fixednum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//			fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//			Cell fixednum = row.createCell(cellnum++);
//			fixednum.setCellValue("额定数量");
//			fixednum.setCellStyle(fixednum_style);
//			sheet.setColumnWidth(cellnum - 1, 1200);
//		}
		

		CellStyle yesterdaynum_style = getStyle(wb, IndexedColors.BLACK, null);
		yesterdaynum_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		yesterdaynum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		Cell lastnum = row.createCell(cellnum++);
		lastnum.setCellValue("上期结余数");
		lastnum.setCellStyle(yesterdaynum_style);
		//sheet.setColumnWidth(cellnum - 1, 1800);
		
		Cell lastnum_num = row3.createCell(cellnum-1);
		lastnum_num.setCellValue("数量");
		lastnum_num.setCellStyle(yesterdaynum_style);
		sheet.setColumnWidth(cellnum-1, 1800);
		Cell lastnum_net = row3.createCell(cellnum++);
		lastnum_net.setCellValue("金额");
		lastnum_net.setCellStyle(yesterdaynum_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		CellStyle blue_style = getStyle(wb, IndexedColors.LIGHT_BLUE, null);
		Cell purchasenum = row.createCell(cellnum++);
		purchasenum.setCellValue("本期采购新增");
		purchasenum.setCellStyle(blue_style);
		sheet.setColumnWidth(cellnum - 1, 1200);

		Cell oldnum = row.createCell(cellnum++);
		oldnum.setCellValue("本期旧品新增");
		oldnum.setCellStyle(blue_style);
		sheet.setColumnWidth(cellnum - 1, 1200);

		CellStyle red_style = getStyle(wb, IndexedColors.RED, null);
		Cell installoutnum = row.createCell(cellnum++);
		installoutnum.setCellValue("本期领用数");
		installoutnum.setCellStyle(red_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		CellStyle green_style = getStyle(wb, IndexedColors.GREEN, null);
		Cell repairinnum = row.createCell(cellnum++);
		repairinnum.setCellValue("本期维修返还数");
		repairinnum.setCellStyle(green_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		CellStyle orange_style = getStyle(wb, IndexedColors.ORANGE, null);
		Cell scrapoutnum = row.createCell(cellnum++);
		scrapoutnum.setCellValue("报废出库数量");
		scrapoutnum.setCellStyle(orange_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		Cell repairoutnum = row.createCell(cellnum++);
		repairoutnum.setCellValue("维修出库数量");
		repairoutnum.setCellStyle(orange_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		CellStyle plum_style = getStyle(wb, IndexedColors.PLUM, null);
		Cell adjustoutnum = row.createCell(cellnum++);
		adjustoutnum.setCellValue("本期借用数");
		adjustoutnum.setCellStyle(plum_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		Cell adjustinnum = row.createCell(cellnum++);
		adjustinnum.setCellValue("本期归还数");
		adjustinnum.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		CellStyle nownum_style = getStyle(wb, IndexedColors.BLACK, null);
		nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		Cell nownum = row.createCell(cellnum++);
		nownum.setCellValue("本期结余数");
		nownum.setCellStyle(nownum_style);
		//sheet.setColumnWidth(cellnum - 1, 1800);
		Cell nownum_num = row3.createCell(cellnum-1);
		nownum_num.setCellValue("数量");
		nownum_num.setCellStyle(nownum_style);
		sheet.setColumnWidth(cellnum-1, 1800);
		Cell nownum_net = row3.createCell(cellnum++);
		nownum_net.setCellValue("金额");
		nownum_net.setCellStyle(nownum_style);
		sheet.setColumnWidth(cellnum - 1, 1800);

		if (store_type == 3) {
			CellStyle supplementnum_style = getStyle(wb, IndexedColors.BLACK, null);
			supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			Cell supplementnum = row.createCell(cellnum++);
			supplementnum.setCellValue("增补数量");
			supplementnum.setCellStyle(supplementnum_style);
			sheet.setColumnWidth(cellnum - 1, 1200);
		}

		Cell memo = row.createCell(cellnum++);
		memo.setCellValue("备注");
		memo.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum - 1, 2400);

		// sheet.createFreezePane(16, 2);
		sheet.createFreezePane(sparepart_month_freeze_num, 4);
		
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 7, 8));
		sheet.addMergedRegion(new CellRangeAddress(2, (short) 2, 17, 18));
	}

	int sparepart_month_freeze_num = 20;// 在建仓库月冻结的列数
	int type_group_end_num = 15;// 小类和大类分组的结束列

//	@RequestMapping("/inventory/month/sparepart/excelTpl.do")
//	public void excelTpl(HttpServletResponse response,Integer store_type) throws IOException {
//
//		// 首先获取大类，小类的内容，然后按照格式输出，最后，设置压缩问题
//		List<EquipmentType> equipmentTypes = equipmentTypeRepository.queryTypeAndSubtype();
//
//		String store_name_title="备品备件仓库";
//		if (store_type == 1) {
//			store_name_title = "在建仓库";
//		}
//
//		XSSFWorkbook wb = new XSSFWorkbook();
//		Sheet sheet = wb.createSheet("盘点汇总表");
//		Row title = sheet.createRow(0);// 一共有11列
//		title.setHeight((short) 660);
//		Cell title_cell = title.createCell(0);
//		title_cell.setCellValue(store_name_title+"盘点汇总表");
//		CellStyle cs = wb.createCellStyle();
//		Font f = wb.createFont();
//		f.setFontHeightInPoints((short) 16);
//		// f.setColor(IndexedColors.RED.getIndex());
//		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		cs.setFont(f);
//		cs.setAlignment(CellStyle.ALIGN_CENTER);
//		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		title_cell.setCellStyle(cs);
//		// 和并单元格
//		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) type_group_end_num+1));
//		
//		// 设置第一行,设置列标题
//		//sparepart_addRow1(wb, sheet,store_type);
//		
//		Row row_date = sheet.createRow(1);
//		Cell cell_date=row_date.createCell(0);
//		CellStyle cell_date_style = wb.createCellStyle();
//		Font cell_date_f = wb.createFont();
//		cell_date_f.setFontHeightInPoints((short) 12);
//		cell_date_f.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		cell_date_style.setFont(cell_date_f);
//		cell_date_style.setAlignment(CellStyle.ALIGN_LEFT);
//		cell_date_style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		cell_date.setCellStyle(cell_date_style);
//		cell_date.setCellValue("统计日期:______到_______");
//		sheet.addMergedRegion(new CellRangeAddress(1, (short) 1, 0, (short) type_group_end_num+1));
//
//		// 设置第一行,设置列标题
//		sparepart_addRow1(wb, sheet,store_type);
//
//		CellStyle type_name_style = this.getStyle(wb, IndexedColors.BLACK, (short) 12);
//		// black_style.setBorderBottom(CellStyle.BORDER_NONE);
//		type_name_style.setWrapText(false);
//		type_name_style.setBorderLeft(CellStyle.BORDER_NONE);
//		type_name_style.setBorderRight(CellStyle.BORDER_NONE);
//		type_name_style.setBorderTop(CellStyle.BORDER_NONE);
//		type_name_style.setAlignment(CellStyle.ALIGN_LEFT);
//		type_name_style.setBorderBottom(CellStyle.BORDER_NONE);
//
//		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.GREY_80_PERCENT, (short) 11);
//		// black_style.setBorderBottom(CellStyle.BORDER_NONE);
//		subtype_name_style.setWrapText(false);
//		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
//		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
//		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
//		subtype_name_style.setBorderBottom(CellStyle.BORDER_NONE);
//		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);
//
//
//		CellStyle fixednum_style = getContentStyle(wb, null);
//		fixednum_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
//		fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		
//		CellStyle yesterdaynum_style = getContentStyle(wb, null);
//		yesterdaynum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
//		yesterdaynum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//		CellStyle nownum_style = getContentStyle(wb, null);
//		nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//		nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//		CellStyle supplementnum_style = getContentStyle(wb, null);
//		supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//		supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//
//		CellStyle content_style = getContentStyle(wb, null);
//		CellStyle content_blue_style = getContentStyle(wb, IndexedColors.LIGHT_BLUE);
//		CellStyle content_red_style = getContentStyle(wb, IndexedColors.RED);
//		CellStyle content_green_style = getContentStyle(wb, IndexedColors.GREEN);
//		CellStyle content_orange_style = getContentStyle(wb, IndexedColors.ORANGE);
//		CellStyle content_plum_style = getContentStyle(wb, IndexedColors.PLUM);
//
//		CellStyle content_subtitle_style = getContentStyle(wb, null);
//		content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
//		content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
//		// content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
//
//		int cellnum = 0;
//		int rownum = 3;
//		for (int i = 0; i < equipmentTypes.size(); i++) {
//			cellnum = 0;
//			EquipmentType equipmentType = equipmentTypes.get(i);
//
//			// 这一行必须放在分组的前面，否则会有问题
//			Row row = sheet.createRow(rownum++);
//
//			Cell type_name = row.createCell(cellnum++);
//			type_name.setCellValue(equipmentType.getName());
//			type_name.setCellStyle(type_name_style);
//			// subtype_name.setCellValue(buildDayReport.getSubtype_name());
//			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, (short) type_group_end_num - 1));
//			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));
//
//			// 描绘小类
//			StringBuilder nownum_formule_builder = new StringBuilder();
//			StringBuilder supplementnum_formule_builder = new StringBuilder();
//			if (equipmentType.getSubtypes() != null) {
//				int fromRow_type = rownum;
//				for (EquipmentSubtype equipmentSubtype : equipmentType.getSubtypes()) {
//					cellnum = 0;
//					Row row_subtype = sheet.createRow(rownum++);
//
//					cellnum++;
//
//					Cell subtype_name = row_subtype.createCell(cellnum++);
//					subtype_name.setCellValue(equipmentSubtype.getName());
//					subtype_name.setCellStyle(subtype_name_style);
//
//					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, (short) type_group_end_num - 1));
//					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));
//
//					// 弄几行模拟品名的数据，即几个空行
//					int fromRow_subtype = rownum;
//					for (int k = 0; k < 5; k++) {
//						nownum_formule_builder = new StringBuilder();
//						nownum_formule_builder.append("SUM(");
//
//						supplementnum_formule_builder = new StringBuilder();
//						// nownum_formule_builder.append("SUM(");
//
//						cellnum = 2;// 从第3个单元格开始
//						Row row_prod = sheet.createRow(rownum++);
//
//						Cell brand_name = row_prod.createCell(cellnum++);
//						// brand_name.setCellValue("测试");
//						brand_name.setCellStyle(content_style);
//
//						Cell style = row_prod.createCell(cellnum++);
//						// style.setCellValue("测试");
//						style.setCellStyle(content_style);
//
//						Cell prod_name = row_prod.createCell(cellnum++);
//						// prod_name.setCellValue("测试");
//						prod_name.setCellStyle(content_style);
//
//						Cell store_name = row_prod.createCell(cellnum++);
//						// store_name.setCellValue(sparepartMonthReport.getStore_name());
//						store_name.setCellStyle(content_style);
//
//						Cell unit = row_prod.createCell(cellnum++);
//						// unit.setCellValue("台");
//						unit.setCellStyle(content_style);
//						if (store_type == 3) {
//							// 额定数量
//							Cell fixednum = row_prod.createCell(cellnum++);
//							// fixednum.setCellValue(1);
//							fixednum.setCellStyle(fixednum_style);
//							supplementnum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//						}
//
//						// 上月结余
//						Cell lastnum = row_prod.createCell(cellnum++);
//						// lastnum.setCellValue(2);
//						lastnum.setCellStyle(yesterdaynum_style);
//						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//
//						// 本期采购新增
//						Cell purchasenum = row_prod.createCell(cellnum++);
//						// purchasenum.setCellValue(3);
//						purchasenum.setCellStyle(content_blue_style);
//						nownum_formule_builder.append(",");
//						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//
//						// 本期旧品新增
//						Cell oldnum = row_prod.createCell(cellnum++);
//						// oldnum.setCellValue(4);
//						oldnum.setCellStyle(content_blue_style);
//						nownum_formule_builder.append(",");
//						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//						// 本期领用数
//						Cell installoutnum = row_prod.createCell(cellnum++);
//						// installoutnum.setCellValue(5);
//						installoutnum.setCellStyle(content_red_style);
//						nownum_formule_builder.append(",");
//						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//						// 本期维修返还数
//						Cell repairinnum = row_prod.createCell(cellnum++);
//						// repairinnum.setCellValue(6);
//						repairinnum.setCellStyle(content_green_style);
//						nownum_formule_builder.append(",");
//						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//						// 报废出库
//						Cell scrapoutnum = row_prod.createCell(cellnum++);
//						// scrapoutnum.setCellValue(7);
//						scrapoutnum.setCellStyle(content_orange_style);
//						// 维修出库
//						Cell repairoutnum = row_prod.createCell(cellnum++);
//						// repairoutnum.setCellValue(8);
//						repairoutnum.setCellStyle(content_orange_style);
//						// 本期借用数
//						Cell adjustoutnum = row_prod.createCell(cellnum++);
//						// adjustoutnum.setCellValue(9);
//						adjustoutnum.setCellStyle(content_plum_style);
//						nownum_formule_builder.append(",");
//						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//						// 本期归还数
//						Cell adjustinnum = row_prod.createCell(cellnum++);
//						// adjustinnum.setCellValue(10);
//						adjustinnum.setCellStyle(content_style);
//						nownum_formule_builder.append(",");
//						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
//						nownum_formule_builder.append(")");
//
//						Cell nownum = row_prod.createCell(cellnum++);
//						nownum.setCellFormula(nownum_formule_builder.toString());
//						nownum.setCellStyle(nownum_style);
//						supplementnum_formule_builder.append("-" + CellReference.convertNumToColString(cellnum - 1) + (rownum));
//
//						if (store_type == 3) {
//							Cell supplementnum = row_prod.createCell(cellnum++);
//							// supplementnum.setCellValue(12);
//							supplementnum.setCellStyle(supplementnum_style);
//							supplementnum.setCellFormula(supplementnum_formule_builder.toString());
//						}
//
//						Cell memo = row_prod.createCell(cellnum++);
//						memo.setCellValue("");
//						memo.setCellStyle(content_style);
//
//					}
//					// 小类的收缩
//					sheet.groupRow(fromRow_subtype, rownum);
//					sheet.setRowGroupCollapsed(fromRow_subtype, true);
//				}
//				// 设置最挖曾的收缩,就是类型的收缩
//				sheet.groupRow(fromRow_type, rownum);
//				sheet.setRowGroupCollapsed(fromRow_type, true);
//			}
//		}
//		sheet.setRowSumsBelow(false);
//		sheet.setRowSumsRight(false);
//
//		String filename = "备品备件仓库盘点汇总表_样式表.xlsx";
//		if (store_type == 1) {
//			filename = "在建仓库盘点汇总表_样式表.xlsx";
//		}
//		// FileOutputStream out = new FileOutputStream(filename);
//		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
//		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");
//
//		OutputStream out = response.getOutputStream();
//		wb.write(out);
//
//		out.flush();
//		out.close();
//
//	}

}
