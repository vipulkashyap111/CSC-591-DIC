package proxy;

import java.util.ArrayList;
import java.util.List;

public class ServersIP {

	List<String> ips = new ArrayList<String>();
	
	public List<String> getIpList(){
		return ips;
	}
	
	public void addIp(String ip){
		ips.add(ip);
	}
	
	public void deleteIp(String ip){
		int loc = ips.indexOf(ip);
		if(loc > (-1)){
			System.out.println("Successfully removed ip "+ip+ " from the list");
			ips.remove(loc);
		}
		else {
			System.out.println("The IP "+ ip + " does not exist!");
		}
	}
}
