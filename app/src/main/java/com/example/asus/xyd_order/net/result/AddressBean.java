package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/5.
 */

public class AddressBean {

    private List<AddressesBean> addresses;

    public List<AddressesBean> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressesBean> addresses) {
        this.addresses = addresses;
    }

    public static class AddressesBean implements Serializable{
        /**
         * address : 北京丰台区 天太难思想
         * is_default : 0
         * mobile : 15210967609
         * ua_id : 15
         * user_name : 张艳
         */

        private String address;
        private int is_default;
        private String mobile;
        private int ua_id;
        private String user_name;
        private String region_name_list;
        private String post_code;

        public void setRegion_name_list(String region_name_list) {
            this.region_name_list = region_name_list;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getRegion_name_list() {
            return region_name_list;
        }

        public String getPost_code() {
            return post_code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getUa_id() {
            return ua_id;
        }

        public void setUa_id(int ua_id) {
            this.ua_id = ua_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
