package kv_utility;

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
        System.out.println(bucket + ":" + hash_key + ":" + key + ":" + val);
        if (bucket.containsKey(hash_key)) {
            bucket.get(hash_key).put(key, val);
        } else {
            HashMap<String, ValueDetail> temp = new HashMap<>();
            temp.put(key, val);
            bucket.put(hash_key, temp);
        }
    }
}