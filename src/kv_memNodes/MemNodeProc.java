package kv_memNodes;

import kv_utility.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by abhishek on 11/26/16.
 */
public class MemNodeProc {
    private static ExecutorService workers; /* Workers threads */
    private static ServerSocket request_listerner = null;
    private static InetAddress host_address = null;
    private static RequestHandle request_handler;
    private static KeyValueHM data_store = null;
    private static Date unixTimeGenerator;
    private static HashedBucketMap bucket_map = null;

    public static void main(String arg[]) {
        System.out.println("Starting Memory Node Server");
        if (arg.length != 1) {
            System.out.println("Please run program as below");
            System.out.println("MemNodeProx <PROXY_IP_ADDRESS>");
            System.exit(ProjectConstants.SUCCESS);
        }
        String proxy_address = arg[ProjectConstants.ZERO];
        try {
            notifyProxy(proxy_address);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(ProjectConstants.SUCCESS);
        }
        startServer();
    }

    public static KeyValueHM getData_store() {
        return data_store;
    }

    public static void setData_store(KeyValueHM data_store) {
        MemNodeProc.data_store = data_store;
    }

    public static void startServer() {
        try {
            setUPMN();
            while (ProjectGlobal.is_MN_on) /* Listening on the service request */ {
                request_handler = new RequestHandle(request_listerner.accept());
                workers.execute(request_handler);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void setUPMN() throws IOException {
        host_address = InetAddress.getLocalHost();
        request_listerner = new ServerSocket(ProjectConstants.MN_LISTEN_PORT, ProjectConstants.REQUEST_BACK_LOG);
        workers = Executors.newFixedThreadPool(ProjectConstants.NUM_OF_WORKERS);
        setData_store(new KeyValueHM(KVType.ORIGINAL));
        setBucket_map(new HashedBucketMap());
        setUnixTimeGenerator(new Date());
    }

    /* Notify the proxy which will update the request co-ordinator to upadte the ring structure */
    public static void notifyProxy(String proxy_address) throws IOException {
        Socket proxy_connect = new Socket(proxy_address, ProjectConstants.PR_LISTEN_PORT);

        ClientRequestPacket req_packet = new ClientRequestPacket();
        req_packet.setCommand(ProjectConstants.ADD_MEM_NODES);
        req_packet.setIp_address(InetAddress.getLocalHost().getHostAddress());
    }

    public static Date getUnixTimeGenerator() {
        return unixTimeGenerator;
    }

    public static void setUnixTimeGenerator(Date unixTimeGenerator) {
        MemNodeProc.unixTimeGenerator = unixTimeGenerator;
    }

    public static HashedBucketMap getBucket_map() {
        return bucket_map;
    }

    public static void setBucket_map(HashedBucketMap bucket_map) {
        MemNodeProc.bucket_map = bucket_map;
    }
}
