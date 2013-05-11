package com.mawujun.repository.mybatis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

import com.mawujun.utils.FileUtils;
import com.mawujun.utils.StringTokenizerUtils;
import com.mawujun.utils.SystemUtils;

//@Component
public class DymincReloadMaperEvent extends Thread{
	
	private final Log logger = LogFactory.getLog(getClass());
	private List<DymincReloadAction> dymincReloadActions;
	
	public List<DymincReloadAction> getDymincReloadActions() {
		return dymincReloadActions;
	}
	public void setDymincReloadActions(List<DymincReloadAction> dymincReloadActions) {
		this.dymincReloadActions = dymincReloadActions;
//		if(this.dymincReloadActions==null){
//    		return;
//    	}
//    	for(DymincReloadAction aa:dymincReloadActions){
//    		aa.setEvent(this);
//    	}
	}


	private List<String> folders;
	
	private boolean isJDK7=false;
	
	private boolean flag=true;

	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.startListener();
		//System.out.println("监听映射文件的线程开始。");
		logger.debug("监听映射文件的线程开始。");
		//int i = 0 ; 
        while(flag) { 
            //sdSystem.out.println(i " ") ; 
        } 
        //System.out.println("监听映射文件的线程关闭。");
        logger.debug("监听映射文件的线程关闭。");
	}
	//@Override
	public void stopThread(){
		 flag = false ; 
	}
	
	public DymincReloadMaperEvent(){
		//double version=Double.parseDouble(System.getProperty("java.version").substring(0, 3));
		double version=Double.parseDouble(System.getProperty("java.specification.version").substring(0, 3));
		//JavaVersion
		if(version>=1.7){
			isJDK7=true;
		}	
	}
	

//	public DymincReloadAction getDymincReloadAction() {
//		return dymincReloadAction;
//	}
//	
//	public void setDymincReloadAction(DymincReloadAction dymincReloadAction) {
//		this.dymincReloadAction = dymincReloadAction;	
//		//this.start();
//		//System.out.println("异步执行setDymincReloadAction");
//		//this.startListener();
//		
//	}
	
	public void addDymincReloadAction(DymincReloadAction dymincReloadAction) {
		this.dymincReloadActions.add(dymincReloadAction);	
	}
	public void setFolders(List<String> folders) {
		this.folders = folders;
	}
	
	//@Async
	public void startListener(){
		if(folders==null||folders.size()==0){
			return;
		}

		if(!isJDK7){
			try {
				this.copyDLLFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for(String folder:folders){

				String path=this.getClass().getResource("/").getPath()+folder;
				path=path.replace("%20", " "); 
				File file=new File(path);

			    // watch mask, specify events you care about,
			    // or JNotify.FILE_ANY for all events.
			    int mask = JNotify.FILE_CREATED  | 
			               JNotify.FILE_DELETED  | 
			               JNotify.FILE_MODIFIED ;
			               //JNotify.FILE_RENAMED;

			    // watch subtree?
			    boolean watchSubtree = true;

			    // add actual watch
			    int watchID=0;
			    try {
			    	//System.out.println(" JNotify.addWatch(");
			    	logger.debug(" JNotify.addWatch(");
					watchID = JNotify.addWatch(file.getAbsolutePath(), mask, watchSubtree, new Listener());
//					while(flag) { 
//			            //sdSystem.out.println(i " ") ; 
//			        } 
					//System.out.println(" =======================)");
					logger.debug(" =======================)");
				} catch (JNotifyException e) {
					e.printStackTrace();
				    boolean res=false;
					try {
						res = JNotify.removeWatch(watchID);
					} catch (JNotifyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    if (!res) {
				      // invalid watch ID specified.
				    }
				    e.printStackTrace();
				}
			}
		} else {
			
		}
	}
	
	public void copyDLLFile() throws IOException {
		String pathSeparator=System.getProperty("path.separator");
		String[] library=StringTokenizerUtils.split(System.getProperty("java.library.path"), pathSeparator);
    	for(String lib:library){
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"jnotify.dll"), new File(lib));
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"jnotify_64bit.dll"), new File(lib));
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"libjnotify.jnilib"), new File(lib));
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"libjnotify.so"), new File(lib));
    		getPath("jnotify.dll",lib);
    		getPath("jnotify_64bit.dll",lib);
    		getPath("libjnotify.jnilib",lib);
    		getPath("libjnotify.so",lib);
    		break;
    	}
	}
	/**
	 * 获取项目类的根路径
	 * @return
	 * @throws IOException 
	 */
	public String getPath(String fileName,String libPath) throws IOException {
		InputStream in = this.getClass().getResourceAsStream(fileName);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = in.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);

		data = null;
		File file=new File(libPath+SystemUtils.FILE_SEPARATOR+fileName);
		FileUtils.writeByteArrayToFile(file, outStream.toByteArray());
		//FileUtils.writeByteArrayToFile(new File("D:"+SystemUtils.FILE_SEPARATOR+fileName), outStream.toByteArray());

		return file.getAbsolutePath();
	}
	
//	public void copyDLLFile() throws IOException {
//		String pathSeparator=System.getProperty("path.separator");
//		String[] library=StringTokenizerUtils.split(System.getProperty("java.library.path"), pathSeparator);
//    	for(String lib:library){
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"jnotify.dll"), new File(lib));
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"jnotify_64bit.dll"), new File(lib));
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"libjnotify.jnilib"), new File(lib));
//    		FileUtils.copyFileToDirectory(new File(this.getPath()+"libjnotify.so"), new File(lib));
//    		break;
//    	}
//	}
//	/**
//	 * 获取项目类的根路径
//	 * @return
//	 */
//	public String getPath(){
//		return this.getClass().getResource("/com/mawujun/repository/mybatis").getPath().replace("%20", " "); 
//	}
	

	class Listener implements JNotifyListener {
		long lastCurrentTime=0l;
		
	    public void fileRenamed(int wd, String rootPath, String oldName,String newName) {
	    	
	    	if(dymincReloadActions==null){
	    		return;
	    	}
	    	for(DymincReloadAction aa:dymincReloadActions){
	    		aa.fileRenamed(wd,rootPath, oldName,newName);
	    	}
	    }
	    public void fileModified(int wd, String rootPath, String name) {
	    	
	    	if(dymincReloadActions==null){
	    		return;
	    	}
	    	for(DymincReloadAction aa:dymincReloadActions){
	    		aa.fileModified(rootPath, name);
	    	}
	      
	    }
	    public void fileDeleted(int wd, String rootPath, String name) {
	    	
	    	if(dymincReloadActions==null){
	    		return;
	    	}
	    	for(DymincReloadAction aa:dymincReloadActions){
	    		aa.fileDeleted(rootPath, name);
	    	}
	    	//dymincReloadAction.fileDeleted(rootPath, name);
	    }
	    public void fileCreated(int wd, String rootPath, String name) {
	    	
	    	if(dymincReloadActions==null){
	    		return;
	    	}
	    	for(DymincReloadAction aa:dymincReloadActions){
	    		aa.fileCreated(rootPath, name);
	    	}
	    	//dymincReloadAction.fileCreated(rootPath, name);
	    }
	    
	    
	}


	


	

}
