package com.mawujun.store;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="ems_order")
public class Order extends UUIDEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=60)
	private String orderNo;//订单号
	@Column(length=36)
	private String store_id;
	private Date orderDate;//订购日期
	@Column(length=36)
	private String operater;//操作人的id
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private OrderStatus status=OrderStatus.edit;//状态有两个edit，或者 editover
	@Column(length=36)
	private String project_id;//项目id
	@Column(updatable=false)
	private Date createDate;//订单建立日期
	@Column(length=3)
	private String supplier_id;//供应商id
	@Enumerated(EnumType.STRING)
	@Column(length=15)
	private OrderType orderType;
	
	public String getOrderType_name() {
		if(orderType!=null){
			return orderType.getName();
		} else {
			return null;
		}
	}
	public void setOrderNo(String orderNo) {
		if(orderNo!=null){
			this.orderNo = orderNo.trim();
		}
		
	}
//	@Column(length=2)
//	private String type_id;//类型id
//	@Column(length=4)
//	private String subtype_id;//子类型id
//	@Column(length=6)
//	private String prod_id;//品名id
//	@Column(length=15)
//	private String brand_id;//品牌id
//	@Column(length=3)
//	private String supplier_id;//供应商id
//	@Column(length=50)
//	private String style;//型号
//	
//	@Column(precision=10,scale=2)
//	private Double unitPrice;
//	
//	private Integer orderNum=0;//订货数量
//	private Integer totalNum=0;//累计入库数量
	
	


	
	
//	@org.hibernate.annotations.Type(type="yes_no")
//	private Boolean status=false;//true:已经导出，表示已经使用过了，false：表示还未使用
//	
//	@org.hibernate.annotations.Type(type="yes_no")
//	private Boolean isInStore=false;//true:已经入库，表示已经使用过了，false：表示这个条码还未使用


	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
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


	
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}






	public OrderType getOrderType() {
		return orderType;
	}






	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}


	
}
