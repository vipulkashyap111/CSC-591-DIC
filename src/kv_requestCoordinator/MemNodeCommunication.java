package kv_requestCoordinator;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.PacketTransfer;

import java.net.Socket;

/**
 * Created by gmeneze on 11/27/16.
 */

public class MemNodeCommunication implements Runnable {
    private int threadId;
    private ClientRequestPacket requestPacket;
    private ClientResponsePacket[] response;
    private Socket socket;

    public MemNodeCommunication(Socket socket, int threadId, ClientRequestPacket requestPacket, ClientResponsePacket[] response) {
        this.socket = socket;
        this.threadId = threadId;
        this.requestPacket = requestPacket;
        this.response = response;
    }

    @Override
    public void run() {
        if (threadId == 0)
            requestPacket.setReplicate_ind(true);
        else
            requestPacket.setReplicate_ind(false);
        PacketTransfer.sendRequest(requestPacket, socket);
        response[threadId] = PacketTransfer.recv_response(socket);
    }
}
