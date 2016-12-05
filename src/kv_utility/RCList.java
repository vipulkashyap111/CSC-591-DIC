package kv_utility;

import java.util.ArrayList;

/**
 * Created by abhishek on 11/27/16.
 */
public class RCList {
    private ArrayList<RCDetail> rc_list = null;

    public ArrayList<RCDetail> getList() {
        return rc_list;
    }

    public RCList() {
        rc_list = new ArrayList<RCDetail>();
    }

    public void add(RCDetail rcd) {
        rc_list.add(rcd);
    }

    public void remove(int index)
    {
        rc_list.remove(index);
    }

    public RCDetail get(int index) {
        return rc_list.get(index);
    }

    public int getSize() {
        return rc_list.size();
    }
}
