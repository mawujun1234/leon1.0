package com.mawujun.baseinfo;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.user.User;
import com.mawujun.user.UserRepository;
import com.mawujun.user.UserWorkunit;
import com.mawujun.user.UserWorkunitRepository;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class WorkUnitService extends AbstractService<WorkUnit, String>{

	@Autowired
	private WorkUnitRepository workUnitRepository;
	@Autowired
	private PoleRepository poleRepository;
	@Autowired
	private UserWorkunitRepository userWorkunitRepository;
	@Autowired
	private UserRepository userRepository;
	
	private HashMap<String,WorkUnit> workunits_cache=new HashMap<String,WorkUnit>();
	@Override
	public WorkUnit get(String id) {
		WorkUnit workUnit=workunits_cache.get(id);
		if(workUnit==null){
			return workUnitRepository.get(id);
		} else {
			return workUnit;
		}
	}
	
	@Override
	public WorkUnitRepository getRepository() {
		return workUnitRepository;
	}
	
	public List<EquipmentVO> queryEquipments(EquipmentVO equipmentVO,Integer level,Integer start,Integer limit) {
		//return workUnitRepository.queryEquipments(workUnit_id);
		if(level==1){
			//返回作业单位身上所有的设备，顶级
			return workUnitRepository.queryEquipments_total(equipmentVO);
		} else if(level==2 || level==3){
			Page page=new Page();
			page.setStart(start);
			page.setPageSize(limit);
			page.setParams(equipmentVO);
			Page result=workUnitRepository.queryEquipments(page);
			List<EquipmentVO> list= result.getResult();
			
			EquipmentVO total=new EquipmentVO();
			total.setSubtype_id("total");
			total.setSubtype_name("<b>合计:</b>");
			int total_num=0;
			for(EquipmentVO equi_temp:list){
				total_num+=equi_temp.getNum();
			}
			total.setNum(total_num);

			list.add(total);
			return list;
		} else {
			return null;
		}
	}

	public WorkUnit getByLoginName(String loginName){
		return workUnitRepository.queryUnique(Cnd.where().andEquals(M.WorkUnit.loginName, loginName).andEquals(M.WorkUnit.status, true));
	}
	
	/**
	 * mobile上用到的
	 * @author mawujun 16064988@qq.com 
	 * @return
	 */
	public List<EquipmentSubtype> queryHaveEquipmentInfosTotal(String workUnit_id){
		List<EquipmentSubtype> prodes=workUnitRepository.queryHaveEquipmentInfosTotal(workUnit_id);
		return prodes;
	}
	public List<EquipmentVO> queryHaveEquipmentInfosDetail(String workUnit_id,String prod_id) {
		return workUnitRepository.queryHaveEquipmentInfosDetail(workUnit_id, prod_id);
	}
	
	public Page queryPoles(Page page) {
		return workUnitRepository.queryPoles(page);
	}
	
	public void savePoles(String workunit_id,String[] pole_ids) {	
		for(int i=0;i<pole_ids.length;i++){
			workUnitRepository.savePoles(workunit_id, pole_ids[i]);
		}
	}
	public void deletePoles(String workunit_id,String[] pole_ids) {	
		for(int i=0;i<pole_ids.length;i++){
			workUnitRepository.deletePoles(pole_ids[i]);
		}
	}
	
	public List<WorkUnit> queryByUser(String user_id){
		return workUnitRepository.queryByUser(user_id);
	}
	
	public void checkByUser(String workunit_id,String user_id) {
		User user=userRepository.get(user_id);
		WorkUnit area=workUnitRepository.get(workunit_id);
		UserWorkunit areaUser=new UserWorkunit(area,user);
		userWorkunitRepository.create(areaUser);
	}
	
	public void uncheckByUser(String workunit_id,String user_id) {
		User user=userRepository.get(user_id);
		WorkUnit area=workUnitRepository.get(workunit_id);
		UserWorkunit areaUser=new UserWorkunit(area,user);
		userWorkunitRepository.delete(areaUser);
	}
	public List<WorkUnit> queryCombo(String name){
		return workUnitRepository.queryCombo(ShiroUtils.getUserId());
	}
//	@Override
//	public void delete(WorkUnit entity) {
////		// 先判断是否有订单引用他了，如果有就不能删除
////		if(workUnitRepository.queryUsedCountByOrder(entity.getId())>0){
////			throw new BusinessException("作业单位已经被引用,不能删除!");
////		} else {
////			super.delete(entity);
////		}
//		//先判断在片区中是否已经被引用了，如果已经被引用就不能删除
//		
//		//再判断在
//		
//	}
}
