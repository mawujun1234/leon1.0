package com.mawujun.menu;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
//public class MenuItemRepository extends BaseRepository<MenuItem, String> {
public interface MenuItemRepository extends IRepository<MenuItem, String> {

	public List<String> query4Desktop(Map params);
	public List<MenuItemVO> queryMenuItem(Map params);

}
