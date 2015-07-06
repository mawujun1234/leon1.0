package com.mawujun.adjust;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface AdjustRepository extends IRepository<Adjust, String>{

	public AdjustListVO getAdjustListVOByEcode(@Param("ecode")String ecode,@Param("store_id")String store_id);
	
	public Page query4InStore(Page page);
	public List<AdjustListVO> query4InStoreList(@Param("adjust_id")String adjust_id);
	
	public List<AdjustList> query_borrow_in_adjustList(@Param("adjust_id")String adjust_id);
	
	public void updateAdjustIsAllReturn(@Param("adjust_id")String adjust_id);

}
