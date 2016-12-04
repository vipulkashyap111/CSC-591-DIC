package kv_utility;

import java.util.ArrayList;

/**
 * Created by gmeneze on 12/3/16.
 */
public class MemNodeSyncHelper {
    public ArrayList<MemNodeDetails> prevIps;
    public ArrayList<MemNodeDetails> nextIps;

    public MemNodeSyncHelper() {
        prevIps = new ArrayList<MemNodeDetails>();
        nextIps = new ArrayList<MemNodeDetails>();
    }
}
