package com.mawujun.fun;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mawujun.exception.BusinessException;
import com.mawujun.exception.WebCommonExceptionCode3;
import com.mawujun.menu.MenuItemRepository;
import com.mawujun.menu.MenuItemService;
import com.mawujun.repository.BaseRepository;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.service.AbstractService;
import com.mawujun.utils.M;
import com.mawujun.utils.Params;
import com.mawujun.utils.help.ReportCodeHelper;
import com.mawujun.utils.page.WhereInfo;

@Service
public class FunService extends AbstractService<Fun, String> {
//	@Autowired
//	private FunRepository funRepository;
	@Autowired
	private MenuItemService menuItemService;
	
	@Autowired
	private FunRepository funRepository;
	
	@Override
	public FunRepository getRepository() {
		// TODO Auto-generated method stub
		return funRepository;
	}

	@Override
	public void delete(Fun entity) {
//		//判断是否具有子节点
//		//WhereInfo whereinfo=WhereInfo.parse("parent.id", entity.getId());
//		//int childs=this.queryCount(whereinfo);
//		Fun fun=this.get(entity.getId());//Fun缓存了
//		//int childs=fun.getChildren().size();
//		long childs=super.queryCount(Cnd.select().andEquals(M.Fun.parent.id, fun.getId()));
//		if(childs>0){
//			throw new BusinessException("存在子节点，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
//		}
		

		//menuItemService.delete(fun);
		long menuItemCount=menuItemService.queryCount(Cnd.select().andEquals("fun.id", entity.getId()));
		if(menuItemCount>0){
			throw new BusinessException("有菜单挂钩，不能删除。",WebCommonExceptionCode3.EXISTS_CHILDREN);
		}
		
		super.delete(entity);
		
		
	}
	
////	public String getMaxReportCode(String parent_id){
////		//获取父节点的reportcode
////		//WhereInfo whereinfo=WhereInfo.parse("parent.id", parent_id);
////		//Object reportCode=this.queryMax("reportCode",whereinfo);
////		String newReportCode=ReportCodeHelper.generate3((String)reportCode);
////		return newReportCode;
////	}
//	public String create(Fun entity) {
//		Fun parent=entity.getParent()==null?null:this.get(entity.getParent().getId());
//		if(parent!=null && parent.getFunEnum()==FunEnum.fun && !entity.isFun()){
//			throw new BusinessException("功能不能增加模块节点");
//		}
//		entity.setParent(parent);
//		if(parent!=null){
//			parent.setLeaf(false);
//			super.update(parent);
//		}
//		
//		return super.create(entity);
//
//		//return entity;
//	}
	/**
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param entity
	 * @param isUpdateParent 表示更改父节点 暂时注释掉了
	 * @param oldParent_id
	 */
	public void update(Fun entity,Boolean isUpdateParent,String oldParent_id) {	
		super.update(entity);
	}
	/**
	 * 获取所有界面元素要用的功能id
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param entity
	 * @param isUpdateParent
	 * @param oldParent_id
	 */
	public List<String> queryAllDenyPageElement(String userId,String funId) {	
		//return super.queryList("queryAllDenyPageElement", ParamUtils.init().add("user_id", userId).add("parent_id", funId).add("isenable", true), String.class);
		return this.getRepository().queryAllDenyPageElement(Params.init().add("user_id", userId).add("parent_id", funId).add("isenable", true));
	}


//	public void initCache(){
//		List<Fun> funes=super.queryAll();//super.query(whereinfo);
//		for(Fun fun:funes){
//			//if(fun.getFunEnum()==FunEnum.fun){
//			Hibernate.initialize(fun.getParent());
//				//super.initLazyProperty(fun.getChildren());
//			//}	
//		}
//	}
}
