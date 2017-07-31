package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/18.
 */

public class AttractionsOrderSuccess {

    /**
     * address : 哦哦哦wwwwww
     * book_time : 1505037600
     * group_num : kkh555
     * group_ticket : [{"ticket_id":"5","ticket_name":"一等票","tickets":[{"nums":1,"price":188,"ticket_type":50,"tp_id":5}]},{"ticket_id":"6","ticket_name":"二等票","tickets":[{"nums":6,"price":540,"ticket_type":5,"tp_id":10},{"nums":4,"price":512,"ticket_type":20,"tp_id":11}]}]
     * introduction : 1.票不给退
     * mer_id : 18
     * merchant : {"address":"trigger","logo_path":"/uploads/20170623/d44f30aec36da5b087a8984e135034a9.jpg","mer_name":"Little Witch Academy","mobile":"15605153256","route_name":"交响情人梦"}
     * mobile : 18518158917
     * normal_ticket : [{"ticket_id":"5","ticket_name":"一等票","tickets":[{"nums":1,"price":200,"ticket_type":50,"tp_id":5}]},{"ticket_id":"6","ticket_name":"二等票","tickets":[{"nums":2,"price":200,"ticket_type":5,"tp_id":8},{"nums":1,"price":150,"ticket_type":20,"tp_id":11}]}]
     * ord_id : 133
     * ord_num : SS5630383139
     * ord_status : 0
     * pay_type : 1
     * post_type : 1
     * price : 1790.00
     * region_name_list : 英国北京市
     * user_name : 郑宜衡
     */

    private String address;
    private int book_time;
    private String group_num;
    private String introduction;
    private int mer_id;
    private MerchantBean merchant;
    private String mobile;
    private int ord_id;
    private String ord_num;
    private int ord_status;
    private int pay_type;
    private int post_type;
    private String price;
    private String region_name_list;
    private String user_name;
    private List<GroupTicketBean> group_ticket;
    private List<NormalTicketBean> normal_ticket;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBook_time() {
        return book_time;
    }

    public void setBook_time(int book_time) {
        this.book_time = book_time;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
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

    public MerchantBean getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantBean merchant) {
        this.merchant = merchant;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(int ord_id) {
        this.ord_id = ord_id;
    }

    public String getOrd_num() {
        return ord_num;
    }

    public void setOrd_num(String ord_num) {
        this.ord_num = ord_num;
    }

    public int getOrd_status() {
        return ord_status;
    }

    public void setOrd_status(int ord_status) {
        this.ord_status = ord_status;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getPost_type() {
        return post_type;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public static class MerchantBean {
        /**
         * address : trigger
         * logo_path : /uploads/20170623/d44f30aec36da5b087a8984e135034a9.jpg
         * mer_name : Little Witch Academy
         * mobile : 15605153256
         * route_name : 交响情人梦
         */
        private String date_time;
        private String address;
        private String logo_path;
        private String mer_name;
        private String mobile;
        private String route_name;

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getMer_name() {
            return mer_name;
        }

        public void setMer_name(String mer_name) {
            this.mer_name = mer_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRoute_name() {
            return route_name;
        }

        public void setRoute_name(String route_name) {
            this.route_name = route_name;
        }
    }

    public static class GroupTicketBean extends BaseTicketRouteBean{

    }

    public static class NormalTicketBean extends BaseTicketRouteBean {

    }
}
