package kv_requestCoordinator;

import kv_utility.ProjectConstants;
import kv_utility.ProjectGlobal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
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
}