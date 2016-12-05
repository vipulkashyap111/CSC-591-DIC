package kv_client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import kv_utility.*;

public class ClientRequestHandler {
	public ClientResponsePacket handle(String proxyIPAddrress, ClientRequestObject obj){
        ClientResponsePacket res_packet = new ClientResponsePacket();
		try {
            Socket s = new Socket(proxyIPAddrress, ProjectConstants.PR_LISTEN_PORT);
            ClientRequestPacket req_packet = new ClientRequestPacket();

            req_packet.setCommand(ProjectConstants.GET_RC);
            PacketTransfer.sendRequest(req_packet, s);
            res_packet = PacketTransfer.recv_response(s);
            System.out.println("Res:" + res_packet.getRc().ip);

            ValueDetail val = new ValueDetail();
            val.setValue(obj.getValue());

            s = new Socket(res_packet.getRc().ip, ProjectConstants.RC_LISTEN_PORT);
            req_packet = new ClientRequestPacket();

            req_packet.setCommand(obj.getCommand());
            req_packet.setKey(obj.getKey());

            if(obj.getCommand() == ProjectConstants.PUT){
                req_packet.setVal(val);
            }
            System.out.println("Sending request to RC with following commands: COMMAND: " + req_packet.getCommand() + " KEY: " + req_packet.getKey() + " VALUE: " + req_packet.getVal().getValue());
            PacketTransfer.sendRequest(req_packet, s);
            res_packet = PacketTransfer.recv_response(s);
            System.out.println("Message of operation : " + res_packet.getMessage());

        }
        catch (Exception ex) {
            ex.printStackTrace();
            res_packet.setResponse_code(ProjectConstants.FAILURE);
            return res_packet;
        }
        return res_packet;
	}
}
