package kv_memNodes;

import kv_utility.ProjectConstants;
import kv_utility.ProjectGlobal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by abhishek on 11/26/16.
 */
public class MemNodeProc {
    private static ExecutorService workers; /* Workers threads */
    private static ServerSocket request_listerner = null;
    private InetAddress host_address = null;
    private static CommandHandler request_handler;

    public static void main(String arg[]) {

    }

    public void startServer() {
        try {
            setUPMN();
            while (ProjectGlobal.is_MN_on) /* Listening on the service request */ {
                request_handler = new CommandHandler(request_listerner.accept());
                workers.execute(request_handler);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void setUPMN() throws IOException {
        host_address = InetAddress.getLocalHost();
        request_listerner = new ServerSocket(ProjectConstants.MN_LISTEN_PORT, ProjectConstants.REQUEST_BACK_LOG);
        workers = Executors.newFixedThreadPool(ProjectConstants.NUM_OF_WORKERS);
    }
}
