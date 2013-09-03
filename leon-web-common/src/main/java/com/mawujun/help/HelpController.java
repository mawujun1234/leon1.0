package com.mawujun.help;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mawujun.utils.FileUtils;

@Controller
@RequestMapping("/app")
public class HelpController {

	public String getHelpFilePath(HttpServletRequest request,String funId){
		String path=request.getSession().getServletContext().getRealPath("/")+"doc"+FileUtils.FILE_SEPARATOR+funId;
		String filePath=path+FileUtils.FILE_SEPARATOR+funId+".html";
		return filePath;
	}
	
	@RequestMapping("/help/helpCreateOrupdate")
	@ResponseBody
	public void helpCreateOrupdate(HttpServletRequest request,String funId,String editorValue) throws IOException {				
		//保存为html，以funId为文件夹名称，html文件也是以这个id为名称，如果有图片也上传到这个文件夹下面，
		//展示的时候，新建一个函数，动态的网<script id="editor">标签里面添加内容，也就是util的内容,参考buildUe方法，直接获取html内容，然后展示在标签内
		//查看的时候直接封装成完整的html
		//而用户查看的时候，是用一个html包围这些内容的
		String content=new String(editorValue.getBytes("ISO-8859-1"),"UTF-8");
		String path=request.getSession().getServletContext().getRealPath("/")+"doc"+FileUtils.FILE_SEPARATOR+funId;

		FileUtils.createDir(path);
		
		String filePath=path+FileUtils.FILE_SEPARATOR+funId+".html";
		System.out.println(filePath);
		File file=new File(filePath);
		if(FileUtils.isExists(filePath)){
			FileUtils.deleteFile(filePath);
		}
		
		FileUtils.writeAsString(file, content);
	}
	
	@RequestMapping("/help/helpGetContent")
	@ResponseBody
	public String helpGetContent(HttpServletRequest request,String funId) throws IOException {	
		String filePath=getHelpFilePath(request,funId);
		//JsonConfigHolder.setAutoWrap(false);
		try {
			return FileUtils.readAsString(new File(filePath));
		} catch(Exception e){
			e.printStackTrace();
			
		}
		return "";
		//return "hahah";
	}
	
	/**
	 * 浏览帮助文档
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param request
	 * @param funId
	 * @param map
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/help/helpLookContent")
	//@ResponseBody
	public String helpLookContent(HttpServletRequest request,String funId,ModelMap map) throws IOException {	
		//http://localhost:8083/fun/helpLookContent?funId=fun_manager
		String content=helpGetContent(request,funId);
//		StringBuffer buff=new StringBuffer();
//		buff.append("<html><body>");
//		buff.append(content);
//		buff.append("</body></html>");
//		//return content;
//		//map.addAttribute(content);
		map.addAttribute("content", content);
		return "/desktop/help/HelpLook";
	}
}
