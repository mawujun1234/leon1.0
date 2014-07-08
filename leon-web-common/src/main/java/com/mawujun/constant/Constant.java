package com.mawujun.constant;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.mawujun.annotation.Label;
import com.mawujun.repository.idEntity.IdEntity;
import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 常数类，下面有CodeItem
 * @author mawujun
 *
 */
@Entity
@Table(name="leon_constant")
public class Constant extends UUIDEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=25,unique=true)
	@Label(name="编码")
	private String code;
	@Column(length=25)
	@Label(name="名称")
	private String text;
	@Column(length=200)
	@Label(name="备注")
	private String remark;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="constantType_id")
	private ConstantType constantType;
	
	@OneToMany(mappedBy="constant",fetch=FetchType.EAGER)
	@OrderBy("ordering")
	private Set<ConstantItem> constantItemes;
	
	@Transient
	public final static String discriminator=ConstantEnum.Constant.toString();//用来区分是哪个级别的

public Constant(){
	super();
}
public Constant(String id){
	super();
	this.id=id;
}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ConstantType getConstantType() {
		return constantType;
	}

	public void setConstantType(ConstantType constantType) {
		this.constantType = constantType;
	}

	public Set<ConstantItem> getConstantItemes() {
		return constantItemes;
	}

	public void setConstantItemes(Set<ConstantItem> constantItemes) {
		this.constantItemes = constantItemes;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
