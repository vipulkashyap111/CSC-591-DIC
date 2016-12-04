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
    private static ClientRequestHandler instance = null;

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
            case ProjectConstants.GET:
                System.out.println("recieved GET packet");
                resPacket = rc.get(reqPacket);
                break;

            case ProjectConstants.PUT:
                System.out.println("recieved PUT packet");
                resPacket = rc.put(reqPacket);
                break;

            case ProjectConstants.ADD_MEM_NODES:
                System.out.println("recieved ADD_MEM_NODES packet");
                resPacket = rc.addNode(reqPacket);
                break;

            default:
                resPacket.setMessage("Invalid request to Request Co-ordinator");
                resPacket.setResponse_code(ProjectConstants.FAILURE);
        }
        System.out.println("sending response Packet");
        send_response(resPacket);
    }

    public void send_response(ClientResponsePacket res_packet) {
        try {
            System.out.println("Sending the Response with code : " + res_packet.getResponse_code());
            clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientOutputStream.writeObject(res_packet);
            clientOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
