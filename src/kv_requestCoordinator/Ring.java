package kv_requestCoordinator;

import kv_utility.ClientResponsePacket;
import kv_utility.MemNodeSyncDetails;
import kv_utility.MemNodeSyncHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gmeneze on 11/26/16.
 */

// Nodes IDs will be in the range 0-100

public class Ring {
    HashMap<Integer, String> nodeIdToIp;
    DoublyLinkedList ring;
    private static Ring instance = null;

    protected Ring() {
        // in case someone tries to create object directly.
    }

    public static Ring getInstance() {
        if (instance == null) instance = new Ring();
        if (instance.ring == null) instance.ring = new DoublyLinkedList();
        if (instance.nodeIdToIp == null) instance.nodeIdToIp = new HashMap<Integer, String>();
        return instance;
    }

    public void addNode(String nodeIp, ClientResponsePacket responsePacket) throws ArrayIndexOutOfBoundsException {
        System.out.println("ring object is: " + ring.toString());
        int nodeId = ring.addNode();
        System.out.println("Adding node id: " + nodeId + " for nodeIp: " + nodeIp);
        MemNodeSyncHelper helper = new MemNodeSyncHelper();
        helper.syncIps = new ArrayList<MemNodeSyncDetails>();

        /* Mem Node Sync Helper code */
        if (nodeIdToIp.size() >= 3) {
            System.out.println(" This Node QUALIFIES");

            // Next 2 Node info
            for (int i = 1; i <= 2; i++) {
                MemNodeSyncDetails syncDetails = new MemNodeSyncDetails();
                int nextNodeId = ring.getNextValue(nodeId, i);
                System.out.println("nextNode id: " + nextNodeId);
                syncDetails.setIp_Address(nodeIdToIp.get(nextNodeId));
                int endVal = ring.getPrevValue(nextNodeId, 3);
                syncDetails.setEnd_range((endVal == 0) ? 100 : endVal);
                syncDetails.setStart_range(ring.getPrevValue(nextNodeId, 4) + 1);
                helper.syncIps.add(syncDetails);
            }

            System.out.println("helper syncIps size: " + helper.syncIps.size());
            // new Range info
            MemNodeSyncDetails syncDetails = new MemNodeSyncDetails();
            int nextNodeId = ring.getNextValue(nodeId, 1);
            int prevNodeId = ring.getPrevValue(nodeId, 1);
            syncDetails.setIp_Address(nodeIdToIp.get(nextNodeId));
            syncDetails.setStart_range(prevNodeId + 1);
            syncDetails.setEnd_range(nodeId);
            helper.syncIps.add(syncDetails);
        }
        /* Mem Node Sync Helper end */

        responsePacket.setMemNodeSyncHelper(helper);
        nodeIdToIp.put(nodeId, nodeIp);
        System.out.println("added Node Ip: " + nodeIp + " at Node id: " + nodeId);
    }

    public String[] getThreeNodeIpForKey(int keyValue) {
        System.out.println("In getThreeNodeIpForKey - 1");
        String[] threeNodeIp = new String[3];
        int j = 0;
        System.out.println("The number of registered memory nodes : " + nodeIdToIp.size());

        for (Integer key : nodeIdToIp.keySet()) {
            System.out.println("XXXX  Memory node is: " + nodeIdToIp.get(key) + " key is: " + key + " XXXX");
        }

        for (int i = keyValue; i <= 200; i++) {
            if (nodeIdToIp.containsKey(i % 100)) {
                threeNodeIp[j] = nodeIdToIp.get(i % 100);
                j++;

                if (j == nodeIdToIp.size() || j == 3)
                    break;
            }
        }
        return threeNodeIp;
    }
}
