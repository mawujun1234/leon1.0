package com.mawujun.fun;

import java.util.ArrayList;
import java.util.List;

public class FunVO extends Fun {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fun> children = new ArrayList<Fun>();

	public void addChild(Fun child) {
		this.children.add(child);
	}

	public List<Fun> getChildren() {
		return children;
	}

	public void setChildren(List<Fun> children) {
		this.children = children;
	}
}
