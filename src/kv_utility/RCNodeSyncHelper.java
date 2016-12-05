package kv_utility;

import kv_requestCoordinator.Ring;

import java.io.Serializable;

/**
 * Created by gmeneze on 12/5/16.
 */
public class RCNodeSyncHelper implements Serializable {
    public static final long serialVersionUID = -3040196452457271695L;
    public Ring ring;

    public RCNodeSyncHelper() {
        ring = Ring.getInstance();
    }

    public Ring getRing() {
        return ring;
    }
}
