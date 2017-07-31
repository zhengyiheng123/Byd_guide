package com.example.asus.xyd_order.net.result;

import java.io.Serializable;

/**
 * Created by Zheng on 2017/7/25.
 */

public class PushEntity implements Serializable{
    private String user_id;//推送者id
    private String to_user_id;//接受者id
    private String msg;//推送消息
    private String ord_id;//订单id
    private String ord_status;//订单状态
    private String ord_type;//订单类型 -1|需求订单 1|餐饮订单 5|普通景区 6|表演类 7|火车游船
    private String add_time;//推送时间

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(String ord_id) {
        this.ord_id = ord_id;
    }

    public String getOrd_status() {
        return ord_status;
    }

    public void setOrd_status(String ord_status) {
        this.ord_status = ord_status;
    }

    public String getOrd_type() {
        return ord_type;
    }

    public void setOrd_type(String ord_type) {
        this.ord_type = ord_type;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
