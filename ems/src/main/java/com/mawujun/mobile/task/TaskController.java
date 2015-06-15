package com.mawujun.mobile.task;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.baseinfo.Customer;
import com.mawujun.baseinfo.CustomerService;
import com.mawujun.baseinfo.EquipmentStatus;
import com.mawujun.baseinfo.EquipmentVO;
import com.mawujun.baseinfo.Pole;
import com.mawujun.baseinfo.PoleService;
import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.repository.cnd.Cnd;
import com.mawujun.shiro.ShiroUtils;
import com.mawujun.utils.M;
import com.mawujun.utils.StringUtils;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/task")
public class TaskController {

	@Resource
	private TaskService taskService;
	@Resource
	private PoleService poleService;
	@Resource
	private CustomerService customerService;
	
	
	@Resource
	private OvertimeService overtimeService;


	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/task/queryPoles.do")
	@ResponseBody
	public Page queryPoles(Integer start,Integer limit,String customer_id,String filter_other,String area_id,String workunit_id,String pole_name) {
		Page page=Page.getInstance(start,limit);
		page.addParam("customer_id", customer_id);
		page.addParam("area_id", area_id);
		page.addParam("workunit_id", workunit_id);
		if(StringUtils.hasText(pole_name)){
			page.addParam("pole_name", "%"+pole_name+"%");
		}
		Page result=null;
		if(!StringUtils.hasText(filter_other) || "no_filter".equals(filter_other)){
			result= taskService.queryPoles(page);
		} else if("no_send_task".equals(filter_other)){
			//查询所有没有安装任务的
			result= taskService.queryPoles_no_send_task(page);
		}
		
		
//		//因为一个杆位会有多个任务
//		int i=0;
//		for(Object obj:result.getResult()){
//			Pole pole=(Pole)obj;
//			pole.setId(pole.getId()+"-"+i);
//			i++;
//		}
		return result;
	}
	
	@RequestMapping("/task/query.do")
	@ResponseBody
	public Page query(Integer start,Integer limit,String customer_id,String status,String type,String workunit_id,String pole_id,String pole_name,Boolean isOvertime) {
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Task.customer_id, customer_id);
		if(StringUtils.hasText(status)){
			page.addParam(M.Task.status, status);
		}
		page.addParam(M.Task.workunit_id, workunit_id);
		page.addParam(M.Task.pole_id, pole_id);
		if(StringUtils.hasText(type)){
			page.addParam(M.Task.type, type);
		}
		
		
		if(pole_name!=null){
			page.addParam(M.Task.pole_name, "%"+pole_name+"%");
		}
		if(isOvertime!=null && isOvertime==true){
			page.addParam("isOvertime", true);
			//获取超期时间
			Overtime overtime=overtimeService.get("overtime");
			page.addParam("handling", overtime.getHandling());
			
		}
		return taskService.queryPage(page);
	}
	
	/**
	 * 获取某个任务的设备列表
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @return
	 */
	@RequestMapping("/task/queryEquipList.do")
	@ResponseBody
	public String queryEquipList(String task_id) {
		List<EquipmentVO> list= taskService.queryEquipList(task_id);
		StringBuilder builder=new StringBuilder();
		for(EquipmentVO vo:list){
			builder.append(vo.getEcode());
			builder.append("|");
			builder.append(vo.getSubtype_name());
			builder.append("|");
			builder.append(vo.getProd_name());
			builder.append("|");
			builder.append(vo.getStyle());
			builder.append("|");
			builder.append(vo.getBrand_name());
			builder.append("|");
			builder.append(vo.getSupplier_name());
//			builder.append("|");
//			builder.append(vo.getStatus_name());
			builder.append("<br/>");
		}
		//JsonConfigHolder.setAutoWrap(false);
		return builder.toString();
	}
//	@RequestMapping("/task/confirm.do")
//	@ResponseBody
//	public String confirm(String id) {
//		taskService.confirm(id);
//		return "success";
//	}
	
//	@RequestMapping("/task/back.do")
//	@ResponseBody
//	public String backs(String id) {
//		taskService.update(Cnd.update().set(M.Task.status, TaskStatus.handling).andEquals(M.Task.id, id));
//		return "success";
//	}
	@RequestMapping("/task/cancel.do")
	@ResponseBody
	public String cancel(String id) {
		taskService.cancel(id);
		return "success";
	}
