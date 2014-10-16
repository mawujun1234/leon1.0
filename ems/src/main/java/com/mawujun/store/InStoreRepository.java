package com.mawujun.store;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.store.InStore;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface InStoreRepository extends IRepository<InStore, String>{

	public List<InStoreListVO> queryList(@Param("inStore_id")String inStore_id);
}
