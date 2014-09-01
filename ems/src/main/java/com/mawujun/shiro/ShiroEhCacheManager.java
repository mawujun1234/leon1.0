package com.mawujun.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;

/**
 * 这个事为了整合spring的eache和shiro的eache
 * shiro默认是读取classpath:org/apache/shiro/cache/ehcache/ehcache.xml下的ecache
 * @author mawujun email:16064988@qq.com qq:16064988
 *
 */
public class ShiroEhCacheManager extends EhCacheManager {

}
