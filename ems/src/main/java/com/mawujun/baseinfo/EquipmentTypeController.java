package com.mawujun.baseinfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
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
	public List query(String id,Boolean status) {
		if(status==null){
			status=true;
		}
		List equipmentTypees=null;
		if(StringUtils.hasText(id) && "root".equals(id)){
			Cnd cnd=Cnd.select();
			if(status){
				cnd.andEquals(M.EquipmentType.status, status);
			}
			equipmentTypees=equipmentTypeService.query(cnd.asc(M.EquipmentType.id));
		} else {
			Cnd cnd=Cnd.select().andEquals(M.EquipmentSubtype.parent_id, id).asc(M.EquipmentType.id);
			if(status){
				cnd.andEquals(M.EquipmentType.status, status);
			}
			equipmentTypees=equipmentSubtypeService.query(cnd);
			for(int i=0;i<equipmentTypees.size();i++){
				EquipmentSubtype obj=(EquipmentSubtype)equipmentTypees.get(i);
				obj.setLeaf(true);
			}
		}
//		if(levl==null || levl==0){
//			Cnd cnd=Cnd.select();
//			if(status){
//				cnd.andEquals(M.EquipmentType.status, status);
//			}
//			equipmentTypees=equipmentTypeService.query(cnd.asc(M.EquipmentType.id));
//		} else if(levl==1){
//			if(isGrid==null || isGrid==false){
//				//id=id.substring(0,id.indexOf('_'));
//			}
//			Cnd cnd=Cnd.select().andEquals(M.EquipmentSubtype.parent_id, "root".equals(id)?null:id).asc(M.EquipmentType.id);
//			if(status){
//				cnd.andEquals(M.EquipmentType.status, status);
//			}
//			equipmentTypees=equipmentSubtypeService.query(cnd);
//			for(int i=0;i<equipmentTypees.size();i++){
//				EquipmentSubtype obj=(EquipmentSubtype)equipmentTypees.get(i);
//				obj.setLeaf(true);
//			}
//		} else {
//
//
//			//equipmentTypees=equipmentProdService.queryProdGrid(status,id);
//		}


		
		return equipmentTypees;
	}
	/**
	 * Ems.baseinfo.ProdQueryGrid 这个js类中也用到了
	 * @author mawujun 16064988@qq.com 
	 * @param subtype_id
	 * @param parent_id
	 * @param status
	 * @param prod_name
	 * @param all_prod 是否是过滤所有的品名，true 所有的品名中过滤，false，只从当前的品名中过滤
	 * @return
	 */
	@RequestMapping("/equipmentType/queryProds.do")
	@ResponseBody
	public List<EquipmentProdVO> queryProds(String subtype_id,Boolean status,String style,Boolean all_prod) {
		//当第一次打开设备设备类型的页面的时候
		if(!StringUtils.hasText(subtype_id)){
			return new ArrayList<EquipmentProdVO>();
		}
//		if("root".equals(parent_id)){
//			//return new ArrayList<EquipmentProdVO>();
//			parent_id=null;
//		}
		return equipmentProdService.queryProdGrid(status,subtype_id,all_prod,style);
	}
	
	@RequestMapping("/equipmentType/create.do")
	@ResponseBody
	public  EquipmentTypeAbstract create(@RequestBody EquipmentTypeAbstract equipmentType) {
		equipmentType.setId(equipmentType.getParent_id()+equipmentType.getId());
		if(!StringUtils.hasText(equipmentType.getParent_id())){
			equipmentType.setParent_id(null);
			Long count=equipmentTypeService.queryCount(Cnd.select().andEquals(M.EquipmentType.id, equipmentType.getId()));
			if(count>0){
				throw new BusinessException("编码已经存在");
			}
			equipmentTypeService.create(BeanUtils.copyOrCast(equipmentType, EquipmentType.class));
		} else{
			Long count=equipmentSubtypeService.queryCount(Cnd.select().andEquals(M.EquipmentSubtype.id, equipmentType.getId()));
			if(count>0){
				throw new BusinessException("编码已经存在");
			}
			
			EquipmentType parent=equipmentTypeService.get(equipmentType.getParent_id());
			if(parent==null || parent.getStatus()==false){
				throw new BusinessException("大类已经删除,不能再添加小类");
			}
			
			equipmentSubtypeService.create(BeanUtils.copyOrCast(equipmentType, EquipmentSubtype.class));
		}

		return equipmentType;
	}
	
	public boolean have_same_style(String subtype_id,String style){
		if("/".equals(style)){
			return false;
		}
		Long count=equipmentProdService.queryCount(Cnd.count().andEquals(M.EquipmentProd.subtype_id, subtype_id).andEquals(M.EquipmentProd.style, style));
		if(count>0){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 增加品名和增加套件
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param equipmentProd
	 * @param levl
	 * @return
	 */
	@RequestMapping("/equipmentType/createProd.do")
	@ResponseBody
	public  EquipmentProd createProd(@RequestBody EquipmentProd equipmentProd) {
		if(have_same_style(equipmentProd.getSubtype_id(),equipmentProd.getStyle())){
			throw new BusinessException("该型号已经存在，请修改!");
		}
		//
		if(equipmentProd.getId_suffix()==null || "".equals(equipmentProd.getId_suffix().trim())){
			//在OrderService.insertBarcode中会自动补全-**,达到ecode长度一致
			equipmentProd.setId(equipmentProd.getSubtype_id()+equipmentProd.getId());
		}  else {	
			if("**".equals(equipmentProd.getId_suffix())){
				throw new BusinessException("不能使用**作为二级编码");
			}
			//合并id,注意这里的-千万不能删除，因为为了效率考虑已经有地方已经通过拆分ecode来获取品名编码了，和品名相关数据了
			equipmentProd.setId(equipmentProd.getSubtype_id()+equipmentProd.getId()+"-"+equipmentProd.getId_suffix().trim());
		}
				
		Long count=equipmentProdService.queryCount(Cnd.select().andEquals(M.EquipmentProd.id, equipmentProd.getId()));
		if(count>0){
			throw new BusinessException("编码已经存在");
		}
		
		EquipmentTypeAbstract parent=equipmentSubtypeService.get(equipmentProd.getSubtype_id());
		if(parent==null || parent.getStatus()==false){
			throw new BusinessException("小类已经删除,不能再添加品名");
		}
		
		equipmentProdService.create(equipmentProd);
		return equipmentProd;
	}
	
//	@RequestMapping("/equipmentType/createProdTJ.do")
//	@ResponseBody
//	public EquipmentProd createProdTJ(@RequestBody EquipmentProd equipmentProd) {
//		return equipmentProdService.createProdTJ(equipmentProd);
//	}
	
	@RequestMapping("/equipmentType/update.do")
	@ResponseBody
	public  EquipmentTypeAbstract update(@RequestBody EquipmentTypeAbstract equipmentType) {
		if(!StringUtils.hasText(equipmentType.getParent_id())){
			equipmentType.setParent_id(null);
	
			equipmentTypeService.update(BeanUtils.copyOrCast(equipmentType, EquipmentType.class));
		} else {
			equipmentSubtypeService.update(BeanUtils.copyOrCast(equipmentType, EquipmentSubtype.class));
		} 
		
		return equipmentType;
	}
	@RequestMapping("/equipmentType/updateProd.do")
	@ResponseBody
	public  EquipmentProd updateProd(@RequestBody EquipmentProd equipmentProd) {
		if(have_same_style(equipmentProd.getSubtype_id(),equipmentProd.getStyle())){
			throw new BusinessException("该型号已经存在，请修改!");
		}
		equipmentProdService.update(equipmentProd);
		return equipmentProd;
	}
	
	
	@RequestMapping("/equipmentType/destroy.do")
	@ResponseBody
	public EquipmentTypeAbstract destroy( EquipmentTypeAbstract equipmentType) {
		//equipmentType.setId(equipmentType.getId().substring(0,equipmentType.getId().indexOf('_')));
		if(!StringUtils.hasText(equipmentType.getParent_id())){
			equipmentTypeService.delete(BeanUtils.copyOrCast(equipmentType, EquipmentType.class));
		} else {
			equipmentSubtypeService.delete(BeanUtils.copyOrCast(equipmentType, EquipmentSubtype.class));
		} 
		return equipmentType;
	}
	@RequestMapping("/equipmentType/destroyProd.do")
	@ResponseBody
	public EquipmentProd destroyProd(EquipmentProd equipmentProd) {
		//equipmentType.setId(equipmentType.getId().substring(0,equipmentType.getId().indexOf('_')));
		//这里还要写上删除的业务逻辑，还有绑定数据好像还有点问题
		equipmentProdService.delete(equipmentProd);
		return equipmentProd;
	}
	
	@RequestMapping("/equipmentType/queryTypeCombo.do")
	@ResponseBody
	public List<EquipmentType> queryTypeCombo(String name,Boolean containAll) {
		Cnd  cnd=Cnd.select().andEquals(M.EquipmentType.status, true);
		
		if(StringUtils.hasText(name)){
			cnd.andLike(M.EquipmentType.name, name);
		}
		List<EquipmentType> list= equipmentTypeService.query(cnd);	
		if(containAll!=null && containAll){
			EquipmentType all=new EquipmentType();
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
	@RequestMapping("/equipmentType/querySubtypeCombo.do")
	@ResponseBody
	public List<EquipmentSubtype> querySubtype(String equipmentType_id,String name,Boolean containAll) {
		Cnd cnd=Cnd.select().andEquals(M.EquipmentSubtype.status, true);
		if(StringUtils.hasText(equipmentType_id)){
			cnd.andEquals(M.EquipmentSubtype.parent_id, equipmentType_id);
		}
		if(StringUtils.hasText(name)){
			cnd.andLike(M.EquipmentSubtype.name, name);
		}
		List<EquipmentSubtype> list= equipmentSubtypeService.query(cnd);	
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
	public List<EquipmentProd> queryProdCombo(String equipmentSubtype_id,String name,Boolean containAll) {
		if(!StringUtils.hasText(equipmentSubtype_id)){
			return new ArrayList<EquipmentProd>();
		}
		
		List<EquipmentProd> list= equipmentProdService.query(Cnd.select().andEquals(M.EquipmentProd.status, true).andLike(M.EquipmentProd.name, name).andEquals(M.EquipmentProd.subtype_id, equipmentSubtype_id));	
		if(containAll!=null && containAll){
			EquipmentProd all=new EquipmentProd();
			all.setId("");
			all.setName("所有");
			list.add(0, all);
		}
		return list;
	}
	
//	/**
//	 * 用于combobox
//	 * @author mawujun email:160649888@163.com qq:16064988
//	 * @param name
//	 * @return
//	 */
//	@RequestMapping("/equipmentType/queryBrandCombo.do")
//	@ResponseBody
//	public List<Brand> queryBrandCombo(String prod_id) {
//		
//		return equipmentProdService.queryBrandCombo(prod_id);
//	}
//	
//	@RequestMapping("/equipmentType/queryProdGrid.do")
//	@ResponseBody
//	public List<EquipmentProdVO> queryProdGrid(String subtype_id) {
//		List<EquipmentProdVO> equipmentTypees=equipmentProdService.queryProdGrid(true,subtype_id);
//		return equipmentTypees;
//	}
}
