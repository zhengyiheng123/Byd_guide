package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/8/10.
 */

public class TrainSuccessBean {

    /**
     * address : 哦哦哦wwwwww
     * book_time : 1502326800
     * group_num : kkmdmd
     * introduction :
     * mer_id : 19
     * merchant : {"address":"pekopon","logo_path":"/uploads/20170714/dec3006a392c96a71b4848849d3087ab.png","mer_name":"银河铁道999","mobile":"15605153256","route_name":"沃尔夫冈湖区游船(往)"}
     * mobile : 18518158917
     * ord_id : 232
     * ord_num : SC201708100001
     * ord_status : 0
     * ord_type : 7
     * pay_type : 1
     * post_type : 1
     * price : 77.00
     * region_name_list : 英国北京市
     * ticket : {"date":"2017-08-10","end_station":"Strobl","nums":"1","start_station":"Ried-falkenstein","ticket_type":"2","time_end":"19:55","time_spend":"03:40","time_start":"16:15"}
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
    private int ord_type;
    private int pay_type;
    private int post_type;
    private String price;
    private String region_name_list;
    private TicketBean ticket;
    private String user_name;

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

    public int getOrd_type() {
        return ord_type;
    }

    public void setOrd_type(int ord_type) {
        this.ord_type = ord_type;
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

    public TicketBean getTicket() {
        return ticket;
    }

    public void setTicket(TicketBean ticket) {
        this.ticket = ticket;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public static class MerchantBean {
        /**
         * address : pekopon
         * logo_path : /uploads/20170714/dec3006a392c96a71b4848849d3087ab.png
         * mer_name : 银河铁道999
         * mobile : 15605153256
         * route_name : 沃尔夫冈湖区游船(往)
         */

        private String address;
        private String logo_path;
        private String mer_name;
        private String mobile;
        private String route_name;

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

    public static class TicketBean {
        /**
         * date : 2017-08-10
         * end_station : Strobl
         * nums : 1
         * start_station : Ried-falkenstein
         * ticket_type : 2
         * time_end : 19:55
         * time_spend : 03:40
         * time_start : 16:15
         */

        private String date;
        private String end_station;
        private String nums;
        private String start_station;
        private String ticket_type;
        private String time_end;
        private String time_spend;
        private String time_start;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEnd_station() {
            return end_station;
        }

        public void setEnd_station(String end_station) {
            this.end_station = end_station;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }

        public String getStart_station() {
            return start_station;
        }

        public void setStart_station(String start_station) {
            this.start_station = start_station;
        }

        public String getTicket_type() {
            return ticket_type;
        }

        public void setTicket_type(String ticket_type) {
            this.ticket_type = ticket_type;
        }

        public String getTime_end() {
            return time_end;
        }

        public void setTime_end(String time_end) {
            this.time_end = time_end;
        }

        public String getTime_spend() {
            return time_spend;
        }

        public void setTime_spend(String time_spend) {
            this.time_spend = time_spend;
        }

        public String getTime_start() {
            return time_start;
        }

        public void setTime_start(String time_start) {
            this.time_start = time_start;
        }
    }
}
