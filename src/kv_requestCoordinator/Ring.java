package kv_requestCoordinator;

import java.util.HashMap;

/**
 * Created by gmeneze on 11/26/16.
 */

// Nodes IDs will be in the range 0-100

public class Ring {

    HashMap<Integer, String> nodeIdToIp;
    DoublyLinkedList ring;

    public Ring() {
        ring = new DoublyLinkedList();
    }

    public void addNode(String nodeIp) throws ArrayIndexOutOfBoundsException {
        int nodeId = ring.addNode();
        nodeIdToIp.put(nodeId, nodeIp);
    }

    public String getNodeIpForKey(int keyValue) {
        for (int i = keyValue; keyValue < 100; i++) {
            if (nodeIdToIp.containsKey(i)) {
                return nodeIdToIp.get(i);
            }
        }
        return nodeIdToIp.get(0);
    }
}
