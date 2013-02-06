package com.mawujun.utils.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class ApplicationListenerAAA implements ApplicationListener<ContextRefreshedEvent> {

	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		//event.a
		System.out.println("=====================================");
		//System.setProperty("spring.profiles.active", "production");
	}

}
