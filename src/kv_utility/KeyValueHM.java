package kv_utility;

import java.util.HashMap;

/**
 * Created by abhishek on 11/26/16.
 */
public class KeyValueHM {
    private HashMap<String, ValueDetail> in_mem_data_store = null;
    private KVType storage_type = null;

    public KeyValueHM(KVType storage_type) {
        this.in_mem_data_store = new HashMap<>();
        this.storage_type = this.storage_type;
    }
}