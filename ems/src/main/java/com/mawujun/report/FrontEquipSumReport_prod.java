package com.mawujun.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 这里存放的hi品名和 数量
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class FrontEquipSumReport_prod {
	private String prod_id;
	private String prod_name;
	private String prod_style;
	private Integer num;
	private BigDecimal value_net;
	
	
	
	public BigDecimal getValue_net() {
		if(value_net!=null){
			return value_net.setScale(2, RoundingMode.HALF_UP);
		}
		return new BigDecimal(0);
	}
	public void setValue_net(BigDecimal value_net) {
		this.value_net = value_net;
	}
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getProd_style() {
		return prod_style;
	}
	public void setProd_style(String prod_style) {
		this.prod_style = prod_style;
	}
	

}
