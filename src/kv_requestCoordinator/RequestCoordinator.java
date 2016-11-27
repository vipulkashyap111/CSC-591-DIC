package kv_requestCoordinator;

import com.google.common.hash.Hashing;
import kv_utility.ProjectConstants;

/**
 * Created by gmeneze on 11/26/16.
 */
public class RequestCoordinator {
    Ring ring;

    public RequestCoordinator() {
        ring = new Ring();
    }

    public String getIpAddress(String key) {
        int keyValue = getHash(key);
        return ring.getNodeIpForKey(keyValue);
    }

    public void put(String key, Object value) {
        getIpAddress(key);
    }

    public int getHash(String key) {
        return Hashing.consistentHash(Hashing.md5().hashUnencodedChars(key), ProjectConstants.MAX_NUM_NODES);
    }

    public Object get(String key) {
        getIpAddress(key);
    }
}
