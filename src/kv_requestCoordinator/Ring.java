package kv_requestCoordinator;

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

    public void addNode(String nodeIp) throws ArrayIndexOutOfBoundsException {
        System.out.println("ring object is: " + ring.toString());
        int nodeId = ring.addNode();
        System.out.println("Adding node id: " + nodeId + " for nodeIp: " + nodeIp);
        nodeIdToIp.put(nodeId, nodeIp);
        System.out.println("added Node Ip: " + nodeIp + " at Node id: " + nodeId);
    }

    public String[] getPrevTwoNodes(int keyValue) {
        //System.out.println("In getPrevTwoNodes - 1");
        String[] prevTwo = new String[2];
        int j = 0;
        //System.out.println("The number of registered memory nodes : " + nodeIdToIp.size());

        for (int i = keyValue; i >= -100; i--) {
            int key = i < 0 ? (-1 * i) : i;
            if (nodeIdToIp.containsKey(key % 100)) {
                prevTwo[j] = nodeIdToIp.get(key % 100);
                j++;

                if (j == nodeIdToIp.size() || j == 2)
                    break;
            }
        }
        return prevTwo;
    }

    public String[] getNextTwoNodes(int keyValue) {
        String[] nextTwo = new String[2];
        String[] nextThree = getThreeNodeIpForKey(keyValue);

        nextTwo[0] = nextThree[0];
        nextTwo[1] = nextThree[1];

        return nextTwo;
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
