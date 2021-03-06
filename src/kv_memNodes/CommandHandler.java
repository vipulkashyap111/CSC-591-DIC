package kv_memNodes;

import kv_utility.*;

import java.util.HashMap;

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
            /* Check another list */
            val = MemNodeProc.getData_store().get(req_packet.getKey(), req_packet.getStorage_type() == KVType.ORIGINAL ? KVType.REPLICATED : KVType.ORIGINAL);
        }
        System.out.println("Got value : " + req_packet.getKey() + ":" + val + ":" + req_packet.getStorage_type());
        if (val == null) {
            res_packet.setMessage(ProjectConstants.MESG_KNF);
            res_packet.setResponse_code(ProjectConstants.KNF);
            res_packet.setVal(new ValueDetail());
        } else {
            res_packet.setMessage(ProjectConstants.MESG_KF);
            res_packet.setResponse_code(ProjectConstants.SUCCESS);
            res_packet.setVal(val);
            /* Update the time stamp */
            val.setLast_access_write(MemNodeProc.getUnixTimeGenerator());
            /* Update the list */
            System.out.println("Updating list process : " + MemNodeProc.getTime_sorted_list().removeElement(val));
            MemNodeProc.getTime_sorted_list().addFirst(val);
        }
        return res_packet;
    }

    public static ClientResponsePacket handlePut(ClientRequestPacket req_packet)
    {
        ClientResponsePacket res_packet = new ClientResponsePacket();
        ValueDetail val = new ValueDetail();
        val.setValue(req_packet.getVal().getValue());
        val.setHashed_value(req_packet.getVal().getHashed_value());
        val.setUnixTS(req_packet.getVal().getUnixTS());
        val.setLast_access_write(MemNodeProc.getUnixTimeGenerator());
        val.setKey(req_packet.getKey());
        System.out.println("Key got for put : " + req_packet.getKey() + ":" + req_packet.getStorage_type() + ":" + req_packet.getVal().getValue());
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

    public static ClientResponsePacket handleSync(ClientRequestPacket req_packet)
    {
        HashMap<String,ValueDetail> total_data = null;
        /* If original is being maintained by new added one then move to replicated one */
        if(req_packet.getStorage_type() == KVType.ORIGINAL)
            total_data = MemNodeProc.getBucket_map().getDSListFromBucket(req_packet.getStart_range(),req_packet.getEnd_range(),true);
        else
            total_data = MemNodeProc.getBucket_map().getDSListFromBucket(req_packet.getStart_range(),req_packet.getEnd_range(),false);
        /* Send this list back to new node */
        ClientResponsePacket res_packet = new ClientResponsePacket();
        res_packet.setResponse_code(ProjectConstants.SUCCESS);
        res_packet.setSync_data(total_data);
        return res_packet;
    }
}