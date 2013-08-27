package com.mawujun.role;

import com.mawujun.fun.Fun;

/**
 * 观察者，当角色权限发生变化的时候，就会通知这个类的子类
 * @author mawujun email:16064988@163.com qq:16064988
 *
 */
public interface RoleFunObserver {
	/**
	 * 为角色授权
	 * @author mawujun email:16064988@163.com qq:16064988
	 */
	public void create(Role role,Fun fun);
	/**
	 * 取消角色上额某个权限
	 * @author mawujun email:16064988@163.com qq:16064988
	 */
	public void destroy(Role role,Fun fun);
	
}
