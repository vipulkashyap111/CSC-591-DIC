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

    public String[] getThreeNodeIpForKey(int keyValue) {
        String[] threeNodeIp = new String[3];
        int j = 0;
        for (int i = keyValue; keyValue < 100; i++) {
            if (nodeIdToIp.containsKey(i)) {
                threeNodeIp[j] = nodeIdToIp.get(i);
                j++;

                if (j == 3)
                    break;
            }
        }
        return threeNodeIp;
    }
}
