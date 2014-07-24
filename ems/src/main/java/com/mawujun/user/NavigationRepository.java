package com.mawujun.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface NavigationRepository extends IRepository<Navigation, String>{
	public Navigation get(String id);
	public String getMaxReportCode(@Param("parentId") String parentId);
	/**
	 * 新增头顶的菜单
	 * @return
	 */
	public void save(Navigation node);
	public void update(Navigation node);
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
	public List<Navigation> loadNaviT(Map<String,Object> param);
	public List<Navigation> loadNaviT4admin(Map<String,Object> param);
	public void checkedNavigation(Map<String,Object> param);
	public void unCheckedNavigation(Map<String,Object> param);
	
	
	public List<Navigation> getLeftNavi(String user_id);
}
