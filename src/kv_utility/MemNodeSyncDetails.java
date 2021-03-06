package kv_utility;

import java.io.Serializable;

/**
 * Created by gmeneze on 12/3/16.
 */
public class MemNodeSyncDetails implements Serializable
{
    public static final long serialVersionUID = -3040196452457271695L;
    private int start_range;
    private int end_range;
    private String ip_Address;
    private KVType storage_type;

    public KVType getStorage_type() {
        return storage_type;
    }

    public void setStorage_type(KVType storage_type) {
        this.storage_type = storage_type;
    }

    public String getIp_Address() {
        return ip_Address;
    }

    public void setIp_Address(String ip_Address) {
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
