package com.example.asus.xyd_order.net.result;

import java.io.Serializable;

/**
 * Created by Zheng on 2017/6/23.
 */

public class RegisterBean implements Serializable{

    /**
     * mobile : 18518158917
     * area_code : 353
     * user_name : HD422626949699
     * email :
     * user_id : 25
     * apitoken : caa23a5b486a5473ac686e7269a88035b18cca87
     */

    private String mobile;
    private String area_code;
    private String user_name;
    private String email;
    private String user_id;
    private String apitoken;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getApitoken() {
        return apitoken;
    }

    public void setApitoken(String apitoken) {
        this.apitoken = apitoken;
    }
}
