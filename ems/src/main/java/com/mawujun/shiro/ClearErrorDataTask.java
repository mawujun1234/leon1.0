package com.mawujun.shiro;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("clearErrorDataTask")
@Lazy(value=false)
public class ClearErrorDataTask {
	
//	@Resource(name="mapper.clearErrorDataTask")
//	private ClearErrorDataTaskMapper clearErrorDataTaskMapper;
//	
//	//@Scheduled(cron = "0/30 * * * * ?")
//	@Scheduled(cron = "0 50 23 * * ?")	
//    public void work() {
//		clearErrorDataTaskMapper.clearems_datarole_user1();
//		clearErrorDataTaskMapper.clearems_datarole_user2();
//		clearErrorDataTaskMapper.clearems_funrole_user1();
//		clearErrorDataTaskMapper.clearems_funrole_user2();
//		clearErrorDataTaskMapper.clearems_navigation_funrole1();
//		clearErrorDataTaskMapper.clearems_navigation_funrole2();
//		clearErrorDataTaskMapper.clearems_navigation_user1();
//		clearErrorDataTaskMapper.clearems_navigation_user2();
//    }
}
