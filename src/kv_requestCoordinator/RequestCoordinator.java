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
        ring = Ring.getInstance();
        workers = Executors.newFixedThreadPool(ProjectConstants.THREE);
    }

    public ClientResponsePacket addNode(ClientRequestPacket requestPacket) {
        System.out.println("===RC object is: " + this.toString());
        System.out.println("===Ring object is: " + ring.toString());
        ClientResponsePacket responsePacket = new ClientResponsePacket();
        System.out.println("request packet ip address is: " + requestPacket.getIp_address());
        ring.addNode(requestPacket.getIp_address(), responsePacket);
        responsePacket.setMessage("Added Node " + requestPacket.getIp_address() + " successfully");
        responsePacket.setResponse_code(ProjectConstants.SUCCESS);
        return responsePacket;
    }

    public String[] getIpAddresses(String key) {
        int keyValue = getHash(key);
        return ring.getThreeNodeIpForKey(keyValue);
    }

    public ClientResponsePacket put(ClientRequestPacket requestPacket) {
        //set unix time and hashvalue of key in request Packet
        requestPacket.getVal().setUnixTS(System.currentTimeMillis());
        requestPacket.getVal().setHashed_value(getHash(requestPacket.getKey()));

        System.out.println("KEY VALUE is: " + requestPacket.getKey() + " HASH is: " + getHash(requestPacket.getKey()));

        ClientResponsePacket[] response = new ClientResponsePacket[3];
        String[] threeIpAddresses = getIpAddresses(requestPacket.getKey());
        Socket[] socket = new Socket[3];
        System.out.println("PUT - 1");
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("PUT - 1" + i + " in address: " + threeIpAddresses[i]);
                socket[i] = new Socket(threeIpAddresses[i], ProjectConstants.MN_LISTEN_PORT);
                MemNodeCommunication communicate = new MemNodeCommunication(socket[i], i, requestPacket, response);
                workers.execute(communicate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("PUT - 2");
        try {
            workers.shutdown();
            workers.awaitTermination(ProjectConstants.ONE, TimeUnit.MINUTES);
            socket[0].close();
            socket[1].close();
            socket[2].close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("PUT - 3");
        int failureCount = 0;
        ClientResponsePacket successPacket = null;
        ClientResponsePacket failurePacket = null;
        for (ClientResponsePacket responsePacket : response) {
            System.out.println("PUT - 3 inner");
            if (responsePacket == null)
                throw new RuntimeException("Response not recieved in one minute");

            if (responsePacket.getResponse_code() == ProjectConstants.FAILURE) {
                failurePacket = responsePacket;
                failureCount++;
            } else {
                successPacket = responsePacket;
            }
        }
        System.out.println("PUT - 4");
        if (failureCount < 2) {
            return successPacket;
        }
        else {
            return failurePacket;
        }
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
                System.out.println("GET - 1" + i + " in address: " + threeIpAddresses[i]);
                socket[i] = new Socket(threeIpAddresses[i], ProjectConstants.MN_LISTEN_PORT);
                MemNodeCommunication communicate = new MemNodeCommunication(socket[i], i, requestPacket, response);
                workers.execute(communicate);
            } catch (IOException e) {
                System.out.println("Node at : " + threeIpAddresses[i] + " is down.");
                System.out.println("Moving forward");
            }
        }

        try {
            workers.shutdown();
            workers.awaitTermination(ProjectConstants.ONE, TimeUnit.MINUTES);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <= 2; i++) {
            try {
                if (socket[i] != null)
                    socket[i].close();
            } catch (IOException e) {
                System.out.println("Socket is already closed");
                System.out.println("moving on");
            }
        }


        ClientResponsePacket sucessPacket = null;
        long maxTime = -1;
        for (int i = 0; i < 3; i++) {
            if (response[i] == null) {
                System.out.println("DID NOT RECIEVE RESPONSE FROM: " + threeIpAddresses[i]);
                System.out.println("continuing");
                continue;
                //throw new RuntimeException("Response not recieved in one minute");
            }

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
            if (response[i] == null) {
                System.out.println("DID NOT RECIEVE RESPONSE FROM: " + threeIpAddresses[i]);
                System.out.println("continuing");
                continue;
            }

            System.out.println("response packet details are: " + " response code: " + response[i].getResponse_code() + " Time: " + response[i].getVal().getUnixTS() + " maxTime is: " + maxTime);
            if (response[i].getResponse_code() == ProjectConstants.FAILURE || response[i].getVal().getUnixTS() < maxTime) {
                try {
                    Socket newSocket = new Socket(threeIpAddresses[i], ProjectConstants.MN_LISTEN_PORT);
                    request.setCommand(ProjectConstants.PUT);
                    if (i == 0)
                        request.setReplicate_ind(true);
                    else
                        request.setReplicate_ind(false);
                    PacketTransfer.sendRequest(requestPacket, newSocket);
                    newSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sucessPacket;
    }
}
