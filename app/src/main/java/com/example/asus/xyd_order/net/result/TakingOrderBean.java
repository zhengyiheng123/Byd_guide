package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/7.
 */

public class TakingOrderBean {

    private List<DemandsBean> demands;

    public List<DemandsBean> getDemands() {
        return demands;
    }

    public void setDemands(List<DemandsBean> demands) {
        this.demands = demands;
    }

    public static class DemandsBean {
        /**
         * dmd_id : 135
         * group_num : 1492849283
         * group_type : 3
         * service_type : 2
         * level_req : 3
         * start_time : 1481111111
         * end_time : 1481126399
         * ord_num : DM54029809355859
         * countries : ["法国","英国","德国","俄罗斯"]
         */
        private int dmd_id;
        private String group_num;
        private int group_type;
        private int service_type;
        private int level_req;
        private int start_time;
        private int end_time;
        private String ord_num;
        private List<String> countries;


        public int getDmd_id() {
            return dmd_id;
        }

        public void setDmd_id(int dmd_id) {
            this.dmd_id = dmd_id;
        }

        public String getGroup_num() {
            return group_num;
        }

        public void setGroup_num(String group_num) {
            this.group_num = group_num;
        }

        public int getGroup_type() {
            return group_type;
        }

        public void setGroup_type(int group_type) {
            this.group_type = group_type;
        }

        public int getService_type() {
            return service_type;
        }

        public void setService_type(int service_type) {
            this.service_type = service_type;
        }

        public int getLevel_req() {
            return level_req;
        }

        public void setLevel_req(int level_req) {
            this.level_req = level_req;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public String getOrd_num() {
            return ord_num;
        }

        public void setOrd_num(String ord_num) {
            this.ord_num = ord_num;
        }

        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
            this.countries = countries;
        }
    }
}
