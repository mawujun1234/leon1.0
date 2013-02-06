package com.mawujun.utils.icon;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.mawujun.utils.page.QueryResult;

public class IconUtil {
	/**
	 * 读取icon文件
	 * @param filePath
	 */
	public static QueryResult<Icon>  readIcon(String filePath,String wildcard ,String contextPath,QueryResult<Icon> page) {
		List<Icon> result=new ArrayList<Icon>();
		
		File file=new File(filePath);
		IOFileFilter fileFilter=null;
		if(wildcard ==null || "".equals(wildcard.trim())){
			//fileFilter=FileFilterUtils.suffixFileFilter("png");
			wildcard="*";
		} //else {
		//	wildcard+="*.png";
		//}
			//fileFilter=FileFilterUtils.andFileFilter(FileFilterUtils.prefixFileFilter(prefixName),FileFilterUtils.suffixFileFilter("png"));
			//fileFilter=FileFilterUtils.andFileFilter(new WildcardFileFilter(wildcard),FileFilterUtils.suffixFileFilter("png"));
			
		
		fileFilter = new WildcardFileFilter(wildcard);

		//}
		
		List<File> list=(List<File>) FileUtils.listFiles(file,fileFilter, null);
		Collections.sort(list, NameFileComparator.NAME_COMPARATOR);
		for(int i=page.getStart();(i<list.size()&&i<page.getStart()+page.getPageSize());i++){
			//System.out.println(list.get(i).getName());
			Icon icon=new Icon();
			icon.setFileName(list.get(i).getName());
			//icon.setPath(filePath+System.getenv("file.separator")+icon.getFileName());
			
			result.add(icon);
		}
		page.setResult(result);
		
		if(page.isCountTotal()) {
			page.setTotalItems(list.size());
		}
		return page;	
	}
}
