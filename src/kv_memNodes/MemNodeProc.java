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
    private static DoubleLL time_sorted_list = null;

    public static void main(String arg[]) {
        System.out.println("Starting Memory Node Server");
        if (arg.length != 1) {
            System.out.println("Please run program as below");
            System.out.println("MemNodeProx <PROXY_IP_ADDRESS>");
            System.exit(ProjectConstants.SUCCESS);
        }
        String proxy_address = arg[ProjectConstants.ZERO];
        try {
            if (!notifyProxy(proxy_address)) {
                System.out.println("Unsucessfull attemp to communicate to the proxy...");
                return;
            }
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
        request_listerner = new ServerSocket(ProjectConstants.MN_LISTEN_PORT, ProjectConstants.REQUEST_BACK_LOG);
        workers = Executors.newFixedThreadPool(ProjectConstants.NUM_OF_WORKERS);
        setData_store(new KeyValueHM());
        setBucket_map(new HashedBucketMap());
        setTime_sorted_list(new DoubleLL());
        setUnixTimeGenerator(new Date());
    }

    /* Notify the proxy which will update the request co-ordinator to upadte the ring structure */
    public static boolean notifyProxy(String proxy_address) throws IOException {
        host_address = InetAddress.getLocalHost();
        Socket proxy_connect = new Socket(proxy_address, ProjectConstants.PR_LISTEN_PORT);
        ClientRequestPacket req_packet = new ClientRequestPacket();
        req_packet.setCommand(ProjectConstants.GET_RING);
        req_packet.setIp_address(InetAddress.getLocalHost().getHostAddress());
        PacketTransfer.sendRequest(req_packet, proxy_connect);
        ClientResponsePacket res_packet = PacketTransfer.recv_response(proxy_connect);
        System.out.println("Got Response : " + res_packet.getResponse_code() + res_packet.getRc_list().size());
        notifyRC(res_packet);
        return (res_packet != null && res_packet.getResponse_code() == ProjectConstants.SUCCESS);
    }

    /* Notify the RCs */
    public static boolean notifyRC(ClientResponsePacket res) throws IOException {
        Socket rc_conn = null;
        ClientRequestPacket req_packet = new ClientRequestPacket();
        ClientResponsePacket res_packet = null;
        req_packet.setCommand(ProjectConstants.ADD_MEM_NODES);
        for (int i = 0; i < res.getRc_list().size(); i++) {
            rc_conn = new Socket(res.getRc_list().get(i).ip, ProjectConstants.RC_LISTEN_PORT);
            req_packet.setIp_address(host_address.getHostAddress());
            PacketTransfer.sendRequest(req_packet, rc_conn);
            res_packet = PacketTransfer.recv_response(rc_conn);
            System.out.println("Notified RC : " + res_packet.getResponse_code());
        }
        return res_packet.getResponse_code() == ProjectConstants.SUCCESS;
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

    public static DoubleLL getTime_sorted_list() {
        return time_sorted_list;
    }

    public static void setTime_sorted_list(DoubleLL time_sorted_list) {
        MemNodeProc.time_sorted_list = time_sorted_list;
    }
}