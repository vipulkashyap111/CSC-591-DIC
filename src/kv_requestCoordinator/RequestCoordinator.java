package kv_requestCoordinator;

import com.google.common.hash.Hashing;

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
        String ipAddress = ring.getNodeIpForKey(keyValue);
        return ipAddress;
    }

    public void put(String key, Object value) {
        getIpAddress(key);
    }

    public int getHash(String key) {
        int keyValue = Hashing.consistentHash(Hashing.md5().hashUnencodedChars(key), 100);
        return keyValue;
    }

    public Object get(String key) {
        getIpAddress(key);
    }

}
