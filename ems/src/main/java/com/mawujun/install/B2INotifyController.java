package com.mawujun.install;
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

import com.mawujun.install.B2INotify;
import com.mawujun.install.B2INotifyService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/b2INotify")
public class B2INotifyController {

	@Resource
	private B2INotifyService b2INotifyService;

//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/b2INotify/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.B2INotify.sampleName, "%"+sampleName+"%");
//		return b2INotifyService.queryPage(page);
//	}

	@RequestMapping("/b2INotify/query.do")
	@ResponseBody
	public List<B2INotifyVO> query() {	
		List<B2INotifyVO> b2INotifyes=b2INotifyService.queryAllVO();
		return b2INotifyes;
	}
	

	

//	@RequestMapping("/b2INotify/load.do")
//	public B2INotify load(String id) {
//		return b2INotifyService.get(id);
//	}
//	
//	@RequestMapping("/b2INotify/create.do")
//	@ResponseBody
//	public B2INotify create(@RequestBody B2INotify b2INotify) {
//		b2INotifyService.create(b2INotify);
//		return b2INotify;
//	}
//	
//	@RequestMapping("/b2INotify/update.do")
//	@ResponseBody
//	public  B2INotify update(@RequestBody B2INotify b2INotify) {
//		b2INotifyService.update(b2INotify);
//		return b2INotify;
//	}
//	
//	@RequestMapping("/b2INotify/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		b2INotifyService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/b2INotify/destroy.do")
//	@ResponseBody
//	public B2INotify destroy(@RequestBody B2INotify b2INotify) {
//		b2INotifyService.delete(b2INotify);
//		return b2INotify;
//	}
//	
	
}
