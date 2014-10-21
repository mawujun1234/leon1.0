package com.mawujun.report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
