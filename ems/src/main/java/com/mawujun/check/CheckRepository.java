package com.mawujun.check;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface CheckRepository extends IRepository<Check, String>{

	public void createCheckList(@Param("check_id")String check_id,@Param("ecode")String ecode);

}
