<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="com.mawujun.util.web.WebUtils"%>
<% 
    //获取当前功能图片的存放地方
    Cookie cookie=WebUtils.getCookie(request,"help_funId_folder");
	String path ="doc/"+cookie.getValue()+"/upload";
	//String path = "upload";
	String imgStr ="";
	String realpath = getRealPath(request,path)+"/"+path;
	System.out.println(realpath);
	List<File> files = getFiles(realpath,new ArrayList());
	
	String replacePath=getRealPath(request,path)+"doc";
	//System.out.println(files.size());
	for(File file :files ){
		imgStr+=file.getPath().replace(replacePath,"")+"ue_separate_ue";
		//System.out.println(imgStr);
	}
	if(imgStr!=""){
        imgStr = imgStr.substring(0,imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
    }
	//System.out.println(imgStr);
	out.print("/"+imgStr);		
%>
<%!
public List getFiles(String realpath, List files) {
	
	File realFile = new File(realpath);
	if (realFile.isDirectory()) {
		File[] subfiles = realFile.listFiles();
		for(File file :subfiles ){
			if(file.isDirectory()){
				getFiles(file.getAbsolutePath(),files);
			}else{
				if(!getFileType(file.getName()).equals("")) {
					files.add(file);
				}
			}
		}
	}
	return files;
}

public String getRealPath(HttpServletRequest request,String path){
	
	String realPath = request.getSession().getServletContext().getRealPath("/");
	//System.out.println(realPath);
	return realPath ;
	//ServletContext application = request.getSession().getServletContext();
	//String str = application.getRealPath(request.getServletPath());
	//return new File(str).getParent();
}

public String getFileType(String fileName){
	String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
	Iterator<String> type = Arrays.asList(fileType).iterator();
	while(type.hasNext()){
		String t = type.next();
		if(fileName.endsWith(t)){
			return t;
		}
	}
	return "";
}
%>