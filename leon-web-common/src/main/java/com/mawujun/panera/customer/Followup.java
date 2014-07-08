package com.mawujun.panera.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 往来跟进记录
 * @author mawujun 16064988@qq.com  
 *
 */
@Entity
@Table(name="panera_followup")
public class Followup extends UUIDEntity {
	private Date createDate;//等级时间
	@Column(length=30)
	private String method;//跟进方式
	@Column(length=1000)
	private String content;//跟进内容
	//private String imgs;
	
	private Date feedbackDate;
	@Column(length=1000)
	private String feedbackContetnt;
	
	private Date nextDate;//计划下一次跟进时间
	@Column(length=1000)
	private String nextContent;
	@org.hibernate.annotations.Type(type="yes_no")
	private Boolean nextHandled=false;
	
	
	@Column(length=36)
	private String customer_id;
	@Transient
	private String customer_name;
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public Date getNextDate() {
		return nextDate;
	}
	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}
	public String getNextContent() {
		return nextContent;
	}
	public void setNextContent(String nextContent) {
		this.nextContent = nextContent;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getFeedbackContetnt() {
		return feedbackContetnt;
	}
	public void setFeedbackContetnt(String feedbackContetnt) {
		this.feedbackContetnt = feedbackContetnt;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public Boolean getNextHandled() {
		return nextHandled;
	}
	public void setNextHandled(Boolean nextHandled) {
		this.nextHandled = nextHandled;
	}
	

}
