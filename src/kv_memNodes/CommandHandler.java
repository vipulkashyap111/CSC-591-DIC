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
        ValueDetail val = MemNodeProc.getData_store().get(req_packet.getKey(), req_packet.getStorage_type());
        if (val == null) {
            res_packet.setMessage(ProjectConstants.MESG_KNF);
            res_packet.setResponse_code(ProjectConstants.KNF);
        } else {
            res_packet.setMessage(ProjectConstants.MESG_KF);
            res_packet.setResponse_code(ProjectConstants.SUCCESS);
            res_packet.setVal(val);
            /* Update the time stamp */
            val.setUnixTS(MemNodeProc.getUnixTimeGenerator().getTime());
            /* Update the list */
            System.out.println("Updating list process : " + MemNodeProc.getTime_sorted_list().removeElement(val));
            MemNodeProc.getTime_sorted_list().addFirst(val);
        }
        return res_packet;
    }

    public static ClientResponsePacket handlePut(ClientRequestPacket req_packet) {
        ClientResponsePacket res_packet = new ClientResponsePacket();
        ValueDetail val = new ValueDetail();
        val.setValue(req_packet.getVal().getValue());
        val.setHashed_value(req_packet.getVal().getHashed_value());
        val.setUnixTS(MemNodeProc.getUnixTimeGenerator().getTime());

        /* Add to data store */
        if (MemNodeProc.getData_store().containsKey(req_packet.getKey(), req_packet.getStorage_type()))
            MemNodeProc.getTime_sorted_list().removeElement(val);
        MemNodeProc.getData_store().put(req_packet.getKey(), val, req_packet.getStorage_type());

        /* Add to Time sorted list */
        MemNodeProc.getTime_sorted_list().addFirst(val);

        /* Add to the bucket as per the hash */
        MemNodeProc.getBucket_map().add(req_packet.getVal().getHashed_value(), req_packet.getKey(), val);

        res_packet.setResponse_code(ProjectConstants.SUCCESS);
        res_packet.setMessage(ProjectConstants.SUCCESS_PUT);
        return res_packet;
    }

    // public static ClientResponsePacket handleSync(ClientRequestPacket req_packet)
    {

    }
}