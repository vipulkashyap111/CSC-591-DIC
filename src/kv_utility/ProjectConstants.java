package kv_utility;

/**
 * Created by abhishek on 11/26/16.
 */
public class ProjectConstants {
    public static final int MN_LISTEN_PORT = 5000;
    public static final int REQUEST_BACK_LOG = 25;
    public static final int NUM_OF_WORKERS = 20;

    /* Return Status */
    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;

    /* Command list */
    public static final int PUT = 0;
    public static final int GET = 1;
}
