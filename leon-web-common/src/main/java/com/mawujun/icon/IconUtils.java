package com.mawujun.icon;

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
		//generatorIcon();
		generatorAllPngCss();
	}
	
	public static String getPngClsName(String fileName,int size){
		return "pngs_"+size+"_"+fileName.substring(0,fileName.lastIndexOf("."));
	}
	public static void generatorAllPngCss() throws IOException {
		// String path1=IconUtils.class.getResource("/").getPath();
		int size=32;

		String path = "E:\\eclipse\\workspace\\leon\\leon-web-common\\src\\main\\webapp\\pngs\\"+size;
		// TODO Auto-generated method stub
		Collection<File> pngs = FileUtils.listFiles(new File(path),new String[] { "png", "gif" }, false);

		//生成到menu的目录下面，只有menu会引用这个最全的css
		FileWriter fos = new FileWriter("E:\\eclipse\\workspace\\leon\\leon-web-common\\src\\main\\webapp\\common" + "\\pngs.css");
		BufferedWriter bw = new BufferedWriter(fos);
		for (File file : pngs) {
			String aa = "." + getPngClsName(file.getName(),32)+ "{background: url(../pngs/32/" + file.getName()+ ") left top no-repeat !important;}";
			bw.append(aa);
			aa = "." + getPngClsName(file.getName(),16)+ "{background: url(../pngs/16/" + file.getName()+ ") left top no-repeat !important;}";
			bw.append(aa);
			bw.newLine();
			// System.out.println(aa);
		}
		bw.close();
		fos.close();

	}
	/**
	 * 根据数据库中使用了的png图标生成css文件，降低css文件的大小
	 * 注意要同时生成32位和64位的 css
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @throws IOException
	 */
	public static void generatorPngCss(String cssPath, String img) throws IOException {
//		// String path1=IconUtils.class.getResource("/").getPath();
//
//		String path = "E:\\eclipse\\workspace\\leon\\leon-web-common\\src\\main\\webapp\\pngs\\16";
//		// TODO Auto-generated method stub
//		Collection<File> pngs = FileUtils.listFiles(new File(path),new String[] { "png", "gif" }, true);
//
//		//生成到menu的目录下面，只有menu会引用这个最全的css
//		FileWriter fos = new FileWriter("E:\\eclipse\\workspace\\leon\\leon-web-common\\src\\main\\webapp\\desktop\\menu" + "\\pngs.css");
//		BufferedWriter bw = new BufferedWriter(fos);
//		for (File file : pngs) {
//			String aa = "." + getClsName(file.getName())
//					+ "{background: url(../pngs/16/" + file.getName()+ ") left top no-repeat !important;}";
//			bw.append(aa);
//			bw.newLine();
//			// System.out.println(aa);
//		}
//		bw.close();
//		fos.close();

	}
	public static void generatorIcon() throws IOException{
		//String path1=IconUtils.class.getResource("/").getPath();
		
		String path="E:\\eclipse\\workspace\\leon\\leon-web-common\\src\\main\\webapp\\icons";
		// TODO Auto-generated method stub
		Collection<File> pngs=FileUtils.listFiles(new File(path), new String[]{"png","gif"}, true);
		
		FileWriter  fos=new FileWriter(path+"\\icons.css");  
		BufferedWriter bw=new BufferedWriter(fos);  
		for(File file:pngs){
			String aa="."+getClsName(file.getName())+"{background: url(../icons/"+file.getName()+") left top no-repeat !important;}";
			bw.append(aa);
			bw.newLine();
			//System.out.println(aa);
		}
		bw.close();
		fos.close();
	}
	
	public static String getClsName(String fileName){
		return "icons_"+fileName.substring(0,fileName.lastIndexOf("."));
	}

}
