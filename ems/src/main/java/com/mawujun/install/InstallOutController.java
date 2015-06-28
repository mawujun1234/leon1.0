package com.mawujun.install;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.exception.BusinessException;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/installOut")
public class InstallOutController {

	@Resource
	private InstallOutService outStoreService;
	@Resource
	private EquipmentService equipmentService;

//	/**
//	 * 请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param id 是父节点的id
//	 * @return
//	 */
//	@RequestMapping("/installOut/query.do")
//	@ResponseBody
//	public List<InstallOut> query(String id) {
//		Cnd cnd=Cnd.select().andEquals(M.OutStore.parent.id, "root".equals(id)?null:id);
//		List<InstallOut> outStorees=outStoreService.query(cnd);
//		//JsonConfigHolder.setFilterPropertys(InstallOut.class,M.OutStore.parent.name());
//		return outStorees;
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
//	@RequestMapping("/installOut/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.OutStore.sampleName, "%"+sampleName+"%");
//		return outStoreService.queryPage(page);
//	}

//	@RequestMapping("/installOut/query.do")
//	@ResponseBody
//	public List<InstallOut> query() {	
//		List<InstallOut> outStorees=outStoreService.queryAll();
//		return outStorees;
//	}
//	
//
//	@RequestMapping("/installOut/load.do")
//	public InstallOut load(String id) {
//		return outStoreService.get(id);
//	}
//	
//	@RequestMapping("/installOut/create.do")
//	@ResponseBody
//	public InstallOut create(@RequestBody InstallOut outStore) {
//		outStoreService.create(outStore);
//		return outStore;
//	}
//	
//	@RequestMapping("/installOut/update.do")
//	@ResponseBody
//	public  InstallOut update(@RequestBody InstallOut outStore) {
//		outStoreService.update(outStore);
//		return outStore;
//	}
//	
//	@RequestMapping("/installOut/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		outStoreService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/installOut/destroy.do")
//	@ResponseBody
//	public InstallOut destroy(@RequestBody InstallOut outStore) {
//		outStoreService.delete(outStore);
//		return outStore;
//	}
	
