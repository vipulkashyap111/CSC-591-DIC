package kv_memNodes;

import kv_utility.DoubleLL;
import kv_utility.ProjectConstants;
import kv_utility.ProjectGlobal;
import kv_utility.ValueDetail;

/**
 * Created by abhishek on 11/27/16.
 */
public class CleanUpDmn extends Thread {
    DoubleLL value_list = null;

    @Override
    public void run() {
        System.out.println("Starting the CleanUp DMN");
        while (ProjectGlobal.is_CLEANUP_on)
        {
            /* Need to add synchronization since clean up and addition to list should not happen at the same time */
            System.out.println("Checking for cleanup values....");
            checkForOldKeys();
            try
            {
                Thread.sleep(ProjectConstants.CLEAN_UP_WT_TM);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                return;
            }
        }
    }

    public void checkForOldKeys()
    {
        value_list = MemNodeProc.getTime_sorted_list();
        long curr_ts = MemNodeProc.getUnixTimeGenerator().getTime();
        ValueDetail last = value_list.reverseIterator();

        /* Start from tail and keep on removing till all the element are removed. */
        while (last != null)
        {
            if ((curr_ts - last.getUnixTS()) < ProjectConstants.OLD_THRESHOLD)
                break;
            last = value_list.getRNext(last);
        }
        if(last.next != null)
            last.next.prev = null;
        last.next = null;
        return;
    }
}