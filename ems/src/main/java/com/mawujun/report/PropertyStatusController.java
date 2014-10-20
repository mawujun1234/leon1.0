package com.mawujun.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public void query(String store_id){
		store_id="2c90838a48f27b350148f2a91b81000d";
		//获取已入库的数量，长仓库中获取
		String sql="select subtype_id,count(ecode) from ems_equipment where store_id='"+store_id+"' and status='1' group by subtype_id";

		//获取安装出库的数量,被作业单位持有的数量,
		//设备状态时领用出库，单领用单是这个仓库的，而且作业单位是同个作业单位的
		？？
		sql="select a.subtype_id,count(a.ecode) from ems_equipment a, ems_installout b where a.status='2' and a.workunit_id=b.workUnit_id and b.store_id='"+store_id+"' "
				+ "group by subtype_id";
		
//		
//		//获取损坏的数量
//		//状态是损坏，并且在这个仓库里面的数量
//		sql="select subtype_id,count(ecode) from ems_equipment where store_id='"+store_id+"' and status='4' group by subtype_id";
		
		//获取入库待维修的数量
		sql="select subtype_id,count(ecode) from ems_equipment where store_id='"+store_id+"' and status='5' group by subtype_id";
		//获取发往维修中心的数量
		sql="select a.subtype_id,count(a.ecode) from ems_equipment a, ems_repair b where a.status='6'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='1' group by subtype_id";
		//获取外修中的数量,注意设备状态中，还没有区分出 外修中还是维修中，统一使用维修中
		sql="select a.subtype_id,count(a.ecode) from ems_equipment a, ems_repair b where a.status='8'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='2' and rpa_type='outrpa' group by subtype_id";
		//获取维修中的数量
		sql="select a.subtype_id,count(a.ecode) from ems_equipment a, ems_repair b where a.status='8'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='2' and rpa_type='innerrpa' group by subtype_id";
		//获取维修后已出库的数量
		sql="select a.subtype_id,count(a.ecode) from ems_equipment a, ems_repair b where a.status='9'"
				+ " and a.ecode=b.ecode and b.str_out_id='"+store_id+"' and b.status='3' group by subtype_id";
		//获取在途的数量，借用归还的数量
		//使用中  的数量 可以使使用中 从这个仓库出去的领用单的数量，排除一个设备多次从这个仓库领用的情况
		
		
	}
}
