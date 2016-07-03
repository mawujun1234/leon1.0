package com.mawujun.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UIElementRepository extends IRepository<UIElement, String>{

	public List<UIElement> queryByFunRole(@Param("navigation_id")String navigation_id,@Param("funRole_id")String funRole_id) ;
}
