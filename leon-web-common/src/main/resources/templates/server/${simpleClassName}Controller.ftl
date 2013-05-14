  
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

	@RequestMapping("/queryPage.do")
	@ResponseBody
	public ExtjsJsonResult queryPage(HttpEntity<PageRequest> pageRequest) {
		QueryResult<${simpleClassName}> queryResult=${simpleClassNameFirstLower}Service.queryPage(pageRequest.getBody());
		return ExtjsJsonResult.initResult(queryResult);
	}
	
	@RequestMapping("/get.do")
	public ExtjsJsonResult get(${idType} id) {
		${simpleClassName} ${simpleClassNameFirstLower}=${simpleClassNameFirstLower}Service.get(id);
		return ExtjsJsonResult.initResult(${simpleClassNameFirstLower});
	}
	
	@RequestMapping("/insert.do")
	@ResponseBody
	public ExtjsJsonResult insert(HttpEntity<${simpleClassName}> ${simpleClassNameFirstLower}) {
		${simpleClassNameFirstLower}Service.insert(${simpleClassNameFirstLower}.getBody());
		return ExtjsJsonResult.initResult(${simpleClassNameFirstLower}.getBody());
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public ExtjsJsonResult update(HttpEntity<${simpleClassName}> ${simpleClassNameFirstLower} ) {
		${simpleClassNameFirstLower}Service.update(${simpleClassNameFirstLower}.getBody());
		return ExtjsJsonResult.initResult(${simpleClassNameFirstLower}.getBody());
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public ExtjsJsonResult delete(${idType} id) {
		${simpleClassNameFirstLower}Service.delete(id);
		return ExtjsJsonResult.initResult(id);
	}
	
	/**
	 * 根据删除条件 进行删除
	 */
	@RequestMapping("/deleteByWhereInfos.do")
	@ResponseBody
	public ExtjsJsonResult deleteByWhereInfos(WhereInfo[] whereInfos) {
		int result=${simpleClassNameFirstLower}Service.delete(whereInfos);
		return ExtjsJsonResult.initResult(result);
	}
}
