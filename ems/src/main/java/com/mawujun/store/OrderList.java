package com.mawujun.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_orderlist")
public class OrderList extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=60)
	private String order_id;//订单id
	
	@Column(length=2)
	private String type_id;//类型id
	@Column(length=4)
	private String subtype_id;//子类型id
	@Column(length=10)
	private String prod_id;//品名id
	@Column(length=15)
	private String brand_id;//品牌id
//	@Column(length=3)
//	private String supplier_id;//供应商id
//	@Column(length=50)
//	private String style;//型号
	
	@Column(precision=10,scale=2)
	private Double unitPrice;
	private Integer quality_month;//质保时间长度，以月为单位
	
	private Integer orderNum=0;//订货数量
	private Integer totalNum=0;//累计入库数量,如果该品名下面还有拆分，拆分几个就会乘以几个，会是orderNum的倍数
	
	@Transient
	private Integer  printNum;//要打印的数目
	
	public void addTotalnum(int add){
		if(this.totalNum==null){
			this.totalNum=0;
		}
		this.totalNum=this.totalNum+add;
		if(this.totalNum>this.orderNum){
			throw new BusinessException("订单订购数量已经全部入库，不能再入库了，条码打印重复了。");
		}
	}
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getSubtype_id() {
		return subtype_id;
	}
	public void setSubtype_id(String subtype_id) {
		this.subtype_id = subtype_id;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

//	public String getStyle() {
//		return style;
//	}
//	public void setStyle(String style) {
//		this.style = style;
//	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getPrintNum() {
		return printNum;
	}
	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}
	public Integer getQuality_month() {
		return quality_month;
	}
	public void setQuality_month(Integer quality_month) {
		this.quality_month = quality_month;
	}

}
