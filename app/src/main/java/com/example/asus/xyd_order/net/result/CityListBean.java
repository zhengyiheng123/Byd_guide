package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/12.
 */

public class CityListBean implements Serializable{

    private List<RegionsBean> regions;

    public List<RegionsBean> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionsBean> regions) {
        this.regions = regions;
    }

    public static class RegionsBean implements Serializable{
        public RegionsBean(int region_id, String region_name, int region_pid) {
            this.region_id = region_id;
            this.region_name = region_name;
            this.region_pid = region_pid;
        }

        /**
         * region_id : 2
         * region_name : 法国
         * region_pid : 0
         */

        private int region_id;
        private String region_name;
        private int region_pid;

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
}
