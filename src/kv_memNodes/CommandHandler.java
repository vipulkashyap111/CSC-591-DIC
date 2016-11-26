package kv_memNodes;

import kv_utility.ClientRequestPacket;
import kv_utility.ProjectConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by abhishek on 11/26/16.
 * Class responsible for handling command request from Request Co-Ordinator
 *
 */
public class CommandHandler implements Runnable
{
    private Socket req_socket = null;
    private ObjectInputStream ois = null;
    private ClientRequestPacket req_packet = null;

    public CommandHandler(Socket req)
    {
        this.req_socket = req;
    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(req_socket.getInputStream());
            req_packet = (ClientRequestPacket) ois.readObject();

            if (req_packet == null) {
                System.out.println("Request was not properly recieved!");
                return;
            }
            handle_request(req_packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ois.close();
                req_socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handle_request(ClientRequestPacket req_packet) {
        switch (req_packet.getCommand()) {
            case ProjectConstants.GET:
                break;
            case ProjectConstants.PUT:
                break;
            case ProjectConstants.:
                break;
        }
    }
}
