package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/17.
 */

public class RouteDetails {

    /**
     * group_start : 10
     * group_ticket : [{"ticket_id":5,"ticket_name":"一等票","tickets":[{"nums":0,"price":"188.00","ticket_nums":50,"ticket_type":"1-100岁","tp_id":5}]},{"ticket_id":6,"ticket_name":"二等票","tickets":[{"nums":0,"price":"90.00","ticket_nums":5,"ticket_type":"1-12岁","tp_id":10},{"nums":0,"price":"128.00","ticket_nums":20,"ticket_type":"13-18岁","tp_id":11},{"nums":0,"price":"228.00","ticket_nums":20,"ticket_type":"19-100岁","tp_id":12}]},{"ticket_id":7,"ticket_name":"三等票","tickets":[{"nums":0,"price":"128.00","ticket_nums":10,"ticket_type":"1-18岁","tp_id":6},{"nums":0,"price":"188.00","ticket_nums":10,"ticket_type":"19-100岁","tp_id":7}]}]
     * img_path : /uploads/20170703/64a7faa318843170dbeb38f0c86e07e8.jpg
     * introduction : 1.票不给退
     * mer_id : 18
     * normal_ticket : [{"ticket_id":5,"ticket_name":"一等票","tickets":[{"nums":0,"price":"200.00","ticket_nums":50,"ticket_type":"1-100岁","tp_id":5}]},{"ticket_id":6,"ticket_name":"二等票","tickets":[{"nums":0,"price":"100.00","ticket_nums":5,"ticket_type":"学生票","tp_id":8},{"nums":0,"price":"100.00","ticket_nums":3,"ticket_type":"残疾人票","tp_id":9},{"nums":0,"price":"100.00","ticket_nums":5,"ticket_type":"1-12岁","tp_id":10},{"nums":0,"price":"150.00","ticket_nums":20,"ticket_type":"13-18岁","tp_id":11},{"nums":0,"price":"240.00","ticket_nums":20,"ticket_type":"19-100岁","tp_id":12}]},{"ticket_id":7,"ticket_name":"三等票","tickets":[{"nums":0,"price":"150.00","ticket_nums":10,"ticket_type":"1-18岁","tp_id":6},{"nums":0,"price":"200.00","ticket_nums":10,"ticket_type":"19-100岁","tp_id":7}]}]
     * route_id : 4
     * user_address : {"address":"诺德中","mobile":"18566322554","region_name_list":"中国丰台","user_name":"郑前期"}
     */

    private int group_start;
    private String img_path;
    private String introduction;
    private int mer_id;
    private int route_id;
    private UserAddressBean user_address;
    private List<GroupTicketBean> group_ticket;
    private List<NormalTicketBean> normal_ticket;

    public int getGroup_start() {
        return group_start;
    }

    public void setGroup_start(int group_start) {
        this.group_start = group_start;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
        this.mer_id = mer_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public UserAddressBean getUser_address() {
        return user_address;
    }

    public void setUser_address(UserAddressBean user_address) {
        this.user_address = user_address;
    }

    public List<GroupTicketBean> getGroup_ticket() {
        return group_ticket;
    }

    public void setGroup_ticket(List<GroupTicketBean> group_ticket) {
        this.group_ticket = group_ticket;
    }

    public List<NormalTicketBean> getNormal_ticket() {
        return normal_ticket;
    }

    public void setNormal_ticket(List<NormalTicketBean> normal_ticket) {
        this.normal_ticket = normal_ticket;
    }

    public static class UserAddressBean {
        /**
         * address : 诺德中
         * mobile : 18566322554
         * region_name_list : 中国丰台
         * user_name : 郑前期
         */

        private String address;
        private String mobile;
        private String region_name_list;
        private String user_name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRegion_name_list() {
            return region_name_list;
        }

        public void setRegion_name_list(String region_name_list) {
            this.region_name_list = region_name_list;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }

    public static class GroupTicketBean extends BaseTicketRouteBean {

    }

    public static class NormalTicketBean extends BaseTicketRouteBean {

    }
}
