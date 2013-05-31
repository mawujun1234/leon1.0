package com.mawujun.repository.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import com.mawujun.repository.idEntity.IdEntity;

public class HibernateUtils {
	public static Object getIdDirect(IdEntity entity) {
	    if (entity instanceof HibernateProxy) {
	        LazyInitializer lazyInitializer = ((HibernateProxy) entity).getHibernateLazyInitializer();
	        if (lazyInitializer.isUninitialized()) {
	            return  lazyInitializer.getIdentifier();
	        }
	    }
	    return entity.getId();
	}
	
	public static void initLazyProperty(Object proxy) {
		Hibernate.initialize(proxy);
	}
}
