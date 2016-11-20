package com.mawujun.check;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.controller.spring.mvc.json.JsonConfigHolder;
import com.mawujun.utils.M;
import com.mawujun.utils.page.Page;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/check")
public class CheckController {

	@Resource
	private CheckService checkService;

	
	@RequestMapping("/check/queryPager.do")
	@ResponseBody
	public Page queryPager(Integer start,Integer limit,CheckStatus status) {	
		Page page=Page.getInstance(start,limit);
		page.addParam(M.Check.status, status);
		
		Page checkes=checkService.queryPage(page);
		
		JsonConfigHolder.setDatePattern("yyyy-MM-dd HH:mm:ss");
		return checkes;
	}
	@RequestMapping("/check/complete.do")
	@ResponseBody
	public String complete(String check_id) {	
		
		checkService.complete(check_id);

		return "success";
	}
	/**
	 * 转移
	 * @param check_id
	 * @return
	 */
	@RequestMapping("/check/transfer.do")
	@ResponseBody
	public String transfer(Trim trim) {	
		
		checkService.transfer(trim);

		return "success";
	}
	/**
	 * 交换，把两个点位上的设备进行交换
	 * @param check_id
	 * @returnexchange
	 */
	@RequestMapping("/check/exchange.do")
	@ResponseBody
	//public String exchange(@RequestBody Trim scan_eqip,@RequestBody Trim pole_eqip) {	
	public String exchange(@RequestBody TrimBindVO trimBindVO) {	
		
		checkService.exchange(trimBindVO.getScan_eqip(), trimBindVO.getPole_eqip());
		return "success";
	}
	/**
	 * 卸载，从点位上把多余的设备卸载下来
	 * @param uninstall
	 * @return
	 */
	@RequestMapping("/check/uninstall.do")
	@ResponseBody
	public String uninstall(Trim trim) {	
		
		checkService.uninstall(trim);

		return "success";
	}
	
	
	
	@RequestMapping("/check/queryDifferentEquipment.do")
	@ResponseBody
	public Map<String,List<EquipmentVO1>> queryDifferentEquipment(String check_id,String pole_id,Boolean onlyDifferent) {	
		List<EquipmentVO1> scan_records= checkService.queryScanEquipment(check_id);
		List<EquipmentVO1> pole_records= checkService.queryPoleEquipment(pole_id);
		Map<String,List<EquipmentVO1>> results=new HashMap<String,List<EquipmentVO1>>();
		//只显示有差异的
		//if(onlyDifferent){
			List<EquipmentVO1> scan_records_new=new ArrayList<EquipmentVO1>();
			List<EquipmentVO1> pole_records_new=new ArrayList<EquipmentVO1>();
			
			for(EquipmentVO1 scan_record:scan_records){
				boolean bool=true;
				for(EquipmentVO1 pole_record:pole_records){
					if(scan_record.getEcode().equals(pole_record.getEcode())){
						bool=false;
						break;
					}
				}
				//if(bool){
					//scan_records_new.add(scan_record);
				//}
				scan_record.setDiff(bool);
				if(onlyDifferent && bool){
					scan_records_new.add(scan_record);
				} else if(!onlyDifferent){
					scan_records_new.add(scan_record);
				}
			}
			
			for(EquipmentVO1 pole_record:pole_records){
				boolean bool=true;
				for(EquipmentVO1 scan_record:scan_records){
					if(scan_record.getEcode().equals(pole_record.getEcode())){
						bool=false;
						break;
					}
				}
//				if(bool){
//					pole_records_new.add(pole_record);
//				}
				pole_record.setDiff(bool);
				if(onlyDifferent && bool){
					pole_records_new.add(pole_record);
				} else  if(!onlyDifferent){
					pole_records_new.add(pole_record);
				}
			}
			results.put("scan_records",scan_records_new );
			results.put("pole_records", pole_records_new);
//		} else {
//			results.put("scan_records",scan_records );
//			results.put("pole_records", pole_records);
//		}
		return results;
	}
	/**
	 * 查询出点位上和扫描出来设备不 一样的，用来替换用
	 * @param check_id
	 * @param pole_id
	 * @param onlyDifferent
	 * @return
	 */
	@RequestMapping("/check/queryDifferentPoleEquipment.do")
	@ResponseBody
	public List<EquipmentVO1> queryDifferentPoleEquipment(String check_id,String pole_id,Boolean onlyDifferent) {	
		List<EquipmentVO1> scan_records= checkService.queryScanEquipment(check_id);
		List<EquipmentVO1> pole_records= checkService.queryPoleEquipment(pole_id);
		//Map<String,List<EquipmentVO>> results=new HashMap<String,List<EquipmentVO>>();
		//只显示有差异的
		//if(onlyDifferent){
		//	List<EquipmentVO> scan_records_new=new ArrayList<EquipmentVO>();
			List<EquipmentVO1> pole_records_new=new ArrayList<EquipmentVO1>();
			
//			for(EquipmentVO scan_record:scan_records){
//				boolean bool=true;
//				for(EquipmentVO pole_record:pole_records){
//					if(scan_record.getEcode().equals(pole_record.getEcode())){
//						bool=false;
//						break;
//					}
//				}
//				if(bool){
//					scan_records_new.add(scan_record);
//				}
//			}
			
			for(EquipmentVO1 pole_record:pole_records){
				boolean bool=true;
				for(EquipmentVO1 scan_record:scan_records){
					if(scan_record.getEcode().equals(pole_record.getEcode())){
						bool=false;
						break;
					}
				}
				if(bool){
					pole_records_new.add(pole_record);
				}
			}
			//results.put("scan_records",scan_records_new );
			//results.put("pole_records", pole_records_new);
		//} else {
			//results.put("scan_records",scan_records );
			//results.put("pole_records", pole_records);
		//}
		return pole_records_new;
	}
//	@RequestMapping("/check/queryScanEquipment.do")
//	@ResponseBody
//	public List<EquipmentVO> queryScanEquipment(String check_id) {	
//
//		return checkService.queryScanEquipment(check_id);
//	}
//	@RequestMapping("/check/queryPoleEquipment.do")
//	@ResponseBody
//	public List<EquipmentVO> queryPoleEquipment(String pole_id) {	
//
//		return checkService.queryPoleEquipment(pole_id);
//	}
	

//	@RequestMapping("/check/load.do")
//	public Check load(String id) {
//		return checkService.get(id);
//	}
//	
//	@RequestMapping("/check/create.do")
//	@ResponseBody
//	public Check create(@RequestBody Check check) {
//		checkService.create(check);
//		return check;
//	}
//	
//	@RequestMapping("/check/update.do")
//	@ResponseBody
//	public  Check update(@RequestBody Check check) {
//		checkService.update(check);
//		return check;
//	}
//	
//	@RequestMapping("/check/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		checkService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/check/destroy.do")
//	@ResponseBody
//	public Check destroy(@RequestBody Check check) {
//		checkService.delete(check);
//		return check;
//	}
	
	
}
