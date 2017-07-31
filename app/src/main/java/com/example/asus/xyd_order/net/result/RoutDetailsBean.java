package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/13.
 */

public class RoutDetailsBean {

    /**
     * driver_free : 1
     * group_start : 10
     * group_ticket : [{"nums":0,"price":"288.00","ticket_nums":40,"ticket_type":"1-18岁","tp_id":8},{"nums":0,"price":"188.00","ticket_nums":22,"ticket_type":"18-50岁","tp_id":9},{"nums":0,"price":"300.00","ticket_nums":100,"ticket_type":"51-100岁","tp_id":12}]
     * guide_free : 1
     * mer_id : 9
     * normal_ticket : [{"nums":0,"price":"300.00","ticket_nums":40,"ticket_type":"1-18岁","tp_id":8},{"nums":0,"price":"200.00","ticket_nums":22,"ticket_type":"18-50岁","tp_id":9},{"nums":0,"price":"100.00","ticket_nums":20,"ticket_type":"学生票","tp_id":10},{"nums":0,"price":"100.00","ticket_nums":11,"ticket_type":"残疾人票","tp_id":11},{"nums":0,"price":"200.00","ticket_nums":100,"ticket_type":"51-100岁","tp_id":12}]
     * route_id : 306
     * user_address : {"address":"诺德中","mobile":"18566322554","region_name_list":"中国丰台","user_name":"郑前期"}
     */

    private int driver_free;
    private int group_start;
    private int guide_free;
    private int mer_id;
    private int route_id;
    private UserAddressBean user_address;
    private List<GroupTicketBean> group_ticket;
    private List<NormalTicketBean> normal_ticket;


    public int getDriver_free() {
        return driver_free;
    }

    public void setDriver_free(int driver_free) {
        this.driver_free = driver_free;
    }

    public int getGroup_start() {
        return group_start;
    }

    public void setGroup_start(int group_start) {
        this.group_start = group_start;
    }

    public int getGuide_free() {
        return guide_free;
    }

    public void setGuide_free(int guide_free) {
        this.guide_free = guide_free;
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

    public static class GroupTicketBean extends BaseTicketBean{

    }

    public static class NormalTicketBean extends BaseTicketBean{

    }
}
