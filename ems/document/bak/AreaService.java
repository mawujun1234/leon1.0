package com.mawujun.baseinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.service.AbstractService;
import com.mawujun.user.UserWorkunit;
import com.mawujun.user.UserWorkunitRepository;
import com.mawujun.user.User;
import com.mawujun.user.UserRepository;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
//@Service
//@Transactional(propagation=Propagation.REQUIRED)
public class AreaService extends AbstractService<Area, String>{

	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserWorkunitRepository userAreaRepository;
	
	@Override
	public AreaRepository getRepository() {
		return areaRepository;
	}
	
//	public List<Area> queryAllWithWorkunit() {
//		return this.getRepository().queryAllWithWorkunit();
//	}
	@Override
	public void delete(Area area) {
		//判断是否已经被使用了
		super.delete(area);
	}

//	public Page queryPoles(Page page) {
//		return areaRepository.queryPoles(page);
//	}
	
	public List<PoleVO> queryPolesAndEquipments(String area_id) {
		return areaRepository.queryPolesAndEquipments(area_id);
	}
	
//	public List<Area> queryByUser(String user_id){
//		return areaRepository.queryByUser(user_id);
//	}
//	
//	public void checkByUser(String area_id,String user_id) {
//		User user=userRepository.get(user_id);
//		Area area=areaRepository.get(area_id);
//		UserWorkunit areaUser=new UserWorkunit(area,user);
//		userAreaRepository.create(areaUser);
//	}
//	
//	public void uncheckByUser(String area_id,String user_id) {
//		User user=userRepository.get(user_id);
//		Area area=areaRepository.get(area_id);
//		UserWorkunit areaUser=new UserWorkunit(area,user);
//		userAreaRepository.delete(areaUser);
//	}
}
