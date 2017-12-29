package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/12/29.
 */

public class RegionsBean {
    public RegionsBean() {
    }

    public RegionsBean(int region_id, String region_name, int region_pid, String original_name) {
        this.region_id = region_id;
        this.region_name = region_name;
        this.region_pid = region_pid;
        this.original_name=original_name;
    }

    /**
     * region_id : 2
     * region_name : 法国
     * region_pid : 0
     */
    private String original_name;
    private int region_id;
    private String region_name;
    private int region_pid;

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getRegion_pid() {
        return region_pid;
    }

    public void setRegion_pid(int region_pid) {
        this.region_pid = region_pid;
    }
}
