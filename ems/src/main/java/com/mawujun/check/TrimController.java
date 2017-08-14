package com.mawujun.check;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/trim")
public class TrimController {

	@Resource
	private TrimService trimService;



	@RequestMapping("/trim/queryByCheck.do")
	@ResponseBody
	public List<TrimVO> queryByCheck(String check_id) {	
		List<TrimVO> trimes=trimService.queryByCheck(check_id);
		return trimes;
	}
	

//	@RequestMapping("/trim/load.do")
//	public Trim load(String id) {
//		return trimService.get(id);
//	}
//	
//	@RequestMapping("/trim/create.do")
//	@ResponseBody
//	public Trim create(@RequestBody Trim trim) {
//		trimService.create(trim);
//		return trim;
//	}
//	
//	@RequestMapping("/trim/update.do")
//	@ResponseBody
//	public  Trim update(@RequestBody Trim trim) {
//		trimService.update(trim);
//		return trim;
//	}
//	
//	@RequestMapping("/trim/deleteById.do")
//	@ResponseBody
//	public String deleteById(String id) {
//		trimService.deleteById(id);
//		return id;
//	}
//	
//	@RequestMapping("/trim/destroy.do")
//	@ResponseBody
//	public Trim destroy(@RequestBody Trim trim) {
//		trimService.delete(trim);
//		return trim;
//	}
	
	
}
