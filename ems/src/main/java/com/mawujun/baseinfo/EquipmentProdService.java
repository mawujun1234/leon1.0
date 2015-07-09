package com.mawujun.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;







import org.springframework.web.bind.annotation.RequestBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.baseinfo.EquipmentProd;
import com.mawujun.baseinfo.EquipmentProdRepository;
import com.mawujun.exception.BusinessException;


/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class EquipmentProdService extends AbstractService<EquipmentProd, String>{

	@Autowired
	private EquipmentProdRepository equipmentProdRepository;
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Override
	public EquipmentProdRepository getRepository() {
		return equipmentProdRepository;
	}
	/**
	 * 增加套件下面的拆分件
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipmentProd
	 * @return
	 */
	public EquipmentProd createProdTJ( EquipmentProd equipmentProd){
		// 合并id
				equipmentProd.setId(equipmentProd.getParent_id()+"-" +equipmentProd.getId());

				Long count = this.queryCount(Cnd.select().andEquals(M.EquipmentProd.id, equipmentProd.getId()));
				if (count > 0) {
					throw new BusinessException("编码已经存在");
				}

				EquipmentProd parent = this.get(equipmentProd.getParent_id());
				if (parent == null || parent.getStatus() == false) {
					throw new BusinessException("品名已经删除,不能再进行套件拆分");
				}

				//改变属性
				equipmentProd.setType(EquipmentProdType.DJ);
				equipmentProd.setSubtype_id(parent.getSubtype_id());
				parent.setType(EquipmentProdType.TJ);
				this.update(parent);

				this.create(equipmentProd);
				return equipmentProd;
	}
	/**
	 * 删除品名
	 */
	@Override
	public void delete(EquipmentProd entity) {
		//先判断是否具有子类型
		Long aa=this.queryCount(Cnd.select().andEquals(M.EquipmentProd.parent_id, entity.getId()));
		if(aa>0){
			throw new BusinessException("该品名是套件或具有零件,不能删除!");
		} 
		
		//判断有没有设备在引用，如果有就接着下一个判断，否则就直接删除
		aa=equipmentRepository.queryCount(Cnd.select().andEquals(M.Equipment.prod_id, entity.getId()));
		if(aa>0){
			this.getRepository().update(Cnd.update().set(M.EquipmentProd.status, false).andEquals(M.EquipmentProd.id, entity.getId()));
			throw new BusinessException("该品名已经被使用,不能删除,只能无效处理!");
		} else {
			entity=this.getRepository().get(entity.getId());
			this.getRepository().delete(entity);
			//只有当套件的配件的才需要判断
			if(StringUtils.hasText(entity.getParent_id())){
				List<EquipmentProd> aaaa=this.getRepository().queryProd_tj_children(entity.getParent_id());
				if(aaaa==null || aaaa.size()==0){
					EquipmentProd parent=this.getRepository().get(entity.getParent_id());
					parent.setType(EquipmentProdType.DJ);
				}
			}
			
			//判断该品名是否属于套件下的某个配件，如果是，就判断父品名是否还有子配件，如果没有就把type改成LJ
		}
//		//this
//		Long count=equipmentRepository.queryCount(Cnd.select().andEquals(M.Equipment.prod_id, entity.getId()).andNotIn(M.Equipment.status, 0,30));
//		if(count>0){
//			throw new BusinessException("还存在使用中的设备在使用该品名,不能取消该品名!");
//		}
//		
//		this.getRepository().update(Cnd.update().set(M.EquipmentProd.status, false).andEquals(M.EquipmentProd.id, entity.getId()));
	}
	
	
	public List<EquipmentProdVO> queryProdGrid(Boolean status,String subtype_id,String parent_id,Boolean all_prod,String style) {
		Map<String,Object> params=new HashMap<String,Object>();
		//当选择从所有品名中过滤的时候，就不用添加子类型了
		if(all_prod==null || all_prod==false){
			params.put(M.EquipmentProd.subtype_id, subtype_id);
		}
		
		if(status!=null){
			params.put(M.EquipmentProd.status, status);
		}
		if(StringUtils.hasText(parent_id) ){
			params.put(M.EquipmentProd.parent_id, parent_id);
		} else {
			//当展开品名的拆分零件的时候，是不要带上过滤条件的
			if(StringUtils.hasText(style)){
				params.put(M.EquipmentProd.style, "%"+style+"%");
			}
		}
		
		
		return this.getRepository().queryProdGrid(params);
	}
	@Override
	public void update(EquipmentProd equipmentProd){
		//先判断该设备是否可以修改
		//如果该品名已经在设备里面已经存在了，就不能修改了
		List<String> styles=this.getRepository().checkProd_used_in_equipment(equipmentProd.getId());
		if(styles.size()>=2){
			throw new BusinessException("该品名的型号，出现了不一致的情况，请联系管理员，先进行修改!");
		}
		if(styles.size()==1){
			if(!styles.get(0).equals(equipmentProd.getStyle())){
				throw new BusinessException("该品名的型号已经被打印并被使用，不能再修改!");
			}
		}
		
		this.getRepository().update(equipmentProd);
	}
//	public List<Brand> queryProdGrid(String subtype_id) {
//		return this.getRepository().queryBrandCombo(subtype_id);
//	}
}
