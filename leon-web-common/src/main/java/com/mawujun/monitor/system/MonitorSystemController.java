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
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.win32.LocaleInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	/**
	 * 获取硬盘盘符的个数
	 * 
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 * @throws SigarException
	 */
	@RequestMapping("/monitorSystem/getDevNames")
	@ResponseBody
	public List<String> getDevNames() throws SigarException {
		FileSystem fslist[] = sigar.getFileSystemList();  
		//http://kgd1120.iteye.com/blog/1254657
        DecimalFormat df = new DecimalFormat("#0.00");  
        // String dir = System.getProperty("user.home");// 当前用户文件夹路径  
        List<String> devNames=new ArrayList<String>();
        for (int i = 0; i < fslist.length; i++) {  
        	if (fslist[i].getType() == 2){
        		devNames.add(fslist[i].getDevName());
        	}
        }
        return devNames;
	}
	
	/**
	 * 获取除系统信息外的所有其他信息，例如CPU,内存，硬盘，网络
	 * @author mawujun email:16064988@163.com qq:16064988
	 * @return
	 * @throws SigarException
	 */
	@RequestMapping("/monitorSystem/querySystemInfo")
	public Map<String,Object> queryOtherInfo() throws SigarException {
		Map<String,Object> systemInfo=getSystemInfo();
		Map<String,Object> cpuInfo=getCpuInfo();
		Map<String,Object> memoryInfo=getMemoryInfo();
		Map<String,Object> fileSystemInfo=getFileSystemInfo();
		Map<String,Object> netInfo=getNetInfo();
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("systemInfo", systemInfo);
		result.put("cpuInfo", cpuInfo);
		result.put("memoryInfo", memoryInfo);
		result.put("fileSystemInfo", fileSystemInfo);
		result.put("netInfo", netInfo);

		return result;
	}
	
	Sigar sigar = new Sigar();
	
	private Map<String,Object> getSystemInfo() throws SigarException{
		Map<String,Object> systemInfo=new HashMap<String,Object>();
		String host = getHostName();
		//addMap("主机名称",host,SystenInfoType.SYTEM);
		systemInfo.put("host", host);//主机名称
		
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
		//addMap("主机全名",fqdn,SystenInfoType.SYTEM);
		systemInfo.put("fqdn", fqdn);//主机全名
		
		if (SigarLoader.IS_WIN32) {
            LocaleInfo info = new LocaleInfo();
			//addMap("语言",info.toString(),SystenInfoType.SYTEM);
			systemInfo.put("localeInfo", info.toString());//语言
			
        }
		systemInfo.put("user_name", System.getProperty("user.name"));//当前用户
		
		OperatingSystem sys = OperatingSystem.getInstance();
		
        // 操作系统内核类型如： 386、486、586等x86  
        systemInfo.put("arch", sys.getArch());//操作系统内核类型
        systemInfo.put("dataModel", sys.getDataModel());//操作系统内核版本
        systemInfo.put("cpuEndian", sys.getCpuEndian());//"操作系统cpu endian
        // 系统描述  
     	systemInfo.put("description", sys.getDescription());//操作系统描述
        systemInfo.put("name", sys.getName());//操作系统名称
        //systemInfo.put("machine", sys.getMachine());//操作系统机器
        systemInfo.put("version", sys.getVersion());//操作系统版本
        systemInfo.put("patchlevel", sys.getPatchLevel());//操作系统补丁包
        systemInfo.put("vendor", sys.getVendor());//操作系统厂商
        systemInfo.put("vendorVersion", sys.getVendorVersion());//操作系统厂商版本
        if (sys.getVendorCodeName() != null) {
            systemInfo.put("vendorcodename", sys.getVendorCodeName());
        }
        
        
        systemInfo.put("java_vm_version", System.getProperty("java.vm.version"));//
        systemInfo.put("java_vm_vendor", System.getProperty("java.vm.vendor"));
        systemInfo.put("java_home", System.getProperty("java.home"));
        
        return systemInfo;
	}
	private Map<String,Object> getCpuInfo() throws SigarException{
		Map<String,Object> cpuInfo=new HashMap<String,Object>();
		org.hyperic.sigar.CpuInfo[] infos = sigar.getCpuInfoList();
        //addMap("CPU个数", infos.length+"",SystenInfoType.CPUINFO);     
        org.hyperic.sigar.CpuInfo info = infos[0];
       
        cpuInfo.put("cpu_vendor",  info.getVendor());//厂商
        cpuInfo.put("cpu_model", info.getModel());//型号
        cpuInfo.put("cpu_mhz",  info.getMhz());//频率
        cpuInfo.put("cpu_totalCPUs", info.getTotalCores());//逻辑处理器
//        if ((info.getTotalCores() != info.getTotalSockets()) ||
//            (info.getCoresPerSocket() > info.getTotalCores())) {
//        	cpuInfo.put("physicalCPUs", info.getTotalSockets());//物理 CPU个数
//        	cpuInfo.put("coresPerCPU", info.getCoresPerSocket());//CPU内核个数/个
//
//        }
//        long cacheSize = info.getCacheSize();
//        if (cacheSize != Sigar.FIELD_NOTIMPL) {
//        	cpuInfo.put("cacheSize", cacheSize);// 缓冲存储器数量
//        }
        
        CpuPerc cpu=this.sigar.getCpuPerc();
        cpuInfo.put("cpu_userTime" , CpuPerc.format(cpu.getUser()));//用户使用率
        cpuInfo.put("cpu_sysTime" , CpuPerc.format(cpu.getSys()));//系统使用率
        cpuInfo.put("cpu_idleTime" , CpuPerc.format(cpu.getIdle()));//当前空闲率:
        cpuInfo.put("cpu_waitTime" , CpuPerc.format(cpu.getWait()));//当前等待率
        cpuInfo.put("cpu_niceTime" , CpuPerc.format(cpu.getNice()));
        cpuInfo.put("cpu_combined" , CpuPerc.format(cpu.getCombined()));//总的使用率
        cpuInfo.put("cpu_irqTime" , CpuPerc.format(cpu.getIrq()));
//        if (SigarLoader.IS_LINUX) {
//            cpuInfo.put("softIrqTime" , CpuPerc.format(cpu.getSoftIrq()));
//            cpuInfo.put("stolenTime" , CpuPerc.format(cpu.getStolen()));
//        }
        
        
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
		memoryInfo.put("mem_total", mem.getTotal() / 1024 / 1024);
		memoryInfo.put("mem_used",  mem.getUsed() / 1024 / 1024);
		memoryInfo.put("mem_free", mem.getFree() / 1024 / 1024);
		

//		// addMap("Mem total", mem.getTotal()+"",SystenInfoType.FREEINFO);
//		addMap("实际内存使用量", mem.getActualUsed() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("实际内存剩余量", mem.getActualFree() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
		memoryInfo.put("mem_actual_total", mem.getTotal() / 1024 / 1024);
		memoryInfo.put("mem_actual_used",  mem.getActualUsed() / 1024 / 1024);
		memoryInfo.put("mem_actual_free", mem.getActualFree() / 1024 / 1024);

//		addMap("交换区总量", swap.getTotal() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("当前交换区使用量", swap.getUsed() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
//		addMap("当前交换区剩余量", swap.getFree() / 1024 / 1024 + "",SystenInfoType.FREEINFO);
		memoryInfo.put("mem_swap_total", swap.getTotal() / 1024 / 1024);
		memoryInfo.put("mem_swap_used",  swap.getUsed() / 1024 / 1024);
		memoryInfo.put("mem_swap_free", swap.getFree() / 1024 / 1024);
		
		return memoryInfo;
	}
	
	private Map<String, Object> getFileSystemInfo() throws SigarException {
		
		Map<String,Object> fileSystemInfo=new HashMap<String,Object>();
		
		FileSystem fslist[] = sigar.getFileSystemList();  
		//http://kgd1120.iteye.com/blog/1254657
        DecimalFormat df = new DecimalFormat("#0.00");  
        // String dir = System.getProperty("user.home");// 当前用户文件夹路径  
        for (int i = 0; i < fslist.length; i++) {  
            FileSystem fs = fslist[i];  

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
            	StringBuilder builder=new StringBuilder();
            	builder.append(fs.getSysTypeName()+";");
            	builder.append( df.format((float)usage.getTotal()/1024/1024) + "G"+";");
            	builder.append(df.format((float)usage.getFree()/1024/1024) + "G"+";");
            	//builder.append(df.format((float)usage.getAvail()/1024/1024) + "G"+";");
            	builder.append(df.format((float)usage.getUsed()/1024/1024) + "G"+";");
            	
            	fileSystemInfo.put(fs.getDevName()+"_devInfo", builder);
            	
//            	// 分区的盘符名称  
//                fileSystemInfo.put(fs.getDevName()+"_devName", fs.getDevName());
////                // 分区的盘符名称  
////                fileSystemInfo.put(fs.getDevName()+"_dirName", fs.getDirName());
//                //System.out.println("fs.getFlags() = " + fs.getFlags());//  
//                // 文件系统类型，比如 FAT32、NTFS  
//                fileSystemInfo.put(fs.getDevName()+"_sysTypeName",  fs.getSysTypeName());
////                // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等   
////                fileSystemInfo.put(fs.getDevName()+"_typeName", fs.getTypeName());
////                // 文件系统类型  
////                fileSystemInfo.put(fs.getDevName()+"_type", fs.getType());
//                // 文件系统总大小  
//                fileSystemInfo.put(fs.getDevName()+"_total", df.format((float)usage.getTotal()/1024/1024) + "G");
//                // 文件系统剩余大小  
//                fileSystemInfo.put(fs.getDevName()+"_free", df.format((float)usage.getFree()/1024/1024) + "G");
//                // 文件系统可用大小   
//                fileSystemInfo.put(fs.getDevName()+"_avail", df.format((float)usage.getAvail()/1024/1024) + "G");
//                // 文件系统已经使用量    
//                fileSystemInfo.put(fs.getDevName()+"_used", df.format((float)usage.getUsed()/1024/1024) + "G");
//                double usePercent = usage.getUsePercent() * 100D;  
//                // 文件系统资源的利用率  
//                fileSystemInfo.put(fs.getDevName()+"_usage",  df.format(usePercent) + "%");
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
            //System.out.println(" DiskReads = " + usage.getDiskReads());  
            fileSystemInfo.put(fs.getDevName()+"_diskreads",  usage.getDiskReads());
            //System.out.println(" DiskWrites = " + usage.getDiskWrites());  
            fileSystemInfo.put(fs.getDevName()+"_diskwrites",  usage.getDiskWrites());
        }  
        return fileSystemInfo;  
	}
	
	private Map<String, Object> getNetInfo() throws SigarException {
		Map<String,Object> netInfo=new HashMap<String,Object>();
		
		String ifNames[] = sigar.getNetInterfaceList();  
        for (int i = 0; i < ifNames.length; i++) {  
            String name = ifNames[i];  
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);  
            netInfo.put("net_name" , name);// 网络设备名  
            netInfo.put("net_address" , ifconfig.getAddress());// IP地址  
            netInfo.put("net_netmask" , ifconfig.getNetmask());// 子网掩码  
            netInfo.put("net_macAddr" , ifconfig.getHwaddr());// 网卡MAC地址  
            if ((ifconfig.getFlags() & 1L) <= 0L) {  
            	System.out.println("!IFF_UP...skipping getNetInterfaceStat");  
                continue;  
            }  
            try {  
                NetInterfaceStat ifstat=sigar.getNetInterfaceStat(name);  
                netInfo.put("net_rxpackets" , ifstat.getRxPackets());// 接收的总包裹数  
                netInfo.put("net_txpackets" , ifstat.getTxPackets());// 发送的总包裹数  
                netInfo.put("net_rxbytes" , ifstat.getRxBytes());// 接收到的总字节数  
                netInfo.put("net_txbytes" , ifstat.getTxBytes());// 发送的总字节数  
                netInfo.put("net_rxerrors" , ifstat.getRxErrors());// 接收到的错误包数  
                netInfo.put("net_txerrors" , ifstat.getTxErrors());// 发送数据包时的错误数  
                netInfo.put("net_rxdropped" , ifstat.getRxDropped());// 接收时丢弃的包数  
                netInfo.put("net_txdropped" , ifstat.getTxDropped());// 发送时丢弃的包数  
            } catch (SigarNotImplementedException e) {  
            } catch (SigarException e) {  
            	System.out.println(e.getMessage());  
            }  
        }  
        return netInfo;
	}

}
