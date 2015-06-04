package com.mawujun.install;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface InstallInRepository extends IRepository<InstallIn, String>{

	/**
	 * 获取保修单的相关信息
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	public InstallIn getInstallInByEcode(@Param("ecode")String ecode);
	
	//public EquipmentVO getEquipmentByEcode(@Param("ecode")String ecode,@Param("workunit_id")String workunit_id);
	public EquipmentVO getEquipmentByEcode(@Param("ecode")String ecode,@Param("workunit_id")String workunit_id);
//	/**
//	 * 查询领用单和返库单的数据
//	 * @author mawujun 16064988@qq.com 
//	 * @param page
//	 * @return
//	 */
//	public Page queryMain_in(Page page);
//	
//	
//	public List<InOutListVO> queryList_in(@Param("inOut_id")String inOut_id) ;
	
	public Page queryMain(Page page);
	public List<InstallInListVO> queryList(@Param("installIn_id")String installIn_id) ;
	
}
