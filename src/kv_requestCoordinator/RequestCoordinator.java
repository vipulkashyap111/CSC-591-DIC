package kv_requestCoordinator;

import com.google.common.hash.Hashing;
import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.PacketTransfer;
import kv_utility.ProjectConstants;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by gmeneze on 11/26/16.
 */
public class RequestCoordinator {
    Ring ring;

    public RequestCoordinator() {
        ring = new Ring();
    }

    public String getIpAddress(String key) {
        int keyValue = getHash(key);
        return ring.getNodeIpForKey(keyValue);
    }

    public ClientResponsePacket put(ClientRequestPacket requestPacket) {
        ClientResponsePacket response = null;
        try {
            Socket socket = new Socket(getIpAddress(requestPacket.getKey()), ProjectConstants.MN_LISTEN_PORT);
            PacketTransfer.sendRequest(requestPacket, socket);
            response = PacketTransfer.recv_response(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public int getHash(String key) {
        return Hashing.consistentHash(Hashing.md5().hashUnencodedChars(key), ProjectConstants.MAX_NUM_NODES);
    }

    public ClientResponsePacket get(ClientRequestPacket requestPacket) {
        ClientResponsePacket response = null;
        try {
            Socket socket = new Socket(getIpAddress(requestPacket.getKey()), ProjectConstants.MN_LISTEN_PORT);
            PacketTransfer.sendRequest(requestPacket, socket);
            response = PacketTransfer.recv_response(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
