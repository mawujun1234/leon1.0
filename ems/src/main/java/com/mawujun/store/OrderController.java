package com.mawujun.store;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
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
	
	/**
	 * 根据订单号，查询所有的订单明细
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/order/queryMain.do")
	@ResponseBody
	public Page queryMain(Integer start,Integer limit,String store_id,Date date_start,Date date_end,String orderNo) {
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Order.store_id, store_id);
		if(orderNo!=null && !"".equals(orderNo.trim())){
			page.addParam(M.Order.orderNo, "%"+orderNo+"%");
		}
		
		page.addParam("date_start", date_start);
		page.addParam("date_end", date_end);
		page.addParam("user_id", ShiroUtils.getAuthenticationInfo().getId());
		Page orderes=orderService.queryMain(page);
		return orderes;
	}

	/**
	 * 根据订单号，查询所有的订单明细
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/order/queryList.do")
	@ResponseBody
	public Page queryList(Integer start,Integer limit,String orderNo) {	
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Order.orderNo, orderNo);
		
		Page orderes=orderService.queryList(page);
		return orderes;
	}
	

//	@RequestMapping("/order/load.do")
//	public Order load(String id) {
//		return orderService.get(id);
//	}
//	
//	@RequestMapping("/order/create.do")
//	@ResponseBody
//	public Order create(@RequestBody Order order) {
//		orderService.create(order);
//		return order;
//	}
	
//	@RequestMapping("/order/update.do")
//	@ResponseBody
//	public  Order update(@RequestBody Order order) {
//		orderService.update(order);
//		return order;
//	}
//	
//	@RequestMapping("/order/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		orderService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/order/destroy.do")
//	@ResponseBody
//	public Order destroy(@RequestBody Order order) {
//		orderService.delete(order);
//		return order;
//	}
	
	/**
	 * 查询该用户可以编辑的仓库的所有订单,而且订单还咩有全部入库
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @return
	 */
	@RequestMapping("/order/queryUncomplete.do")
	@ResponseBody
	public List<Map<String,String>> queryUncomplete() {	
		
		return orderService.queryUncompleteOrderno();
	}
	
	@RequestMapping("/order/create.do")
	@ResponseBody
	public String create(@RequestBody Order[] orderes) throws  IOException{
		orderService.create(orderes);
		return "success";
	}
	
	@RequestMapping("/order/exportBarcode.do")
	@ResponseBody
	public String exportBarcode(HttpServletRequest request,HttpServletResponse response,@RequestBody OrderVO[] orderVOs) throws  IOException{

		
		List<BarcodeVO> results=new ArrayList<BarcodeVO>();
		results=orderService.getBarCodeList(orderVOs);

		String contextPath=request.getSession().getServletContext().getRealPath("/");
		
		String fileName="qrcode("+orderVOs[0].getOrderNo()+").xls";
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
		

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("二维码");
        
        HSSFRow hssfRow0 = hssfSheet.createRow(0);
    	HSSFCell cell00 = hssfRow0.createCell(0);
    	cell00.setCellValue("条码");
    	HSSFCell cell011 = hssfRow0.createCell(1);
    	cell011.setCellValue("型号");
    	HSSFCell cell012= hssfRow0.createCell(2);
    	cell012.setCellValue("品牌");
    	HSSFCell cell013 = hssfRow0.createCell(3);
    	cell013.setCellValue("供应商");
    	HSSFCell cell014 = hssfRow0.createCell(4);
    	cell014.setCellValue("小类");
    	HSSFCell cell015 = hssfRow0.createCell(5);
    	cell015.setCellValue("品名");
    	
        for(int i=1;i<=results.size();i++){
        	BarcodeVO barcodeVO=results.get(i-1);
        	
        	HSSFRow hssfRow = hssfSheet.createRow(i);
        	HSSFCell cell0 = hssfRow.createCell(0);
        	cell0.setCellValue(barcodeVO.getEcode());
        	HSSFCell cell1 = hssfRow.createCell(1);
        	cell1.setCellValue(barcodeVO.getStyle());
        	
        	HSSFCell cell2 = hssfRow.createCell(2);
        	cell2.setCellValue(barcodeVO.getBrand_name());
        	HSSFCell cell3 = hssfRow.createCell(3);
        	cell3.setCellValue(barcodeVO.getSupplier_name());
        	HSSFCell cell4 = hssfRow.createCell(4);
        	cell4.setCellValue(barcodeVO.getSubtype_name());
        	HSSFCell cell5 = hssfRow.createCell(5);
        	cell5.setCellValue(barcodeVO.getProd_name());
        	 
        }
        OutputStream out = new FileOutputStream(file);
        hssfWorkbook.write(out);
        out.close();


	    //return "/"+filePath.replace(File.separatorChar, '/');
	    return fileName;
	}
	
//	@RequestMapping("/order/exportBarcode.do")
//	@ResponseBody
//	public String exportBarcode(HttpServletRequest request,HttpServletResponse response,@RequestBody OrderVO[] orderVOs) throws  IOException{
//
//		
//		List<String> results=new ArrayList<String>();
//		results=orderService.getBarCodeList(orderVOs);
//
//		String contextPath=request.getSession().getServletContext().getRealPath("/");
//		
//		String fileName="barcode("+orderVOs[0].getOrderNo()+").txt";
//		String filePath="temp"+File.separatorChar+fileName;
//		String path=contextPath+filePath;
//		File file=new File(path);
//		if(!file.exists()){
//			//File temp=new File(contextPath+"temp");
//			if (!file.getParentFile().exists()) {
//				file.getParentFile().mkdir();
//			}
//			file.createNewFile();
//		}
//		FileWriter writer = new FileWriter(file, false);
//		for(String ecode:results){
//			writer.append(ecode+"\r\n");
//		} 
//	    writer.close();
//
//	    //return "/"+filePath.replace(File.separatorChar, '/');
//	    return fileName;
//	}
	
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
		response.setContentType("application/vnd.ms-excel;charset=uft-8");
		//response.setContentType("text/plain; charset=gb2312");
		
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
