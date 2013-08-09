  
<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
package ${basepackage};
import javax.annotation.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import com.mawujun.page.PageRequest;
import com.mawujun.page.QueryResult;
import com.mawujun.spring.ExtjsJsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

<#include "/java_copyright.include"/>

@Controller
@RequestMapping("/${simpleClassNameFirstLower}")
public class ${simpleClassName}Controller {

	@Resource
	private ${simpleClassName}Service ${simpleClassNameFirstLower}Service;

	@RequestMapping("/query")
	@ResponseBody
	public List<${simpleClassName}> query() {
		//WhereInfo whereinfo=WhereInfo.parse("parent.id", id);
		//if("root".equals(id)){
		//	whereinfo=WhereInfo.parse("parent.id", "is",null);
		//}
		//List<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.query(whereinfo);
		//${simpleClassNameFirstLower}Service.query(Cnd.select().andLike("subjects", subjectType).asc("sort"));
		
		List<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.queryAll();
		return ${simpleClassNameFirstLower}es;
	}
//	/**
//	 * 这是基于分页的几种写法
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/query")
//	@ResponseBody
//	public QueryResult<${simpleClassName}> query(Integer start,Integer limit,String userName){
//
//		PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(false).addLikeWhere("name", userName,MatchMode.ANYWHERE,true);
//		QueryResult<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.queryPage(pageRqeust);
//		//PageRequest pageRqeust=PageRequest.init(start, limit).setSqlModel(true).setSqlId("queryUser")//注意这个setSqlId，这个值是xml文件中的值
//		//		.addWhere("group_id",groupId).addLikeWhere("name",  userName);
//		//QueryResult<${simpleClassName}> ${simpleClassNameFirstLower}es=${simpleClassNameFirstLower}Service.queryPageMybatis(pageRqeust,${simpleClassName}.class);
//		//JsonConfigHolder.setRootName("children");
//		//JsonConfigHolder.setFilterPropertys("parents,children,mutex,funes");
//		return ${simpleClassNameFirstLower}es;
//	}
	
	@RequestMapping("/load")
	public ${simpleClassName} load(${idType} id) {
		return ${simpleClassNameFirstLower}Service.get(id);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public ${simpleClassName} create(@RequestBody ${simpleClassName} ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.create(${simpleClassNameFirstLower});
		return ${simpleClassNameFirstLower};
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public  ${simpleClassName} update(@RequestBody ${simpleClassName} ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.update(${simpleClassNameFirstLower});
		return ${simpleClassNameFirstLower};
	}
	
	@RequestMapping("/destroy")
	@ResponseBody
	public ${idType} destroy(${idType} id) {
		${simpleClassNameFirstLower}Service.delete(id);
		return id;
	}
	
	@RequestMapping("/destroy")
	@ResponseBody
	public ${simpleClassName} destroy(@RequestBody ${simpleClassName} ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.delete(${simpleClassNameFirstLower});
		return ${simpleClassNameFirstLower};
	}
	
	
}
