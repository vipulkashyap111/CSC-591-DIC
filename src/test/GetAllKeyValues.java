package test;

import kv_utility.*;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
/**
 * Created by gmeneze on 12/4/16.
 */
public class GetAllKeyValues {

    public static void main(String[] args)
    {
        String nodeIp = args[0];
        try {
            Socket socket = new Socket(nodeIp, ProjectConstants.PR_LISTEN_PORT);
            ClientRequestPacket reqPacket = new ClientRequestPacket();
            reqPacket.setCommand(ProjectConstants.GET_All_KEY_VALUES);
            reqPacket.setStorage_type(KVType.ORIGINAL);
            PacketTransfer.sendRequest(reqPacket, socket);
            ClientResponsePacket resPacket = PacketTransfer.recv_response(socket);
            HashMap<String, ValueDetail> map = resPacket.getSync_data();

            System.out.println("Primary keys are :- ");
            for(String key : map.keySet())
            {
                ValueDetail value = map.get(key);

            }

        }
        catch(IOException e)
        {
            System.out.println("failed to open socket");
            e.printStackTrace();
        }
    }
}
