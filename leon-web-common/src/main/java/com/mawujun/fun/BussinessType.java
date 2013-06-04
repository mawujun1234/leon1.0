package com.mawujun.fun;

import javax.persistence.Embeddable;

import com.mawujun.constant.cache.ConstantItemProxy;

/**
 * 业务类型，用于权限判断
 * @author mawujun email:mawujun1234@163.com qq:16064988
 *
 */
@Embeddable
public class BussinessType extends ConstantItemProxy {

	@Override
	public void setConstantCode() {
		// TODO Auto-generated method stub
		this.constantCode="Fun_BussinessType";
	}
}
