package kv_requestCoordinator;

import com.google.common.hash.Hashing;
import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.ProjectConstants;

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

        for (int i = 0; i < 3; i++) {
            MemNodeCommunication communicate = new MemNodeCommunication(threeIpAddresses[i], i, requestPacket, response);
            workers.execute(communicate);
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

        for (int i = 0; i < 3; i++) {
            MemNodeCommunication communicate = new MemNodeCommunication(threeIpAddresses[i], i, requestPacket, response);
            workers.execute(communicate);
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
            if (responsePacket.getResponse_code() == ProjectConstants.FAILURE) {
                failurePacket = responsePacket;
                failureCount++;
            } else {
                successPacket = responsePacket;
            }
        }

        //TODO: Ask out of sync ones to update
        if (failureCount < 3)
            return successPacket;
        else
            return failurePacket;
    }
}
