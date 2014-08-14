package com.mawujun.store;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
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
	public List<OrderVO> query(String orderNo) {	
		List<OrderVO> orderes=orderService.query(orderNo);
		return orderes;
	}
	

	@RequestMapping("/order/load.do")
	public Order load(String id) {
		return orderService.get(id);
	}
	
//	@RequestMapping("/order/create.do")
//	@ResponseBody
//	public Order create(@RequestBody Order order) {
//		orderService.create(order);
//		return order;
//	}
	
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
	
	@RequestMapping("/order/create.do")
	@ResponseBody
	public String export(@RequestBody Order[] orderes) throws  IOException{
		Long count=orderService.queryCount(Cnd.count(M.Order.orderNo).andEquals(M.Order.orderNo, orderes[0].getOrderNo()));
		if(count!=null && count>0){
			throw new BusinessException("该订单号已经存在");
		}

		orderService.createBatch(orderes);
		return "success";
	}
	
	@RequestMapping("/order/exportBarcode.do")
	@ResponseBody
	public String exportBarcode(HttpServletRequest request,HttpServletResponse response,@RequestBody OrderVO[] orderVOs) throws  IOException{

		
		List<String> results=new ArrayList<String>();
		results=orderService.getBarCodeList(orderVOs);

		String contextPath=request.getSession().getServletContext().getRealPath("/");
		
		String fileName="barcode("+orderVOs[0].getOrderNo()+").txt";
		String filePath="temp"+File.separatorChar+fileName;
		String path=contextPath+filePath;
		File file=new File(path);
		if(!file.exists()){
			//File temp=new File(contextPath+"temp");
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdir();
			}
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file, false);
		for(String ecode:results){
			writer.append(ecode+"\r\n");
		} 
	    writer.close();

	    //return "/"+filePath.replace(File.separatorChar, '/');
	    return fileName;
	}
	
	@RequestMapping("/order/downloadBarcode.do")
	//@ResponseBody
	public void downloadBarcode(HttpServletRequest request,HttpServletResponse response,String fileName) throws  IOException{
		String contextPath=request.getSession().getServletContext().getRealPath("/");
		String filePath="temp"+File.separatorChar+fileName;
		String path=contextPath+filePath;
		File file=new File(path);
		//FileReader reader=new FileReader(file);
		FileInputStream in=new FileInputStream(file);

		response.setHeader("content-disposition","attachment; filename="+fileName);
		//response.setContentType("application/octet-stream");
		response.setContentType("text/plain; charset=gb2312");
		
		OutputStream  out = response.getOutputStream();
		int n;
		byte b[]=new byte[1024];
		while((n=in.read(b))!=-1){
			out.write(b,0,n);
		}
		in.close();
		
		out.flush();
		out.close();
		

	}

	
}
