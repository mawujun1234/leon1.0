package com.mawujun.desktop;

import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;

@Repository
public interface DesktopConfigRepository extends
		IRepository<DesktopConfig, String> {

}
