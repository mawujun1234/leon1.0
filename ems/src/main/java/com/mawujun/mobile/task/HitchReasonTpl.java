package com.mawujun.mobile.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mawujun.repository.idEntity.IdEntity;

/**
 * 故障原因模板
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
@Entity
@Table(name="ems_hitchreasontpl")
public class HitchReasonTpl implements IdEntity<Integer>{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UserSequence")
	@SequenceGenerator(name = "UserSequence", sequenceName = "seq_hitchreasontpl", allocationSize=20)
	private Integer id;
	@Column(length=30)
	private String name;
	@Column(length=500)
	private String tpl;
	@Column(length=36)
	private String hitchType_id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTpl() {
		return tpl;
	}
	public void setTpl(String tpl) {
		this.tpl = tpl;
	}
	public String getHitchType_id() {
		return hitchType_id;
	}
	public void setHitchType_id(String hitchType_id) {
		this.hitchType_id = hitchType_id;
	}
}
