package com.mawujun.check;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/check")
public class CheckController {

	@Resource
	private CheckService checkService;


	@RequestMapping("/check/queryPager.do")
	@ResponseBody
	public Page queryPager(Integer start,Integer limit,CheckStatus status) {	
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Check.status, status);
		
		Page checkes=checkService.queryPage(page);
		return checkes;
	}
	

//	@RequestMapping("/check/load.do")
//	public Check load(String id) {
//		return checkService.get(id);
//	}
//	
//	@RequestMapping("/check/create.do")
//	@ResponseBody
//	public Check create(@RequestBody Check check) {
//		checkService.create(check);
//		return check;
//	}
//	
//	@RequestMapping("/check/update.do")
//	@ResponseBody
//	public  Check update(@RequestBody Check check) {
//		checkService.update(check);
//		return check;
//	}
//	
//	@RequestMapping("/check/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		checkService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/check/destroy.do")
//	@ResponseBody
//	public Check destroy(@RequestBody Check check) {
//		checkService.delete(check);
//		return check;
//	}
	
	
}
