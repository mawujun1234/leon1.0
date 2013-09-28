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
public class MonitorController {
	public enum SystenInfoType {
		SYTEM,CPUINFO,FREEINFO,FILESYSTEMINFO,NETWORK
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
	@RequestMapping("/monitor/querySystemInfo")
	public List<Map<String, String>> querySystemInfo() throws SigarException {
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
			sigar.close();
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
        addMap("操作系统架构",sys.getArch(),SystenInfoType.SYTEM);
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
        CpuPerc[] cpus =sigar.getCpuPercList();
        org.hyperic.sigar.CpuInfo info = infos[0];
        long cacheSize = info.getCacheSize();
        addMap("Vendor", info.getVendor(),SystenInfoType.CPUINFO);
        addMap("Model", info.getModel(),SystenInfoType.CPUINFO);
        addMap("Mhz", info.getMhz()+"",SystenInfoType.CPUINFO);
        addMap("Total CPUs", info.getTotalCores()+"",SystenInfoType.CPUINFO);
        if ((info.getTotalCores() != info.getTotalSockets()) ||
            (info.getCoresPerSocket() > info.getTotalCores())) {
        	addMap("Physical CPUs.", info.getTotalSockets()+"",SystenInfoType.CPUINFO);
        	addMap("Cores per CPU", info.getCoresPerSocket()+"",SystenInfoType.CPUINFO);

        }
        if (cacheSize != Sigar.FIELD_NOTIMPL) {
        	addMap("Cache size", cacheSize+"",SystenInfoType.CPUINFO);
        }

        Mem mem   = sigar.getMem();
        Swap swap = sigar.getSwap();
        addMap("Mem total", mem.getTotal()+"",SystenInfoType.FREEINFO);
        addMap("Mem used", mem.getUsed()+"",SystenInfoType.FREEINFO);
        addMap("Mem free", mem.getFree()+"",SystenInfoType.FREEINFO);
        
       // addMap("Mem total", mem.getTotal()+"",SystenInfoType.FREEINFO);
        addMap("actual used", mem.getActualUsed()+"",SystenInfoType.FREEINFO);
        addMap("actual free", mem.getActualFree()+"",SystenInfoType.FREEINFO);
        
        addMap("swap total", swap.getTotal()+"",SystenInfoType.FREEINFO);
        addMap("swap used", swap.getUsed()+"",SystenInfoType.FREEINFO);
        addMap("swap free", swap.getFree()+"",SystenInfoType.FREEINFO);
        
        addMap("RAM", mem.getRam()+"",SystenInfoType.FREEINFO);
        
        
        FileSystem[] fileSystems=sigar.getFileSystemList();
        addMap("File Systems", Arrays.asList(fileSystems).toString(),SystenInfoType.FILESYSTEMINFO);
        
        addMap("Network Interfaces",Arrays.asList(sigar.getNetInterfaceList()).toString(),SystenInfoType.NETWORK);
        
        return list;
	}

}
