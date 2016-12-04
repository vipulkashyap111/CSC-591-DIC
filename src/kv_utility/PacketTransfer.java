package kv_utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by abhishek on 11/27/16.
 */
public class PacketTransfer {
    public synchronized static void sendRequest(ClientRequestPacket req_packet, Socket connection) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(req_packet);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static ClientResponsePacket recv_response(Socket connection) {
        ObjectInputStream ois = null;
        ClientResponsePacket res_packet = null;
        try {
            /* Wait for the response packet from the server */
            ois = new ObjectInputStream(connection.getInputStream());
            res_packet = (ClientResponsePacket) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res_packet;
        }
    }
}
