package com.mawujun.constant.cache;

import com.mawujun.constant.domain.CodeItem;

/**
 * 当要对常熟缓存进行扩展的时候，请继承这个类，
 * @author mawujun
 *
 */
public interface ICodeCache {
	public CodeItem  get(String codeClassId,String code);
	public void  put(String key,CodeItem codeItem);
}
