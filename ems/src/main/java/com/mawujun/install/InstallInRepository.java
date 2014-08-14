package com.mawujun.install;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.install.InstallIn;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface InstallInRepository extends IRepository<InstallIn, String>{

	public InstallIn getInstallInByEcode(@Param("ecode")String ecode);
}