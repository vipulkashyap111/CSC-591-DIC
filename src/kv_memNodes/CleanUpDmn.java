package kv_memNodes;

import kv_utility.DoubleLL;
import kv_utility.ProjectConstants;
import kv_utility.ProjectGlobal;

/**
 * Created by abhishek on 11/27/16.
 */
public class CleanUpDmn implements Runnable {
    DoubleLL value_list = null;

    @Override
    public void run() {
        System.out.println("Starting the CleanUp DMN");
        while (ProjectGlobal.is_CLEANUP_on) {
            checkForOldKeys();
            try {
                Thread.sleep(ProjectConstants.CLEAN_UP_WT_TM);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public CleanUpDmn() {

    }

    public void checkForOldKeys() {
        value_list = MemNodeProc.getTime_sorted_list();
    }
}