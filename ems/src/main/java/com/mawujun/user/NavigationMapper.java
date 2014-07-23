package com.mawujun.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mawujun.repository1.IRepository;

public interface NavigationMapper extends IRepository<NavNode, String>{
	public NavNode get(String id);
	public String getMaxReportCode(@Param("parentId") String parentId);
	/**
	 * 新增头顶的菜单
	 * @return
	 */
	public void save(NavNode node);
	public void update(NavNode node);
	/**
	 * 删除头顶的菜单
	 * @return
	 */
	public void delete(String id);
	public List<Map<String,Object>> list(Map<String,Object> param);
	/**
	 * 获取头顶的菜单
	 * @return
	 */
	public List<NavNode> loadNaviT(Map<String,Object> param);
	public List<NavNode> loadNaviT4admin(Map<String,Object> param);
	public void checkedNavigation(Map<String,Object> param);
	public void unCheckedNavigation(Map<String,Object> param);
	
	
	public List<NavNode> getLeftNavi(String user_id);
}
