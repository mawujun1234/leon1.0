package com.mawujun.store;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.user.User;
import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.store.Barcode;
import com.mawujun.store.BarcodeService;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.CacheMgr;
import com.mawujun.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/barcode")
public class BarcodeController {

	@Resource
	private BarcodeService barcodeService;
	@Resource(name="cacheMgr")
	CacheMgr cacheMgr;

//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/barcode/query.do")
//	@ResponseBody
//	public List<Barcode> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.Barcode.parent.id, "root".equals(id)?null:id);
//		List<Barcode> barcodees=barcodeService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(Barcode.class,M.Barcode.parent.name());
//		return barcodees;
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
//	@RequestMapping("/barcode/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Barcode.sampleName, "%"+sampleName+"%");
//		return barcodeService.queryPage(page);
//	}
//
//	@RequestMapping("/barcode/query.do")
//	@ResponseBody
//	public List<Barcode> query() {	
//		List<Barcode> barcodees=barcodeService.queryAll();
//		return barcodees;
//	}
//	
//
//	@RequestMapping("/barcode/load.do")
//	public Barcode load(String id) {
//		return barcodeService.get(id);
//	}
//	
//	@RequestMapping("/barcode/create.do")
//	@ResponseBody
//	public Barcode create(@RequestBody Barcode barcode) {
//		barcodeService.create(barcode);
//		return barcode;
//	}
//	
//	@RequestMapping("/barcode/update.do")
//	@ResponseBody
//	public  Barcode update(@RequestBody Barcode barcode) {
//		barcodeService.update(barcode);
//		return barcode;
//	}
//	
//	@RequestMapping("/barcode/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		barcodeService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/barcode/destroy.do")
//	@ResponseBody
//	public Barcode destroy(@RequestBody Barcode barcode) {
//		barcodeService.delete(barcode);
//		return barcode;
//	}
	
	SimpleDateFormat timeFormat=new SimpleDateFormat("yyyyMMddHHmmss"); 
	@RequestMapping("/barcode/export.do")
	@ResponseBody
	public String export(HttpServletRequest request,HttpServletResponse response,@RequestBody Barcode[] barcodes) throws  IOException{
		//保存数据，同时导出编码
//		User user=ShiroUtils.getAuthenticationInfo();
//		Map<String,Object> map=cacheMgr.getQrcode(ShiroUtils.getLoginName()+user.getLoginDate().getTime());
//		if(map.get("isSaved")==null){
//			barcodeService.saveQrcodeList(map);
//			map.put("isSaved","true");
//		}
		
		List<Barcode> results=new ArrayList<Barcode>();
//		try {
			results=barcodeService.getBarCodeList(barcodes);
//		} catch(ConstraintViolationException e){
//			throw new BusinessException("条码重复,请重新导出!");
//		}
		String contextPath=request.getSession().getServletContext().getRealPath("/");
		
		String fileName="barcode("+timeFormat.format(new Date())+").txt";
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
		FileWriter writer = new FileWriter(file, true);
		for(Barcode barcode:results){
			writer.append(barcode.getId()+"\r\n");
		} 
	    writer.close();

	    //return "/"+filePath.replace(File.separatorChar, '/');
	    return fileName;
	}
	
	@RequestMapping("/barcode/download.do")
	//@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response,String fileName) throws  IOException{
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