//
//	/**
//	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
//	 * @author mawujun email:16064988@163.com qq:16064988
//	 * @param start
//	 * @param limit
//	 * @param userName
//	 * @return
//	 */
//	@RequestMapping("/task/query.do")
//	@ResponseBody
//	public Page query(Integer start,Integer limit,String sampleName){
//		Page page=Page.getInstance(start,limit);//.addParam(M.Task.sampleName, "%"+sampleName+"%");
//		return taskService.queryPage(page);
//	}
	
	
//	@RequestMapping("/task/create.do")
//	@ResponseBody
//	public Task create(@RequestBody Task task) {
//		taskService.create(new Task[]{task});
//		return task;
//	}
	
	@RequestMapping("/task/create.do")
	@ResponseBody
	public String create(@RequestBody Task[] taskes) {	
		for(Task task:taskes){
			task.setCreaterType(TaskCreaterType.manager);
		}
		taskService.create(taskes);
		return "success";
	}
	
	
	/**
	 * 这是基于分页的几种写法,的例子，请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param start
	 * @param limit
	 * @param userName
	 * @return
	 */
	@RequestMapping("/task/mobile/query.do")
	@ResponseBody
	public Page mobile_query(Integer start,Integer limit,String status,String type,String orderBy){
		Page page=Page.getInstance(start,limit);//.addParam(M.Task.sampleName, "%"+sampleName+"%");
		page.addParam(M.Task.status, status);
		page.addParam(M.Task.type, type);
		page.addParam(M.Task.workunit_id, ShiroUtils.getAuthenticationInfo().getId());
		
		page.addParam("orderBy", (orderBy==null||"".equals(orderBy))?"createDate":orderBy);

		Page aa =taskService.mobile_queryPage(page);
		return aa;
	}
	
	@RequestMapping("/task/mobile/search.do")
	@ResponseBody
	public Page mobile_search(String status,String searchStr,Integer start,Integer limit){
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Task.status, status);
		page.addParam("searchStr", "%"+searchStr+"%");
		page.addParam(M.Task.workunit_id, ShiroUtils.getAuthenticationInfo().getId());
		Page aa =taskService.mobile_search(page);
		return aa;
		//return taskService.mobile_search(status, "%"+searchStr+"%", ShiroUtils.getAuthenticationInfo().getId());
	}
	
	
	/**
	 * 查询某个任务下的设备挂载情况
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task_id
	 * @return
	 */
	@RequestMapping("/task/mobile/queryTaskEquipmentInfos.do")
	@ResponseBody
	public Map<String,Object> mobile_queryTaskEquipmentInfos(String task_id){
		
		List<TaskEquipmentListVO> equipmentVOs=taskService.mobile_queryTaskEquipmentInfos(task_id);
		Task task=taskService.get(task_id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(M.Task.hitchReason, task.getHitchReason());
		map.put(M.Task.hitchReasonTpl_id, task.getHitchReasonTpl_id());
		map.put(M.Task.hitchType_id, task.getHitchType_id());
		map.put(M.Task.type, task.getType());
		map.put("equipmentVOs", equipmentVOs);
		return map;
		//return equipmentVOs;
	}
	/**
	 * 获取某个设备的信息，主要用于扫描的额时候
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/task/mobile/getTaskEquipmentList.do")
	@ResponseBody
	public TaskEquipmentListVO getTaskEquipmentList(String ecode,String task_id,TaskType task_type,String pole_id){
		return taskService.mobile_getAndCreateTaskEquipmentList(ecode, task_id,task_type,pole_id);
		//return equipmentVO;
	}
	/**
	 * 在某个任务上删除某个设备
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param ecode
	 * @return
	 */
	@RequestMapping("/task/mobile/deleteTaskEquipmentList.do")
	@ResponseBody
	public String deleteTaskEquipmentList(String taskEquipmentList_id,String ecode){
		taskService.mobile_deleteTaskEquipmentList(ecode,taskEquipmentList_id);
		return "success";
		//return equipmentVO;
	}
	
	/**
	 * 移动端的设备进行保存
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task
	 * @return
	 */
	@RequestMapping("/task/mobile/save.do")
	@ResponseBody
	//public String mobile_save(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason,String[] ecodes,Integer[] equipment_statuses) {
	public String mobile_save(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason) {
		//jquery 2 json地方有文图，，不能将数组正确的转换
		taskService.mobile_save(task_id,hitchType_id,hitchReasonTpl_id,hitchReason);
		return "success";
	}
	
	/**
	 * 移动端的设备进行保存
	 * @author mawujun email:160649888@163.com qq:16064988
	 * @param task
	 * @return
	 */
	@RequestMapping("/task/mobile/submit.do")
	@ResponseBody
	//public String mobile_submit(String task_id,String task_type,String[] ecodes,Integer[] equipment_statuses) {
	public String mobile_submit(String task_id,Integer hitchType_id,Integer hitchReasonTpl_id,String hitchReason) {
		//jquery 2 json地方有文图，，不能将数组正确的转换
		taskService.mobile_submit(task_id,hitchType_id,hitchReasonTpl_id,hitchReason);
		return "success";
	}
	

	/**
	 * 请按自己的需求修改
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param id 是父节点的id
	 * @return
	 */
	@RequestMapping("/task/mobile/queryPoles.do")
	@ResponseBody
	public List<Map<String,Object>> mobile_queryPoles(String pole_name) {
		List<Pole> list=taskService.mobile_queryPoles("%"+pole_name+"%", ShiroUtils.getAuthenticationInfo().getId());
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		for(Pole pole:list){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put(M.Pole.id, pole.getId());
			map.put("code", pole.getCode());
			map.put("name", pole.getName());
			map.put("address", pole.geetFullAddress());
			map.put("status", pole.getStatus());
			result.add(map);
		}

		return result;
	}
	
	@RequestMapping("/task/mobile/create.do")
	@ResponseBody
	public String mobile_create(String type,String memo,String pole_id) {
		//首先判断当前杆位上是否已经存在任务了，如果已经存在，就不能发送了
		
		Task task=new Task();
		task.setType(TaskType.valueOf(type));
		task.setMemo(memo);
		task.setPole_id(pole_id);
		
		Pole pole=poleService.get(pole_id);
		task.setPole_name(pole.getName());
		task.setPole_address(pole.geetFullAddress());
		task.setWorkunit_id(ShiroUtils.getAuthenticationInfo().getId());
		task.setWorkunit_name(ShiroUtils.getName());
		
		Customer customer=customerService.get(pole.getCustomer_id());
		task.setCustomer_id(customer.getId());
		task.setCustomer_name(customer.getName());
		
		task.setCreaterType(TaskCreaterType.workunit);
		
		taskService.create(new Task[]{task});
		return "success";
	}
	
	
	
	@RequestMapping("/task/queryRepairTaskesReport.do")
	@ResponseBody
	public Page queryRepairTaskesReport(Integer start,Integer limit,String workunit_id,String date_start,String date_end) {
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Task.workunit_id, workunit_id);
		page.addParam("date_start", date_start);
		page.addParam("date_end", date_end);

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return taskService.queryRepairTaskesReport(page);
	}
	
	@RequestMapping("/task/queryUnrepairPoleReport.do")
	@ResponseBody
	public Page queryUnrepairPoleReport(Integer start,Integer limit,String workunit_id,String customer_id) {
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Task.workunit_id, workunit_id);
		page.addParam(M.Task.customer_id, customer_id);

		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return taskService.queryUnrepairPoleReport(page);
	}
	
	//PoleController
	@RequestMapping("/task/exportRepairTaskesReport.do")
	@ResponseBody
	public void exportRepairTaskesReport(HttpServletResponse response,String workunit_id,String date_start,String date_end) throws IOException {
		List<TaskRepairReport> taskes=taskService.exportRepairTaskesReport(workunit_id,date_start,date_end);
		
		XSSFWorkbook wb =new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		int rownum=0;
		
		build_addColumnName(wb,sheet,rownum);
		
		// 开始构建整个excel的文件
		if (taskes != null && taskes.size() > 0) {
			rownum++;
			build_content(taskes, wb, sheet, rownum);
		}
		String filename = "维修任务明细.xlsx";
		 //FileOutputStream out = new FileOutputStream(filename);
		response.setHeader("content-disposition", "attachment; filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1"));
		//response.setContentType("application/vnd.ms-excel;charset=uft-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=uft-8");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		
		out.flush();
		out.close();
	}
	
	private void build_addColumnName(XSSFWorkbook wb,Sheet sheet,int rowInt){
		//CellStyle black_style=getStyle(wb,IndexedColors.BLACK,(short)11);
		 
		Row row = sheet.createRow(rowInt);
		int cellnum=0;
		
		Cell id=row.createCell(cellnum++);
		id.setCellValue("任务编号");
		
		Cell customer_name=row.createCell(cellnum++);
		customer_name.setCellValue("派出所");
		
		Cell pole_code=row.createCell(cellnum++);
		pole_code.setCellValue("点位编号");
		
		Cell pole_name=row.createCell(cellnum++);
		pole_name.setCellValue("点位名称");
		
		Cell workunit_name=row.createCell(cellnum++);
		workunit_name.setCellValue("作业单位");
		
		Cell hitchReason=row.createCell(cellnum++);
		hitchReason.setCellValue("故障现象");
		
		Cell createDate=row.createCell(cellnum++);
		createDate.setCellValue("任务下派时间");
		
		Cell startHandDate=row.createCell(cellnum++);
		startHandDate.setCellValue("维修到达时间");
		
//		Cell completeDate=row.createCell(cellnum++);
//		completeDate.setCellValue("完成时间");
		
		Cell finishTime=row.createCell(cellnum++);
		finishTime.setCellValue("总耗时");
		
		Cell repairTime=row.createCell(cellnum++);
		repairTime.setCellValue("修复耗时");
		
		Cell isOverTime=row.createCell(cellnum++);
		isOverTime.setCellValue("是否超时");
		
		Cell hitchType=row.createCell(cellnum++);
		hitchType.setCellValue("故障类型");
		
		Cell memo=row.createCell(cellnum++);
		memo.setCellValue("备注");
			
	}
	SimpleDateFormat yMdHms=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private void build_content(List<TaskRepairReport> taskes,XSSFWorkbook wb,Sheet sheet,int rownum){
		int cellnum=0;

		for(TaskRepairReport task:taskes){
			cellnum=0;
			Row row = sheet.createRow(rownum++);
			
			Cell id=row.createCell(cellnum++);
			id.setCellValue(task.getId());
			
			Cell customer_name=row.createCell(cellnum++);
			customer_name.setCellValue(task.getCustomer_name());
			
			Cell pole_code=row.createCell(cellnum++);
			pole_code.setCellValue(task.getPole_code());
			
			Cell pole_name=row.createCell(cellnum++);
			pole_name.setCellValue(task.getPole_name());
			
			Cell workunit_name=row.createCell(cellnum++);
			workunit_name.setCellValue(task.getWorkunit_name());
			
			Cell hitchReason=row.createCell(cellnum++);
			hitchReason.setCellValue(task.getHitchReason());
			
			Cell createDate=row.createCell(cellnum++);
			createDate.setCellValue(yMdHms.format(task.getCreateDate()));
			
			Cell startHandDate=row.createCell(cellnum++);
			startHandDate.setCellValue(task.getStartHandDate()!=null?yMdHms.format(task.getStartHandDate()):"");
			
//			Cell completeDate=row.createCell(cellnum++);
//			completeDate.setCellValue(task.getCompleteDate()!=null?yMdHms.format(task.getCompleteDate()):"");//==null?"":task.getCompleteDate());
			
			Cell finishTime=row.createCell(cellnum++);
			finishTime.setCellValue(task.getFinishTime());
			
			Cell repairTime=row.createCell(cellnum++);
			repairTime.setCellValue(task.getRepairTime());
			
			Cell isOverTime=row.createCell(cellnum++);
			isOverTime.setCellValue(task.isOverTime?"超时":"否");
			
			Cell hitchType=row.createCell(cellnum++);
			hitchType.setCellValue(task.getHitchType());
			
			//Cell memo=row.createCell(cellnum++);
			//memo.setCellValue();
		}
	}

//	@RequestMapping("/task/query.do")
//	@ResponseBody
//	public List<Task> query() {	
//		List<Task> taskes=taskService.queryAll();
//		return taskes;
//	}
//	
//
//	@RequestMapping("/task/load.do")
//	public Task load(String id) {
//		return taskService.get(id);
//	}
//	
//	@RequestMapping("/task/create.do")
//	@ResponseBody
//	public Task create(@RequestBody Task task) {
//		taskService.create(task);
//		return task;
//	}
//	
//	@RequestMapping("/task/update.do")
//	@ResponseBody
//	public  Task update(@RequestBody Task task) {
//		taskService.update(task);
//		return task;
//	}
//	
//	@RequestMapping("/task/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		taskService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/task/destroy.do")
//	@ResponseBody
//	public Task destroy(@RequestBody Task task) {
//		taskService.delete(task);
//		return task;
//	}
	
	
}
