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
        nodeIdToIp = new HashMap<Integer, String>();
    }

    public void addNode(String nodeIp) throws ArrayIndexOutOfBoundsException {
        int nodeId = ring.addNode();
        System.out.println("Adding node id: " + nodeId + " for nodeIp: " + nodeIp);
        nodeIdToIp.put(nodeId, nodeIp);
        System.out.println("added Node Ip: " + nodeIp + " at Node id: " + nodeId);
    }

    public String[] getThreeNodeIpForKey(int keyValue) {
        System.out.println("In getThreeNodeIpForKey - 1");
        String[] threeNodeIp = new String[3];
        int j = 0;
        System.out.println("The number of registered memory nodes : " + nodeIdToIp.size());
        for (int i = keyValue; i < 100; i++) {
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
