package kv_utility;

/**
 * Created by abhishek on 11/26/16.
 */
public class ValueDetail {
    private String value = null;
    private long unixTS = 0;
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
}
