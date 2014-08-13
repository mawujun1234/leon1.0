package com.mawujun.store;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/order")
public class OrderController {

	@Resource
	private OrderService orderService;


//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/order/query.do")
//	@ResponseBody
//	public List<Order> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Order.parent.id, "root".equals(id)?null:id);
//		List<Order> orderes=orderService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Order.class,M.Order.parent.name());
//		return orderes;
//	}

//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/order/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Order.sampleName, "%"+sampleName+"%");
//		return orderService.queryPage(page);
//	}

	@RequestMapping("/order/query.do")
	@ResponseBody
	public List<Order> query() {	
		List<Order> orderes=orderService.queryAll();
		return orderes;
	}
	

	@RequestMapping("/order/load.do")
	public Order load(String id) {
		return orderService.get(id);
	}
	
	@RequestMapping("/order/create.do")
	@ResponseBody
	public Order create(@RequestBody Order order) {
		orderService.create(order);
		return order;
	}
	
	@RequestMapping("/order/update.do")
	@ResponseBody
	public  Order update(@RequestBody Order order) {
		orderService.update(order);
		return order;
	}
	
	@RequestMapping("/order/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		orderService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/order/destroy.do")
	@ResponseBody
	public Order destroy(@RequestBody Order order) {
		orderService.delete(order);
		return order;
	}
	
	
}
