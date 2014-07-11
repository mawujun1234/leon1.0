package com.mawujun.panera.continents;

/**
 * 各大洲
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public enum Continent {
	
	
	Asia("亚洲"),Europe("欧洲"),Oceania("大洋洲"),Africa("非洲"),NorthAmerica("北美洲"),SouthAmerica("南洲");
	
	private String text;
	private Continent(String name) {
		this.text=name;
	}

	public String getText(){
		return this.text;
	}
}
