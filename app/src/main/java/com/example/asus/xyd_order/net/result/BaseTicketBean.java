package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/7/14.
 */

public class BaseTicketBean {
    /**
     * nums : 0
     * price : 288.00
     * ticket_nums : 40
     * ticket_type : 1-18岁
     * tp_id : 8
     */

    private int nums;
    private String price;
    private int ticket_nums;
    private String ticket_type;
    private int tp_id;
    private Double totalPrice;

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
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
