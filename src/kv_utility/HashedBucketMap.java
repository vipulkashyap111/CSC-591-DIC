package kv_utility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abhishek on 11/26/16.
 */
public class HashedBucketMap {
    private HashMap<String, ArrayList<ValueDetail>> bucket = null;

    public HashedBucketMap() {
        bucket = new HashMap<>();
    }
}
