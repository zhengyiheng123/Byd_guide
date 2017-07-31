package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/20.
 */

public class MyOrderBean {

    private List<OrdersBean> orders;

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean {
        /**
         * add_time : 1500522191
         * address : trigger
         * book_time : 1505037600
         * date_time : 2017.09.10 18:0020:00
         * logo_path : /uploads/20170623/d44f30aec36da5b087a8984e135034a9.jpg
         * mer_name : Little Witch Academy
         * mobile : 15605153256
         * ord_id : 194
         * ord_num : SS9021200251
         * ord_status : 2
         * ord_type : 6
         * price : 2612.00
         * route_name : 交响情人梦
         */
        private int mer_id;
        private int pay_type;
        private int add_time;
        private String address;
        private int book_time;
        private String date_time;
        private String logo_path;
        private String mer_name;
        private String mobile;
        private int ord_id;
        private String ord_num;
        private int ord_status;
        private int ord_type;
        private String price;
        private String route_name;

        public int getMer_id() {
            return mer_id;
        }

        public void setMer_id(int mer_id) {
            this.mer_id = mer_id;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

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

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRoute_name() {
            return route_name;
        }

        public void setRoute_name(String route_name) {
            this.route_name = route_name;
        }
    }
}
