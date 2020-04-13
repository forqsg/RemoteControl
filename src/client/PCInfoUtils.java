package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import Message.ProcessInfo;

public class PCInfoUtils {
	public static Sigar sigar = new Sigar();
	public static Runtime runtime = Runtime.getRuntime();
	public static String getMAC() {
		String MAC="";
		try {
			String[] ifaces = sigar.getNetInterfaceList();
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
						|| cfg.getAddress().equals("0.0.0.0")
						|| (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				MAC=cfg.getHwaddr();
				break;
			}
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MAC;
	}
	public static String getOSName()
	{
		
		Properties props=System.getProperties(); 
		String osName = props.getProperty("os.name");
		return osName;

	}

	public static ProcessInfo[] getTaskList()
	{
		ProcessInfo[] procInfos = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			String cmd="cmd /c tasklist";
			Process exec = runtime.exec(cmd);
			BufferedReader br=new BufferedReader(new InputStreamReader(exec.getInputStream()));
			String result="";
			boolean isValid=false;
			List<ProcessInfo> list=new ArrayList<ProcessInfo>();
			while((result=br.readLine())!=null)
			{
				if(isValid)
				{
					String[] infos=result.split("\\s{2,}");
					String[] details=infos[1].split("\\s");// PID and 会话名
					
					ProcessInfo procInfo=new ProcessInfo();
					procInfo.name=infos[0];
					procInfo.PID=details[0];
					procInfo.huihuaName=details[1];
					procInfo.memory=infos[3];
					list.add(procInfo);
					
				}
				else
				{
					if(result.contains("system")||result.contains("System"))
					{
						isValid=true;
						String[] infos=result.split("\\s{2,}");
						String[] details=infos[1].split("\\s");// PID and 会话名

						ProcessInfo procInfo=new ProcessInfo();
						procInfo.name=infos[0];
						procInfo.PID=details[0];
						procInfo.huihuaName=details[1];
						procInfo.memory=infos[3];
						list.add(procInfo);
					}
				}
				
			}
			procInfos=new ProcessInfo[list.size()];
			Iterator<ProcessInfo> iterator = list.iterator();
			int i=0;
			while(iterator.hasNext())
			{
				procInfos[i++]=iterator.next();
			}
			
		} catch (IOException e) {
			System.out.println("获取进程列表出错");
		}
		return procInfos;
		
	}
	public static String getPerformance() //内存总量，使用量，剩余量，CPU总量，用户使用，系统使用，空闲，总使用量
	{
		StringBuffer sb=new StringBuffer();
		try {
			Mem mem = sigar.getMem();
			sb.append(mem.getTotal()/1024L/1024L+"MB//");
			sb.append(mem.getUsed()/1024L/1024L+"MB//");
			sb.append(mem.getFree()/1024L/1024L+"MB//");
			
			sb.append(sigar.getCpuInfoList()[0].getMhz()+"MHz//");
			CpuPerc cpuPerc = sigar.getCpuPercList()[0];

			sb.append(CpuPerc.format(cpuPerc.getUser())+"//");
			sb.append(CpuPerc.format(cpuPerc.getSys())+"//");
			sb.append(CpuPerc.format(cpuPerc.getIdle())+"//");
			sb.append(CpuPerc.format(cpuPerc.getCombined())+"//");
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
//	
//	public static void main(String[] args) {
//
//			//System.out.println(getMAC());
//			//System.out.println(getOSName());\
//			//System.out.println(getTaskList()[0].name);
//		System.out.println(getOSName());
//
//	}
	
	
}
