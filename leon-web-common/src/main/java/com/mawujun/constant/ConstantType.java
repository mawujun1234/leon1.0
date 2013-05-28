package com.mawujun.constant;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.annotation.Label;
import com.mawujun.repository.idEntity.AutoIdEntity;
import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 常数代码的类型 分类
 * @author mawujun
 *
 */
@Entity
@Table(name="leon_ConstantType")
public class ConstantType extends UUIDEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(length=25)
	@Label(name="名称")
	private String text;
	@Column(length=200)
	@Label(name="备注")
	private String remark;
	
	@OneToMany(mappedBy="constantType")
	@Column(length=36)
	private Set<Constant> constants;

	@Transient
	public final static String discriminator="ConstantType";//用来区分是哪个级别的

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Constant> getConstants() {
		return constants;
	}

	public void setConstants(Set<Constant> constants) {
		this.constants = constants;
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
