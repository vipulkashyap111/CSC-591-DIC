package kv_memNodes;

import kv_utility.ClientRequestPacket;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by abhishek on 11/26/16.
 * Class responsible for handling command request from Request Co-Ordinator
 *
 */
public class CommandHandler implements Runnable
{
    private Socket req = null;
    private ObjectInputStream ois = null;
    private ClientRequestPacket req_packet = null;

    public CommandHandler(Socket req)
    {
        this.req = req;
    }

    @Override
    public void run() {
        
    }

    public void handle_request(ClientRequestPacket req_packet) {

    }
}
