package com.mawujun.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.repository1.IRepository;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
public class SwitchUserService extends AbstractService<SwitchUser, String>{
	@Autowired
	private SwitchUserRepository switchUserRepository;
	@Autowired
	UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	public Page querySwitchUsers(Integer start,Integer limit,String userName,String masterId){
		User master=userService.get(masterId);
		//PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(false).addLikeWhere("name", userName,MatchMode.ANYWHERE,true);
		//
		if(master.isAdmin()){	
			Page page=Page.getInstance(start,limit).addParam("name", "%"+userName+"%");
			return  userService.queryPage(page);
		} else {
			//pageRqeust.addWhere("masterId",masterId).setSqlId("querySwitchUsers").setSqlModel(true);
			Page page=Page.getInstance(start,limit).addParam("masterId", masterId);
			return userRepository.querySwitchUsers(page);
		}
		
//		User master=userService.get(masterId);
//		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(false).addLikeWhere("name", userName,MatchMode.ANYWHERE,true);
//		if(master.isAdmin()){	
//			return  userService.queryPage(pageRqeust);
//		} else {
//			pageRqeust.addWhere("masterId",masterId).setSqlId("querySwitchUsers").setSqlModel(true);
//			return userService.queryPageMybatis(pageRqeust);
//		}
	}
	@Override
	public IRepository<SwitchUser, String> getRepository() {
		// TODO Auto-generated method stub
		return switchUserRepository;
	}
	

}
