package kv_proxy;

/**
 * Created by abhishek on 11/27/16.
 */
public class ProxyProc {
    public static ProxyServer ps = null;

    public static void main(String[] arg) {
        System.out.println("Starting Proxy server");
        ps = new ProxyServer();
        ps.start();
    }
}