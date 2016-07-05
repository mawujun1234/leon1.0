package com.mawujun.baseinfo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity(name="ems_area_workunit")
public class AreaWorkUnit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @ManyToOne
	private Area area;
	@Id
    @ManyToOne
	private WorkUnit workUnit;
	
	public AreaWorkUnit(Area area, WorkUnit workUnit) {
		super();
		this.area = area;
		this.workUnit = workUnit;
	}
	public AreaWorkUnit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkUnit getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(WorkUnit workUnit) {
		this.workUnit = workUnit;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((workUnit == null) ? 0 : workUnit.hashCode());
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
		AreaWorkUnit other = (AreaWorkUnit) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (workUnit == null) {
			if (other.workUnit != null)
				return false;
		} else if (!workUnit.equals(other.workUnit))
			return false;
		return true;
	}

}
