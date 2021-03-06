package kv_utility;

import kv_memNodes.MemNodeProc;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhishek on 11/26/16.
 */
public class HashedBucketMap {
    private ConcurrentHashMap<Integer, HashMap<String, ValueDetail>> bucket = null;

    public HashedBucketMap() {
        bucket = new ConcurrentHashMap<>();
    }

    public void add(int hash_key, String key, ValueDetail val) {
        if (bucket.containsKey(hash_key)) {
            bucket.get(hash_key).put(key, val);
        } else {
            HashMap<String, ValueDetail> temp = new HashMap<>();
            temp.put(key, val);
            bucket.put(hash_key, temp);
        }
    }

    public HashMap<String,ValueDetail> getDSListFromBucket(int start,int end,boolean should_removed)
    {
        HashMap<String,ValueDetail> res = new HashMap<>();
        HashMap<String,ValueDetail> hm = new HashMap<>();
        for(int i = start;i <= end;i++)
        {
            if(!bucket.containsKey(i%100))
                continue;
            res.putAll(bucket.get(i%100));
            if (should_removed) {
                System.out.println("Migration Size : " + bucket.get(i % 100).size());
                MemNodeProc.migrate_data_to_repl(bucket.get(i % 100));
            }
        }
        return res;
    }

    public void removeAll(int hash, String key) {
        System.out.println("Removing from HashBucket : " + hash + ":" + key);
        bucket.get(hash).remove(key);
    }
}