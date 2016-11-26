package kv_utility;

/**
 * Created by abhishek on 11/26/16.
 */
public class ValueDetail {
    private String value = null;
    private double unixTS = 0;
    private double hashed_value = 0;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getUnixTS() {
        return unixTS;
    }

    public void setUnixTS(double unixTS) {
        this.unixTS = unixTS;
    }

    public double getHashed_value() {
        return hashed_value;
    }

    public void setHashed_value(double hashed_value) {
        this.hashed_value = hashed_value;
    }
}
