package com.mawujun.user;
import java.awt.Menu;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UIElementService extends AbstractService<UIElement, String>{

	@Autowired
	private UIElementRepository uIElementRepository;
	@Autowired
	private FunRoleRepository funRoleRepository;
	@Autowired
	private NavigationRepository navigationRepository;
	@Autowired
	private UIElementFunRoleRepository uIElementFunRoleRepository;
	
	@Override
	public UIElementRepository getRepository() {
		return uIElementRepository;
	}

	public List<UIElement> queryByFunRole(String navigation_id,String funRole_id) {
		return uIElementRepository.queryByFunRole(navigation_id, funRole_id);
	}
	public void checkByFunRole(String uIElement_id,String funRole_id) {
		UIElement uIElement=uIElementRepository.get(uIElement_id);
		FunRole funRole=funRoleRepository.get(funRole_id);
		
		UIElementFunRole uIElementFunRole=new UIElementFunRole(uIElement,funRole);
		uIElementFunRoleRepository.create(uIElementFunRole);
		
	}
	public void uncheckByFunRole(String uIElement_id,String funRole_id) {
		UIElement uIElement=uIElementRepository.get(uIElement_id);
		FunRole funRole=funRoleRepository.get(funRole_id);
		
		UIElementFunRole uIElementFunRole=new UIElementFunRole(uIElement,funRole);
		uIElementFunRoleRepository.delete(uIElementFunRole);
	}
	
	@Transactional(readOnly=true)
	public List<String> queryElement(String jsp_url) {
		if(!ShiroUtils.isLogon()){
			return new ArrayList<String>();
		}
		//使用方法hidden:!Permision.canShow('sample_design_create'),
		return  uIElementRepository.queryElement(jsp_url, ShiroUtils.getUserId());
	}
}
