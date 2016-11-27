package kv_utility;

import java.io.Serializable;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientResponsePacket implements Serializable {
    private String message = null;
    private int response_code = 0;
    private String key = null;
    private ValueDetail val = new ValueDetail();

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
}