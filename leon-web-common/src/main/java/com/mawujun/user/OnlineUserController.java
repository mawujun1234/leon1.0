package com.mawujun.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.StringUtils;

@Controller
public class OnlineUserController {
	@Autowired
	SessionRegistry sessionRegistry;
	SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping("/onlineUser/query")
	public List<Object> query(String loginName){
		List<Object> onlineUsers=sessionRegistry.getAllPrincipals();
		boolean filterUser=StringUtils.hasText(loginName);
		
		List<Object> result=new ArrayList<Object>();
		for(int i=0;i<onlineUsers.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			UserDetailsImpl userDetail=((UserDetailsImpl)onlineUsers.get(i));
			map.put("loginName", userDetail.getUser().getLoginName());
			map.put("name", userDetail.getUser().getName());
			map.put("lastIp", userDetail.getUser().getLastIp());
			map.put("lastLoginDate", dateformat.format(userDetail.getUser().getLastLoginDate()));
			map.put("onlineSeconds", ((new Date()).getTime()-userDetail.getUser().getLastLoginDate().getTime())/1000);
			
			List<SessionInformation> aaaa = sessionRegistry.getAllSessions(userDetail,false);
			map.put("lastRequest", dateformat.format(aaaa.get(aaaa.size()-1).getLastRequest()));
			map.put("count", aaaa.size());//同个账号登陆的次数
			map.put("sessionId", aaaa.get(0).getSessionId());//取最早的时候的sessionId
			
			if(filterUser){
				if(userDetail.getUser().getLoginName().indexOf(loginName)!=-1){
					result.add(map);
				}
			} else {
				result.add(map);
			}
			
			
		}
		return result;
	}
	/**
	 * 强制注销某个会话
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	@RequestMapping("/onlineUser/forceExpired")
	public String forceExpired(String sessionId){
		SessionInformation info=sessionRegistry.getSessionInformation(sessionId);
		info.expireNow();
		return sessionId;
	}
}
