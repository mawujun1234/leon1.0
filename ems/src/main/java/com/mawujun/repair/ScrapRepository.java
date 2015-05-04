package com.mawujun.repair;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.Params;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ScrapRepository extends IRepository<Scrap, String>{

	public Page queryScrapReport(Page page);
	public List<RepairVO> queryScrapReport(Params params);
}
