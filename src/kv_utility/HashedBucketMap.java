package kv_utility;

import java.util.HashMap;

/**
 * Created by abhishek on 11/26/16.
 */
public class HashedBucketMap {
    private HashMap<Integer, HashMap<String, ValueDetail>> bucket = null;

    public HashedBucketMap() {
        bucket = new HashMap<>();
    }

    public void add(int hash_key, String key, ValueDetail val) {
        if (bucket.containsKey(hash_key)) {
            bucket.get(key).put(key, val);
        } else {
            HashMap<String, ValueDetail> temp = new HashMap<>();
            temp.put(key, val);
            bucket.put(hash_key, temp);
        }
    }
}