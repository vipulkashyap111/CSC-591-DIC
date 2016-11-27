package kv_utility;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek on 11/26/16.
 */
public class KeyValueHM {
    private ConcurrentHashMap<String, ValueDetail> in_mem_data_store = null;
    private KVType storage_type = null;

    public KeyValueHM(KVType storage_type) {
        this.in_mem_data_store = new ConcurrentHashMap<>();
        this.storage_type = this.storage_type;
    }

    public boolean containsKey(String key) {
        return in_mem_data_store.containsKey(key);
    }

    public ValueDetail get(String key) {
        return in_mem_data_store.get(key);
    }

    public ValueDetail put(String key, ValueDetail value) {
        return in_mem_data_store.put(key, value);
    }
}