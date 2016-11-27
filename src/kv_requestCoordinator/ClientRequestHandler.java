package kv_requestCoordinator;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;

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


    public ClientRequestHandler(Socket soc) {
        this.clientSocket = soc;
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
        String[] arg;

        switch (reqPacket.getCommand()) {
            case Pro
        }

    }

}
