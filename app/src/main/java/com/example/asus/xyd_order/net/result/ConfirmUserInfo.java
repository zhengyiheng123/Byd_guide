package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/7/3.
 */

public class ConfirmUserInfo {
    private int user_id ;
    private String user_name;
    private String avatar;
    private String mobile;
    private String email;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }
}
