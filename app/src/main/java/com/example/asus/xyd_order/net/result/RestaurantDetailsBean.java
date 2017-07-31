package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/7.
 */

public class RestaurantDetailsBean {

    private String address;
    private int cancel_condition;
    private String pic_meal;
    private int rank;

    private int is_collected;
    private String res_id;
    private String res_name;
    private int seat_num;
    private List<String> banners;
    private List<GroupMealBean> group_meal;
    private List<String> park;
    private List<SingleMealBean> single_meal;
    private List<String> time;
    private String map_url;

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

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCancel_condition() {
        return cancel_condition;
    }

    public void setCancel_condition(int cancel_condition) {
        this.cancel_condition = cancel_condition;
    }

    public String getPic_meal() {
        return pic_meal;
    }

    public void setPic_meal(String pic_meal) {
        this.pic_meal = pic_meal;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<GroupMealBean> getGroup_meal() {
        return group_meal;
    }

    public void setGroup_meal(List<GroupMealBean> group_meal) {
        this.group_meal = group_meal;
    }

    public List<String> getPark() {
        return park;
    }

    public void setPark(List<String> park) {
        this.park = park;
    }

    public List<SingleMealBean> getSingle_meal() {
        return single_meal;
    }

    public void setSingle_meal(List<SingleMealBean> single_meal) {
        this.single_meal = single_meal;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public static class GroupMealBean {
        /**
         * img_path : /uploads/20170421/9c4cd4441a22010eb169479977cd2c39.png
         * meal_id : 1
         * meal_name : 黑椒牛柳
         * meal_price : ["222.00","333.00","666.00"]
         */

        private String img_path;
        private int meal_id;
        private String meal_name;
        private List<String> meal_price;

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public int getMeal_id() {
            return meal_id;
        }

        public void setMeal_id(int meal_id) {
            this.meal_id = meal_id;
        }

        public String getMeal_name() {
            return meal_name;
        }

        public void setMeal_name(String meal_name) {
            this.meal_name = meal_name;
        }

        public List<String> getMeal_price() {
            return meal_price;
        }

        public void setMeal_price(List<String> meal_price) {
            this.meal_price = meal_price;
        }
    }

    public static class SingleMealBean  implements Serializable{
        /**
         * img_path : /uploads/20170421/9c4cd4441a22010eb169479977cd2c39.png
         * meal_id : 2
         * meal_name : 番茄意大利面
         * meal_price : 666.00
         * meal_weight : 200
         * ischecked
         * nums
         */


        private int nums;

        private String img_path;
        private int meal_id;
        private String meal_name;
        private Double meal_price;
        private int meal_weight;

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public int getMeal_id() {
            return meal_id;
        }

        public void setMeal_id(int meal_id) {
            this.meal_id = meal_id;
        }

        public String getMeal_name() {
            return meal_name;
        }

        public void setMeal_name(String meal_name) {
            this.meal_name = meal_name;
        }

        public Double getMeal_price() {
            return meal_price;
        }

        public void setMeal_price(Double meal_price) {
            this.meal_price = meal_price;
        }

        public int getMeal_weight() {
            return meal_weight;
        }

        public void setMeal_weight(int meal_weight) {
            this.meal_weight = meal_weight;
        }
    }
}
