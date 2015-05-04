package com.mawujun.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.repair.RepairVO;
import com.mawujun.utils.M;

/**
 * 资产状况表
 * @author mawujun 16064988@qq.com  
 *
 */
@Controller
public class PropertyStatusController {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/propertystatus/query.do")
	public List<Map<String,Object>> query(String store_id){
		//store_id="2c90838a48f27b350148f2a91b81000d";
		//获取已入库的数量，长仓库中获取
		String sql="select subtype_id,count(ecode) as num from ems_equipment where store_id='"+store_id+"' and status='1' group by subtype_id";
		List<Map<String,Object>> in_storage=jdbcTemplate.queryForList(sql);

//		//获取安装出库的数量,被作业单位持有的数量,
//		//设备状态时领用出库，单领用单是这个仓库的，而且作业单位是同个作业单位的
//		//？？
//		sql="select a.subtype_id,count(a.ecode) from ems_equipment a, ems_installout b where a.status='2' and a.workunit_id=b.workUnit_id and b.store_id='"+store_id+"' "
//				+ "group by subtype_id";		
//		
//		//获取损坏的数量
//		//状态是损坏，并且在这个仓库里面的数量
//		sql="select subtype_id,count(ecode) from ems_equipment where store_id='"+store_id+"' and status='4' group by subtype_id";
		
		//获取入库待维修的数量
		sql="select subtype_id,count(ecode) as num  from ems_equipment where store_id='"+store_id+"' and status='5' group by subtype_id";
		List<Map<String,Object>> wait_for_repair=jdbcTemplate.queryForList(sql);
		//获取发往维修中心的数量
		sql="select a.subtype_id,count(a.ecode) as num  from ems_equipment a, ems_repair b where a.status='6'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='1' group by subtype_id";
		List<Map<String,Object>> to_repair=jdbcTemplate.queryForList(sql);
		//获取外修中的数量,注意设备状态中，还没有区分出 外修中还是维修中，统一使用维修中
		sql="select a.subtype_id,count(a.ecode) as num  from ems_equipment a, ems_repair b where a.status='8'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='2' and rpa_type='outrpa' group by subtype_id";
		List<Map<String,Object>> outside_repairing=jdbcTemplate.queryForList(sql);
		//获取维修中的数量
		sql="select a.subtype_id,count(a.ecode) as num  from ems_equipment a, ems_repair b where a.status='8'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='2' and rpa_type='innerrpa' group by subtype_id";
		List<Map<String,Object>> inner_repairing=jdbcTemplate.queryForList(sql);
		//获取维修后已出库的数量
		sql="select a.subtype_id,count(a.ecode) as num  from ems_equipment a, ems_repair b where a.status='9'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='3' group by subtype_id";
		List<Map<String,Object>> out_repair=jdbcTemplate.queryForList(sql);
		//获取在途的数量，借用归还的数量
		//使用中  的数量 可以使使用中 从这个仓库出去的领用单的数量，排除一个设备多次从这个仓库领用的情况
		
		//获取小类的表
		sql="select id,name from ems_equipmentsubtype where status='Y'";
		List<Map<String,Object>> ems_equipmentsubtype=jdbcTemplate.queryForList(sql);
		for(Map<String,Object> equipmentsubtype:ems_equipmentsubtype){
			equipmentsubtype.put(M.EquipmentSubtype.id, equipmentsubtype.get("ID"));
			equipmentsubtype.put(M.EquipmentSubtype.name, equipmentsubtype.get("NAME"));
			for(Map<String,Object> map:in_storage){
				if(equipmentsubtype.get("id").equals(map.get("subtype_id"))){
					equipmentsubtype.put("in_storage", map.get("num"));
				}
			}
			
			for(Map<String,Object> map:wait_for_repair){
				if(equipmentsubtype.get("id").equals(map.get("subtype_id"))){
					equipmentsubtype.put("wait_for_repair", map.get("num"));
				}
			}
			
			for(Map<String,Object> map:to_repair){
				if(equipmentsubtype.get("id").equals(map.get("subtype_id"))){
					equipmentsubtype.put("to_repair", map.get("num"));
				}
			}
			
			for(Map<String,Object> map:outside_repairing){
				if(equipmentsubtype.get("id").equals(map.get("subtype_id"))){
					equipmentsubtype.put("outside_repairing", map.get("num"));
				}
			}
			
			for(Map<String,Object> map:inner_repairing){
				if(equipmentsubtype.get("id").equals(map.get("subtype_id"))){
					equipmentsubtype.put("inner_repairing", map.get("num"));
				}
			}
			
			for(Map<String,Object> map:out_repair){
				if(equipmentsubtype.get("id").equals(map.get("subtype_id"))){
					equipmentsubtype.put("out_repair", map.get("num"));
				}
			}
		}
		return ems_equipmentsubtype;
		
	}
	
	@RequestMapping("/propertystatus/export.do")
	public void export(HttpServletResponse response,String store_id) throws IOException{
		List<Map<String,Object>> result=query(store_id);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		build_addColumnName(wb,sheet,rownum);
		
		// 开始构建整个excel的文件
		if (result != null && result.size() > 0) {
			rownum++;
			build_content(result, wb, sheet, rownum);
		}
		String filename = "仓库资产情况.xlsx";
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
		//CellStyle black_style=getStyle(wb,IndexedColors.BLACK,(short)11);
		 
		Row row = sheet.createRow(rowInt);
		int cellnum=0;
		
		Cell name=row.createCell(cellnum++);
		name.setCellValue("小类");
		
		Cell in_storage=row.createCell(cellnum++);
		in_storage.setCellValue("已入库");
		
		Cell wait_for_repair=row.createCell(cellnum++);
		wait_for_repair.setCellValue("入库待维修");
		
		Cell to_repair=row.createCell(cellnum++);
		to_repair.setCellValue("发往维修中心");
		
		Cell outside_repairing=row.createCell(cellnum++);
		outside_repairing.setCellValue("外修中");
		
		Cell inner_repairing=row.createCell(cellnum++);
		inner_repairing.setCellValue("维修中");
		
		Cell out_repair=row.createCell(cellnum++);
		out_repair.setCellValue("维修后已出库");
		
	}
	
	SimpleDateFormat yMdHms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void build_content(List<Map<String,Object>> list,XSSFWorkbook wb,Sheet sheet,int rownum){
		int cellnum=0;

		for(Map<String,Object> map:list){
			cellnum=0;
			Row row = sheet.createRow(rownum++);
			
			Cell name=row.createCell(cellnum++);
			name.setCellValue(map.get("name").toString());
			
			Cell in_storage=row.createCell(cellnum++);
			in_storage.setCellValue(map.get("in_storage")!=null?map.get("in_storage").toString():"");
			
			Cell wait_for_repair=row.createCell(cellnum++);
			wait_for_repair.setCellValue(map.get("wait_for_repair")!=null?map.get("wait_for_repair").toString():"");
			
			Cell to_repair=row.createCell(cellnum++);
			to_repair.setCellValue(map.get("to_repair")!=null?map.get("to_repair").toString():"");
			
			Cell outside_repairing=row.createCell(cellnum++);
			outside_repairing.setCellValue(map.get("outside_repairing")!=null?map.get("outside_repairing").toString():"");
			
			Cell inner_repairing=row.createCell(cellnum++);
			inner_repairing.setCellValue(map.get("inner_repairing")!=null?map.get("inner_repairing").toString():"");
			
			Cell out_repair=row.createCell(cellnum++);
			out_repair.setCellValue(map.get("out_repair")!=null?map.get("out_repair").toString():"");
			

		}
	}
}
