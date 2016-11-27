package kv_proxy;

import kv_utility.ProjectConstants;
import kv_utility.ProjectGlobal;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by abhishek on 11/27/16.
 */
public class ProxyServer {
    public ExecutorService workers; /* Workers threads */
    private ProxyRequestHandler svc_req = null;
    public RCList rc_data = null;
    public ServerSocket proxy_connect = null;
    int rc_index = 0;

    public void start() {
        try {
            setUpProxy();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        startServer();
    }

    public void setUpProxy() throws IOException {
        rc_data = new RCList();
        workers = Executors.newFixedThreadPool(ProjectConstants.NUM_OF_WORKERS);
        proxy_connect = new ServerSocket(ProjectConstants.PR_LISTEN_PORT, ProjectConstants.REQUEST_BACK_LOG);
    }

    synchronized public int getNextIndex() {
        rc_index++;
        return rc_index % rc_data.getSize();
    }

    public void startServer() {
        try {
            while (ProjectGlobal.is_PROXY_on) {
                svc_req = new ProxyRequestHandler(proxy_connect.accept());
                workers.execute(svc_req);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
