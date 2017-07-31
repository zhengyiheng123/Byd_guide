package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/7/19.
 */

public class ServiceBean {

    /**
     * mobile : 12515151617
     * mobile_bak : 12515151618
     * address : 法国巴黎苏黎世机场
     * time : 9:00-12:00 14:00-18:00
     * email : 12515151617@qq.com
     * fax : 12345678
     */

    private String mobile;
    private String mobile_bak;
    private String address;
    private String time;
    private String email;
    private String fax;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile_bak() {
        return mobile_bak;
    }

    public void setMobile_bak(String mobile_bak) {
        this.mobile_bak = mobile_bak;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
