package com.mawujun.parameter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mawujun.exception.BusinessException;
import com.mawujun.utils.SystemUtils;

@Service
public class PGeneratorService {
	/**
	 * 更新Pfile文件的内容
	 * @author mawujun 16064988@qq.com 
	 * @throws IOException
	 */
	public void updatePjavaFile(List<String> ids) {
		StringBuffer buffer=new StringBuffer();
		buffer.append("package com.mawujun.parameter;\n");
		buffer.append("/**\n");
		buffer.append(" * 这个类的内容是动态添加的,不要修改他，修改了也会被覆盖的\n");
		buffer.append(" * @author mawujun email:16064988@163.com qq:16064988\n");
		buffer.append(" *\n");
		buffer.append(" */\n");
		buffer.append("public enum P {\n");
		int i=0;
		for(String id:ids){
			buffer.append(id);
			i++;
			if(i!=ids.size()){
				buffer.append(",\n");
			} else {
				buffer.append(";\n");
			}
		}
		buffer.append("}\n");
		
		
		//String path=Test.class.getResource("").toString()+"P.java";
		String fileName=System.getProperty("user.dir")+SystemUtils.FILE_SEPARATOR+"src"+SystemUtils.FILE_SEPARATOR+"main"+SystemUtils.FILE_SEPARATOR+"java"+SystemUtils.FILE_SEPARATOR+"com"+SystemUtils.FILE_SEPARATOR+"mawujun"+SystemUtils.FILE_SEPARATOR+"parameter"+SystemUtils.FILE_SEPARATOR+"P.java";
		System.out.println(fileName);
		
		try {
			FileWriter writer = new FileWriter(fileName, false);
			 writer.write(buffer.toString());
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new BusinessException("修改P.class失败!");
		}
       
	}



}
