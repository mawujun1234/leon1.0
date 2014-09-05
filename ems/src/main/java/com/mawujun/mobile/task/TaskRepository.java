package com.mawujun.mobile.task;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.mobile.task.Task;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface TaskRepository extends IRepository<Task, String>{
	public Page queryPoles(Page page);
	
	public List<EquipmentVO> queryTaskEquipmentInfos(@Param("task_id")String task_id);
}
