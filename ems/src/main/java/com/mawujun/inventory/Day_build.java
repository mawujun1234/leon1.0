package com.mawujun.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 在建仓库，日结数据
 * @author mawujun 16064988@qq.com  
 *
 */
//@Entity
//@Table(name="report_day_build")
//@IdClass(Day_build_PK.class)  
public class Day_build  implements IdEntity<Day_build_PK>{
	@Id
	@Column(length=36)
	private Integer daykey;//20140101这种形式
	@Id
	@Column(length=36)
	private String prod_id;//品名id
	@Id
	@Column(length=36)
	private String store_id;//仓库id，所属仓库
	
	@Column(length=8)
	private Integer monthkey;//外键关联到月结表
	
	//这下面的4个值，都是当天的月结库存数据(历史数据)，真实的月结库存数据时在月结表里面的。
	//上期(月)结余数
	//本期(月)新增数
	//本期(月)领用数
	//本期(月)结余数
	
	//新增数,今天新增的数量
	//领用数，今天领用的数量

	@Override
	public void setId(Day_build_PK id) {
		this.daykey=id.getDaykey();
		this.prod_id=id.getProd_id();
		this.store_id=id.getStore_id();
	}

	@Override
	public Day_build_PK getId() {
		// TODO Auto-generated method stub
		return new Day_build_PK(daykey,prod_id,store_id);
	}
	

}
