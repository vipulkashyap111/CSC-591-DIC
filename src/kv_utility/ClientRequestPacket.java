package kv_utility;

import java.io.Serializable;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientRequestPacket implements Serializable {
    private int command;
    private String clientReqId;

    private String file_name;
    private String[] arguments;
    private int file_size;
    private boolean replicate_ind;

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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public boolean isReplicate_ind() {
        return replicate_ind;
    }

    public void setReplicate_ind(boolean replicate_ind) {
        this.replicate_ind = replicate_ind;
    }
}
