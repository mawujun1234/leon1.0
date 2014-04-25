package com.mawujun.parameter;

/**
 * 存储最终返回来的额参数的值
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public class ParameterValue {
	//存放主题的id，可能是用户id，角色id，组id等等
	private String key;
	//存放参数值
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
