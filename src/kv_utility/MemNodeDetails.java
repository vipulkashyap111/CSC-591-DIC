package kv_utility;

/**
 * Created by gmeneze on 12/3/16.
 */
public class MemNodeDetails {
    private int start_range;
    private int end_range;
    private int ip_Address;

    public int getIp_Address() {
        return ip_Address;
    }

    public void setIp_Address(int ip_Address) {
        this.ip_Address = ip_Address;
    }

    public int getEnd_range() {
        return end_range;
    }

    public void setEnd_range(int end_range) {
        this.end_range = end_range;
    }

    public int getStart_range() {
        return start_range;
    }

    public void setStart_range(int start_range) {
        this.start_range = start_range;
    }
}
