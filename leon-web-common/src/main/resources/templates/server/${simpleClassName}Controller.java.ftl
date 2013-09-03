  
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
import com.mawujun.controller.spring.mvc.JsonConfigHolder;

<#include "/java_copyright.include"/>

@Controller
//@RequestMapping("/${simpleClassNameFirstLower}")
public class ${simpleClassName}Controller {

	@Resource
	private ${simpleClassName}Service ${simpleClassNameFirstLower}Service;

	<#if extenConfig.showModel=="tree">
	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/${simpleClassNameFirstLower}/query.do")
	@ResponseBody
	public List<${simpleClassName}> query(String id) {
		WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
		if("root".equals(id)){
			whereinfo=WhereInfo.parse("parent.id", "is",null);
		}
		List<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.query(whereinfo);
		//${simpleClassNameFirstLower}Service.query(Cnd.select().andEqual("parent.id", id).asc("sort"));
		return ${simpleClassNameFirstLower}es;
	}
	<#elseif extenConfig.showModel=="page">
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
	public QueryResult<${simpleClassName}> query(Integer start,Integer limit,String userName){

		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(false).addLikeWhere("name", userName,MatchMode.ANYWHERE,true);
		QueryResult<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.queryPage(pageRqeust);
		//PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(true).setSqlId("queryUser")//注意这个setSqlId，这个值是xml文件中的值
		//		.addWhere("group_id",groupId).addLikeWhere("name",  userName);
		//QueryResult<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.queryPageMybatis(pageRqeust,${simpleClassName}.class);
		//JsonConfigHolder.setRootName("children");
		//JsonConfigHolder.setFilterPropertys("parents,children,mutex,funes");
		return ${simpleClassNameFirstLower}es;
	}
	<#else>
	@RequestMapping("/${simpleClassNameFirstLower}/query.do")
	@ResponseBody
	public List<${simpleClassName}> query() {	
		List<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.queryAll();
		return ${simpleClassNameFirstLower}es;
	}
	</#if>

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
	
	@RequestMapping("/${simpleClassNameFirstLower}/destroy.do")
	@ResponseBody
	public ${idType} destroy(${idType} id) {
		${simpleClassNameFirstLower}Service.delete(id);
		return id;
	}
	
	@RequestMapping("/${simpleClassNameFirstLower}/destroy.do")
	@ResponseBody
	public ${simpleClassName} destroy(@RequestBody ${simpleClassName} ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.delete(${simpleClassNameFirstLower});
		return ${simpleClassNameFirstLower};
	}
	
	
}
