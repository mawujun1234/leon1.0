package com.mawujun.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.shiro.ShiroUtils;

@Service("navigationService")
@Transactional
public class NavigationService  {
	@Resource(name="navigationRepository")
	private NavigationRepository navigationRepository;
	@Resource(name="userRepository")
	private UserRepository userRepository;


	public String save(Navigation node) {
		node.setId(UUID.randomUUID().toString());
		if(node.getParentId()==null){
			String repoarCode=navigationRepository.getMaxReportCode(node.getParentId());
			node.setReportCode(ReportCodeHelper.generate3(repoarCode));
			
		} else {
			String repoarCode=navigationRepository.getMaxReportCode(node.getParentId());
			if(repoarCode==null){
				Navigation parent=navigationRepository.get(node.getParentId());
				node.setReportCode(ReportCodeHelper.generateFirstChildCode3(parent.getReportCode()));
			} else {
				node.setReportCode(ReportCodeHelper.generate3(repoarCode));
			}
			
		}
		
		navigationRepository.save(node);
		return node.getId();
	}
	public String update(Navigation node) {
	
		navigationRepository.update(node);
		return node.getId();
	}


	public void delete(String id) {
		// TODO Auto-generated method stub
		navigationRepository.delete(id);
	}
	
	public List<Map<String, Object>> list(String parentId) {
		// TODO Auto-generated method stub
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		return navigationRepository.list(param);
	}
	
	public List<Navigation> loadNaviT(String parentId) {
		// TODO Auto-generated method stub
		
		//User user=userRepository.getByUsername(username);
		User user=ShiroUtils.getAuthenticationInfo();
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("user_id", user.getId());
		return navigationRepository.loadNaviT(param);
	}
	public List<Navigation> loadNaviT4admin(String username,String parentId) {
		// TODO Auto-generated method stub

		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		//param.put("username", username);
		return navigationRepository.loadNaviT4admin(param);
	}
	
	public void checkedNavigation(String navigation_id,String username) {
		User user=userRepository.getByUsername(username);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("user_id", user.getId());
		navigationRepository.checkedNavigation(param);
	}
	public void unCheckedNavigation(String navigation_id,String username) {
		User user=userRepository.getByUsername(username);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("user_id", user.getId());
		
		navigationRepository.unCheckedNavigation(param);
	}



	public List<Navigation> getLeftNavi() {
		//User user=userRepository.getByUsername(username);
		User user=ShiroUtils.getAuthenticationInfo();
		return navigationRepository.getLeftNavi(user.getId());
	}

	
}
