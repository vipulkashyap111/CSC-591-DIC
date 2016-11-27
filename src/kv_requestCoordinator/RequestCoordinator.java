package kv_requestCoordinator;

import com.google.common.hash.Hashing;
import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.PacketTransfer;
import kv_utility.ProjectConstants;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by gmeneze on 11/26/16.
 */
public class RequestCoordinator {
    Ring ring;
    private static ExecutorService workers;

    public RequestCoordinator() {
        ring = new Ring();
        workers = Executors.newFixedThreadPool(ProjectConstants.THREE);
    }

    public String[] getIpAddresses(String key) {
        int keyValue = getHash(key);
        return ring.getThreeNodeIpForKey(keyValue);
    }

    public ClientResponsePacket put(ClientRequestPacket requestPacket) {
        ClientResponsePacket[] response = new ClientResponsePacket[3];
        String[] threeIpAddresses = getIpAddresses(requestPacket.getKey());
        Socket[] socket = new Socket[3];

        for (int i = 0; i < 3; i++) {
            try {
                socket[i] = new Socket(threeIpAddresses[i], ProjectConstants.MN_LISTEN_PORT);
                MemNodeCommunication communicate = new MemNodeCommunication(socket[i], i, requestPacket, response);
                workers.execute(communicate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            workers.shutdown();
            workers.awaitTermination(ProjectConstants.ONE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int failureCount = 0;
        ClientResponsePacket successPacket = null;
        ClientResponsePacket failurePacket = null;
        for (ClientResponsePacket responsePacket : response) {
            if (responsePacket == null)
                throw new RuntimeException("Response not recieved in one minute");

            if (responsePacket.getResponse_code() == ProjectConstants.FAILURE) {
                failurePacket = responsePacket;
                failureCount++;
            } else {
                successPacket = responsePacket;
            }
        }

        if (failureCount < 2)
            return successPacket;
        else
            return failurePacket;
    }

    public int getHash(String key) {
        return Hashing.consistentHash(Hashing.md5().hashUnencodedChars(key), ProjectConstants.MAX_NUM_NODES);
    }

    public ClientResponsePacket get(ClientRequestPacket requestPacket) {
        ClientResponsePacket[] response = new ClientResponsePacket[3];
        String[] threeIpAddresses = getIpAddresses(requestPacket.getKey());
        Socket[] socket = new Socket[3];

        for (int i = 0; i < 3; i++) {
            try {
                socket[i] = new Socket(threeIpAddresses[i], ProjectConstants.MN_LISTEN_PORT);
                MemNodeCommunication communicate = new MemNodeCommunication(socket[i], i, requestPacket, response);
                workers.execute(communicate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            workers.shutdown();
            workers.awaitTermination(ProjectConstants.ONE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ClientResponsePacket sucessPacket = null;
        long maxTime = Long.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            if (response[i] == null)
                throw new RuntimeException("Response not recieved in one minute");

            if (response[i].getResponse_code() == ProjectConstants.SUCCESS) {

                if (response[i].getVal().getUnixTS() > maxTime) {
                    maxTime = response[i].getVal().getUnixTS();
                    sucessPacket = response[i];
                }
            }
        }

        // Key not found in any clients
        if (sucessPacket == null)
            return response[0];

        ClientRequestPacket request = new ClientRequestPacket();
        request.setKey(sucessPacket.getKey());
        request.setVal(sucessPacket.getVal());

        for (int i = 0; i < 3; i++) {
            if (response[i].getResponse_code() == ProjectConstants.FAILURE || response[i].getVal().getUnixTS() < maxTime) {
                request.setCommand(ProjectConstants.PUT);

                if (i == 0)
                    request.setReplicate_ind(true);
                else
                    request.setReplicate_ind(false);

                PacketTransfer.sendRequest(requestPacket, socket[i]);
            }
        }

        return sucessPacket;
    }
}
