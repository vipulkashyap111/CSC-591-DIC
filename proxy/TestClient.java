package proxy;

import java.net.*;
import java.io.*;

public class TestClient{
	public static void main(String[] args) throws IOException{
		Socket client;
		try{
			client = new Socket("127.0.0.1", 8080);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			BufferedReader in = new BufferedReader(
	                new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			System.out.println("Message "+ in.readLine());
			client.close();
		} catch (IOException e) {
	        System.out.println(e);
	    }
	}	
}
