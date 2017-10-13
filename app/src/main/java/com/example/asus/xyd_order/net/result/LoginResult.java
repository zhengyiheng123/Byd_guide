package com.example.asus.xyd_order.net.result;

/**
 * 这一类的entity 的定义就直接的放在一个包里面就行了
 *
 */
public class LoginResult {
    private int user_id;
    private String user_name;
    private String mobile;
    private String email;
    private String apitoken;
    private String avatar;
    //1注册未完成 认证成功2
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApitoken(String apitoken) {
        this.apitoken = apitoken;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getApitoken() {
        return apitoken;
    }
}
