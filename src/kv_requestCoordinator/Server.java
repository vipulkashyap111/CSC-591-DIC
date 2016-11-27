package kv_requestCoordinator;

import kv_utility.ClientRequestPacket;
import kv_utility.PacketTransfer;
import kv_utility.ProjectConstants;
import kv_utility.ProjectGlobal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by gmeneze on 11/26/16.
 */
public class Server {
    private static ExecutorService workers; /* Workers threads */
    private static ConcurrentHashMap<String, ClientRequestHandler> activeClientList;
    private static ServerSocket clientRequest;
    private static InetAddress hostAddress;
    private static ClientRequestHandler currReq;
    private static String newClientReqId;


    public static void setUpServer() throws IOException, ClassNotFoundException {
        hostAddress = InetAddress.getLocalHost();
        clientRequest = new ServerSocket(ProjectConstants.RC_LISTEN_PORT, ProjectConstants.REQUEST_BACK_LOG);
        activeClientList = new ConcurrentHashMap<String, ClientRequestHandler>();
        workers = Executors.newFixedThreadPool(ProjectConstants.NUM_OF_WORKERS);


    }

    public static void cleanUpServer() {
        try {
            clientRequest.close(); /* Close the server socket connection */
            workers.shutdown();
            workers.awaitTermination(ProjectConstants.ONE, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServer() {
        try {
            setUpServer();

            while (ProjectGlobal.is_RC_on) {
                currReq = new ClientRequestHandler(clientRequest.accept());
                workers.execute(currReq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            cleanUpServer();
        }
    }

    public static void main(String[] args) {
        if (args.length > 1 || args.length == 0) {
            System.err.println("Invalid number of arguments");
            System.out.println("Run server as : java Server <proxy-ip-address>");
            System.exit(1);
        }

        String proxyIpAddress = args[0];
        Socket proxySocket = null;
        ClientRequestPacket requestPacket = new ClientRequestPacket();
        InetAddress host_address = null;

        try {
            host_address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            proxySocket = new Socket(proxyIpAddress, ProjectConstants.MN_LISTEN_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        requestPacket.setCommand(ProjectConstants.ADD_RC_NODES);
        requestPacket.setIp_address(host_address.getHostAddress());
        PacketTransfer.sendRequest(requestPacket, proxySocket);
        startServer();
    }
}
