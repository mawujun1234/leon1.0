package com.mawujun.maven.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo( name = "generateP", defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class GenerateP_Mojo extends AbstractMojo {
	@Parameter
	private String driverClassName;
	@Parameter
	private String url;
	@Parameter
	private String username;
	@Parameter
	private String password;
	
	@Parameter
	private String targetMDir;

	public void execute() {
		// TODO Auto-generated method stub
		getLog().info("***********正在开始生成!************");
		
		
		try {
			List<String> ids = getIds();
			updatePjavaFile(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<String>  getIds() throws Exception{
		    Class.forName(driverClassName);
	        Connection conn = DriverManager.getConnection(url, username, password);
	        Statement stat = conn.createStatement();
	        ResultSet rest = stat.executeQuery("select id from leon_Parameter");
	        List<String> ids=new ArrayList<String>();
	        while (rest.next()) {
	            ids.add(rest.getString(1));
	        }
	        rest.close();
	        stat.close();
	        conn.close();
	        return ids;
	}

	/**
	 * 更新Pfile文件的内容
	 * @author mawujun 16064988@qq.com 
	 * @throws IOException
	 */
	public void updatePjavaFile(List<String> ids) throws IOException {
		
		StringBuffer buffer=new StringBuffer();
		buffer.append("package com.mawujun.utils;\n");
		buffer.append("/**\n");
		buffer.append(" * 这个类的内容是动态添加的,不要修改他，修改了也会被覆盖的\n");
		buffer.append(" * 里面的值是参数id，就是系统设置的各种参数的id\n");
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
		//String fileName=System.getProperty("user.dir")+SystemUtils.FILE_SEPARATOR+"src"+SystemUtils.FILE_SEPARATOR+"main"+SystemUtils.FILE_SEPARATOR+"java"+SystemUtils.FILE_SEPARATOR+"com"+SystemUtils.FILE_SEPARATOR+"mawujun"+SystemUtils.FILE_SEPARATOR+"parameter"+SystemUtils.FILE_SEPARATOR+"P.java";
		//System.out.println(fileName);
		File file=new File(targetMDir+File.separatorChar+"P.java");
    	//file.delete();
    	if(!file.exists()){
    		file.createNewFile();
    	}
		
		try {
			FileWriter writer = new FileWriter(file, false);
			 writer.write(buffer.toString());
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new BusinessException("修改P.class失败!");
		}
       
	}
}
