package test;

import kv_utility.*;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
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

            System.out.print("=============================================");
            System.out.println("Primary keys are :- ");
            for(String key : map.keySet())
            {
                System.out.println("key is: " + key + " value: " + map.get(key).getValue());
            }
            System.out.println("=============================================");

            reqPacket.setStorage_type(KVType.REPLICATED);
            PacketTransfer.sendRequest(reqPacket, socket);
            resPacket = PacketTransfer.recv_response(socket);
            map = resPacket.getSync_data();

            System.out.print("=============================================");
            System.out.println("Backed-up keys are :- ");
            for (String key : map.keySet()) {
                System.out.println("key is: " + key + " value: " + map.get(key).getValue());
            }
            System.out.println("=============================================");

        }
        catch(IOException e)
        {
            System.out.println("failed to open socket");
            e.printStackTrace();
        }
    }
}
