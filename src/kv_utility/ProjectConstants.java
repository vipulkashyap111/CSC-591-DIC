package kv_utility;

/**
 * Created by abhishek on 11/26/16.
 */
public class ProjectConstants {
    public static final int MN_LISTEN_PORT = 5000;
    public static final int RC_LISTEN_PORT = 7000;
    public static final int PR_LISTEN_PORT = 7001;
    public static final int ALIVE_REQUEST = 50;
    public static final int REQUEST_BACK_LOG = 25;
    public static final int NUM_OF_WORKERS = 20;
    public static final int CLEAN_UP_WT_TM = 100000;
    public static final int OLD_THRESHOLD = 300000;
    public static final int ONE = 1;
    public static final int THREE = 3;
    public static final int ZERO = 0;

    /* Return Status */
    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;

    /* Command list */
    public static final int PUT = 0;
    public static final int GET = 1;
    public static final int ADD_MEM_NODES = 2;
    public static final int SYNC_MEM_NODE = 10;
    public static final int GET_RC = 3;
    public static final int GET_RING = 4;
    public static final int ADD_RC_NODES = 5;

    /* Message Constants */
    public static final String MESG_KNF = "Unnable to fetch the key";
    public static final int KNF = 1;
    public static final String MESG_RCNF = "Unnable to find request Co-Ordinator";
    public static final String MESG_RCF = "Found request Co-Ordinator";
    public static final String MESG_KF = "Fetched the key from Data Store";
    public static final String SUCCESS_PUT = "Successfully Stored the Value";
    public static final String MESG_ADDED_RC = "Successfully added the Request Co-Ordinator";
    public static final int MAX_NUM_NODES = 100;
}
