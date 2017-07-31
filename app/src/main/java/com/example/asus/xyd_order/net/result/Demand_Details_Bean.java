package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/6/26.
 */

public class Demand_Details_Bean {

    /**
     * company : 哦呦喂做最我
     * countries : ["法国","英国","德国"]
     * dmd_area : 3
     * dmd_desc : 魔王勇者
     * dmd_id : 100
     * end_time : 1262361599
     * group_num : 1492849283
     * group_type : 1
     * level_req : 3
     * mobile : 18212392010
     * ord_num : BM1944317471
     * pay_type : 1
     * price : 2000.00
     * reply_user_info : {"birth":"1994-07-14","gender":0,"mobile":"15210967609","user_id":40,"user_name":"lemon"}
     * route_desc : 我想问一下
     * route_img_path : /uploads/demand/20170627/409cdac88abe3c3df784fade41233c28.png,/uploads/demand/20170627/88ee8392a7d7c890de19e447186fe6c3.png,/uploads/demand/20170627/7e1eb0ce171df7522f64a5507bc53e43.jpg
     * service_type : 2
     * start_time : 1262275200
     * user_name : Bloodmage
     */
    private String company;
    private int dmd_area;
    private String dmd_desc;
    private int dmd_id;
    private int end_time;
    private String group_num;
    private int group_type;
    private int level_req;
    private String mobile;
    private String ord_num;
    private int pay_type;
    private String price;
    private ReplyUserInfoBean reply_user_info;
    private String route_desc;
    private String route_img_path;
    private int service_type;
    private int start_time;
    private String user_name;
    private List<String> countries;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getDmd_area() {
        return dmd_area;
    }

    public void setDmd_area(int dmd_area) {
        this.dmd_area = dmd_area;
    }

    public String getDmd_desc() {
        return dmd_desc;
    }

    public void setDmd_desc(String dmd_desc) {
        this.dmd_desc = dmd_desc;
    }

    public int getDmd_id() {
        return dmd_id;
    }

    public void setDmd_id(int dmd_id) {
        this.dmd_id = dmd_id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrd_num() {
        return ord_num;
    }

    public void setOrd_num(String ord_num) {
        this.ord_num = ord_num;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ReplyUserInfoBean getReply_user_info() {
        return reply_user_info;
    }

    public void setReply_user_info(ReplyUserInfoBean reply_user_info) {
        this.reply_user_info = reply_user_info;
    }

    public String getRoute_desc() {
        return route_desc;
    }

    public void setRoute_desc(String route_desc) {
        this.route_desc = route_desc;
    }

    public String getRoute_img_path() {
        return route_img_path;
    }

    public void setRoute_img_path(String route_img_path) {
        this.route_img_path = route_img_path;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public static class ReplyUserInfoBean {
        /**
         * birth : 1994-07-14
         * gender : 0
         * mobile : 15210967609
         * user_id : 40
         * user_name : lemon
         */
        private String avatar;
        private String birth;
        private int gender;
        private String mobile;
        private int user_id;
        private String user_name;

        public String getBirth() {
            return birth;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
