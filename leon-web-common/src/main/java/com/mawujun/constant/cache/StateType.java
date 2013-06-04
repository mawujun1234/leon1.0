package com.mawujun.constant.cache;

import javax.persistence.Embeddable;


@Embeddable
public class StateType extends ConstantItemProxy {

	@Override
	public void setConstantCode() {
		// TODO Auto-generated method stub
		this.constantCode="CODE_StateType";//Constant的ID
	}

}
