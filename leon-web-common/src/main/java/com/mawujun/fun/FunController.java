package com.mawujun.fun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.user.login.UserDetailsImpl;
import com.mawujun.utils.M;
import com.mawujun.utils.SecurityContextHolder;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;

/**
 * 
 * @author mawujun mawujun1234@163.com
 *
 */
@Controller
public class FunController {
	@Autowired
	private FunService funService;

//	/**
//	 * 桌面程序中把菜单按权限读取出来
//	 * @return
//	 */
//	@RequestMapping("/fun/query")
//	@ResponseBody
//	public List<Fun> query(String id){
////		WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
////		if("root".equals(id)){
////			whereinfo=WhereInfo.parse("parent.id", "is",null);
////		}
//		//List<Fun> funes=funService.query(whereinfo);
//		Cnd cnd=Cnd.select();
//		if("root".equals(id)){
//			cnd.andIsNull(M.Fun.parent.id);
//		} else {
//			cnd.andEquals(M.Fun.parent.id, id);
//		}
//		List<Fun> funes=funService.query(cnd);
//		JsonConfigHolder.setFilterPropertys(M.Fun.parent.name());
//		//JsonConfigHolder.setRootName("children");
//		return funes;
//	}
	/**
	 * 一次性读取出所有的节点数据,构建出了整棵树
	 * @return
	 */
	@RequestMapping("/fun/query")
	@ResponseBody
	public Page query(Integer start,Integer limit){	
		
		Page page=Page.getInstance(start,limit);//.addParam(M.Fun.sampleName, "%"+sampleName+"%");
		return funService.queryPage(page);
		
	}
	@RequestMapping("/fun/load")
	@ResponseBody
	public Fun load(String id){		
		return funService.get(id);
	}
	
	@RequestMapping("/fun/create")
	@ResponseBody
	public Fun create(@RequestBody Fun fun){	
//		if(true){
//			throw new RuntimeException("测试一场日志");
//		}
		funService.createOrUpdate(fun);
		return fun;
	}
	
	@RequestMapping("/fun/update")
	@ResponseBody
	public Fun update(@RequestBody Fun fun){		
		funService.createOrUpdate(fun);
		 return fun;
	}
	
//	@RequestMapping("/fun/update")
//	@ResponseBody
//	public Fun update(@RequestBody Fun fun,Boolean isUpdateParent,String oldParent_id){		
//		 funService.update(fun,isUpdateParent,oldParent_id);
//		 return fun;
//	}
	
	@RequestMapping("/fun/destroy")
	@ResponseBody
	public Fun destroy(@RequestBody Fun fun){		
		funService.delete(fun);
		return fun;
	}
	
	@RequestMapping("/fun/generatorPermissionJs")
	//@ResponseBody
	public ModelAndView generatorPermissionJs(){	
		UserDetailsImpl userDetail=SecurityContextHolder.getUserDetailsImpl();
		ModelAndView mv=new ModelAndView();
		List<String> funIds= funService.queryAllowedFunId(userDetail.getId());
		mv.addObject("funIds", funIds);
		mv.setViewName("/desktop/fun/generatorPermissionJs");
		return mv;
		//return "generatorPermissionJs";	
//		//生成css文件内容
//		StringBuilder builder=new StringBuilder();
//		//所有没有授权过的全国获取过来了，因为默认就是拒绝的
//		for(String elementId:funIds){s
//			builder.append("#"+elementId+"{display:none;}");
//		}
//		
//		JsonConfigHolder.setAutoWrap(false);
//		System.out.println(Thread.currentThread().getId()+"==========================================");
//		return builder.toString();
//		//return "#generator-2c908385412fd0e701412fd93e1d0001{display:none;}";
	}
	
//	/**
//	 * 生成界面元素的css文件，用来控制界面元素的显示和隐藏
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping("/fun/generatorElementCss.css")
//	@ResponseBody
//	public String generatorElementCss(String funId){	
//		UserDetailsImpl userDetail=SecurityContextHolder.getUserDetailsImpl();
//		if(userDetail.isAdmin()){
//			//return "";
//		}
//		
//		if(!StringUtils.hasText(funId)){
//			throw new BusinessException("获取界面元素权限的时候，没有传入父功能id");
//		}
//		
//		List<String> elementIds= funService.queryAllFunId(userDetail.getId(),funId);
//		//生成css文件内容
//		StringBuilder builder=new StringBuilder();
//		//所有没有授权过的全国获取过来了，因为默认就是拒绝的
//		for(String elementId:elementIds){
//			builder.append("#"+elementId+"{display:none;}");
//		}
//		
//		JsonConfigHolder.setAutoWrap(false);
//		System.out.println(Thread.currentThread().getId()+"==========================================");
//		return builder.toString();
//		//return "#generator-2c908385412fd0e701412fd93e1d0001{display:none;}";
//	}
	
	@RequestMapping("/fun/load/{id}")
	@ResponseBody
	public Fun loadByReset(@PathVariable String id){		
		return funService.get(id);
	}
	
	@RequestMapping("/fun/destory/{id}")
	@ResponseBody
	public void destory(@PathVariable String id){		
		funService.deleteById(id);
	}
	
}
