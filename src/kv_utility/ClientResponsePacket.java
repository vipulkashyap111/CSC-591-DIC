package kv_utility;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientResponsePacket {
    private String message = null;
    private int return_status = 0;
    private String key;
    private String value;
    private String response_code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReturn_status() {
        return return_status;
    }

    public void setReturn_status(int return_status) {
        this.return_status = return_status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }
}
