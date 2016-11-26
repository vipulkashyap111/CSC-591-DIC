package kv_utility;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gmeneze on 11/26/16.
 */
public class ClientRequestPacket implements Serializable {
    public int command;
    public String clientReqId;

    public String file_name;
    public String arguments[];
    public int file_size;
    public boolean replicate_ind;

    public ArrayList<Node> dn_list;
}
