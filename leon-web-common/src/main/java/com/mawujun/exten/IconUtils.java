package com.mawujun.exten;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import com.mawujun.utils.FileUtils;

public class IconUtils {

	/**
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String path="E:\\eclipse\\workspace\\leon\\leon-web-common\\src\\main\\webapp\\icons";
		// TODO Auto-generated method stub
		Collection<File> pngs=FileUtils.listFiles(new File(path), new String[]{"png","gif"}, true);
		
		FileWriter  fos=new FileWriter(path+"\\icons.css");  
		BufferedWriter bw=new BufferedWriter(fos);  
		for(File file:pngs){
			String aa=".icons_"+file.getName().substring(0,file.getName().lastIndexOf("."))+"{background: url(../icons/"+file.getName()+") left top no-repeat !important;}";
			bw.append(aa);
			bw.newLine();
			//System.out.println(aa);
		}
		bw.close();
		fos.close();
		
	}

}
