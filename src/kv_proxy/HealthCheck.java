package kv_proxy;

import kv_utility.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by abhishek on 12/4/16.
 */
public class HealthCheck extends Thread
{
    private Socket conn = null;
    private ClientRequestPacket req_packet = null;
    private ClientResponsePacket res_packet = null;

    public void run()
    {
        req_packet = new ClientRequestPacket();
        req_packet.setCommand(ProjectConstants.ALIVE_REQUEST);
        while(ProjectGlobal.is_alive_server_on)
        {
            System.out.println("Health Check for RC...");
            try
            {
                Thread.sleep(ProjectConstants.HEALTH_CHECK);
                checkRC();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void checkRC()
    {
        if(ProxyProc.ps.rc_data == null)
            return;

        for(int i = 0;i < ProxyProc.ps.rc_data.getSize();i++)
        {
            try {
                conn = new Socket(ProxyProc.ps.rc_data.get(i).ip, ProjectConstants.RC_LISTEN_PORT);
            } catch (IOException ex) {
                ProxyProc.ps.rc_data.remove(i);
                continue;
            }
            PacketTransfer.sendRequest(req_packet,conn);
            res_packet = PacketTransfer.recv_response(conn);
            System.out.println("Found RC : " + ProxyProc.ps.rc_data.get(i).ip + ":" + res_packet.getResponse_code());
            if(res_packet.getResponse_code() != ProjectConstants.SUCCESS)
                ProxyProc.ps.rc_data.remove(i);
        }
    }
}
