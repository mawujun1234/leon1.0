package com.mawujun.baseinfo;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/pole")
public class PoleController {

	@Resource
	private PoleService poleService;
	@Resource
	private CustomerService customerService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/pole/query.do")
//	@ResponseBody
//	public List<Pole> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Pole.parent.id, "root".equals(id)?null:id);
//		List<Pole> polees=poleService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Pole.class,M.Pole.parent.name());
//		return polees;
//	}

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/pole/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String sampleName,String customer_id,String nameORcode,Boolean filterContainArea){
		Page page=Page.getInstance(start,limit).addParam(M.Pole.customer_id, customer_id);//.addParam(M.Pole.sampleName, "%"+sampleName+"%");
		page.addParam("nameORcode", nameORcode);
		if(filterContainArea!=null && filterContainArea==true){
			//filterContainArea=true表示过滤掉已经选择了片区的杆位,主要用在为area选择杆位的时候
			page.addParam("filterContainArea", true);
			
		}
		return poleService.queryPage(page);
	}

//	@RequestMapping("/pole/query.do")
//	@ResponseBody
//	public List<Pole> query() {	
//		List<Pole> polees=poleService.queryAll();
//		return polees;
//	}
	

	@RequestMapping("/pole/load.do")
	public Pole load(String id) {
		return poleService.get(id);
	}
	
	@RequestMapping("/pole/create.do")
	@ResponseBody
	public Pole create(@RequestBody Pole pole) {
		pole.setStatus(PoleStatus.uninstall);
		poleService.create(pole);
		return pole;
	}
	
	@RequestMapping("/pole/update.do")
	@ResponseBody
	public  Pole update(@RequestBody Pole pole) {
		poleService.update(pole);
		return pole;
	}
	
	@RequestMapping("/pole/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		poleService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/pole/destroy.do")
	@ResponseBody
	public Pole destroy(Pole pole) {
		poleService.delete(pole);
		return pole;
	}
	
	@RequestMapping("/pole/transform.do")
	@ResponseBody
	public  String transform(String customer_id,String[] pole_ids) {
		poleService.transform(customer_id, pole_ids);
		return "{success:true}";
	}
	
	
	@RequestMapping("/pole/queryEquipments.do")
	@ResponseBody
	public List<Pole> queryEquipments(String id){		
		return poleService.queryEquipments(id);
	}
	
	//==============================================================================================================
	
	@RequestMapping("/pole/exportPoles.do")
	@ResponseBody
	public void exportPoles(HttpServletResponse response,String customer_id) throws IOException {	
		
		List<PoleVO> poles=poleService.queryPolesAndEquipments(customer_id);
		Customer customer=customerService.get(customer_id);
		//Area area=areaService.get(area_id);
		//WorkUnit workUnit=workUnitService.get(area.getWorkunit_id());
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		Row row = sheet.createRow(rownum);
		row.setHeight((short)660);
		
		CellStyle title_cellStyle=wb.createCellStyle();
		title_cellStyle.setWrapText(true);
		title_cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		title_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		
		
		Cell title=row.createCell(0);
		title.setCellStyle(title_cellStyle);
		//title.setCellValue("所在片区:"+area.getName()+"。负责的作业单位:"+workUnit.getName());
		title.setCellValue(new XSSFRichTextString("客户:"+customer.getName())); 
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,13));
		
		//创建列明
		rownum++;
		build_addColumnName(wb,sheet,rownum);
		
		//开始构建整个excel的文件
		if(poles!=null && poles.size()>0){
			rownum++;
			build_content(poles,wb,sheet,rownum);
		}
		
		
		
		String filename = customer.getName()+"-点位设备信息.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		//response.setContentType("application/vnd.ms-excel;charset=uft-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
	}
	private void build_addColumnName(XSSFWorkbook wb,Sheet sheet,int rowInt){
		CellStyle black_style=getStyle(wb,IndexedColors.BLACK,(short)11);
		 
		Row row = sheet.createRow(rowInt);
		int cellnum=0;
		Cell code=row.createCell(cellnum++);
		code.setCellValue("编码");
		code.setCellStyle(black_style);
		
		Cell name=row.createCell(cellnum++);
		name.setCellValue("点位名称");
		name.setCellStyle(black_style);
		
		Cell address=row.createCell(cellnum++);
		address.setCellValue("地址");
		address.setCellStyle(black_style);
		
		Cell status=row.createCell(cellnum++);
		status.setCellValue("状态");
		status.setCellStyle(black_style);
		
//		Cell workunit=row.createCell(cellnum++);
//		workunit.setCellValue("作业单位");
//		workunit.setCellStyle(black_style);
//		sheet.setColumnWidth(cellnum-1, "列".getBytes().length*5*256);
		
//		Cell customer=row.createCell(cellnum++);
//		customer.setCellValue("所属客户");
//		customer.setCellStyle(black_style);
//		sheet.setColumnWidth(cellnum-1, "列".getBytes().length*5*256);
		
		Cell ecode=row.createCell(cellnum++);
		ecode.setCellValue("条码");
		ecode.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum-1, "列".getBytes().length*6*256);
		
		Cell subtype=row.createCell(cellnum++);
		subtype.setCellValue("设备类型");
		subtype.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum-1, "列".getBytes().length*6*256);
		Cell prod_name=row.createCell(cellnum++);
		prod_name.setCellValue("品名");
		prod_name.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum-1, "列".getBytes().length*6*256);
		Cell style=row.createCell(cellnum++);
		style.setCellValue("型号");
		style.setCellStyle(black_style);
		Cell prod_spec=row.createCell(cellnum++);
		prod_spec.setCellValue("规格");
		prod_spec.setCellStyle(black_style);
		
		Cell brand_name=row.createCell(cellnum++);
		brand_name.setCellValue("品牌");
		brand_name.setCellStyle(black_style);
		Cell supplier_name=row.createCell(cellnum++);
		supplier_name.setCellValue("供应商");
		supplier_name.setCellStyle(black_style);
		Cell installData=row.createCell(cellnum++);
		installData.setCellValue("安装时间");
		installData.setCellStyle(black_style);
		sheet.setColumnWidth(cellnum-1, "列".getBytes().length*4*256);
		
		//冻结窗格
		sheet.createFreezePane(4, 1);
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
	
	SimpleDateFormat ymd=new SimpleDateFormat("yyyy-MM-dd");
	private void build_content(List<PoleVO> poles,XSSFWorkbook wb,Sheet sheet,int rownum){
		CellStyle yellow_background_style = wb.createCellStyle();
		yellow_background_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		yellow_background_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		int cellnum=0;
		for(PoleVO pole:poles){
			cellnum=0;
			Row row = sheet.createRow(rownum++);
			Cell code=row.createCell(cellnum++);
			code.setCellValue(pole.getCode()==null?"":pole.getCode()+"");
			code.setCellStyle(yellow_background_style);
			
			Cell name=row.createCell(cellnum++);
			name.setCellValue(pole.getName());
			name.setCellStyle(yellow_background_style);
			
			Cell address=row.createCell(cellnum++);
			address.setCellValue(pole.geetFullAddress());
			address.setCellStyle(yellow_background_style);
			
			Cell status=row.createCell(cellnum++);
			status.setCellValue(pole.getStatus_name());
			status.setCellStyle(yellow_background_style);
			
//			Cell workunit=row.createCell(cellnum++);
//			workunit.setCellValue(pole.getWorkunit_name());
//			workunit.setCellStyle(yellow_background_style);
			
//			Cell customer=row.createCell(cellnum++);
//			customer.setCellValue(pole.getCustomer_name());
//			customer.setCellStyle(yellow_background_style);
			//设置设备信息的背景色
			for(int aa=cellnum;aa<=cellnum+7;aa++){
				Cell aaa=row.createCell(aa);
				aaa.setCellStyle(yellow_background_style);
			}
			
			//显示设备信息
			if(pole.getEquipments()!=null){
				int fromRow=rownum;
				
				
				List<EquipmentVO> equipments=pole.getEquipments();
				for(EquipmentVO vo:equipments){
					int cellnum_equip=cellnum;
					Row row_equip = sheet.createRow(rownum++);
					Cell ecode=row_equip.createCell(cellnum_equip++);
					ecode.setCellValue(vo.getEcode());
					
					Cell subtype=row_equip.createCell(cellnum_equip++);
					subtype.setCellValue(vo.getSubtype_name());
					
					Cell prod_name=row_equip.createCell(cellnum_equip++);
					prod_name.setCellValue(vo.getProd_name());
					
					Cell style=row_equip.createCell(cellnum_equip++);
					style.setCellValue(vo.getStyle());
					
					Cell prod_spec=row_equip.createCell(cellnum_equip++);
					prod_spec.setCellValue(vo.getProd_spec());
					
					Cell brand_name=row_equip.createCell(cellnum_equip++);
					brand_name.setCellValue(vo.getBrand_name());
					
					Cell supplier_name=row_equip.createCell(cellnum_equip++);
					supplier_name.setCellValue(vo.getSupplier_name());
					
					Cell last_install_date=row_equip.createCell(cellnum_equip++);
					if(vo.getLast_install_date()!=null){
						last_install_date.setCellValue(ymd.format(vo.getLast_install_date()));
					}
					
				}
//				sheet.groupRow(fromRow, fromRow+pole.getEquipments().size());
//				sheet.setRowGroupCollapsed(fromRow, true);
				
				sheet.groupRow(fromRow, rownum-1);
				sheet.setRowGroupCollapsed(fromRow, true);
			}
			
		}
		sheet.setRowSumsBelow(false);
		sheet.setRowSumsRight(false);
	}
}
