package com.mawujun.mobile.task;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mawujun.repository1.IRepository;
import com.mawujun.utils.page.Page;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.Pole;
import com.mawujun.mobile.task.Task;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface TaskRepository extends IRepository<Task, String>{
	public String queryMax_id(@Param("createDate")String createDate);
	public Page queryPoles(Page page);
	public int count_task_quip_status(@Param("task_id")String task_id);
	/**
	 * 返回已经入库的条码
	 * @author mawujun 16064988@qq.com 
	 * @param task_id
	 * @param ecodes
	 * @return
	 */
	public List<String> count_task_quip_status1(@Param("task_id")String task_id,@Param("ecodes")List<String> ecodes);
	public List<String> query_task_equip_list(@Param("task_id")String task_id);
	/**
	 * 根据条码查询最新的任务信息
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param page
	 * @return
	 */
	public Task queryMaxId_ecode(@Param("ecode")String ecode);
	
	public Page mobile_queryPage(Page page) ;
	public List<EquipmentVO> mobile_queryTaskEquipmentInfos(@Param("task_id")String task_id);
	
	public List<Task> queryReadOvertimeTask(@Param("read")Integer read) ;
	public List<Task> queryHandlingOvertimeTask(@Param("handling")Integer handling) ;
	
	
	public List<Pole> mobile_queryPoles(@Param("pole_name")String pole_name,@Param("workunit_id")String workunit_id);
}
