package kv_utility;

/**
 * Created by abhishek on 11/26/16.
 */
public enum KVType {
    ORIGINAL("ORIGINAL"),
    REPLICATED("REPLICATED");

    String type = null;

    KVType(String type) {
        this.type = type;
    }
}
