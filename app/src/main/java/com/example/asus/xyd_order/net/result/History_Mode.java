package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/5.
 */

public class History_Mode {

    private List<SamplesBean> samples;

    public List<SamplesBean> getSamples() {
        return samples;
    }

    public void setSamples(List<SamplesBean> samples) {
        this.samples = samples;
    }

    public static class SamplesBean {
        /**
         * countries : ["法国","英国","德国","俄罗斯"]
         * ds_id : 19
         * ds_name : 33333333
         * end_time : 1481126399
         * group_num : 1492849283
         * group_type : 3
         * level_req : 3
         * service_type : 2
         * start_time : 1481111111
         */

        private int ds_id;
        private String ds_name;
        private int end_time;
        private String group_num;
        private int group_type;
        private int level_req;
        private int service_type;
        private int start_time;
        private List<String> countries;

        public int getDs_id() {
            return ds_id;
        }

        public void setDs_id(int ds_id) {
            this.ds_id = ds_id;
        }

        public String getDs_name() {
            return ds_name;
        }

        public void setDs_name(String ds_name) {
            this.ds_name = ds_name;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
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

        public int getLevel_req() {
            return level_req;
        }

        public void setLevel_req(int level_req) {
            this.level_req = level_req;
        }

        public int getService_type() {
            return service_type;
        }

        public void setService_type(int service_type) {
            this.service_type = service_type;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
            this.countries = countries;
        }
    }
}
