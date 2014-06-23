package com.mawujun.panera.continents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mawujun.repository.idEntity.UUIDEntity;

/**
 * 
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
@Entity
@Table(name="panera_Country")
public class Country extends UUIDEntity {
	private String name_en;
	private String name;
	@Enumerated(EnumType.STRING)
	private Continent continent;
	@Transient
	private String continent_name;

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Continent getContinent() {
		return continent;
	}

	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	public String getContinent_name() {
		//return continent_name;
		return this.getContinent().getText();
	}

//	public void setContinent_name(String continent_name) {
//		this.continent_name = continent_name;
//	}

}
