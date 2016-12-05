package kv_proxy;

/**
 * Created by abhishek on 11/27/16.
 */
public class ProxyProc {
    public static ProxyServer ps = null;

    public static void main(String[] arg)
    {
        System.out.println("Starting Proxy Server");
        ps = new ProxyServer();
        /* Start the heart beat checker */
        HealthCheck hc = new HealthCheck();
        hc.start();
        ps.start();
    }
}