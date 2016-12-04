package kv_client;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
 
/**
 *
 * @author vkkashya
 */
public class ClientServer {
 
	public static void main(String[] args) {
		try {
	        // Bind to port 8080
	        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
	        // Adding '/put' context
	        httpServer.createContext("/put", new RequestHandler());
	        // Adding 'get' context
	        httpServer.createContext("/get", new RequestHandler());
	        // Start the server
	        httpServer.start();
	    } catch (IOException ex) {
	    	System.out.println("Exception while creating server "+ ex);
	    }
	}
 
    // Handler for '/' context
	static class RequestHandler implements HttpHandler {
	    @Override
	    public void handle(HttpExchange he) throws IOException {
	        System.out.println("Serving the request");
	        // Serve for POST requests only 
	        if (he.getRequestMethod().equalsIgnoreCase("POST")) {
		        try {
		        	ClientRequestObject obj = new ClientRequestObject();
		        	ClientRequestHandler rq = new ClientRequestHandler();
		        	String command = he.getRequestURI().toString();
		        	// REQUEST Headers
		            Headers requestHeaders = he.getRequestHeaders();				            	
		          
		            //Parse text request body
		            InputStreamReader isr =  new InputStreamReader(he.getRequestBody(),"utf-8");
		            BufferedReader br = new BufferedReader(isr);		      

		            int b;
		            StringBuilder buf = new StringBuilder(512);
		            while ((b = br.read()) != -1) {
		                buf.append((char) b);
		            }
		            System.out.println(buf);
		            br.close();
		            isr.close();
		            
		            obj = parse(buf, command);
		            String response = rq.handle(obj);
		            // RESPONSE Headers
		            Headers responseHeaders = he.getResponseHeaders();
		
		            // Send RESPONSE Headers
		            
		            //String response = "Welcome Real's HowTo test page";
		            he.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
		            // RESPONSE Body
		            OutputStream os = he.getResponseBody();
		            os.write(response.getBytes());
		            he.close(); 
		            
		        } catch (Exception e) {
		        	System.out.println("Exception thrown while writing response to POST");
		        	e.printStackTrace();
		      }
	    } else{
	    	try{
	    		String response = "/put should only be POST request";
	    	    he.sendResponseHeaders(400, response.length());
	    	    OutputStream os = he.getResponseBody();
	    	    os.write(response.getBytes());
	    	    os.close();
	    	} catch (Exception e){
	    		System.out.println("Exception thrown while writing response to GET");
	    		e.printStackTrace();
	    	}
	    }	            
	    }
	    
	    public ClientRequestObject parse(StringBuilder buf, String command) throws Exception{
	    	ClientRequestObject obj = new ClientRequestObject();
	    	obj.setCommand(command);
	    	String[] pair = buf.toString().split(" ");
	    	if(pair.length>2)
	    		throw new Exception("Invalid request body");
	    	obj.setKey(pair[0]);
	    	obj.setValue(pair[1]);
 	    	return obj;
	    }
	}
}