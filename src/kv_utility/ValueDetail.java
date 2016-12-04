package kv_utility;

import java.io.Serializable;

/**
 * Created by abhishek on 11/26/16.
 */
public class ValueDetail implements Serializable {
    private String value = null;
    private long unixTS = 0;
    private long last_access_write = 0;
    private int hashed_value = 0;
    public ValueDetail prev = null;
    public ValueDetail next = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getUnixTS() {
        return unixTS;
    }

    public void setUnixTS(long unixTS) {
        this.unixTS = unixTS;
    }

    public int getHashed_value() {
        return hashed_value;
    }

    public void setHashed_value(int hashed_value) {
        this.hashed_value = hashed_value;
    }

    public long getLast_access_write() {
        return last_access_write;
    }

    public void setLast_access_write(long last_access_write) {
        this.last_access_write = last_access_write;
    }
}
