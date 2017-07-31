package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/17.
 */

public class BaseTicketRouteBean implements Serializable {
    /**
     * ticket_id : 5
     * ticket_name : 一等票
     * tickets : [{"nums":0,"price":"188.00","ticket_nums":50,"ticket_type":"1-100岁","tp_id":5}]
     */
    //预定总票数
    private int allCount;
    private int ticket_id;
    private String ticket_name;
    private List<TicketsBean> tickets;

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTicket_name() {
        return ticket_name;
    }

    public void setTicket_name(String ticket_name) {
        this.ticket_name = ticket_name;
    }

    public List<TicketsBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketsBean> tickets) {
        this.tickets = tickets;
    }

    public static class TicketsBean implements Serializable{
        /**
         * nums : 0
         * price : 188.00
         * ticket_nums : 50
         * ticket_type : 1-100岁
         * tp_id : 5
         * totalPrice:1000总价
         */

        private int nums;
        private String price;
        private int ticket_nums;
        private String ticket_type;
        private int tp_id;
        private Double totlePrice;

        public Double getTotlePrice() {
            return totlePrice;
        }

        public void setTotlePrice(Double totlePrice) {
            this.totlePrice = totlePrice;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getTicket_nums() {
            return ticket_nums;
        }

        public void setTicket_nums(int ticket_nums) {
            this.ticket_nums = ticket_nums;
        }

        public String getTicket_type() {
            return ticket_type;
        }

        public void setTicket_type(String ticket_type) {
            this.ticket_type = ticket_type;
        }

        public int getTp_id() {
            return tp_id;
        }

        public void setTp_id(int tp_id) {
            this.tp_id = tp_id;
        }
    }
}
