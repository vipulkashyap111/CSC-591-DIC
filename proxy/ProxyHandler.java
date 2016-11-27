package proxy;

import java.util.List;

public class ProxyHandler {
	ServersIP obj = new ServersIP();
	List<String> ips = obj.getIpList();
	
	public void addAllIp(){
		obj.addIp("IP1");
		obj.addIp("IP2");
		obj.addIp("IP3");
		obj.addIp("IP4");
	}
	
	public List<String> rPopLPush(){	
		String last = ips.remove(ips.size()-1);
		ips.add(0, last);
		return ips;
	}
	
	public void display(){
		System.out.println("Current List");
		for(String ip: ips){
			System.out.println(ip);
		}
	}
}
