package com.mawujun.role;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
@Repository
public interface RoleRepository extends IRepository<Role, String> {
	public List<Map<String,Object>> queryRoleUrl();
	/**
	 * 查找改角色下面权限和选择了的权限
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param page
	 * @return
	 */
	public Page queryFun(Page page);
}
