package com.mawujun.repository.hibernate.schemaExport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.mawujun.repository.idEntity.AutoIdEntity;

@Entity
public class Model extends AutoIdEntity<Integer> {
	private String name;
	private int age;
	
	private Date createDate;
	
	@OneToMany(mappedBy="parent")
	private List<Model> childen;
	public void setChilden(List<Model> childen) {
		this.childen = childen;
	}

	//@JsonIgnore
	@ManyToOne
	private Model parent;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Model> getChilden() {
		return childen;
	}

	public void addChilden(Model childen) {
		if(this.childen==null){
			this.childen=new ArrayList<Model>();
		}
		this.childen.add(childen);
	}

	public Model getParent() {
		return parent;
	}

	public void setParent(Model parent) {
		this.parent = parent;
	}



}
