package com.mawujun.constant;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.annotation.Label;
import com.mawujun.repository.idEntity.IdEntity;

/**
 * 常数类，下面有CodeItem
 * @author mawujun
 *
 */
@Entity
@Table(name="leon_Constant")
public class Constant implements IdEntity<String> {
	
	@Id
	@Column(length=25)
	@Label(name="编码")
	private String id;
	@Column(length=25)
	@Label(name="名称")
	private String text;
	@Column(length=200)
	@Label(name="备注")
	private String remark;
	
	@ManyToOne
	@JoinColumn(name="constantType_id")
	private ConstantType constantType;
	
	@OneToMany(mappedBy="constant")
	@OrderBy("ordering")
	private Set<ConstantItem> constantItemes;
	
	@Transient
	public final static String discriminator="Constant";//用来区分是哪个级别的

	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public void setId(String id) {
		// TODO Auto-generated method stub
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

	public static String getDiscriminator() {
		return discriminator;
	}


}
