package com.mawujun.panera.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.panera.continents.Continent;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="panera_customer")
public class Customer extends UUIDEntity {
	@Column(length=100)
	private String code;
	@Column(length=100)
	private String name;
	//@ManyToOne(fetch=FetchType.EAGER)
	//private CustomerSource customerSource;
	@Column(length=36)
	private String customerSource_id;
	@Transient
	private String customerSource_name;
//	@ManyToOne(fetch=FetchType.EAGER)
//	private CustomerProperty customerProperty;
	@Column(length=36)
	private String customerProperty_id;
	@Transient
	private String customerProperty_name;
	
		
//	private Country country;
	//@Column(length=36)
	//@Transient
	//@Column(length=36)
	@Transient
	private String continent_id;
	
	@Column(length=36)
	private String country_id;
	@Transient
	private String country_name;
	

	@Column(length=200)
	private String address;
	
	@Column(length=200)
	private String website;
	
	@Column(length=100)
	private String businessPhase_id;
	@Transient
	private String businessPhase_name;
	//@Column(length=20)
	@Transient
	private Integer followupNum;//跟进次数，这个是只读的，是在跟进记录里面设置的
	private Date inquiryDate;//初次询盘时间
	@Column(length=1500)
	private String inquiryContent;//初次询盘内容
	 @org.hibernate.annotations.Type(type="yes_no")
	private Boolean deleted=false;
	
	
	
	
	private Integer star;//星级，还要加很多评判的字段	
	private Integer expYear;//光带几年经验
	private Integer proportion;//光带占比
	private Integer customerType;//客户类型
	private Integer empNum;//员工人数
	private Integer buyMoney;//光带年采购额
	private Integer quality;//质量档次
	private Integer price;//价格档次
	private Integer moq;//每单每款MOQ
	private Integer paymentTerms;//付款条款
	
	@Transient
	private String contact_id;
	@Transient
	private String contact_name;
	@Transient
	private String contact_position;
	@Transient
	private String contact_phone;
	@Transient
	private String contact_mobile;
	@Transient
	private String contact_chatNum;
	@Transient
	private String contact_fax;
	@Transient
	private String contact_email;
	@Transient
	private Boolean contact_isDefault;
	
	//计算星级
	public Integer calculate() {
		Integer star=0;
		star+=this.getExpYear();
		star+=this.getProportion();
		star+=this.getCustomerType();
		star+=this.getEmpNum();
		star+=this.getBuyMoney();
		star+=this.getQuality();
		star+=this.getPrice();
		star+=this.getMoq();
		if(this.getPaymentTerms()==400){
			star+=4;
		} else {
			star+=this.getPaymentTerms();
		}
		
		if(star<15){
			return 1;
		} else if(star>=15 && star<20){
			return 2;
		} else if(star>=20 && star<28){
			return 3;
		} else if(star>=28 && star<37){
			return 4;
		} else if(star>=37 ){
			return 5;
		}
		return star;
	}
	public Contact geetContact(){
		Contact contact=new Contact();
		contact.setId(contact_id);
		contact.setName(contact_name);
		contact.setPosition(contact_position);
		contact.setPhone(contact_phone);
		contact.setMobile(contact_mobile);
		contact.setChatNum(contact_chatNum);
		contact.setFax(contact_fax);
		contact.setEmail(contact_email);
		contact.setIsDefault(contact_isDefault);
		contact.setCustomer_id(this.getId());
		return contact;
	}
	public String setContinent_name() {
		if(Continent.valueOf(this.getContinent_id())!=null){
			return Continent.valueOf(this.getContinent_id()).getText();
		}
		return null;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	public Date getInquiryDate() {
		return inquiryDate;
	}
	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
	}
	public String getInquiryContent() {
		return inquiryContent;
	}
	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}


	public String getCustomerSource_id() {
		return customerSource_id;
	}
	public void setCustomerSource_id(String customerSource_id) {
		this.customerSource_id = customerSource_id;
	}
	public String getCustomerProperty_id() {
		return customerProperty_id;
	}
	public void setCustomerProperty_id(String customerProperty_id) {
		this.customerProperty_id = customerProperty_id;
	}

	public String getContinent_id() {
		return continent_id;
	}
	public void setContinent_id(String continent_id) {
		this.continent_id = continent_id;
	}
	public String getCountry_id() {
		return country_id;
	}
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}
	public String getBusinessPhase_id() {
		return businessPhase_id;
	}
	public void setBusinessPhase_id(String businessPhase_id) {
		this.businessPhase_id = businessPhase_id;
	}
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_position() {
		return contact_position;
	}
	public void setContact_position(String contact_position) {
		this.contact_position = contact_position;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getContact_mobile() {
		return contact_mobile;
	}
	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}
	public String getContact_chatNum() {
		return contact_chatNum;
	}
	public void setContact_chatNum(String contact_chatNum) {
		this.contact_chatNum = contact_chatNum;
	}
	public String getContact_fax() {
		return contact_fax;
	}
	public void setContact_fax(String contact_fax) {
		this.contact_fax = contact_fax;
	}
	public String getContact_email() {
		return contact_email;
	}
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getExpYear() {
		return expYear;
	}

	public void setExpYear(Integer expYear) {
		this.expYear = expYear;
	}

	public Integer getProportion() {
		return proportion;
	}

	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getEmpNum() {
		return empNum;
	}

	public void setEmpNum(Integer empNum) {
		this.empNum = empNum;
	}

	public Integer getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(Integer buyMoney) {
		this.buyMoney = buyMoney;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getMoq() {
		return moq;
	}

	public void setMoq(Integer moq) {
		this.moq = moq;
	}

	public Integer getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(Integer paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getCustomerSource_name() {
		return customerSource_name;
	}

	public void setCustomerSource_name(String customerSource_name) {
		this.customerSource_name = customerSource_name;
	}

	public String getCustomerProperty_name() {
		return customerProperty_name;
	}

	public void setCustomerProperty_name(String customerProperty_name) {
		this.customerProperty_name = customerProperty_name;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getBusinessPhase_name() {
		return businessPhase_name;
	}

	public void setBusinessPhase_name(String businessPhase_name) {
		this.businessPhase_name = businessPhase_name;
	}
	public Boolean getContact_isDefault() {
		return contact_isDefault;
	}
	public void setContact_isDefault(Boolean contact_isDefault) {
		this.contact_isDefault = contact_isDefault;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Integer getFollowupNum() {
		return followupNum;
	}
	public void setFollowupNum(Integer followupNum) {
		this.followupNum = followupNum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

//	@OneToMany(mappedBy="customer",fetch=FetchType.LAZY,cascade={CascadeType.REFRESH})
//	private List<Contact> contacts;
	
	
	
	
	

}
