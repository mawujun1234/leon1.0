package com.mawujun.monitor.system;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
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
        
        //http://kgd1120.iteye.com/blog/1254657
        FileSystem[] fileSystems=sigar.getFileSystemList();
        //fileSystems[0].get
        addMap("文件系统", Arrays.asList(fileSystems).toString(),SystenInfoType.FILESYSTEMINFO);
        
        addMap("网络接口",Arrays.asList(sigar.getNetInterfaceList()).toString(),SystenInfoType.NETWORK);
        sigar.close();
        return list;
	}
	
	/**
	 * 获取除系统信息外的所有其他信息，例如CPU,内存，硬盘，网络
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 * @throws SigarException
	 */
	@RequestMapping("/monitorSystem/queryOtherInfo")
	public List<Map<String, String>> queryOtherInfo() throws SigarException {
		return null;
	}
	
	Sigar sigar = new Sigar();
	private Map<String,Object> getCpuInfo() throws SigarException{
		Map<String,Object> cpuInfo=new HashMap<String,Object>();
		org.hyperic.sigar.CpuInfo[] infos = sigar.getCpuInfoList();
        //addMap("CPU个数", infos.length+"",SystenInfoType.CPUINFO);     
        org.hyperic.sigar.CpuInfo info = infos[0];
        long cacheSize = info.getCacheSize();
        //addMap("厂商", info.getVendor());
        cpuInfo.put("vendor",  info.getVendor());
        //addMap("型号", info.getModel());
        cpuInfo.put("model", info.getModel());
        //addMap("频率", info.getMhz()+"");
        cpuInfo.put("mhz",  info.getMhz());
        //addMap("逻辑处理器", info.getTotalCores());
        cpuInfo.put("totalCPUs", info.getTotalCores());
        if ((info.getTotalCores() != info.getTotalSockets()) ||
            (info.getCoresPerSocket() > info.getTotalCores())) {
        	//addMap("物理 CPU个数.", info.getTotalSockets(),SystenInfoType.CPUINFO);
        	cpuInfo.put("physicalCPUs", info.getTotalSockets());
        	//addMap("CPU内核个数/个", info.getCoresPerSocket(),SystenInfoType.CPUINFO);
        	cpuInfo.put("coresPerCPU", info.getCoresPerSocket());

        }
        if (cacheSize != Sigar.FIELD_NOTIMPL) {
        	//addMap("缓冲存储器数量", cacheSize,SystenInfoType.CPUINFO);
        	cpuInfo.put("cacheSize", cacheSize);// 缓冲存储器数量
        }
        
        CpuPerc cpu=this.sigar.getCpuPerc();
        cpuInfo.put("userTime" , CpuPerc.format(cpu.getUser()));//用户使用率
        cpuInfo.put("sysTime" , CpuPerc.format(cpu.getSys()));//系统使用率
        cpuInfo.put("idleTime" , CpuPerc.format(cpu.getIdle()));//当前空闲率:
        cpuInfo.put("waitTime" , CpuPerc.format(cpu.getWait()));//当前等待率
        cpuInfo.put("niceTime" , CpuPerc.format(cpu.getNice()));
        cpuInfo.put("combined" , CpuPerc.format(cpu.getCombined()));//总的使用率
        cpuInfo.put("irqTime" , CpuPerc.format(cpu.getIrq()));
        if (SigarLoader.IS_LINUX) {
            cpuInfo.put("softIrqTime" , CpuPerc.format(cpu.getSoftIrq()));
            cpuInfo.put("stolenTime" , CpuPerc.format(cpu.getStolen()));
        }
        
//     // 方式二，当多个cpu的时候，获取多个cpu的使用情况
//        CpuPerc cpuList[] = null;  
//        try {  
//            cpuList = sigar.getCpuPercList();  
//        } catch (SigarException e) {  
//            e.printStackTrace();  
//            return;  
//        }  
//        for (int i = 0; i < cpuList.length; i++) {  
//            printCpuPerc(cpuList[i]);  
//        }  
        return cpuInfo;
	}
	
	private Map<String, Object> getMemoryInfo() throws SigarException {
		Map<String,Object> memoryInfo=new HashMap<String,Object>();
		
		Mem mem = sigar.getMem();
		Swap swap = sigar.getSwap();
//		addMap("内存总量", mem.getTotal() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("当前内存使用量", mem.getUsed() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("当前内存剩余量", mem.getFree() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
		memoryInfo.put("total", mem.getTotal() / 1024 / 1024);
		memoryInfo.put("used",  mem.getUsed() / 1024 / 1024);
		memoryInfo.put("free", mem.getFree() / 1024 / 1024);
		

//		// addMap("Mem total", mem.getTotal()+"",SystenInfoType.FREEINFO);
//		addMap("实际内存使用量", mem.getActualUsed() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("实际内存剩余量", mem.getActualFree() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
		memoryInfo.put("actual_total", mem.getTotal() / 1024 / 1024);
		memoryInfo.put("actual_used",  mem.getActualUsed() / 1024 / 1024);
		memoryInfo.put("actual_free", mem.getActualFree() / 1024 / 1024);

//		addMap("交换区总量", swap.getTotal() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("当前交换区使用量", swap.getUsed() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("当前交换区剩余量", swap.getFree() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
		memoryInfo.put("swap_total", swap.getTotal() / 1024 / 1024);
		memoryInfo.put("swap_used",  swap.getUsed() / 1024 / 1024);
		memoryInfo.put("swap_free", swap.getFree() / 1024 / 1024);
		
		return memoryInfo;
	}
	
	private Map<String, Object> getFileSystemInfo() throws SigarException {
		FileSystem fslist[] = sigar.getFileSystemList();  
		//http://kgd1120.iteye.com/blog/1254657
        DecimalFormat df = new DecimalFormat("#0.00");  
        // String dir = System.getProperty("user.home");// 当前用户文件夹路径  
        for (int i = 0; i < fslist.length; i++) {  
            System.out.println("\n~~~~~~~~~~" + i + "~~~~~~~~~~");  
            FileSystem fs = fslist[i];  
            // 分区的盘符名称  
            System.out.println("fs.getDevName() = " + fs.getDevName());  
            // 分区的盘符名称  
            System.out.println("fs.getDirName() = " + fs.getDirName());  
            System.out.println("fs.getFlags() = " + fs.getFlags());//  
            // 文件系统类型，比如 FAT32、NTFS  
            System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());  
            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等  
            System.out.println("fs.getTypeName() = " + fs.getTypeName());  
            // 文件系统类型  
            System.out.println("fs.getType() = " + fs.getType());  
            FileSystemUsage usage = null;  
            try {  
                usage = sigar.getFileSystemUsage(fs.getDirName());  
            } catch (SigarException e) {  
                if (fs.getType() == 2)  
                    throw e;  
                continue;  
            }  
            switch (fs.getType()) {  
            case 0: // TYPE_UNKNOWN ：未知  
                break;  
            case 1: // TYPE_NONE  
                break;  
            case 2: // TYPE_LOCAL_DISK : 本地硬盘  
                // 文件系统总大小  
                System.out.println(" Total = " + df.format((float)usage.getTotal()/1024/1024) + "G");  
                // 文件系统剩余大小  
                System.out.println(" Free = " + df.format((float)usage.getFree()/1024/1024) + "G");  
                // 文件系统可用大小  
                System.out.println(" Avail = " + df.format((float)usage.getAvail()/1024/1024) + "G");  
                // 文件系统已经使用量  
                System.out.println(" Used = " + df.format((float)usage.getUsed()/1024/1024) + "G");  
                double usePercent = usage.getUsePercent() * 100D;  
                // 文件系统资源的利用率  
                System.out.println(" Usage = " + df.format(usePercent) + "%");  
                break;  
            case 3:// TYPE_NETWORK ：网络  
                break;  
            case 4:// TYPE_RAM_DISK ：闪存  
                break;  
            case 5:// TYPE_CDROM ：光驱  
                break;  
            case 6:// TYPE_SWAP ：页面交换  
                break;  
            }  
            System.out.println(" DiskReads = " + usage.getDiskReads());  
            System.out.println(" DiskWrites = " + usage.getDiskWrites());  
        }  
        return;  
	}

}
