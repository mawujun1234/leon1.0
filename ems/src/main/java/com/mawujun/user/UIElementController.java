package com.mawujun.user;
import java.awt.Menu;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.repository.cnd.Cnd;
import com.mawujun.utils.M;
/**
 * @author mawujun qq:16064988 e-mail:16064988@qq.com 
 * @version 1.0
 * @since 1.0
 */
@Controller
//@RequestMapping("/uIElement")
public class UIElementController {

	@Resource
	private UIElementService uIElementService;



	@RequestMapping("/uIElement/queryAll.do")
	@ResponseBody
	public List<UIElement> queryAll(String navigation_id) {	
		List<UIElement> uIElementes=uIElementService.query(Cnd.select().andEquals(M.UIElement.navigation_id,navigation_id));
		return uIElementes;
	}
	
	
	

	@RequestMapping("/uIElement/load.do")
	public UIElement load(String id) {
		return uIElementService.get(id);
	}
	
	@RequestMapping("/uIElement/create.do")
	@ResponseBody
	public UIElement create(@RequestBody UIElement uIElement) {
		uIElementService.create(uIElement);
		return uIElement;
	}
	
	@RequestMapping("/uIElement/update.do")
	@ResponseBody
	public  UIElement update(@RequestBody UIElement uIElement) {
		uIElementService.update(uIElement);
		return uIElement;
	}
	
	@RequestMapping("/uIElement/deleteById.do")
	@ResponseBody
	public String deleteById(String id) {
		uIElementService.deleteById(id);
		return id;
	}
	
	@RequestMapping("/uIElement/destroy.do")
	@ResponseBody
	public UIElement destroy(@RequestBody UIElement uIElement) {
		uIElementService.delete(uIElement);
		return uIElement;
	}
	
	@RequestMapping("/uIElement/queryByFunRole.do")
	@ResponseBody
	public List<UIElement> queryByFunRole(String navigation_id,String funRole_id) {	
		List<UIElement> uIElementes=uIElementService.queryByFunRole(navigation_id, funRole_id);
		return uIElementes;
	}
	
	
	@RequestMapping("/uIElement/checkByFunRole.do")
	@ResponseBody
	public String checkByFunRole(String uIElement_id,String funRole_id) {
		uIElementService.checkByFunRole(uIElement_id, funRole_id);
		return "success";
	}
	
	@RequestMapping("/uIElement/uncheckByFunRole.do")
	@ResponseBody
	public String uncheckByFunRole(String uIElement_id,String funRole_id) {
		uIElementService.uncheckByFunRole(uIElement_id, funRole_id);
		return "success";
	}
	
	@RequestMapping("/uIElement/queryElementPermission.do")
	//@ResponseBody
	public void queryElementPermission(String url,HttpServletResponse response) throws IOException {
		if(url==null || "".equals(url)){
			return;
		}
		response.setContentType("application/json");
		List<UIElement> elements=uIElementService.queryElement(url);
		StringBuilder builder=new StringBuilder("{");
		for(UIElement menu:elements){
			builder.append(menu.getCode()+":true,");
		}
		String aa="{}";
		if(builder.length()>1){
			aa=builder.substring(0,builder.length()-1);
			aa=aa+"}";
		}
		aa="var Permision = {elements:"+aa+",canShow: function(code) {if(!code){alert('请输入界面元素的code!');return;}if(this.elements[code]){return true;} else {return false;}}};";
		PrintWriter out = response.getWriter();  
		out.write(aa);
		out.flush();
		out.close();
	}
}
