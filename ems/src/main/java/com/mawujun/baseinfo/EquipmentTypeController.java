package com.mawujun.baseinfo;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.BeanUtils;
import com.mawujun.utils.M;
import com.mawujun.baseinfo.EquipmentType;
import com.mawujun.baseinfo.EquipmentTypeService;
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
	public List query(String id,Integer levl,Boolean isGrid) {
		List equipmentTypees=null;
		if(levl==null || levl==0){
			equipmentTypees=equipmentTypeService.query(Cnd.select());
		} else if(levl==1){
			if(isGrid==null || isGrid==false){
				id=id.substring(0,id.indexOf('_'));
			}
			Cnd cnd=Cnd.select().andEquals(M.EquipmentSubtype.parent_id, "root".equals(id)?null:id);
			equipmentTypees=equipmentSubtypeService.query(cnd);
			for(int i=0;i<equipmentTypees.size();i++){
				EquipmentSubtype obj=(EquipmentSubtype)equipmentTypees.get(i);
				obj.setLeaf(true);
			}
		} else {
			if(isGrid==null || isGrid==false){
				id=id.substring(0,id.indexOf('_'));
			}
			Cnd cnd=Cnd.select().andEquals(M.EquipmentSubtype.parent_id, "root".equals(id)?null:id);
			equipmentTypees=equipmentProdService.query(cnd);
		}
		
		if(isGrid==null || isGrid==false){
			for(int i=0;i<equipmentTypees.size();i++){
				EquipmentTypeVO obj=(EquipmentTypeVO)equipmentTypees.get(i);
				obj.setId(obj.getId()+"_"+obj.getLevl());
			}
		}


		return equipmentTypees;
	}
	
	@RequestMapping("/equipmentType/update.do")
	@ResponseBody
	public  EquipmentTypeVO update(@RequestBody EquipmentTypeVO equipmentType,Integer levl) {

//		if(equipmentType.getParent_id().indexOf('_')!=-1){
//			equipmentType.setParent_id(equipmentType.getParent_id().substring(0,equipmentType.getParent_id().indexOf('_')));
//		}
		
		if(equipmentType.getLevl()==1){
			if("root".equalsIgnoreCase(equipmentType.getParent_id())){
				equipmentType.setParent_id(null);
			}
			equipmentTypeService.createOrUpdate(BeanUtils.copyOrCast(equipmentType, EquipmentType.class));
		} else if(equipmentType.getLevl()==2){
			equipmentSubtypeService.createOrUpdate(BeanUtils.copyOrCast(equipmentType, EquipmentSubtype.class));
		} else if(equipmentType.getLevl()==3){
			equipmentProdService.createOrUpdate(BeanUtils.copyOrCast(equipmentType, EquipmentProd.class));
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
	public EquipmentTypeVO destroy( EquipmentTypeVO equipmentType) {
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
	
	
}
