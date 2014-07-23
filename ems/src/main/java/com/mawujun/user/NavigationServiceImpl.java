package com.mawujun.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("service.nav")
@Transactional
public class NavigationServiceImpl  {
	@Resource(name="mapper.nav")
	private NavigationMapper navigationMapper;
	@Resource(name="mapper.user")
	private UserRepository userMapper;


	public String save(NavNode node) {
		node.setId(UUID.randomUUID().toString());
		if(node.getParentId()==null){
			String repoarCode=navigationMapper.getMaxReportCode(node.getParentId());
			node.setReportCode(ReportCodeHelper.generate3(repoarCode));
			
		} else {
			String repoarCode=navigationMapper.getMaxReportCode(node.getParentId());
			if(repoarCode==null){
				NavNode parent=navigationMapper.get(node.getParentId());
				node.setReportCode(ReportCodeHelper.generateFirstChildCode3(parent.getReportCode()));
			} else {
				node.setReportCode(ReportCodeHelper.generate3(repoarCode));
			}
			
		}
		
		navigationMapper.save(node);
		return node.getId();
	}
	public String update(NavNode node) {
	
		navigationMapper.update(node);
		return node.getId();
	}


	public void delete(String id) {
		// TODO Auto-generated method stub
		navigationMapper.delete(id);
	}
	
	public List<Map<String, Object>> list(String parentId) {
		// TODO Auto-generated method stub
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		return navigationMapper.list(param);
	}
	
	public List<NavNode> loadNaviT(String username,String parentId) {
		// TODO Auto-generated method stub
		
		User user=userMapper.getByUsername(username);
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("user_id", user.getId());
		return navigationMapper.loadNaviT(param);
	}
	public List<NavNode> loadNaviT4admin(String username,String parentId) {
		// TODO Auto-generated method stub

		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		param.put("username", username);
		return navigationMapper.loadNaviT4admin(param);
	}
	
	public void checkedNavigation(String navigation_id,String username) {
		User user=userMapper.getByUsername(username);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("user_id", user.getId());
		navigationMapper.checkedNavigation(param);
	}
	public void unCheckedNavigation(String navigation_id,String username) {
		User user=userMapper.getByUsername(username);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("navigation_id", navigation_id);
		param.put("user_id", user.getId());
		
		navigationMapper.unCheckedNavigation(param);
	}



	public List<NavNode> getLeftNavi(String username) {
		User user=userMapper.getByUsername(username);
		//User user=userMapper.getByUsername(username);
		return navigationMapper.getLeftNavi(user.getId());
	}

	
}
