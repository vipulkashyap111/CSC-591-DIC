package kv_utility;

import java.io.Serializable;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientRequestPacket implements Serializable {
    private int command;
    private boolean replicate_ind;
    private String key = null;
    private KVType storage_type;
    private ValueDetail val = new ValueDetail();
    private String ip_address = null;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public boolean isReplicate_ind() {
        return replicate_ind;
    }

    public void setReplicate_ind(boolean replicate_ind) {
        this.replicate_ind = replicate_ind;
    }

    public ValueDetail getVal() {
        return val;
    }

    public void setVal(ValueDetail val) {
        this.val = val;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public KVType getStorage_type() {
        return storage_type;
    }

    public void setStorage_type(KVType storage_type) {
        this.storage_type = storage_type;
    }
}
