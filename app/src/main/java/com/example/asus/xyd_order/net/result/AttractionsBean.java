package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/17.
 */

public class AttractionsBean {

    /**
     * address : 艾泽拉斯大陆
     * banners : ["/uploads/20170623/c09890bf7b6e064896d13e2387d76478.jpg","/uploads/20170623/c88d0d76f8db3b3b9ac41ac781a3eba0.jpg","/uploads/20170623/d44f30aec36da5b087a8984e135034a9.jpg"]
     * introduction : 开门看心情
     * is_bookable : 1
     * rank : 0
     * scene_id : 9
     * scene_name : 天空之城(代理商)
     * sub_cate_id : 5
     * time : ["周一: 10:00-12:00 13:00-18:00","周二: 03:00-05:00 11:00-19:00"]
     * time_desc :
     */
    private String map_url;
    private int is_collected;
    private String address;
    private String introduction;
    private int is_bookable;
    private int rank;
    private int scene_id;
    private String scene_name;
    private int sub_cate_id;
    private String time_desc;
    private List<String> banners;
    private List<String> time;

    public String getMap_url() {
        return map_url;
    }

    public void setMap_url(String map_url) {
        this.map_url = map_url;
    }

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIs_bookable() {
        return is_bookable;
    }

    public void setIs_bookable(int is_bookable) {
        this.is_bookable = is_bookable;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScene_id() {
        return scene_id;
    }

    public void setScene_id(int scene_id) {
        this.scene_id = scene_id;
    }

    public String getScene_name() {
        return scene_name;
    }

    public void setScene_name(String scene_name) {
        this.scene_name = scene_name;
    }

    public int getSub_cate_id() {
        return sub_cate_id;
    }

    public void setSub_cate_id(int sub_cate_id) {
        this.sub_cate_id = sub_cate_id;
    }

    public String getTime_desc() {
        return time_desc;
    }

    public void setTime_desc(String time_desc) {
        this.time_desc = time_desc;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
