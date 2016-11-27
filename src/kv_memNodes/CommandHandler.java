package kv_memNodes;

import kv_utility.ClientRequestPacket;
import kv_utility.ClientResponsePacket;
import kv_utility.ProjectConstants;
import kv_utility.ValueDetail;

/**
 * Created by abhishek on 11/26/16.
 * Class responsible for handling command request from Request Co-Ordinator
 *
 */
public class CommandHandler
{
    public static ClientResponsePacket handleGet(ClientRequestPacket req_packet)
    {
        ClientResponsePacket res_packet = new ClientResponsePacket();
        ValueDetail val = MemNodeProc.getData_store().get(req_packet.getKey());
        if (val == null) {
            res_packet.setMessage(ProjectConstants.MESG_KNF);
            res_packet.setResponse_code(ProjectConstants.KNF);
        } else {
            res_packet.setMessage(ProjectConstants.MESG_KF);
            res_packet.setResponse_code(ProjectConstants.SUCCESS);
            res_packet.setVal(val);
            /* Update the time stamp */
            val.setUnixTS(MemNodeProc.getUnixTimeGenerator().getTime());
        }
        return res_packet;
    }

    public static ClientResponsePacket handlePut(ClientRequestPacket req_packet) {
        ClientResponsePacket res_packet = new ClientResponsePacket();
        ValueDetail val = new ValueDetail();
        val.setValue(req_packet.getVal().getValue());
        val.setHashed_value(req_packet.getVal().getHashed_value());
        val.setUnixTS(MemNodeProc.getUnixTimeGenerator().getTime());

        MemNodeProc.getData_store().put(req_packet.getKey(), val);

        /* Add to the bucket as per the hash */
        MemNodeProc.getBucket_map().add(req_packet.getVal().getHashed_value(), req_packet.getKey(), val);

        res_packet.setResponse_code(ProjectConstants.SUCCESS);
        res_packet.setMessage(ProjectConstants.SUCCESS_PUT);
        return res_packet;
    }
}
