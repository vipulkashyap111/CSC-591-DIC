package kv_requestCoordinator;

import kv_utility.ClientRequestPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientRequestHandler implements Runnable {
    private Socket clientSocket;
    private String clientReqId;
    private ObjectInputStream clientInputStream;
    private ObjectOutputStream clientOutputStream;
    private ClientRequestPacket req_packet;


    public ClientRequestHandler(Socket soc, String clientReqId) {
        this.clientSocket = soc;
        this.clientReqId = clientReqId;
    }

    @Override
    public void run() {

        try {
            clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
            req_packet = (ClientRequestPacket) clientInputStream.readObject();

            if (req_packet == null)
                System.out.println("Handle NULL case");

            System.out.println("Request for ID : " + req_packet.clientReqId);

            handle_command(req_packet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }

    }


}
