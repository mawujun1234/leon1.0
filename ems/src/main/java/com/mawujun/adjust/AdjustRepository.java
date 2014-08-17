package com.mawujun.adjust;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repair.RepairVO;
import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.adjust.Adjust;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface AdjustRepository extends IRepository<Adjust, String>{

	public AdjustVO getAdjustVOByEcode(@Param("ecode")String ecode,@Param("store_id")String store_id);
	
	public Page query4InStr(Page page);
	public List<AdjustListVO> query4InStrList(@Param("adjust_id")String adjust_id);
}
