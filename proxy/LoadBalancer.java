package proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LoadBalancer {
	public static void main(String[] args) throws IOException{
		ServerSocket listener = new ServerSocket(8080);
		ProxyHandler proxy = new ProxyHandler();
		proxy.addAllIp();
		proxy.display();
		try {
			while(true){
				new Proxy(listener.accept(), proxy).start();
			}
        }
        finally {
            listener.close();
        }
	}
	
	private static class Proxy extends Thread {
		private Socket socket;
		private ProxyHandler proxy;
		
		public Proxy(Socket socket, ProxyHandler proxy){
			this.socket = socket;
			this.proxy = proxy;
		}
		
		public void run(){
			try{
				BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
				System.out.println(in);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				proxy.display();
				out.println(proxy.ips.get(0));
				out.flush();
				proxy.rPopLPush();
			} catch (IOException e) {
                System.out.println("Error handling the request "+ e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Couldn't close the socket, what's going on?");
                }
                System.out.println("Connection with client closed");
            }
		}
	}
}
