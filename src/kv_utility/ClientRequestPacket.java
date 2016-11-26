package kv_utility;

import java.io.Serializable;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientRequestPacket implements Serializable {
    private int command;
    private String clientReqId;
    private String[] arguments;
    private boolean replicate_ind;
    private String key;
    private String value;

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getClientReqId() {
        return clientReqId;
    }

    public void setClientReqId(String clientReqId) {
        this.clientReqId = clientReqId;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public boolean isReplicate_ind() {
        return replicate_ind;
    }

    public void setReplicate_ind(boolean replicate_ind) {
        this.replicate_ind = replicate_ind;
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
}
