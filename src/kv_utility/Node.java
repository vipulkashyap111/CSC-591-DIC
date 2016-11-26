package kv_utility;

import java.io.Serializable;

/**
 * Stores Information about a SINGLE Node of any type - MemNode, requestCoordinator or Proxy
 */

public class Node implements Serializable {
    public String IPAddr;
    public String memNodeID;
    public double size;
    public long capacity;
    public boolean isAlive;

    public Node(String IPAddr, String memNodeID, long capacity) {
        this.IPAddr = IPAddr;
        this.memNodeID = memNodeID;
        size = 0;
        this.capacity = capacity;
        isAlive = true;
    }

    public Node(Node Node) {
        this.IPAddr = Node.IPAddr;
        this.memNodeID = Node.memNodeID;
        this.size = Node.size;
        this.capacity = Node.capacity;
        this.isAlive = Node.isAlive;
    }
}
