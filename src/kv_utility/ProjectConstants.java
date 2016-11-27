package kv_utility;

/**
 * Created by abhishek on 11/26/16.
 */
public class ProjectConstants {
    public static int MN_LISTEN_PORT = 5000;
    public static int RC_LISTEN_PORT = 7000;
    public static int REQUEST_BACK_LOG = 25;
    public static int NUM_OF_WORKERS = 20;
    public static int ONE = 1;

    /* Return Status */
    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;

    /* Command list */
    public static final int PUT = 0;
    public static final int GET = 1;

    /* CONSTANTS FOR TYPE OF CLIENT REQUESTS i.e. commands*/
    public final static int GET = 11;
    public final static int PUT = 12;
}
