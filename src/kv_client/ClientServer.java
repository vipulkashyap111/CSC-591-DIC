package kv_client;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import kv_utility.ClientResponsePacket;
import kv_utility.ProjectConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
 
/**
 *
 * @author vkkashya
 */
public class ClientServer {

    static String proxyIpAddress;
	public static void main(String[] args) {
		if (args.length > 1 || args.length == 0) {
			System.err.println("Invalid number of arguments");
			System.out.println("Run server as : java ClientServer <proxy-ip-address>");
			System.exit(1);
		}
        proxyIpAddress = args[0];
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
            System.out.println("Serving the " + he.getRequestMethod().toString() + " request");
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
		            ClientResponsePacket packet = rq.handle(proxyIpAddress, obj);
                    String response;
		            if(packet.getResponse_code() == ProjectConstants.SUCCESS){
                        String value = packet.getVal().getValue();

                        if(value != null){
                            response = packet.getMessage() + " " + packet.getVal().getValue();
                        } else
                            response = packet.getMessage();
                    } else{
		                response = "Failure while doing the operation!";
                    }

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
                    String response = e.getMessage();
                    // RESPONSE Headers
                    Headers responseHeaders = he.getResponseHeaders();

                    // Send RESPONSE Headers

                    //String response = "Welcome Real's HowTo test page";
                    he.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
                    // RESPONSE Body
                    OutputStream os = he.getResponseBody();
                    os.write(response.getBytes());
                    he.close();
		      }
	    } else{
	    	try{
	    		String response = "/put or /get should only be POST request";
	    	    he.sendResponseHeaders(400, response.length());
	    	    OutputStream os = he.getResponseBody();
	    	    os.write(response.getBytes());
	    	    os.close();
	    	} catch (Exception e){
	    		System.out.println("Exception thrown while writing response to GET/PUT");
	    		e.printStackTrace();
                String response = e.getMessage();
                // RESPONSE Headers
                Headers responseHeaders = he.getResponseHeaders();

                // Send RESPONSE Headers

                //String response = "Welcome Real's HowTo test page";
                he.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
                // RESPONSE Body
                OutputStream os = he.getResponseBody();
                os.write(response.getBytes());
                he.close();
	    	}
	    }	            
	    }
	    
	    public ClientRequestObject parse(StringBuilder buf, String command) throws Exception{
            ClientRequestObject obj = new ClientRequestObject();
            System.out.println(command);
            String[] pair = buf.toString().split(" ");
            if(pair.length>2)
                throw new Exception("Invalid request body");
            if(command.equals("/put")){
                obj.setCommand(ProjectConstants.PUT);
                if(pair.length != 2){
                    throw new Exception("Put request should have 2 arguments");
                }else {
                    obj.setKey(pair[0]);
                    obj.setValue(pair[1]);
                }
            } else {
                obj.setCommand(ProjectConstants.GET);
                obj.setKey(pair[0]);
            }
            System.out.println("Returned object is : " + obj.getCommand() + " " + obj.getKey() + " " + obj.getValue());
            return obj;
	    }
	}
}