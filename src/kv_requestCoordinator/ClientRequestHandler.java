package kv_requestCoordinator;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.ProjectConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientRequestHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream clientInputStream;
    private ObjectOutputStream clientOutputStream;
    private ClientRequestPacket reqPacket;
    private RequestCoordinator rc;


    public ClientRequestHandler(Socket soc) {
        this.clientSocket = soc;
        this.rc = new RequestCoordinator();
    }

    @Override
    public void run() {

        try {
            clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
            reqPacket = (ClientRequestPacket) clientInputStream.readObject();

            if (reqPacket == null)
                System.out.println("Handle NULL case");

            handle_command(reqPacket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientInputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handle_command(ClientRequestPacket reqPacket) {
        ClientResponsePacket resPacket = null;

        switch (reqPacket.getCommand()) {
            case ProjectConstants.REGISTER:
                //TODO
                break;
            case ProjectConstants.GET:
                resPacket = rc.get(reqPacket.getKey());
                break;

            case ProjectConstants.PUT:
                resPacket = rc.put(reqPacket.getKey(), reqPacket.getValue());
                break;
        }
        send_response(resPacket);
    }

    public void send_response(ClientResponsePacket res_packet) {
        try {
            System.out.println("Sending the Response with code : " + res_packet.response_code);
            oos = new ObjectOutputStream(client_socket.getOutputStream());
            oos.writeObject(res_packet);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
