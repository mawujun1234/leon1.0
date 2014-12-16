package com.mawujun.store;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_order")
public class Order extends UUIDEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=25)
	private String orderNo;//订单号
	@Column(length=36)
	private String store_id;
	@Column(length=2)
	private String type_id;//类型id
	@Column(length=4)
	private String subtype_id;//子类型id
	@Column(length=6)
	private String prod_id;//品名id
	@Column(length=15)
	private String brand_id;//品牌id
	@Column(length=3)
	private String supplier_id;//供应商id
	@Column(length=50)
	private String style;//型号
	
//	@Column(length=6)
//	private String ymd;//年月日
//	@Column(length=3)
//	private Integer serialNum;//流水号
	
	@Column(precision=10,scale=2)
	private Double unitPrice;
	
	private Integer orderNum=0;//订货数量
	private Integer totalNum=0;//累计入库数量
	
	private Date orderDate;//订购日期
	@Column(length=36)
	private String operater;//操作人的id

	/**
	 * 返回订单状态，true表示已经完成了，false表示还没有完成
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public Boolean getStatus(){
		if(this.orderNum==this.totalNum){
			return true;
		} else {
			return false;
		}
	}
	
	
//	@org.hibernate.annotations.Type(type="yes_no")
//	private Boolean status=false;//true:已经导出，表示已经使用过了，false：表示还未使用
//	
//	@org.hibernate.annotations.Type(type="yes_no")
//	private Boolean isInStore=false;//true:已经入库，表示已经使用过了，false：表示这个条码还未使用


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
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
