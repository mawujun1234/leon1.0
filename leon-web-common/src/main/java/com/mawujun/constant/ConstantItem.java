package com.mawujun.constant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.mawujun.annotation.Label;
import com.mawujun.repository.idEntity.UUIDEntity;

@Entity
@Table(name="leon_ConstantItem",uniqueConstraints={
		@UniqueConstraint(columnNames = {"code","constant_id"})
})
public class ConstantItem extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=25)
	@Label(name="编码")
	private String code;//具体的值，例如0,1,2等这些代码
	@Column(length=25)
	@Label(name="名称")
	private String text;//0,1等的名词，如有效，无效等
	@Column(length=200)
	@Label(name="备注")
	private String remark;
	
	@Label(name="排序")
	private int ordering;//排序
	@ManyToOne
	@JoinColumn(name="constant_id")
	private Constant constant;
	
	@Transient
	public final static String discriminator=ConstantEnum.ConstantItem.toString();//用来区分是哪个级别的
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public Constant getConstant() {
		return constant;
	}
	public void setConstant(Constant constant) {
		this.constant = constant;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDiscriminator() {
		return discriminator;
	}

	
}
