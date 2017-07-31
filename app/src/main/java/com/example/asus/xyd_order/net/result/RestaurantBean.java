package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/7.
 */

public class RestaurantBean {

    private List<RestaurantsBean> restaurants;

    public List<RestaurantsBean> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantsBean> restaurants) {
        this.restaurants = restaurants;
    }

    public static class RestaurantsBean {
        /**
         * avg_cost : 0.00
         * distance : 0
         * logo_path :
         * meal_type : 0
         * park : 1,2,3
         * rank : 4
         * res_id : 1
         * res_name : 哈尔的移动城堡
         */

        private String avg_cost;
        private String distance;
        private String logo_path;
        private int meal_type;
        private String park;
        private int rank;
        private int res_id;
        private String res_name;

        public String getAvg_cost() {
            return avg_cost;
        }

        public void setAvg_cost(String avg_cost) {
            this.avg_cost = avg_cost;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public String getPark() {
            return park;
        }

        public void setPark(String park) {
            this.park = park;
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
}
