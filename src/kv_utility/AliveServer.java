package kv_utility;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by gmeneze on 12/4/16.
 */
public class AliveServer implements Runnable {
    ServerSocket ping_server = null;

    public AliveServer() {
        try {
            ping_server = new ServerSocket(ProjectConstants.ALIVE_LISTEN_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSetUp() {
        return ping_server != null;
    }

    @Override
    public void run() {
        try {
            while (ProjectGlobal.is_alive_server_on) {
                ping_server.accept().close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ping_server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
