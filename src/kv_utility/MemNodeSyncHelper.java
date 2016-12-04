package kv_utility;

import java.util.ArrayList;

/**
 * Created by gmeneze on 12/3/16.
 */
public class MemNodeSyncHelper {
    private ArrayList<String> prevIps;
    private ArrayList<String> nextIps;

    public MemNodeSyncHelper() {
        prevIps = new ArrayList<String>();
        nextIps = new ArrayList<String>();
    }
}
