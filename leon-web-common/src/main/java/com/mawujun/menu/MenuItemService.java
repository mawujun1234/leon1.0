package com.mawujun.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.fun.Fun;
import com.mawujun.repository.BaseRepository;
import com.mawujun.service.BaseService;
import com.mawujun.utils.help.ReportCodeHelper;
import com.mawujun.utils.page.WhereInfo;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MenuItemService extends BaseService<MenuItem, String> {
	
	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public BaseRepository<MenuItem, String> getRepository() {
		// TODO Auto-generated method stub
		return menuItemRepository;
	}
	
	public void create(MenuItem entity) {
		Object reportCode=null;
		if(entity.getParent()!=null){
			WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getParent().getId());
			reportCode=menuItemRepository.queryMax("reportCode",whereinfo);
		}
		//获取父节点的reportcode
		
		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
		entity.setReportCode(newReportCode);
		
		getRepository().create(entity);
	}
	
	public List<Map<String,Object>> query4Desktop(String menuId) {
		//根据mybatis去查询
		Map params=new HashMap();
		params.put("menu_id", menuId);
		return getRepository().queryList("query4Desktop", params);
	}

}
