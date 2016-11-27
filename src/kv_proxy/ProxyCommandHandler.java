package kv_proxy;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.ProjectConstants;

import java.util.ArrayList;

/**
 * Created by abhishek on 11/27/16.
 */
public class ProxyCommandHandler {
    public static ClientResponsePacket handleGetRC(ClientRequestPacket req_packet) {
        ClientResponsePacket res_packet = new ClientResponsePacket();
        RCDetail rcd = null;
        ProxyServer ps_inst = ProxyProc.ps;
        rcd = ps_inst.rc_data.get(ps_inst.getNextIndex());
        if (rcd == null) {
            System.out.println("No Request Co-Ordinator Found..");
            res_packet.setMessage(ProjectConstants.MESG_RCNF);
            res_packet.setResponse_code(ProjectConstants.FAILURE);
            return res_packet;
        }
        res_packet.setMessage(ProjectConstants.MESG_RCF);
        res_packet.setResponse_code(ProjectConstants.SUCCESS);
        res_packet.setRc(rcd);
        System.out.println("Successfully gave RC  : " + rcd.ip);
        return res_packet;
    }

    public static ClientResponsePacket handleGetRing(ClientRequestPacket req_packet) {
        ClientResponsePacket res_packet = new ClientResponsePacket();
        ProxyServer ps_inst = ProxyProc.ps;
        ArrayList<RCDetail> rc_list = ps_inst.rc_data.getList();
        if (rc_list == null) {
            System.out.println("No Request Co-Ordinator Rind Found..");
            res_packet.setMessage(ProjectConstants.MESG_RCNF);
            res_packet.setResponse_code(ProjectConstants.FAILURE);
            return res_packet;
        }
        res_packet.setMessage(ProjectConstants.MESG_RCF);
        res_packet.setResponse_code(ProjectConstants.SUCCESS);
        res_packet.setRc_list(rc_list);
        System.out.println("Successfully gave RC Ring : " + rc_list.size());
        return res_packet;
    }

    public static ClientResponsePacket handleAddRC(ClientRequestPacket req_packet) {
        ClientResponsePacket res_packet = new ClientResponsePacket();
        ProxyServer ps_inst = ProxyProc.ps;
        RCDetail rcd = new RCDetail();
        rcd.ip = new String(req_packet.getIp_address());
        ps_inst.rc_data.add(rcd);
        res_packet.setMessage(ProjectConstants.MESG_ADDED_RC);
        res_packet.setResponse_code(ProjectConstants.SUCCESS);
        System.out.println("Successfully added the RC Node : " + rcd.ip);
        return res_packet;
    }
}
