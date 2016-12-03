package test;

import kv_utility.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by abhishek on 11/27/16.
 */
public class MakeRequest {
    public static void main(String arg[]) {
        System.out.println("Starting Memory Node Server");
        if (arg.length != 1) {
            System.out.println("Please run program as below");
            System.out.println("MemNodeProx <PROXY_IP_ADDRESS>");
            System.exit(ProjectConstants.SUCCESS);
        }
        String proxy_address = arg[ProjectConstants.ZERO];
        try {
            Socket s = new Socket(proxy_address, ProjectConstants.PR_LISTEN_PORT);
            ClientRequestPacket req_packet = new ClientRequestPacket();
            ValueDetail val = new ValueDetail();
            val.setValue("First Value");
            req_packet.setCommand(ProjectConstants.GET_RC);
            PacketTransfer.sendRequest(req_packet, s);
            ClientResponsePacket res_packet = PacketTransfer.recv_response(s);
            System.out.println("Res:" + res_packet.getRc().ip);

            s = new Socket(res_packet.getRc().ip, ProjectConstants.RC_LISTEN_PORT);
            req_packet = new ClientRequestPacket();
            req_packet.setCommand(ProjectConstants.PUT);
            req_packet.setKey("Test");
            req_packet.setVal(val);
            PacketTransfer.sendRequest(req_packet, s);
            res_packet = PacketTransfer.recv_response(s);
            System.out.println("Message of operation : " + res_packet.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
