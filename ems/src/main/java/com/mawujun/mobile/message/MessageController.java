package com.mawujun.mobile.message;
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

import com.mawujun.mobile.message.Message;
import com.mawujun.mobile.message.MessageService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/message")
public class MessageController {

	@Resource
	private MessageService messageService;

//
//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/message/query.do")
//	@ResponseBody
//	public List<Message> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Message.parent.id, "root".equals(id)?null:id);
//		List<Message> messagees=messageService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Message.class,M.Message.parent.name());
//		return messagees;
//	}
//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/message/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Message.sampleName, "%"+sampleName+"%");
//		return messageService.queryPage(page);
//	}
//
//	@RequestMapping("/message/query.do")
//	@ResponseBody
//	public List<Message> query() {	
//		List<Message> messagees=messageService.queryAll();
//		return messagees;
//	}
//	
//
//	@RequestMapping("/message/load.do")
//	public Message load(String id) {
//		return messageService.get(id);
//	}
//	
//	@RequestMapping("/message/create.do")
//	@ResponseBody
//	public Message create(@RequestBody Message message) {
//		messageService.create(message);
//		return message;
//	}
//	
//	@RequestMapping("/message/update.do")
//	@ResponseBody
//	public  Message update(@RequestBody Message message) {
//		messageService.update(message);
//		return message;
//	}
//	
//	@RequestMapping("/message/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		messageService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/message/destroy.do")
//	@ResponseBody
//	public Message destroy(@RequestBody Message message) {
//		messageService.delete(message);
//		return message;
//	}
	
	
}
