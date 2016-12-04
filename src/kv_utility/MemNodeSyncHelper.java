package kv_utility;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gmeneze on 12/3/16.
 */
public class MemNodeSyncHelper implements Serializable {
    public static final long serialVersionUID = -3040196452457271695L;
    public ArrayList<MemNodeSyncDetails> syncIps;

    public MemNodeSyncHelper() {
        syncIps = new ArrayList<MemNodeSyncDetails>();
    }
}
