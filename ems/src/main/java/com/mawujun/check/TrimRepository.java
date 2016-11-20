package com.mawujun.check;

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
public interface TrimRepository extends IRepository<Trim, String>{

	public List<TrimVO> queryByCheck(@Param("check_id")String check_id);
}
