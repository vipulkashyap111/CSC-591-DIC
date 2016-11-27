package kv_requestCoordinator;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.PacketTransfer;
import kv_utility.ProjectConstants;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by gmeneze on 11/27/16.
 */

public class MemNodeCommunication implements Runnable {
    private int threadId;
    private ClientRequestPacket requestPacket;
    private String nodeIpAddress;
    private ClientResponsePacket[] response;

    public MemNodeCommunication(String nodeIpAddress, int threadId, ClientRequestPacket requestPacket, ClientResponsePacket[] response) {
        this.threadId = threadId;
        this.requestPacket = requestPacket;
        this.nodeIpAddress = nodeIpAddress;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(this.nodeIpAddress, ProjectConstants.MN_LISTEN_PORT);
            PacketTransfer.sendRequest(requestPacket, socket);
            response[threadId] = PacketTransfer.recv_response(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
