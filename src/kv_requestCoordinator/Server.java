package kv_requestCoordinator;

import kv_utility.*;

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

        /* Test Code */
        /*
        RequestCoordinator rc = new RequestCoordinator();
        ClientRequestPacket testRequestPacket = new ClientRequestPacket();
        testRequestPacket.setIp_address("0");
        System.out.println("ip address is: " + testRequestPacket.getIp_address());
        rc.addNode(testRequestPacket);
        testRequestPacket.setIp_address("50");
        rc.addNode(testRequestPacket);
        testRequestPacket.setIp_address("25");
        rc.addNode(testRequestPacket);
        testRequestPacket.setIp_address("75");
        rc.addNode(testRequestPacket);
        testRequestPacket.setIp_address("12");
        rc.addNode(testRequestPacket);
        testRequestPacket.setIp_address("37");
        ClientResponsePacket responsePacket = rc.addNode(testRequestPacket);

        System.out.println(" syncips size : " + responsePacket.getMemNodeSyncHelper().syncIps.size());

        for(MemNodeSyncDetails details : responsePacket.getMemNodeSyncHelper().syncIps)
        {
            System.out.println("node Ip : " + details.getIp_Address() + " start range: " + details.getStart_range() + " end range: " + details.getEnd_range());
        }
        */
        /* Test Code ends */

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
            proxySocket = new Socket(proxyIpAddress, ProjectConstants.PR_LISTEN_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        requestPacket.setCommand(ProjectConstants.ADD_RC_NODES);
        requestPacket.setIp_address(host_address.getHostAddress());
        PacketTransfer.sendRequest(requestPacket, proxySocket);
        ClientResponsePacket res_packet = PacketTransfer.recv_response(proxySocket);
        System.out.println("Response Recieved : " + res_packet.getResponse_code());
        startServer();
    }
}