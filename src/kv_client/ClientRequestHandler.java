package kv_client;

import java.io.IOException;
import java.net.Socket;

import kv_utility.*;

public class ClientRequestHandler {
	public String handle(ClientRequestObject obj){
		try {
            Socket s = new Socket("152.46.17.199", ProjectConstants.PR_LISTEN_PORT);
            ClientRequestPacket req_packet = new ClientRequestPacket();
            ValueDetail val = new ValueDetail();
            val.setValue(obj.getValue());
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
            return res_packet.getMessage();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return "Error";
	}
}
