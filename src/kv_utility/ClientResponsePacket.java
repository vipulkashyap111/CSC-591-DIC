package kv_utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientResponsePacket implements Serializable {
    public static final long serialVersionUID = -3040096452457271695L;
    private String message = null;
    private int response_code = 0;
    private String key = null;
    private ValueDetail val = new ValueDetail();
    private RCDetail rc = null;
    private ArrayList<RCDetail> rc_list = null;
    private MemNodeSyncHelper memNodeSyncHelper = null;
    private HashMap<String,ValueDetail> sync_data = null;

    public MemNodeSyncHelper getMemNodeSyncHelper() {
        return memNodeSyncHelper;
    }

    public void setMemNodeSyncHelper(MemNodeSyncHelper memNodeSyncHelper) {
        this.memNodeSyncHelper = memNodeSyncHelper;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int return_status) {
        this.response_code = return_status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ValueDetail getVal() {
        return val;
    }

    public void setVal(ValueDetail val) {
        this.val = val;
    }

    public RCDetail getRc() {
        return rc;
    }

    public void setRc(RCDetail rc) {
        this.rc = rc;
    }

    public ArrayList<RCDetail> getRc_list() {
        return rc_list;
    }

    public void setRc_list(ArrayList<RCDetail> rc_list) {
        this.rc_list = rc_list;
    }

    public HashMap<String, ValueDetail> getSync_data() {
        return sync_data;
    }

    public void setSync_data(HashMap<String, ValueDetail> sync_data) {
        this.sync_data = sync_data;
    }
}