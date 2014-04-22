package com.mawujun.fun;

import javax.persistence.Embeddable;

import com.mawujun.constant.cache.ConstantItemProxy;

/**
 * 业务类型，用于权限判断
 * 我们先来看一个需求，在铺货的时候能看到的仓库范围是A,B，在补货的时候可以为卖场补货的仓库范围是B,C，所以在铺，补的时候门店所能看到的仓库范围是不一样的。这个时候就会提出一个“业务类型”的概念，在不同“业务类型”下面所拥有的数据权限是不一样的
 * @author mawujun email:16064988@qq.com qq:16064988
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
