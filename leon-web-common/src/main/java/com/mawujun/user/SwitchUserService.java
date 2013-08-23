package com.mawujun.user;

import com.mawujun.repository.BaseRepository;
import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.page.MatchMode;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
public class SwitchUserService extends BaseRepository<SwitchUser, String>{
	@Autowired
	UserService userService;
	public QueryResult<User> querySwitchUsers(Integer start,Integer limit,String userName,String masterId){
		User master=userService.get(masterId);
		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(false).addLikeWhere("name", userName,MatchMode.ANYWHERE,true);
		if(master.isAdmin()){	
			return  userService.queryPage(pageRqeust);
		} else {
			pageRqeust.addWhere("masterId",masterId).setSqlId("querySwitchUsers").setSqlModel(true);
			return userService.queryPageMybatis(pageRqeust);
		}
	}
	

}
