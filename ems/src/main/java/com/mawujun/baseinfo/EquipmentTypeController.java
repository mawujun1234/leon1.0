package com.mawujun.baseinfo;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
public class EquipmentTypeController {

	@Resource
	private EquipmentTypeService equipmentTypeService;
	@Resource
	private EquipmentSubtypeService equipmentSubtypeService;
	@Resource
	private EquipmentProdService equipmentProdService;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/equipmentType/query.do")
	@ResponseBody
	public List query(String id,Integer levl,Boolean isGrid,Boolean status) {
		if(status==null){
			status=true;
		}
		List equipmentTypees=null;
		if(levl==null || levl==0){
			Cnd cnd=Cnd.select();
			if(status){
				cnd.andEquals(M.EquipmentType.status, status);
			}
			equipmentTypees=equipmentTypeService.query(cnd);
		} else if(levl==1){
			if(isGrid==null || isGrid==false){
				//id=id.substring(0,id.indexOf('_'));
			}
			Cnd cnd=Cnd.select().andEquals(M.EquipmentSubtype.parent_id, "root".equals(id)?null:id);
			if(status){
				cnd.andEquals(M.EquipmentType.status, status);
			}
			equipmentTypees=equipmentSubtypeService.query(cnd);
			for(int i=0;i<equipmentTypees.size();i++){
				EquipmentSubtype obj=(EquipmentSubtype)equipmentTypees.get(i);
				obj.setLeaf(true);
			}
		} else {
			if(isGrid==null || isGrid==false){
				//id=id.substring(0,id.indexOf('_'));
			}
			Cnd cnd=Cnd.select().andEquals(M.EquipmentSubtype.parent_id, "root".equals(id)?null:id);
			if(status){
				cnd.andEquals(M.EquipmentType.status, status);
			}
			equipmentTypees=equipmentProdService.query(cnd);
		}
//		//防止编码重复
//		if(isGrid==null || isGrid==false){
//			for(int i=0;i<equipmentTypees.size();i++){
//				EquipmentTypeAbstract obj=(EquipmentTypeAbstract)equipmentTypees.get(i);
//				//为树上的节点带上编号
//				//obj.setText(obj.getText()+"("+obj.getId()+")");
//				//obj.setId(obj.getId()+"_"+obj.getLevl());
//			}
//		} 


		return equipmentTypees;
	}
	
	@RequestMapping("/equipmentType/create.do")
	@ResponseBody
	public  EquipmentTypeAbstract create(@RequestBody EquipmentTypeAbstract equipmentType,Integer levl) {
		
		if(equipmentType.getLevl()==1){
			if("root".equalsIgnoreCase(equipmentType.getParent_id())){
				equipmentType.setParent_id(null);
			}
			Long count=equipmentTypeService.queryCount(Cnd.select().andEquals(M.EquipmentType.id, equipmentType.getId()));
			if(count>0){
				throw new BusinessException("编码已经存在");
			}
			equipmentTypeService.create(BeanUtils.copyOrCast(equipmentType, EquipmentType.class));
		} else if(equipmentType.getLevl()==2){
			Long count=equipmentSubtypeService.queryCount(Cnd.select().andEquals(M.EquipmentSubtype.id, equipmentType.getId()));
			if(count>0){
				throw new BusinessException("编码已经存在");
			}
			
			EquipmentTypeAbstract aa=equipmentTypeService.get(equipmentType.getParent_id());
			if(aa==null || aa.getStatus()==false){
				throw new BusinessException("大类已经删除,不能再添加小类");
			}
			
			equipmentSubtypeService.create(BeanUtils.copyOrCast(equipmentType, EquipmentSubtype.class));
		} else if(equipmentType.getLevl()==3){
			Long count=equipmentProdService.queryCount(Cnd.select().andEquals(M.EquipmentProd.id, equipmentType.getId()));
			if(count>0){
				throw new BusinessException("编码已经存在");
			}
			
			EquipmentTypeAbstract aa=equipmentSubtypeService.get(equipmentType.getParent_id());
			if(aa==null || aa.getStatus()==false){
				throw new BusinessException("小类已经删除,不能再添加品名");
			}
			
			equipmentProdService.create(BeanUtils.copyOrCast(equipmentType, EquipmentProd.class));
		}
		return equipmentType;
	}
	
