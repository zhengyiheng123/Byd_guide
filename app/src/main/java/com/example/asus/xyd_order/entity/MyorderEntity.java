package com.example.asus.xyd_order.entity;

/**
 * Created by Zheng on 2017/2/16.
 */
public class MyorderEntity  {
    public String orderId;
    public String orderName;
    public String orderDetails;
    public String orderTime;
    public String orderType;

    public MyorderEntity(String orderId, String orderName, String orderDetails, String orderTime, String orderType) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDetails = orderDetails;
        this.orderTime = orderTime;
        this.orderType = orderType;
    }
}
