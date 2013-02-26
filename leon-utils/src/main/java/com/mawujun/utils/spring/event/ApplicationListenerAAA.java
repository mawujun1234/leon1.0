package com.mawujun.utils.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;



public class ApplicationListenerAAA implements ApplicationListener<ContextStartedEvent> {

	public void onApplicationEvent(ContextStartedEvent event) {
		// TODO Auto-generated method stub
		//event.a
		System.out.println("=====================================");
		System.setProperty("spring.profiles.active", "production");
	}

}