	/**
	 * 主要用于新品入库的时候
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/installOut/getEquipmentByEcode.do")
	@ResponseBody
	public EquipmentVO getEquipmentByEcode(String ecode,String store_id) {	
		EquipmentVO equipment= equipmentService.getEquipmentByEcode_in_store(ecode,store_id);
		if(equipment==null){
			//equipment=new Equipment();
			//equipment.setStatus(0);
			throw new BusinessException("对不起，该条码对应的设备不存在，或者该设备挂在其他仓库中!");
		}
		//设备返库的时候，设备如果不是手持或损坏状态的话，就不能进行返库，说明任务没有扫描或者没有提交
		if(equipment.getStatus()!=EquipmentStatus.in_storage){
			throw new BusinessException("设备状态不是\"已入库\",不能领用该设备!");
		}
		return equipment;
	}
	
	/**
	 * 设备出库，设备领用
	 * @author mawujun 16064988@qq.com 
	 * @param equipments
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/installOut/equipmentOutStore.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String equipOutStore(@RequestBody InstallOutList[] installOutListes, InstallOut outStore) { 
		//inStoreService.newInStore(equipments);
		String installOut_id=outStoreService.equipOutStore(installOutListes, outStore);
		return installOut_id;
	}
	
	/**
	 * 设备出库，设备领用
	 * @author mawujun 16064988@qq.com 
	 * @param equipments
	 * @return
	 * @throws JRException 
	 * @throws IOException
	 */
	@RequestMapping("/installOut/equipmentOutStorePrint.do")
	@ResponseBody
	public void equipmentOutStorePrint(HttpServletRequest request,HttpServletResponse response,String installOut_id) throws  IOException { 
		//PrintWriter writer = response.getWriter();
		
		//InstallOutVO installOutVO=outStoreService.getInstallOutVO(installOut_id);
		//List<InstallOutListVO> installOutListes=outStoreService.queryList(installOut_id);
		List<InstallOutListVO> installOutListes=new ArrayList<InstallOutListVO>();
		InstallOutListVO a=new InstallOutListVO();
		a.setBrand_name("1");
		a.setEcode("1");
		a.setInstallOut_id("1");
		a.setProd_name("1");
		a.setSubtype_name("1");
		a.setStyle("1");
		installOutListes.add(a);
		
		
//		JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(installOutListes);
//		
//		//String root_path = request.getSession().getServletContext().getRealPath("/");
//		response.setContentType("text/html;charset=UTF-8");
//		OutputStream outputStream=response.getOutputStream();
//		
//		Map<String,Object> params=new HashMap<String,Object>();
//		params.put("project_name", "111111");  
//
//		
//		String JASPER_FILE_NAME=request.getSession().getServletContext().getRealPath("/install/report/installout.jasper");
//		//JasperReport jasperReport = (JasperReport)JRLoader.loadObject(JASPER_FILE_NAME);
//		File reportFile=new File(JASPER_FILE_NAME);
//		InputStream in=new FileInputStream(reportFile);
//		JasperPrint print = JasperFillManager.fillReport(in, params, dataSource);
//		// 使用JRHtmlExproter导出Html格式
//		
//		HtmlExporter exporter = new HtmlExporter();
//		//exporter.getCurrentJasperPrint()
//		SimpleHtmlExporterOutput simpleHtmlExporterOutput=new SimpleHtmlExporterOutput(outputStream,"UTF-8");
//		exporter.setExporterOutput(simpleHtmlExporterOutput);
//		
//		SimpleHtmlReportConfiguration simpleHtmlReportConfiguration=new SimpleHtmlReportConfiguration();
//		exporter.setConfiguration(simpleHtmlReportConfiguration);
//		
//		//SimpleExporterInputItem simpleExporterInputItem=new SimpleExporterInputItem(print);
//		SimpleExporterInput simpleExporterInput=new SimpleExporterInput(print);
//		exporter.setExporterInput(simpleExporterInput);
//		// 导出
//		exporter.exportReport();
//		outputStream.close();
		
		
//		String JASPER_FILE_NAME=request.getSession().getServletContext().getRealPath("/tuih/report1.jasper");
//		//JasperReport jasperReport = (JasperReport)JRLoader.loadObject(JASPER_FILE_NAME);
//		File reportFile=new File(JASPER_FILE_NAME);
//		InputStream in=new FileInputStream(reportFile);
//		JasperPrint print = JasperFillManager.fillReport(in, params, dataSource);
//		// 使用JRHtmlExproter导出Html格式
//		JRHtmlExporter exporter = new JRHtmlExporter();
////		request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print);
////		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
////		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
////		//exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "./servlets/image?image=");
//		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
//		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GB2312");
//		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<br style='page-break-before:always;'>");//翻页的处理（style='page-break-before:always;）；
//
//
//				     
//		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
//		// 导出
//		exporter.exportReport();
	}
	
	/**
	 * 
	 * @author mawujun 16064988@qq.com 
	 * @param start
	 * @param limit
	 * @param operateDate_start
	 * @param operateDate_end
	 * @param store_id
	 * @param type 是出库单还是入库单
	 * @return
	 */
	@RequestMapping("/installOut/queryMain.do")
	@ResponseBody
	public Page queryMain(Integer start,Integer limit,String operateDate_start,String operateDate_end,String store_id,String workUnit_id,String project_id) { 
		Page page=Page.getInstance(start, limit);
		page.addParam("operateDate_start", operateDate_start);
		page.addParam("operateDate_end", operateDate_end);
		page.addParam(M.InstallOut.store_id, store_id);
		page.addParam(M.InstallOut.workUnit_id, workUnit_id);
		//page.addParam(M.InstallOut.installOutType_id, installOutType_id);
		page.addParam(M.InstallOut.project_id, project_id);
		page=outStoreService.queryMain(page);
		return page;
	}
	
	@RequestMapping("/installOut/queryList.do")
	@ResponseBody
	public List<InstallOutListVO> queryList(String installOut_id) { 
		if(!StringUtils.hasText(installOut_id)){
			throw new BusinessException("请先选择一条单据!");
		}
		List<InstallOutListVO> page=outStoreService.queryList(installOut_id);
		return page;
	}
	
}
