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


}