	@RequestMapping("/equipmentType/update.do")
	@ResponseBody
	public  EquipmentTypeAbstract update(@RequestBody EquipmentTypeAbstract equipmentType,Integer levl) {
		
		if(equipmentType.getLevl()==1){
			if("root".equalsIgnoreCase(equipmentType.getParent_id())){
				equipmentType.setParent_id(null);
			}

			equipmentTypeService.update(BeanUtils.copyOrCast(equipmentType, EquipmentType.class));
		} else if(equipmentType.getLevl()==2){
			equipmentSubtypeService.update(BeanUtils.copyOrCast(equipmentType, EquipmentSubtype.class));
		} else if(equipmentType.getLevl()==3){
			equipmentProdService.update(BeanUtils.copyOrCast(equipmentType, EquipmentProd.class));
		}
		return equipmentType;
	}
	
//	@RequestMapping("/equipmentType/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id,Integer levl) {
//		
//		if(levl==1){
//			equipmentTypeService.deleteById(id);
//		} else if(levl==2){
//			equipmentSubtypeService.deleteById(id);
//		} else if(levl==3){
//			equipmentProdService.deleteById(id);
//		}
//		return id;
//	}
	
	@RequestMapping("/equipmentType/destroy.do")
	@ResponseBody
	public EquipmentTypeAbstract destroy( EquipmentTypeAbstract equipmentType) {
		//equipmentType.setId(equipmentType.getId().substring(0,equipmentType.getId().indexOf('_')));
		if(equipmentType.getLevl()==1){
			equipmentTypeService.delete(BeanUtils.copyOrCast(equipmentType, EquipmentType.class));
		} else if(equipmentType.getLevl()==2){
			equipmentSubtypeService.delete(BeanUtils.copyOrCast(equipmentType, EquipmentSubtype.class));
		} else if(equipmentType.getLevl()==3){
			equipmentProdService.delete(BeanUtils.copyOrCast(equipmentType, EquipmentProd.class));
		}
		return equipmentType;
	}
	
	/**
	 * 用于combobox
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param name
	 * @return
	 */
	@RequestMapping("/equipmentType/querySubtypeCombo.do")
	@ResponseBody
	public List<EquipmentSubtype> querySubtype(String equipmentType_id,String name,Boolean containAll) {
		List<EquipmentSubtype> list= equipmentSubtypeService.query(Cnd.select().andEquals(M.EquipmentSubtype.status, true).andLike(M.EquipmentSubtype.name, name));	
		if(containAll!=null && containAll){
			EquipmentSubtype all=new EquipmentSubtype();
			all.setId("");
			all.setName("所有");
			list.add(0, all);
		}
		return list;
	}
	/**
	 * 用于combobox
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param name
	 * @return
	 */
	@RequestMapping("/equipmentType/queryProdCombo.do")
	@ResponseBody
	public List<EquipmentProd> queryProd(String equipmentSubtype_id,String name,Boolean containAll) {
		if(!StringUtils.hasText(equipmentSubtype_id)){
			return new ArrayList<EquipmentProd>();
		}
		
		List<EquipmentProd> list= equipmentProdService.query(Cnd.select().andEquals(M.EquipmentProd.status, true).andLike(M.EquipmentProd.name, name).andEquals(M.EquipmentProd.parent_id, equipmentSubtype_id));	
		if(containAll!=null && containAll){
			EquipmentProd all=new EquipmentProd();
			all.setId("");
			all.setName("所有");
			list.add(0, all);
		}
		return list;
	}
	
	
}
