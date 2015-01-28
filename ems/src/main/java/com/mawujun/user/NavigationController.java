package com.mawujun.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class NavigationController {
	//rivate static final String SUCCESS="success";
	
	//系统参数
	//private HttpServletRequest request;
	//private String jsonString;
	
	@Resource(name="navigationService")
	NavigationService navigationService;
	
	
	
//	@RequestMapping("/getnavi.do")
//	@ResponseBody
//	public String loadNaviLsit() {
//		String jsonString;
//		//HttpSession session= request.getSession();
//		List<Map<String,Object>> naviList=new ArrayList<Map<String,Object>>();
////		UserBean user = (UserBean) session.getAttribute("loginUser");
//		int right = 3;
////		if(null!=user){
////			right = user.getUserConfirm();
////		}
//		
//		Document doc=null;
//		try {
//			// 打开XML文件，创建DOM
//			doc = LoadXml(java.net.URLDecoder.decode((this.getClass().getResource("/").getPath()+"config/navi-left.xml"),"UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(doc!=null){
//			Element root = doc.getRootElement(); // 得到根元素reports
//			List<?> servicefolders = root.elements("servicefolder"); // 得到根元素所有子元素的集合servicefolders
//			for (Object objectFolders : servicefolders) { // 循环得到servicefolder元素(泛型和增强型for循环)
//				Element servicefolder=(Element) objectFolders;
////				int rights=Integer.parseInt(servicefolder.attributeValue("rights"));
////				if((rights&confirm)==rights) { // 权限判断，是否包含此权限
//					Map<String,Object> folder=new HashMap<String,Object>();
//					Iterator<?> it= servicefolder.attributeIterator();
//					while(it.hasNext()){
//					   Attribute attr=	(Attribute) it.next();
//					   String elName=attr.getName();
//					   if(elName!=null&&!elName.equals("")){
//						   folder.put(elName, attr.getValue());
//					   }
//					}
//					List<?> reports = servicefolder.elements("service"); // 得到此servicefolder下的所有service
//					List<Map<String,Object>> serviceList=new ArrayList<Map<String,Object>>();
//					for (Object objectReport : reports) { // 循环得到service元素(泛型和增强型for循环)
//						Element report=(Element) objectReport;
//						Map<String,Object> service=new HashMap<String,Object>();
//						Iterator<?> sit= report.attributeIterator();
//						while(sit.hasNext()){
//						   Attribute item=	(Attribute) sit.next();
//						   String name=item.getName();
//						   if(name!=null&&!name.equals("")){
//							   String value=item.getValue();
//							   if(isNumeric(value)){
//								   service.put(name, Long.parseLong(item.getValue())); 
//							   }else{
//								   service.put(name, item.getValue());
//							   }
//						   }
//						}
//						serviceList.add(service);
//				    }	
//					folder.put("items", serviceList);
//					naviList.add(folder);
//			}
//		}
//		JSONArray resultJSON=JSONArray.fromObject(naviList);
//	    jsonString=resultJSON.toString();
//	    JsonConfigHolder.setAutoWrap(false);
//		return jsonString;
//	}
//	@RequestMapping("/showmenu.do")
//	@ResponseBody
//	public String LoadNaviT() {
//		String jsonString;
//		//HttpSession session= request.getSession();
//		List<NaviMenu> naviList=new ArrayList<NaviMenu>();
////		LoginUserVO loginUserVO = (LoginUserVO) session.getAttribute("loginUser");
//		int right = 3;
////		if(null!=loginUserVO){
////			right = loginUserVO.getUserConfirm();
////		}
//			// 打开XML文件，创建DOM
//			FileInputStream fi = null;
//			Document doc = null;
//			try {
//				
//				fi = new FileInputStream((this.getClass().getResource("/").getPath()+"config/navi-top.xml"));
//				//fi = new FileInputStream(session.getServletContext().getRealPath("WEB-INF/classes/config/navi-top.xml"));
//				SAXReader sb = new SAXReader();
//				doc = sb.read(fi);
//			}catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (DocumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Element root = doc.getRootElement(); // 得到根元素reports
//			List<Element> servicefolders = root.elements("servicefolder"); // 得到根元素所有子元素的集合servicefolders
//			for (Element servicefolder : servicefolders) { // 循环得到servicefolder元素(泛型和增强型for循环)
//				int rights=Integer.parseInt(servicefolder.attributeValue("rights"));
//				if((rights&right)==rights) { // 权限判断，是否包含此权限
//					NaviMenu folder=new NaviMenu();
//					folder.setLeaf(false);
//					String sfid =servicefolder.attributeValue("id");
//					if(sfid.equals("navi_sf_0")){
//						folder.setLink(servicefolder.attributeValue("link"));
//					}
//					if(null!=sfid&&sfid!=""){folder.setId(sfid);}
//					folder.setText(servicefolder.attributeValue("text"));
//					folder.setRights(rights);
//					List<Element> reports = servicefolder.elements("service"); // 得到此servicefolder下的所有service
//					List<Navi> navimenu=folder.getMenu();
//					for (Element report : reports) { // 循环得到service元素(泛型和增强型for循环)
//						Navi service=new Navi();
//						String sid =report.attributeValue("id");
//						if(null!=sid&&sid!=""){service.setId(sid);}
//						service.setText(report.attributeValue("text"));
//						service.setLink(report.attributeValue("link"));
//						String loadType=report.attributeValue("loadType");
//						if(null!=loadType&&!loadType.equals("")){
//							service.setLoadType(report.attributeValue("loadType"));
//						}
//						navimenu.add(service);
//				    }	
//					naviList.add(folder);
//				}
//			}
//		JSONObject resultJSON=new JSONObject();
//		resultJSON.put("success", true);
//		resultJSON.put("navi", naviList);
//	    jsonString=resultJSON.toString();
//	    
//	    JsonConfigHolder.setAutoWrap(false);
//		return jsonString;
//	}
//	
//	private boolean isNumeric(String str) {    
//		 Pattern pattern = Pattern.compile("[0-9]*");    
//		 return pattern.matcher(str).matches();    
//	}  
//	
//	private Document LoadXml(String path){
//		// 打开XML文件，创建DOM
//		FileInputStream fi = null;
//		Document doc = null;
//		try {
//			fi = new FileInputStream(path);
//			SAXReader sr = new SAXReader();
//			doc = sr.read(fi);
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			if(fi!=null){
//				try {
//					fi.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return doc;
//	}

	@RequestMapping("/nav/loadNaviT.do")
	@ResponseBody
	public List<Navigation> loadNaviT(String node,Boolean showChecked) {
		if("root".equals(node)){
			node=null;
		}

		Subject subject=SecurityUtils.getSubject();
		String username=subject.getPrincipal().toString();
		List<Navigation> list=null;
		if("admin".equals(username)){
			list= navigationService.loadNaviT4admin(username,node);
		} else {
			list= navigationService.loadNaviT(node);
		}
		if(showChecked!=null && showChecked){//如果是叶子节点就加上checked，并且如果选择了的话checked=true
			
			List<Navigation> leftList=navigationService.getLeftNavi();
			for(Navigation map:list){	
				if((Boolean)map.isLeaf()){
					map.setChecked(false);
					for(Navigation navNode:leftList){
						if(navNode.getId().equals(map.getId())){
							//map.put("checked", true);
							map.setChecked(true);
							break;
						} 
					}
				}
			}
		}
		return list;
	}
	
	@RequestMapping("/nav/checkchange.do")
	@ResponseBody
	public String checkchange(String navigation_id,Boolean checked) {
		Subject subject=SecurityUtils.getSubject();
		String username=subject.getPrincipal().toString();
		if(checked){
			navigationService.checkedNavigation(navigation_id, username);
		} else {
			navigationService.unCheckedNavigation(navigation_id, username);
		}
		return "success";

	}
	
	@RequestMapping("/nav/getleftnavi.do")
	@ResponseBody
	public List<Navigation> getLeftNavi() {
		//Subject subject=SecurityUtils.getSubject();
		//String username=subject.getPrincipal().toString();
		List<Navigation> list= navigationService.getLeftNavi();
		for(Navigation node:list){
			node.setId(node.getId()+"_leftFunction");
		}
		return list;
	}
	
	
	
	
	@RequestMapping("/nav/list.do")
	@ResponseBody
	public List<Map<String,Object>> list(String node) {
		if("root".equals(node)){
			node=null;
		}

		return navigationService.list(node);
	}

	@RequestMapping("/nav/save.do")
	@ResponseBody
	public String save(Navigation node) {
		if("root".equals(node.getParentId())){
			node.setParentId(null);
		}
		navigationService.save(node);
		return "success";
	}
	@RequestMapping("/nav/update.do")
	@ResponseBody
	public String update(Navigation node) {
		navigationService.update(node);
		return "success";
	}
	@RequestMapping("/nav/delete.do")
	@ResponseBody
	public String delete(String id) {
		navigationService.delete(id);
		return "success";
	}
	
	@RequestMapping("/nav/downloadManual.do")
	//@ResponseBody
	public void downloadManual(HttpServletRequest request,HttpServletResponse response,String fileName) throws  IOException{
		String contextPath=request.getSession().getServletContext().getRealPath("/");
		//String filePath="temp"+File.separatorChar+fileName;
		fileName="操作手册.docx";
		String path=contextPath+File.separatorChar+fileName;
		File file=new File(path);
		//FileReader reader=new FileReader(file);
		FileInputStream in=new FileInputStream(file);

		response.setHeader("content-disposition","attachment; filename="+new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
		response.setContentType("application/vnd.ms-word;charset=uft-8");
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
	
	@RequestMapping("/nav/downloadApk.do")
	//@ResponseBody
	public void downloadApk(HttpServletRequest request,HttpServletResponse response,String fileName) throws  IOException{
		String contextPath=request.getSession().getServletContext().getRealPath("/");
		//String filePath="temp"+File.separatorChar+fileName;
		fileName="emsmobile-release.apk";
		String path=contextPath+File.separatorChar+fileName;
		File file=new File(path);
		//FileReader reader=new FileReader(file);
		FileInputStream in=new FileInputStream(file);

		response.setHeader("content-disposition","attachment; filename="+fileName);
		response.setContentType("application/vnd.ms-word;charset=uft-8");
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
