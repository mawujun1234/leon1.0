package destory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.mawujun.repository.idEntity.IdEntity;

//@Entity
//@Table(name="ems_barcode",uniqueConstraints = {@UniqueConstraint(columnNames={"subtype_id", "prod_id","brand_id","supplier_id","ymd","serialNum"})})
public class Barcode implements IdEntity<String>{
	@Id
	@Column(length=25)
	private String ecode;//条码 小类(2)+品名(2)+品牌(3)+供应商(3)+年月日(6)+流水号(3)=19位
//	@Column(length=2)
//	private String type_id;//类型id
	@Column(length=3)
	private String subtype_id;//子类型id
	@Column(length=2)
	private String prod_id;//品名id
	@Column(length=3)
	private String brand_id;//品牌id
	@Column(length=3)
	private String supplier_id;//供应商id
	@Column(length=6)
	private String ymd;//年月日
	@Column(length=3)
	private Integer serialNum;//流水号
	
	@Column(length=50)
	private String style;//型号
	@Column(precision=10,scale=2)
	private Double unitPrice;
	
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean status=false;//true:已经导出，表示已经使用过了，false：表示还未使用
	
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean isInStore=false;//true:已经入库，表示已经使用过了，false：表示这个条码还未使用
	
	@Transient
	private String subtype_name;
	@Transient
	private String prod_name;
	@Transient
	private String brand_name;
	@Transient
	private String supplier_name;
	@Transient
	private String store_name;
	
	
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.ecode=id;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ecode;
	}
	/**
	 * 获取key，用来分组，再用来获取某一组的流水号
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	public BarcodeKey getKey(){
		BarcodeKey key=new BarcodeKey();
		key.setBrand_id(this.getBrand_id());
		key.setProd_id(this.getProd_id());
		key.setSubtype_id(this.getSubtype_id());
		key.setSupplier_id(this.getSupplier_id());
		key.setYmd(this.getYmd());
		return key;
	}
	/**
	 * 流水码统计的关键字，数据库查询的时候，就是按照这个来的
	 * @author mawujun email:16064988@163.com qq:16064988
	 *
	 */
	public class BarcodeKey{
		
		private String subtype_id;//子类型id
		private String prod_id;//品名id
		private String brand_id;//品牌id
		private String supplier_id;//供应商id
		private String ymd;//年月日
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
		public String getYmd() {
			return ymd;
		}
		public void setYmd(String ymd) {
			this.ymd = ymd;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((brand_id == null) ? 0 : brand_id.hashCode());
			result = prime * result
					+ ((prod_id == null) ? 0 : prod_id.hashCode());
			result = prime * result
					+ ((subtype_id == null) ? 0 : subtype_id.hashCode());
			result = prime * result
					+ ((supplier_id == null) ? 0 : supplier_id.hashCode());
			result = prime * result + ((ymd == null) ? 0 : ymd.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BarcodeKey other = (BarcodeKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (brand_id == null) {
				if (other.brand_id != null)
					return false;
			} else if (!brand_id.equals(other.brand_id))
				return false;
			if (prod_id == null) {
				if (other.prod_id != null)
					return false;
			} else if (!prod_id.equals(other.prod_id))
				return false;
			if (subtype_id == null) {
				if (other.subtype_id != null)
					return false;
			} else if (!subtype_id.equals(other.subtype_id))
				return false;
			if (supplier_id == null) {
				if (other.supplier_id != null)
					return false;
			} else if (!supplier_id.equals(other.supplier_id))
				return false;
			if (ymd == null) {
				if (other.ymd != null)
					return false;
			} else if (!ymd.equals(other.ymd))
				return false;
			return true;
		}
		private Barcode getOuterType() {
			return Barcode.this;
		}
	}
//	public Barcode clone() {
//		Barcode equip = new Barcode();
//		
//	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
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
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public Integer getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getIsInStore() {
		return isInStore;
	}
	public void setIsInStore(Boolean isInStore) {
		this.isInStore = isInStore;
	}
	public String getSubtype_name() {
		return subtype_name;
	}
	public void setSubtype_name(String subtype_name) {
		this.subtype_name = subtype_name;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	
}
