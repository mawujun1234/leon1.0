package com.mawujun.install;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.page.PageRequest;
import com.mawujun.utils.page.QueryResult;
import com.mawujun.baseinfo.Equipment;
import com.mawujun.baseinfo.EquipmentService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.exception.BusinessException;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.page.Page;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.install.Borrow;
import com.mawujun.install.BorrowService;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/borrow")
public class BorrowController {

	@Resource
	private BorrowService borrowService;
	@Resource
	private EquipmentService equipmentService;

	/**
	 * 主要用于新品入库的时候
	 * @author mawujun 16064988@qq.com 
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/borrow/getEquipmentByEcode.do")
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
			throw new BusinessException("设备状态不是\"已入库\",不能借用该设备!");
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
	@RequestMapping("/borrow/saveAndPrint.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String saveAndPrint(@RequestBody BorrowList[] borrowListes, Borrow borrow, String borrow_id) { 
		//inStoreService.newInStore(equipments);
		borrow_id=borrowService.borrowSaveAndPrint(borrowListes, borrow,borrow_id);
		return borrow_id;
	}
	/**
	 * 设备出库，设备领用
	 * @author mawujun 16064988@qq.com 
	 * @param equipments
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/borrow/equipmentOutStore.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String equipOutStore(@RequestBody BorrowList[] borrowListes, Borrow borrow, String borrow_id) { 
		//inStoreService.newInStore(equipments);
		borrow_id=borrowService.borrow(borrowListes, borrow,borrow_id);
		return borrow_id;
	}
	SimpleDateFormat yyyyMMdd=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 打印借用单
	 * @author mawujun 16064988@qq.com 
	 * @param equipments
	 * @return
	 * @throws IOException
	 * @throws JRException 
	 */
	@RequestMapping("/borrow/equipmentOutStorePrint.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public void equipmentOutStorePrint(HttpServletRequest request,HttpServletResponse response,String borrow_id) throws IOException, JRException {
		BorrowVO borrowVO=borrowService.getBorrowVO(borrow_id);
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("project_name", borrowVO.getProject_name()); 
		params.put("borrow_id", borrowVO.getId());  
		params.put("workunit_name", borrowVO.getWorkUnit_name()); 
		params.put("operater_name", borrowVO.getOperater_name());//仓管姓名
		params.put("operateDate", yyyyMMdd.format(borrowVO.getOperateDate()));
		List<BorrowListVO> borrowListVOs=borrowService.queryList(borrow_id);
		
		
		JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(borrowListVOs);
		

		
		
		String JASPER_FILE_NAME=request.getSession().getServletContext().getRealPath("/install/report/borrow.jasper");
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
	@RequestMapping("/borrow/queryMain.do")
	@ResponseBody
	public Page queryMain(Integer start,Integer limit,String operateDate_start,String operateDate_end,String store_id,String workUnit_id,String project_id,BorrowStatus status) { 
		Page page=Page.getInstance(start, limit);
		page.addParam("operateDate_start", operateDate_start);
		page.addParam("operateDate_end", operateDate_end);
		page.addParam(M.Borrow.store_id, store_id);
		page.addParam(M.Borrow.workUnit_id, workUnit_id);
		page.addParam(M.Borrow.project_id, project_id);
		page.addParam(M.Borrow.status, status);
		page=borrowService.queryMain(page);
		return page;
	}
	
	@RequestMapping("/borrow/queryList.do")
	@ResponseBody
	public List<BorrowListVO> queryList(String borrow_id) { 
		if(!StringUtils.hasText(borrow_id)){
			throw new BusinessException("请先选择一条单据!");
		}
		List<BorrowListVO> page=borrowService.queryList(borrow_id);
		return page;
	}
	
	/**
	 * 在借用返还的时候，或旧设备返库的时候，获取设备信息的
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @param workunit_id
	 * @return
	 */
	@RequestMapping("/borrow/getBorrowListVOByEcode.do")
	@ResponseBody
	public BorrowListVO getBorrowListVOByEcode(String ecode,String store_id) {
		BorrowListVO borrowListVO= borrowService.getBorrowEquipmentByEcode(ecode,store_id);

		return borrowListVO;
	}
	
	@RequestMapping("/borrow/borrowReturn.do")
	@ResponseBody
	//public String equipOutStore(@RequestBody Equipment[] equipments,String store_id,String workUnit_id,String type,String memo) {
	public String borrowReturn(@RequestBody BorrowList borrowlists[],String store_id) { 
		borrowService.borrowReturn(borrowlists,store_id);
		return "success";
	}
	
	
	/**
	 * 查询正在编辑状态的所有领用单
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param operateDate_start
	 * @param operateDate_end
	 * @param store_id
	 * @param workUnit_id
	 * @param project_id
	 * @return
	 */
	@RequestMapping("/borrow/queryEditBorrow.do")
	@ResponseBody
	public List<BorrowVO> queryEditBorrow() { 
		return borrowService.queryEditBorrow();
	}

}
