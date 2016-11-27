package kv_utility;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek on 11/26/16.
 */
public class KeyValueHM {
    private ConcurrentHashMap<String, ValueDetail> in_mem_data_store = null;
    private ConcurrentHashMap<String, ValueDetail> in_mem_repl_data_store = null;

    public KeyValueHM() {
        this.in_mem_data_store = new ConcurrentHashMap<>();
    }

    public boolean containsKey(String key, KVType storage_type) {
        if (storage_type == KVType.ORIGINAL)
            return in_mem_data_store.containsKey(key);
        else
            return in_mem_repl_data_store.containsKey(key);
    }

    public ValueDetail get(String key, KVType storage_type) {
        if (storage_type == KVType.ORIGINAL)
            return in_mem_data_store.get(key);
        else
            return in_mem_repl_data_store.get(key);
    }

    public ValueDetail put(String key, ValueDetail value, KVType storage_type) {
        if (storage_type == KVType.ORIGINAL)
            return in_mem_data_store.put(key, value);
        else
            return in_mem_repl_data_store.put(key, value);
    }
}