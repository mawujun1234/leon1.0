package com.mawujun.install;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

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

import freemarker.template.utility.DateUtil;
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
	@RequestMapping("/installOut/equipmentOutStoreSaveAndPrint.do")
	@ResponseBody
	public String equipmentOutStoreSaveAndPrint(@RequestBody InstallOutList[] installOutListes, InstallOut outStore) { 
		
			
		String installOut_id=outStoreService.equipOutStoreSaveAndPrint(installOutListes, outStore);
		
		
		return installOut_id;
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
	public String equipOutStore(@RequestBody InstallOutList[] installOutListes, InstallOut outStore , String installOut_id) { 
		if(installOut_id==null || "".equals(installOut_id.trim())){
			throw new BusinessException("请先选择一个'编辑中'的领用单!");
		}
		outStoreService.equipOutStore(installOutListes, outStore,installOut_id);
		return installOut_id;
	}
	SimpleDateFormat yyyyMMdd=new SimpleDateFormat("yyyy-MM-dd");
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
	public void equipmentOutStorePrint(HttpServletRequest request,HttpServletResponse response,String installOut_id) throws  IOException, JRException { 
		
		InstallOutVO installOutVO=outStoreService.getInstallOutVO(installOut_id);
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("project_name", installOutVO.getProject_name()); 
		params.put("installout_id", installOutVO.getId());  
		params.put("workunit_name", installOutVO.getWorkUnit_name()); 
		params.put("operater_name", installOutVO.getOperater_name());//仓管姓名
		params.put("operateDate", yyyyMMdd.format(installOutVO.getOperateDate()));
		List<InstallOutListVO> installOutListes=outStoreService.queryList(installOut_id);
		
		
//		List<InstallOutListVO> installOutListes=new ArrayList<InstallOutListVO>();
//		for(int i=0;i<20;i++){
//			InstallOutListVO a=new InstallOutListVO();
//			a.setBrand_name(i+"品牌");
//			a.setEcode("020202-**-1506260006");
//			a.setInstallOut_id(i+"");
//			a.setProd_name(i+"品名品名品名品名品名品名品名品名品名品名sdfetlkjsdfnejkksdjfljl");
//			a.setSubtype_name(i+"小类小类小类小类小类小类小类小类");
//			a.setStyle(i+"DS-2CD4012F(A/W/P/SDI/FC)detgtdsfsdfe4ddferereertgthgh");
//			a.setProd_unit("台");
//			installOutListes.add(a);
//		}
//		Map<String,Object> params=new HashMap<String,Object>();
//		params.put("project_name", "111111"); 
//		params.put("installout_id", "20150630124512001");  
//		params.put("workunit_name", "哈哈哈哈"); 
//		params.put("operater_name", "哈哈哈哈");//仓管姓名
//		params.put("operateDate", "2015-07-01");
		
		
		
		JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(installOutListes);
		

		
		
		String JASPER_FILE_NAME=request.getSession().getServletContext().getRealPath("/install/report/installout.jasper");
		//JasperReport jasperReport = (JasperReport)JRLoader.loadObject(JASPER_FILE_NAME);
		File reportFile=new File(JASPER_FILE_NAME);
		InputStream in=new FileInputStream(reportFile);
		JasperPrint print = JasperFillManager.fillReport(in, params, dataSource);
		
		response.setContentType("application/pdf;charset=UTF-8");
		OutputStream outputStream=response.getOutputStream();
		JRPdfExporter exporter = new JRPdfExporter(); 
		SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput=new SimpleOutputStreamExporterOutput(outputStream);
		exporter.setExporterOutput(simpleOutputStreamExporterOutput);
		
		SimplePdfReportConfiguration simplePdfReportConfiguration=new SimplePdfReportConfiguration();
		exporter.setConfiguration(simplePdfReportConfiguration);
		
		SimpleExporterInput simpleExporterInput=new SimpleExporterInput(print);
		exporter.setExporterInput(simpleExporterInput);
		// 导出
		exporter.exportReport();
		outputStream.close();
		
		

		
//		// 使用JRHtmlExproter导出Html格式
//		response.setContentType("text/html;charset=UTF-8");
//		OutputStream outputStream=response.getOutputStream();
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
