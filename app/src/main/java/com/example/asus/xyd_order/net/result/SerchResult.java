package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/8/8.
 */

public class SerchResult {

    private List<RestaurantsBean> restaurants;
    private List<ScenesBean> scenes;

    public List<RestaurantsBean> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantsBean> restaurants) {
        this.restaurants = restaurants;
    }

    public List<ScenesBean> getScenes() {
        return scenes;
    }

    public void setScenes(List<ScenesBean> scenes) {
        this.scenes = scenes;
    }

    public static class RestaurantsBean {
        /**
         * avg_cost : 0.00
         * logo_path : /uploads/20170718/ea29ff59af520001f40c9e7d45a578d0.jpg
         * meal_type : 1
         * rank : 3
         * res_id : 1
         * res_name : 哈尔的移动城堡
         */

        private String avg_cost;
        private String logo_path;
        private int meal_type;
        private int rank;
        private int res_id;
        private String res_name;

        public String getAvg_cost() {
            return avg_cost;
        }

        public void setAvg_cost(String avg_cost) {
            this.avg_cost = avg_cost;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public int getMeal_type() {
            return meal_type;
        }

        public void setMeal_type(int meal_type) {
            this.meal_type = meal_type;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getRes_id() {
            return res_id;
        }

        public void setRes_id(int res_id) {
            this.res_id = res_id;
        }

        public String getRes_name() {
            return res_name;
        }

        public void setRes_name(String res_name) {
            this.res_name = res_name;
        }
    }

    public static class ScenesBean {
        /**
         * avg_cost : 0.00
         * is_bookable : 1
         * logo_path : /uploads/20170714/dec3006a392c96a71b4848849d3087ab.png
         * scene_id : 20
         * scene_name : 哈尔的移动城堡
         * sub_cate_id : 7
         */

        private String avg_cost;
        private int is_bookable;
        private String logo_path;
        private int scene_id;
        private String scene_name;
        private int sub_cate_id;

        public String getAvg_cost() {
            return avg_cost;
        }

        public void setAvg_cost(String avg_cost) {
            this.avg_cost = avg_cost;
        }

        public int getIs_bookable() {
            return is_bookable;
        }

        public void setIs_bookable(int is_bookable) {
            this.is_bookable = is_bookable;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
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
    }
}
