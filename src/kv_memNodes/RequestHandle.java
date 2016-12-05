package kv_memNodes;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.ProjectConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by abhishek on 11/26/16.
 * Class responsible for handling command request from Request Co-Ordinator
 */
public class RequestHandle implements Runnable {
    private Socket req_socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private ClientRequestPacket req_packet = null;

    public RequestHandle(Socket req) {
        this.req_socket = req;
    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(req_socket.getInputStream());
            req_packet = (ClientRequestPacket) ois.readObject();

            if (req_packet == null) {
                System.out.println("Request was not properly recieved!!");
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

    public void handle_request(ClientRequestPacket req_packet)
    {
        System.out.println("Got Client Request : " + req_packet.getCommand());
        ClientResponsePacket res_packet = null;
        switch (req_packet.getCommand()) {
            case ProjectConstants.GET:
                res_packet = CommandHandler.handleGet(req_packet);
                break;
            case ProjectConstants.PUT:
                res_packet = CommandHandler.handlePut(req_packet);
                break;
            case ProjectConstants.SYNC_MEM_NODE:
                res_packet = CommandHandler.handleSync(req_packet);
                break;
            case ProjectConstants.ADD_MEM_NODES:
            default:
                System.out.println("Wrong Command recieved :" + req_packet.getCommand());
                break;
        }
        send_response(res_packet);
    }

    public void send_response(ClientResponsePacket res_packet) {
        try {
            System.out.println("Sending the Response with code : " + res_packet.getResponse_code());
            oos = new ObjectOutputStream(req_socket.getOutputStream());
            oos.writeObject(res_packet);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}