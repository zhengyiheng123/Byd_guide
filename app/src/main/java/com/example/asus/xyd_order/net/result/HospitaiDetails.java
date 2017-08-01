package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/8/1.
 */

public class HospitaiDetails {

    /**
     * experience : 德国骨科......很好.........
     * hos_address : 德国
     * hos_id : 2
     * hos_name : The Great Ormond Hospital
     * hos_phone : 18282839
     * hos_type : 社区医院
     * img_path : /uploads/hospital/20170731/6e405b5899cc362471344c482b134c49.png
     * time : 135休息 246放假 礼拜日看心情
     */

    private String experience;
    private String hos_address;
    private int hos_id;
    private String hos_name;
    private String hos_phone;
    private String hos_type;
    private String img_path;
    private String time;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getHos_address() {
        return hos_address;
    }

    public void setHos_address(String hos_address) {
        this.hos_address = hos_address;
    }

    public int getHos_id() {
        return hos_id;
    }

    public void setHos_id(int hos_id) {
        this.hos_id = hos_id;
    }

    public String getHos_name() {
        return hos_name;
    }

    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }

    public String getHos_phone() {
        return hos_phone;
    }

    public void setHos_phone(String hos_phone) {
        this.hos_phone = hos_phone;
    }

    public String getHos_type() {
        return hos_type;
    }

    public void setHos_type(String hos_type) {
        this.hos_type = hos_type;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
