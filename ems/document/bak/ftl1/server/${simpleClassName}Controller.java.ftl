  
<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
package ${basepackage};
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
import com.mawujun.utils.M;

import ${basepackage}.${simpleClassName};
import ${basepackage}.${simpleClassName}Service;
<#include "/java_copyright.include"/>

@Controller
//@RequestMapping("/${simpleClassNameFirstLower}")
public class ${simpleClassName}Controller {

	@Resource
	private ${simpleClassName}Service ${simpleClassNameFirstLower}Service;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/${simpleClassNameFirstLower}/query.do")
	@ResponseBody
	public List<${simpleClassName}> query(String id) {
		Cnd cnd=Cnd.select().andEquals(M.${simpleClassName}.parent.id, "root".equals(id)?null:id);
		List<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.query(cnd);
		//JsonConfigHolder.setFilterPropertys(${simpleClassName}.class,M.${simpleClassName}.parent.name());
		return ${simpleClassNameFirstLower}es;
	}

	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/${simpleClassNameFirstLower}/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String sampleName){
		Page page=Page.getInstance(start,limit);//.addParam(M.${simpleClassName}.sampleName, "%"+sampleName+"%");
		return ${simpleClassNameFirstLower}Service.queryPage(page);
	}

	@RequestMapping("/${simpleClassNameFirstLower}/query.do")
	@ResponseBody
	public List<${simpleClassName}> query() {	
		List<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.queryAll();
		return ${simpleClassNameFirstLower}es;
	}
	

	@RequestMapping("/${simpleClassNameFirstLower}/load.do")
	public ${simpleClassName} load(${idType} id) {
		return ${simpleClassNameFirstLower}Service.get(id);
	}
	
	@RequestMapping("/${simpleClassNameFirstLower}/create.do")
	@ResponseBody
	public ${simpleClassName} create(@RequestBody ${simpleClassName} ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.create(${simpleClassNameFirstLower});
		return ${simpleClassNameFirstLower};
	}
	
	@RequestMapping("/${simpleClassNameFirstLower}/update.do")
	@ResponseBody
	public  ${simpleClassName} update(@RequestBody ${simpleClassName} ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.update(${simpleClassNameFirstLower});
		return ${simpleClassNameFirstLower};
	}
	
	@RequestMapping("/${simpleClassNameFirstLower}/deleteById.do")
	@ResponseBody
	public ${idType} deleteById(${idType} id) {
		${simpleClassNameFirstLower}Service.deleteById(id);
		return id;
	}
	
	@RequestMapping("/${simpleClassNameFirstLower}/destroy.do")
	@ResponseBody
	public ${simpleClassName} destroy(@RequestBody ${simpleClassName} ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.delete(${simpleClassNameFirstLower});
		return ${simpleClassNameFirstLower};
	}
	
	
}
