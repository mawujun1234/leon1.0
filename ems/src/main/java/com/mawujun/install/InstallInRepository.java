package com.mawujun.install;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.install.InstallIn;
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
	
	public EquipmentVO getEquipmentByEcode(@Param("ecode")String ecode,@Param("workunit_id")String workunit_id);
}
