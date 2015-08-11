package com.mawujun.install;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.install.B2INotify;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface B2INotifyRepository extends IRepository<B2INotify, String>{

	public List<B2INotifyVO>  queryAllVO(@Param("user_id")String user_id);
}
