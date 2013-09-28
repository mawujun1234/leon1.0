package com.mawujun.monitor.system;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.win32.LocaleInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MonitorSystemController {
	public enum SystenInfoType {
		SYTEM("系统"),CPUINFO("CPU"),FREEINFO("内存"),FILESYSTEMINFO("文件系统"),NETWORK("网络");
		private String name;
		SystenInfoType(String name){
			this.name=name;
		}
		public String getName(){
			return name;
		}
		public String toString(){
			return this.getName();
		}
	}

	 private static String getHostName() {
	        try {
	            return InetAddress.getLocalHost().getHostName();
	        } catch (UnknownHostException e) {
	            return "unknown";
	        }
	    }
	 List<Map<String, String>> list=new ArrayList<Map<String, String>>();
	 private void addMap(String name,String value,SystenInfoType group){
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("value", value);
		map.put("group", group.toString());
		list.add(map);
	 }
	@RequestMapping("/monitorSystem/querySystemInfo")
	public List<Map<String, String>> querySystemInfo() throws SigarException {
		if(list.size()>0){
			list.clear();
			//return list;
		}
		String host = getHostName();
		addMap("主机名称",host,SystenInfoType.SYTEM);
		
		String fqdn;
		Sigar sigar = new Sigar();
		try {
			//File lib = sigar.getNativeLibrary();
			fqdn = sigar.getFQDN();	
		} catch (SigarException e) {
			fqdn = "unknown";
		} finally {
			//sigar.close();
		}
		addMap("主机全名",fqdn,SystenInfoType.SYTEM);
		
		if (SigarLoader.IS_WIN32) {
            LocaleInfo info = new LocaleInfo();
			addMap("语言",info.toString(),SystenInfoType.SYTEM);
        }
		addMap("当前用户",System.getProperty("user.name"),SystenInfoType.SYTEM);
		
		OperatingSystem sys = OperatingSystem.getInstance();
		addMap("操作系统描述",sys.getDescription(),SystenInfoType.SYTEM);
        addMap("操作系统名称",sys.getName(),SystenInfoType.SYTEM);
        addMap("操作系统内核类型",sys.getArch(),SystenInfoType.SYTEM);
        addMap("操作系统机器",sys.getMachine(),SystenInfoType.SYTEM);
        addMap("操作系统版本",sys.getVersion(),SystenInfoType.SYTEM);
        addMap("操作系统补丁包",sys.getPatchLevel(),SystenInfoType.SYTEM);
        addMap("操作系统厂商",sys.getVendor(),SystenInfoType.SYTEM);
        addMap("操作系统厂商版本",sys.getVendorVersion(),SystenInfoType.SYTEM);
        if (sys.getVendorCodeName() != null) {
            addMap("OS code name",sys.getVendorCodeName(),SystenInfoType.SYTEM);
        }
        addMap("操作系统内核版本",sys.getDataModel(),SystenInfoType.SYTEM);
        addMap("操作系统cpu endian",sys.getCpuEndian(),SystenInfoType.SYTEM);
        addMap("Java vm version",System.getProperty("java.vm.version"),SystenInfoType.SYTEM);
        addMap("Java vm vendor",System.getProperty("java.vm.vendor"),SystenInfoType.SYTEM);
        addMap("Java home", System.getProperty("java.home"),SystenInfoType.SYTEM);
        
        
        org.hyperic.sigar.CpuInfo[] infos = sigar.getCpuInfoList();
        //addMap("CPU个数", infos.length+"",SystenInfoType.CPUINFO);     
        org.hyperic.sigar.CpuInfo info = infos[0];
        long cacheSize = info.getCacheSize();
        addMap("厂商", info.getVendor(),SystenInfoType.CPUINFO);
        addMap("型号", info.getModel(),SystenInfoType.CPUINFO);
        addMap("频率", info.getMhz()+"",SystenInfoType.CPUINFO);
        addMap("逻辑处理器", info.getTotalCores()+"",SystenInfoType.CPUINFO);
        if ((info.getTotalCores() != info.getTotalSockets()) ||
            (info.getCoresPerSocket() > info.getTotalCores())) {
        	addMap("物理 CPU个数.", info.getTotalSockets()+"",SystenInfoType.CPUINFO);
        	addMap("CPU内核个数/个", info.getCoresPerSocket()+"",SystenInfoType.CPUINFO);

        }
        if (cacheSize != Sigar.FIELD_NOTIMPL) {
        	addMap("缓冲存储器数量", cacheSize+"",SystenInfoType.CPUINFO);
        }

        Mem mem   = sigar.getMem();
        Swap swap = sigar.getSwap();
        addMap("内存总量", mem.getTotal()/1024/1024+"",SystenInfoType.FREEINFO);
        addMap("当前内存使用量", mem.getUsed()/1024/1024+"",SystenInfoType.FREEINFO);
        addMap("当前内存剩余量", mem.getFree()/1024/1024+"",SystenInfoType.FREEINFO);
        
       // addMap("Mem total", mem.getTotal()+"",SystenInfoType.FREEINFO);
        addMap("实际内存使用量", mem.getActualUsed()/1024/1024+"",SystenInfoType.FREEINFO);
        addMap("实际内存剩余量", mem.getActualFree()/1024/1024+"",SystenInfoType.FREEINFO);
        
        addMap("交换区总量", swap.getTotal()/1024/1024+"",SystenInfoType.FREEINFO);
        addMap("当前交换区使用量", swap.getUsed()/1024/1024+"",SystenInfoType.FREEINFO);
        addMap("当前交换区剩余量", swap.getFree()/1024/1024+"",SystenInfoType.FREEINFO);
        
        addMap("RAM", mem.getRam()+"",SystenInfoType.FREEINFO);
        
        http://kgd1120.iteye.com/blog/1254657
        FileSystem[] fileSystems=sigar.getFileSystemList();
        //fileSystems[0].get
        addMap("文件系统", Arrays.asList(fileSystems).toString(),SystenInfoType.FILESYSTEMINFO);
        
        addMap("网络接口",Arrays.asList(sigar.getNetInterfaceList()).toString(),SystenInfoType.NETWORK);
        sigar.close();
        return list;
	}

}
