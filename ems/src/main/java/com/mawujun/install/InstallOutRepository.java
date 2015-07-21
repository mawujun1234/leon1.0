package com.mawujun.install;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.install.InstallOut;
import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface InstallOutRepository extends IRepository<InstallOut, String>{
	/**
	 * 查询领用单和返库单的数据
	 * @author mawujun 16064988@qq.com 
	 * @param page
	 * @return
	 */
	public Page queryMain(Page page);
	
	public InstallOutVO getInstallOutVO(@Param("installOut_id")String installOut_id);
	public List<InstallOutListVO> queryList(@Param("installOut_id")String installOut_id) ;
	
	public void changeInstallOutListType2installout(@Param("installOut_id")String installOut_id,@Param("installOutListType")InstallOutListType installOutListType,@Param("ecode")String ecode,@Param("pole_id")String pole_id );
	
	public List<InstallOutVO> queryEditInstallOut();
	
	public InstallOutListVO getInstallOutListVOByEcode(@Param("ecode")String ecode,@Param("store_id")String store_id);
}
