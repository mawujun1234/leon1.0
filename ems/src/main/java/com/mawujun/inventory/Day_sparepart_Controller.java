package com.mawujun.inventory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
public class Day_sparepart_Controller {
	@Resource
	private Day_sparepart_Service day_sparepart_Service;
	@Resource
	private EquipmentTypeRepository equipmentTypeRepository;
	@Resource
	private StoreService storeService;
	
	SimpleDateFormat yyyyMMdd_formater=new SimpleDateFormat("yyyy-MM-dd");
	
	
	@RequestMapping("/inventory/day/proc_report_day_sparepart.do")
	public String proc_report_day_sparepart(String store_id,Integer store_type){
		if(store_id==null || "".equals(store_id.trim())){
			day_sparepart_Service.proc_report_day_sparepart(store_type);
		} else {
			day_sparepart_Service.proc_report_day_sparepart(store_id);
		}
		return "success";
	}
	/**
	 * 导出一个时间范围的数据
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param store_id
	 * @param store_type
	 * @param date_start
	 * @param date_end
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/inventory/day/sparepart/excelExport.do")
	public void excelExport(HttpServletResponse response,String store_id,Integer store_type,String date_start,String date_end) throws IOException, ParseException{
		//String date_start_str= yyyyMMdd_formater.format(date_start);
		//String date_end_str= yyyyMMdd_formater.format(date_end);
		
		long diff = yyyyMMdd_formater.parse(date_end).getTime() - yyyyMMdd_formater.parse(date_start).getTime();	
		long day_length = diff / (1000 * 60 * 60 * 24)+1;//一共显示多少明细数据
		if(day_length<=0){
			throw new BusinessException("日期选错了,请重新选择!");
		}
		
		//建立日期范围的key
		Long date_start_times=yyyyMMdd_formater.parse(date_start).getTime();
		
		List<Day_sparepart_type> types = day_sparepart_Service.queryDay_sparepart(store_id, store_type,date_start.replaceAll("-", ""),date_end.replaceAll("-", ""));
		String store_name="所有";
		if(store_id!=null && !"".equals(store_id) ){
			Store store=storeService.get(store_id);
			store_name=store.getName();
		}
		

		// 首先获取大类，小类的内容，然后按照格式输出，最后，设置压缩问题
		//List<EquipmentType> equipmentTypes = equipmentTypeRepository.queryTypeAndSubtype();

		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);// 一共有11列
		title.setHeight((short) 660);
		Cell title_cell = title.createCell(0);
		title_cell.setCellValue(store_name+"  仓库"+date_start+"到"+date_end+"盘点日报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		// f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_LEFT);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		// 和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) type_group_end_num - 1));

		// 设置第一行,设置列标题
		StringBuilder[] formulas = sparepart_addRow1(wb, sheet,day_length);

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

		CellStyle fixednum_style = getContentStyle(wb, null, (short) 9);
		fixednum_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle lastnum_style = getContentStyle(wb, null, (short) 9);
		lastnum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle nownum_style = getContentStyle(wb, null, (short) 9);
		nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle content_style = getContentStyle(wb, null, (short) 9);
		CellStyle content_blue_style = getContentStyle(wb, IndexedColors.LIGHT_BLUE, (short) 9);
		CellStyle content_red_style = getContentStyle(wb, IndexedColors.RED, (short) 9);
		CellStyle content_green_style = getContentStyle(wb, IndexedColors.GREEN, (short) 9);
		CellStyle content_green_style_last = getContentStyle(wb, IndexedColors.GREEN, (short) 9);
		content_green_style_last.setBorderRight(CellStyle.BORDER_DOUBLE);
		CellStyle content_orange_style = getContentStyle(wb, IndexedColors.ORANGE, (short) 9);
		CellStyle content_plum_style = getContentStyle(wb, IndexedColors.PLUM, (short) 9);

		CellStyle content_subtitle_style = getContentStyle(wb, null, (short) 9);
		content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		// content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);

		int cellnum = 0;
		int rownum = 3;
		for (int i = 0; i < types.size(); i++) {
			cellnum = 0;
			Day_sparepart_type equipmentType = types.get(i);

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);

			Cell type_name = row.createCell(cellnum++);
			type_name.setCellValue(equipmentType.getType_name());
			type_name.setCellStyle(type_name_style);
			// subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 0, (short) type_group_end_num - 1));
			sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));

			// 描绘小类
			StringBuilder nownum_formule_builder = new StringBuilder();
			// StringBuilder supplementnum_formule_builder=new StringBuilder();
			if (equipmentType.getSubtypes() != null) {
				int fromRow_type = rownum;
				for (Day_sparepart_subtype equipmentSubtype : equipmentType.getSubtypes()) {
					cellnum = 0;
					Row row_subtype = sheet.createRow(rownum++);

					cellnum++;

					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getSubtype_name());
					subtype_name.setCellStyle(subtype_name_style);

					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, 1, (short) type_group_end_num - 1));
					sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum - 1, type_group_end_num, (short) type_group_end_num + 2));

					//要先对prods进行行列转换
					equipmentSubtype.changeProdes();
					int prods_size=equipmentSubtype.getProdSize();
					// 弄几行模拟品名的数据，即几个空行
					int fromRow_subtype = rownum;
					for (int k = 0; k < prods_size; k++) {
						//主要是为了填写当前行的品名，品牌，所属仓库等信息
						Day_sparepart_prod prod_first=equipmentSubtype.first();
								
						nownum_formule_builder = new StringBuilder();
						nownum_formule_builder.append("SUM(");

						// supplementnum_formule_builder=new StringBuilder();

						cellnum = 2;// 从第3个单元格开始
						Row row_prod = sheet.createRow(rownum++);

						Cell brand_name = row_prod.createCell(cellnum++);
						// brand_name.setCellValue("测试");
						brand_name.setCellStyle(content_style);

						Cell style = row_prod.createCell(cellnum++);
						// style.setCellValue("测试");
						style.setCellStyle(content_style);

						Cell prod_name = row_prod.createCell(cellnum++);
						// prod_name.setCellValue("测试");
						prod_name.setCellStyle(content_style);

						// Cell store_name=row_prod.createCell(cellnum++);
						// store_name.setCellValue(sparepartMonthReport.getStore_name());
						// store_name.setCellStyle(content_style);

						Cell unit = row_prod.createCell(cellnum++);
						// unit.setCellValue("台");
						unit.setCellStyle(content_style);

						// 额定数量
						Cell fixednum = row_prod.createCell(cellnum++);
						// fixednum.setCellValue(1);
						// supplementnum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						fixednum.setCellStyle(fixednum_style);

						// 上月结余
						Cell lastnum = row_prod.createCell(cellnum++);
						// lastnum.setCellValue(2);
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(",");
						lastnum.setCellStyle(lastnum_style);

						// 本期采购新增
						Cell purchasenum = row_prod.createCell(cellnum++);
						purchasenum.setCellStyle(content_blue_style);
						purchasenum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum) + ""));
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(",");

						// 本期旧品新增
						Cell oldnum = row_prod.createCell(cellnum++);
						oldnum.setCellStyle(content_blue_style);
						oldnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum) + ""));
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(",");
						// 本期领用数
						Cell installoutnum = row_prod.createCell(cellnum++);
						installoutnum.setCellStyle(content_red_style);
						installoutnum.setCellFormula(formulas[2].toString().replaceAll("=", (rownum) + ""));
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(",");
						// 本期维修返还数
						Cell repairinnum = row_prod.createCell(cellnum++);
						repairinnum.setCellStyle(content_green_style);
						repairinnum.setCellFormula(formulas[3].toString().replaceAll("=", (rownum) + ""));
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(",");
						// 报废出库
						Cell scrapoutnum = row_prod.createCell(cellnum++);
						scrapoutnum.setCellStyle(content_orange_style);
						scrapoutnum.setCellFormula(formulas[4].toString().replaceAll("=", (rownum) + ""));
						// 维修出库
						Cell repairoutnum = row_prod.createCell(cellnum++);
						repairoutnum.setCellStyle(content_orange_style);
						repairoutnum.setCellFormula(formulas[5].toString().replaceAll("=", (rownum) + ""));

						// 本期借用数
						Cell borrownum = row_prod.createCell(cellnum++);
						borrownum.setCellStyle(content_plum_style);
						borrownum.setCellFormula(formulas[6].toString().replaceAll("=", (rownum) + ""));
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(",");
						// 本期归还数
						Cell borrowreturnnum = row_prod.createCell(cellnum++);
						borrowreturnnum.setCellStyle(content_green_style);
						borrowreturnnum.setCellFormula(formulas[7].toString().replaceAll("=", (rownum) + ""));
						nownum_formule_builder.append(CellReference.convertNumToColString(cellnum - 1) + (rownum));
						nownum_formule_builder.append(")");

						Cell nownum = row_prod.createCell(cellnum++);
						nownum.setCellFormula(nownum_formule_builder.toString());
						nownum.setCellStyle(nownum_style);

						
						日报表，表头的日期改成真是日期，而不是1，2，3这样的序号，变成月日，或者年月日
						for (int j = 1; j <= day_length; j++) {
							Integer daykey=Integer.parseInt(yyyyMMdd_formater.format(new Date(date_start_times+(j*24*60*60*1000))));
							
							// CellStyle blue_style = getStyle(wb,
							// IndexedColors.PALE_BLUE, null);
							Cell purchasenum_mx = row_prod.createCell(cellnum++);
							purchasenum_mx.setCellStyle(content_blue_style);
							// purchasenum_mx.setCellValue(1);

							Cell oldnum_mx = row_prod.createCell(cellnum++);
							oldnum_mx.setCellStyle(content_blue_style);
							// oldnum_mx.setCellValue(2);

							// CellStyle red_style = getStyle(wb,
							// IndexedColors.RED, null);
							Cell installoutnum_mx = row_prod.createCell(cellnum++);
							installoutnum_mx.setCellStyle(content_red_style);
							// installoutnum_mx.setCellValue(3);

							// CellStyle green_style = getStyle(wb,
							// IndexedColors.GREEN, null);
							Cell repairinnum_mx = row_prod.createCell(cellnum++);
							repairinnum_mx.setCellStyle(content_green_style);
							// repairinnum_mx.setCellValue(4);

							// CellStyle orange_style = getStyle(wb,
							// IndexedColors.ORANGE, null);
							Cell scrapoutnum_mx = row_prod.createCell(cellnum++);
							scrapoutnum_mx.setCellStyle(content_orange_style);
							// scrapoutnum_mx.setCellValue(5);

							Cell repairoutnum_mx = row_prod.createCell(cellnum++);
							repairoutnum_mx.setCellStyle(content_orange_style);
							// repairoutnum_mx.setCellValue(6);

							// CellStyle plum_style = getStyle(wb,
							// IndexedColors.PLUM, null);
							Cell adjustoutnum_mx = row_prod.createCell(cellnum++);
							adjustoutnum_mx.setCellStyle(content_plum_style);
							// adjustoutnum_mx.setCellValue(7);

							Cell adjustinnum_mx = row_prod.createCell(cellnum++);
							adjustinnum_mx.setCellStyle(content_green_style_last);
							// adjustinnum_mx.setCellValue(8);

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

		String filename = "备品备件仓库盘点日报表.xlsx";
		// FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);

		out.flush();
		out.close();
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
	
	private CellStyle getContentStyle(XSSFWorkbook wb,IndexedColors color,Short fontSize){
		CellStyle style = wb.createCellStyle();
		//style.setAlignment(CellStyle.ALIGN_CENTER);
		//style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font bord_font = wb.createFont();
		if(fontSize!=null){
			bord_font.setFontHeightInPoints(fontSize);
		} else {
			bord_font.setFontHeightInPoints((short)10);
		}
		if(color!=null){
			bord_font.setColor(color.getIndex());
		}
		//
		//bord_font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		style.setFont(bord_font);
		style.setWrapText(true);//自动换行
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		return style;
	}
	

	private StringBuilder[] sparepart_addRow1(XSSFWorkbook wb,Sheet sheet,long day_length){
		 Row row = sheet.createRow(1);
		 
		 CellStyle black_style=getStyle(wb,IndexedColors.BLACK,null);
		 int cellnum=0;
		 Cell type_name=row.createCell(cellnum++);
		 type_name.setCellValue("大类");
		 type_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 600);
		 
		 Cell subtype_name=row.createCell(cellnum++);
		 subtype_name.setCellValue("小类");
		 subtype_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 600);
		 
		 Cell brand_name=row.createCell(cellnum++);
		 brand_name.setCellValue("品牌");
		 brand_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 2400);
		 
		 Cell style=row.createCell(cellnum++);
		 style.setCellValue("型号");
		 style.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 3600);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
		 Cell prod_name=row.createCell(cellnum++);
		 prod_name.setCellValue("品名");
		 prod_name.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1, 3600);
		 //sheet.autoSizeColumn(cellint-1, true);
		 
//		 Cell store_name=row.createCell(cellnum++);
//		 store_name.setCellValue("仓库");
//		 store_name.setCellStyle(black_style);
//		 sheet.setColumnWidth(cellnum-1, "列".getBytes().length*2*256);
		 
		 Cell unit=row.createCell(cellnum++);
		 unit.setCellValue("单位");
		 unit.setCellStyle(black_style);
		 sheet.setColumnWidth(cellnum-1,600);
		 
		 CellStyle fixednum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
		 fixednum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		 fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell fixednum=row.createCell(cellnum++);
		 fixednum.setCellValue("额定数量");
		 fixednum.setCellStyle(fixednum_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		 
		 CellStyle lastnum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
		 lastnum_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell lastnum=row.createCell(cellnum++);
		 lastnum.setCellValue("上月结余数");
		 lastnum.setCellStyle(lastnum_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle blue_style=getStyle(wb,IndexedColors.LIGHT_BLUE,(short)9);
		 Cell purchasenum=row.createCell(cellnum++);
		 purchasenum.setCellValue("本期采购新增");
		 purchasenum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		
		 Cell oldnum=row.createCell(cellnum++);
		 oldnum.setCellValue("本期旧品新增");
		 oldnum.setCellStyle(blue_style);
		 sheet.setColumnWidth(cellnum-1, 1200);
		 
		 CellStyle red_style=getStyle(wb,IndexedColors.RED,(short)9);
		 Cell installoutnum=row.createCell(cellnum++);
		 installoutnum.setCellValue("本期领用数");
		 installoutnum.setCellStyle(red_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle green_style=getStyle(wb,IndexedColors.GREEN,(short)9);
		 Cell repairinnum=row.createCell(cellnum++);
		 repairinnum.setCellValue("本期维修返还数");
		 repairinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle orange_style=getStyle(wb,IndexedColors.ORANGE,(short)9);
		 Cell scrapoutnum=row.createCell(cellnum++);
		 scrapoutnum.setCellValue("报废出库数量");
		 scrapoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 Cell repairoutnum=row.createCell(cellnum++);
		 repairoutnum.setCellValue("维修出库数量");
		 repairoutnum.setCellStyle(orange_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle plum_style=getStyle(wb,IndexedColors.PLUM,(short)9);
		 Cell adjustoutnum=row.createCell(cellnum++);
		 adjustoutnum.setCellValue("本期借用数");
		 adjustoutnum.setCellStyle(plum_style);
		 sheet.setColumnWidth(cellnum-1,1800);
		 
		 Cell adjustinnum=row.createCell(cellnum++);
		 adjustinnum.setCellValue("本期归还数");
		 adjustinnum.setCellStyle(green_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
		 CellStyle nownum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 Cell nownum=row.createCell(cellnum++);
		 nownum.setCellValue("本月结余数");
		 nownum.setCellStyle(nownum_style);
		 sheet.setColumnWidth(cellnum-1, 1800);
		 
//		 CellStyle supplementnum_style=getStyle(wb,IndexedColors.BLACK,(short)9);
//		 supplementnum_style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
//		 supplementnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		 Cell supplementnum=row.createCell(cellnum++);
//		 supplementnum.setCellValue("增补数量");
//		 supplementnum.setCellStyle(supplementnum_style);
//		 sheet.setColumnWidth(cellnum-1,1200);
		 
		 
		 //-----------------------31天内，字体比较小的
		 CellStyle blue_style_dayofmonth=getStyle_title(wb,IndexedColors.LIGHT_BLUE,(short)8);
		 CellStyle red_style_dayofmonth=getStyle_title(wb,IndexedColors.RED,(short)8);
		 CellStyle green_style_dayofmonth=getStyle_title(wb,IndexedColors.GREEN,(short)8);
		 CellStyle green_style_dayofmonth_last=getStyle_title(wb,IndexedColors.GREEN,(short)8);
		 green_style_dayofmonth_last.setBorderRight(CellStyle.BORDER_DOUBLE);
		 CellStyle orange_style_dayofmonth=getStyle_title(wb,IndexedColors.ORANGE,(short)8);
		 CellStyle plum_style_dayofmonth=getStyle_title(wb,IndexedColors.PLUM,(short)8);
		// CellStyle green_style=getStyle_title(wb,IndexedColors.GREEN,(short)8);
		// CellStyle 
		 
		 
		 //------------------------------------------------

		 Row row2 = sheet.createRow(2);
		for (int j = 0; j < cellnum; j++) {

			sheet.addMergedRegion(new CellRangeAddress(1, 2, (short) j, (short) j));
			// 同时设置第二行的单元格的样式
			Cell temp = row2.createCell(j);
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
			for(int j=1;j<=day_length;j++){
				 
				
				 Cell purchasenum_repeat=row2.createCell(cellnum_repeat++);
				 purchasenum_repeat.setCellValue("采购新增");
				 purchasenum_repeat.setCellStyle(blue_style_dayofmonth);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 purchasenum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 Cell oldnum_repeat=row2.createCell(cellnum_repeat++);
				 oldnum_repeat.setCellValue("旧品新增");
				 oldnum_repeat.setCellStyle(blue_style_dayofmonth);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 oldnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
	
				 Cell installoutnum_repeat=row2.createCell(cellnum_repeat++);
				 installoutnum_repeat.setCellValue("本期领用");
				 installoutnum_repeat.setCellStyle(red_style_dayofmonth);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 installoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				
				 Cell repairinnum_repeat=row2.createCell(cellnum_repeat++);
				 repairinnum_repeat.setCellValue("维修返还数");
				 repairinnum_repeat.setCellStyle(green_style_dayofmonth);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 repairinnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				
				 Cell scrapoutnum_repeat=row2.createCell(cellnum_repeat++);
				 scrapoutnum_repeat.setCellValue("报废出库");
				 scrapoutnum_repeat.setCellStyle(orange_style_dayofmonth);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 scrapoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 Cell repairoutnum_repeat=row2.createCell(cellnum_repeat++);
				 repairoutnum_repeat.setCellValue("维修出库");
				 repairoutnum_repeat.setCellStyle(orange_style_dayofmonth);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 repairoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				
				 Cell adjustoutnum_repeat=row2.createCell(cellnum_repeat++);
				 adjustoutnum_repeat.setCellValue("借用数");
				 adjustoutnum_repeat.setCellStyle(plum_style_dayofmonth);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 adjustoutnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");
				 
				 
				 Cell adjustinnum_repeat=row2.createCell(cellnum_repeat++);
				 adjustinnum_repeat.setCellValue("归还数");
				 adjustinnum_repeat.setCellStyle(green_style_dayofmonth_last);
				 green_style.setBorderRight(CellStyle.BORDER_DOUBLE);
				 sheet.setColumnWidth(cellnum_repeat-1, 1200);
				 adjustinnum_formula.append(CellReference.convertNumToColString(cellnum_repeat-1)).append("=");


				 if(j!=day_length){
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

			

//			Cell memo = row2.createCell(cellnum_repeat++);
//			memo.setCellValue("备注");
//			memo.setCellStyle(black_style);
//			sheet.setColumnWidth(cellnum - 1, "列长".getBytes().length * 256);
			
			//合并单元格的行
			//Row row1 = sheet.createRow(1);
			//创建日期，并合并日期的两列
			black_style=getStyle_title(wb,IndexedColors.BLACK,null);
			cellnum_repeat=cellnum;
			for(int j=1;j<=day_length;j++){
				//合并这两个单元格
				int cellnum_repeat_temp=cellnum_repeat;
				cellnum_repeat=	cellnum_repeat+8;
				sheet.addMergedRegion(new CellRangeAddress(1,1,cellnum_repeat_temp,cellnum_repeat-1)); 
				//设置日期值
				Cell cell11=row.createCell(cellnum_repeat_temp);
				cell11.setCellValue(j);
				cell11.setCellStyle(black_style);
				//设置单个元样式
				cellnum_repeat_temp++;//第一格已经创建过了，不用再创建了
				for(;cellnum_repeat_temp<cellnum_repeat;cellnum_repeat_temp++){
					Cell cell12=row.createCell(cellnum_repeat_temp);
					cell12.setCellStyle(black_style);
					if(cellnum_repeat_temp==(cellnum_repeat-1)){
						black_style.setBorderRight(CellStyle.BORDER_DOUBLE);
					}
				}
				

			}

			 
			 //冻结行和列
			 sheet.createFreezePane(sparepart_month_freeze_num, 3);
			 
			 //生成本期新增数公式
			 StringBuilder[] formulas=new StringBuilder[]{purchasenum_formula,oldnum_formula,installoutnum_formula,repairinnum_formula,scrapoutnum_formula,repairoutnum_formula,adjustoutnum_formula,adjustinnum_formula};
			 return formulas;
		 
		 //sheet.createFreezePane(16, 2);
		
	}
	int sparepart_month_freeze_num=17;//在建仓库月冻结的列数
	int type_group_end_num=17;//小类和大类分组的结束列
	int day_of_month_num=31;
	@RequestMapping("/inventory/day/sparepart/excelTpl.do")
	public void excelTpl(HttpServletResponse response) throws IOException{
		
		//首先获取大类，小类的内容，然后按照格式输出，最后，设置压缩问题
		List<EquipmentType> equipmentTypes=equipmentTypeRepository.queryTypeAndSubtype();
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet("仓库名称");
		Row title = sheet.createRow(0);//一共有11列
		title.setHeight((short)660);
		Cell title_cell=title.createCell(0);
		title_cell.setCellValue("__________仓库________年_________月盘点日报表");
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		//f.setColor(IndexedColors.RED.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setAlignment(CellStyle.ALIGN_LEFT);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		title_cell.setCellStyle(cs);
		//和并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)type_group_end_num-1)); 
		
		
		//设置第一行,设置列标题
		StringBuilder[] formulas=sparepart_addRow1(wb,sheet,31);
		
		
		CellStyle type_name_style = this.getStyle(wb, IndexedColors.BLACK,(short)12);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		type_name_style.setWrapText(false);
		type_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		type_name_style.setBorderRight(CellStyle.BORDER_NONE);
		type_name_style.setBorderTop(CellStyle.BORDER_NONE);
		type_name_style.setAlignment(CellStyle.ALIGN_LEFT);
		type_name_style.setBorderBottom(CellStyle.BORDER_NONE);
		
		CellStyle subtype_name_style = this.getStyle(wb, IndexedColors.GREY_80_PERCENT,(short)11);
		//black_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setWrapText(false);
		subtype_name_style.setBorderLeft(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderRight(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderTop(CellStyle.BORDER_NONE);
		subtype_name_style.setBorderBottom(CellStyle.BORDER_NONE);
		subtype_name_style.setAlignment(CellStyle.ALIGN_LEFT);
		
		CellStyle fixednum_style=getContentStyle(wb,null,(short)9);
		fixednum_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		fixednum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		CellStyle lastnum_style=getContentStyle(wb,null,(short)9);
		 lastnum_style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		 lastnum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		 CellStyle nownum_style=getContentStyle(wb,null,(short)9);
		 nownum_style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		 nownum_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		CellStyle content_style =getContentStyle(wb,null,(short)9);
		CellStyle content_blue_style =getContentStyle(wb,IndexedColors.LIGHT_BLUE,(short)9);	
		CellStyle content_red_style =getContentStyle(wb,IndexedColors.RED,(short)9);	
		CellStyle content_green_style =getContentStyle(wb,IndexedColors.GREEN,(short)9);
		CellStyle content_green_style_last =getContentStyle(wb,IndexedColors.GREEN,(short)9);
			content_green_style_last.setBorderRight(CellStyle.BORDER_DOUBLE);
		CellStyle content_orange_style =getContentStyle(wb,IndexedColors.ORANGE,(short)9);
		CellStyle content_plum_style =getContentStyle(wb,IndexedColors.PLUM,(short)9);
		
		

		 
		 CellStyle content_subtitle_style =getContentStyle(wb,null,(short)9);
		 content_subtitle_style.setBorderLeft(CellStyle.BORDER_NONE);
		 content_subtitle_style.setBorderRight(CellStyle.BORDER_NONE);
		 //content_subtitle_style.setBorderTop(CellStyle.BORDER_NONE);
		 
		
		
		
		int cellnum=0;
		int rownum=3;
		for(int i=0;i<equipmentTypes.size();i++){
			cellnum = 0;
			EquipmentType equipmentType = equipmentTypes.get(i);

			// 这一行必须放在分组的前面，否则会有问题
			Row row = sheet.createRow(rownum++);
			
			Cell type_name = row.createCell(cellnum++);
			type_name.setCellValue(equipmentType.getName());
			type_name.setCellStyle(type_name_style);
			//subtype_name.setCellValue(buildDayReport.getSubtype_name());
			sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,0,(short)type_group_end_num-1)); 
			sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,type_group_end_num,(short)type_group_end_num+2));
			 
			//描绘小类
			StringBuilder nownum_formule_builder=new StringBuilder();
			//StringBuilder supplementnum_formule_builder=new StringBuilder();
			if(equipmentType.getSubtypes()!=null) {
				int fromRow_type=rownum;
				for(EquipmentSubtype equipmentSubtype:equipmentType.getSubtypes()){
					cellnum=0;
					Row row_subtype = sheet.createRow(rownum++);
					
					cellnum++;
					
					Cell subtype_name = row_subtype.createCell(cellnum++);
					subtype_name.setCellValue(equipmentSubtype.getName());
					subtype_name.setCellStyle(subtype_name_style);
					
					sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,1,(short)type_group_end_num-1)); 
					sheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,type_group_end_num,(short)type_group_end_num+2));

					//弄几行模拟品名的数据，即几个空行
					int fromRow_subtype=rownum;
					for(int k=0;k<5;k++){
						nownum_formule_builder=new StringBuilder();
						nownum_formule_builder.append("SUM(");
						
						//supplementnum_formule_builder=new StringBuilder();
						
						cellnum=2;//从第3个单元格开始
						Row row_prod = sheet.createRow(rownum++);
						
						Cell brand_name=row_prod.createCell(cellnum++);
						 //brand_name.setCellValue("测试");
						 brand_name.setCellStyle(content_style);
						 
						 Cell style=row_prod.createCell(cellnum++);
						 //style.setCellValue("测试");
						 style.setCellStyle(content_style);
						 
						 Cell prod_name=row_prod.createCell(cellnum++);
						// prod_name.setCellValue("测试");
						 prod_name.setCellStyle(content_style);
						 
//						 Cell store_name=row_prod.createCell(cellnum++);
//						 store_name.setCellValue(sparepartMonthReport.getStore_name());
//						 store_name.setCellStyle(content_style);
						 
						 Cell unit=row_prod.createCell(cellnum++);
						// unit.setCellValue("台");
						 unit.setCellStyle(content_style);
						 
						 //额定数量
						 Cell fixednum=row_prod.createCell(cellnum++);
						 //fixednum.setCellValue(1);
						 //supplementnum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 fixednum.setCellStyle(fixednum_style);
						 
						 //上月结余
						 Cell lastnum=row_prod.createCell(cellnum++);
						 //lastnum.setCellValue(2);
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 lastnum.setCellStyle(lastnum_style);
						 
						 //本期采购新增
						 Cell purchasenum=row_prod.createCell(cellnum++);
						 purchasenum.setCellStyle(content_blue_style);
						 purchasenum.setCellFormula(formulas[0].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 
						 //本期旧品新增
						 Cell oldnum=row_prod.createCell(cellnum++);
						 oldnum.setCellStyle(content_blue_style);
						 oldnum.setCellFormula(formulas[1].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //本期领用数
						 Cell installoutnum=row_prod.createCell(cellnum++);
						 installoutnum.setCellStyle(content_red_style);
						 installoutnum.setCellFormula(formulas[2].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //本期维修返还数
						 Cell repairinnum=row_prod.createCell(cellnum++);
						 repairinnum.setCellStyle(content_green_style);
						 repairinnum.setCellFormula(formulas[3].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //报废出库
						 Cell scrapoutnum=row_prod.createCell(cellnum++);
						 scrapoutnum.setCellStyle(content_orange_style);
						 scrapoutnum.setCellFormula(formulas[4].toString().replaceAll("=", (rownum)+""));
						 //维修出库
						 Cell repairoutnum=row_prod.createCell(cellnum++);
						 repairoutnum.setCellStyle(content_orange_style);
						 repairoutnum.setCellFormula(formulas[5].toString().replaceAll("=", (rownum)+""));
						 
						 //本期借用数
						 Cell adjustoutnum=row_prod.createCell(cellnum++);
						 adjustoutnum.setCellStyle(content_plum_style);
						 adjustoutnum.setCellFormula(formulas[6].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(",");
						 //本期归还数
						 Cell adjustinnum=row_prod.createCell(cellnum++);
						 adjustinnum.setCellStyle(content_green_style);
						 adjustinnum.setCellFormula(formulas[7].toString().replaceAll("=", (rownum)+""));
						 nownum_formule_builder.append(CellReference.convertNumToColString(cellnum-1)+(rownum));
						 nownum_formule_builder.append(")");
						 
						 Cell nownum=row_prod.createCell(cellnum++);
						 nownum.setCellFormula(nownum_formule_builder.toString());
						 nownum.setCellStyle(nownum_style);
						 
					 
						for (int j = 1; j <= day_of_month_num; j++) {
							//CellStyle blue_style = getStyle(wb, IndexedColors.PALE_BLUE, null);
							Cell purchasenum_mx = row_prod.createCell(cellnum++);
							purchasenum_mx.setCellStyle(content_blue_style);
							//purchasenum_mx.setCellValue(1);

							Cell oldnum_mx = row_prod.createCell(cellnum++);
							oldnum_mx.setCellStyle(content_blue_style);
							//oldnum_mx.setCellValue(2);

							//CellStyle red_style = getStyle(wb, IndexedColors.RED, null);
							Cell installoutnum_mx = row_prod.createCell(cellnum++);
							installoutnum_mx.setCellStyle(content_red_style);
							//installoutnum_mx.setCellValue(3);

							//CellStyle green_style = getStyle(wb, IndexedColors.GREEN, null);
							Cell repairinnum_mx = row_prod.createCell(cellnum++);
							repairinnum_mx.setCellStyle(content_green_style);
							//repairinnum_mx.setCellValue(4);

							//CellStyle orange_style = getStyle(wb, IndexedColors.ORANGE, null);
							Cell scrapoutnum_mx = row_prod.createCell(cellnum++);
							scrapoutnum_mx.setCellStyle(content_orange_style);
							//scrapoutnum_mx.setCellValue(5);

							Cell repairoutnum_mx = row_prod.createCell(cellnum++);
							repairoutnum_mx.setCellStyle(content_orange_style);
							//repairoutnum_mx.setCellValue(6);

							//CellStyle plum_style = getStyle(wb, IndexedColors.PLUM, null);
							Cell adjustoutnum_mx = row_prod.createCell(cellnum++);
							adjustoutnum_mx.setCellStyle(content_plum_style);
							//adjustoutnum_mx.setCellValue(7);

							
							Cell adjustinnum_mx = row_prod.createCell(cellnum++);
							adjustinnum_mx.setCellStyle(content_green_style_last);
							//adjustinnum_mx.setCellValue(8);		
							
						}
						 
						
					}
					//小类的收缩
					sheet.groupRow(fromRow_subtype, rownum);
					sheet.setRowGroupCollapsed(fromRow_subtype, true);
				}
				//设置最挖曾的收缩,就是类型的收缩
				sheet.groupRow(fromRow_type, rownum);
				sheet.setRowGroupCollapsed(fromRow_type, true);
			}			 
		 }
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
		
		
		 String filename = "备品备件仓库盘点日报表_样式表.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
		
	}
	
}
