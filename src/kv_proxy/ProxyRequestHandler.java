package kv_proxy;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.ProjectConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by abhishek on 11/27/16.
 */
public class ProxyRequestHandler implements Runnable {
    private Socket req_socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private ClientRequestPacket req_packet = null;

    public ProxyRequestHandler(Socket req) {
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
        ClientResponsePacket res_packet = null;
        switch (req_packet.getCommand()) {
            case ProjectConstants.GET_RC:
                res_packet = ProxyCommandHandler.handleGetRC(req_packet);
                break;
            case ProjectConstants.GET_RING:
                res_packet = ProxyCommandHandler.handleGetRing(req_packet);
                break;
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
