package com.mawujun.constant.cache;

import com.mawujun.constant.ConstantItem;


/**
 * 当要对常熟缓存进行扩展的时候，请继承这个类，
 * @author mawujun
 *
 */
public interface IConstantCache {
	public ConstantItem  get(String constantCode,String code);
	public void  put(String key,ConstantItem codeItem);
}
