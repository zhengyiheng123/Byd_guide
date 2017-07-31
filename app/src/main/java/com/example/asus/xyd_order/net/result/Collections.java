package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/20.
 */

public class Collections {

    private List<CollectionsBean> collections;

    public List<CollectionsBean> getCollections() {
        return collections;
    }

    public void setCollections(List<CollectionsBean> collections) {
        this.collections = collections;
    }

    public static class CollectionsBean {
        /**
         * avg_cost : 0.00
         * is_bookable :
         * logo_path : /uploads/20170718/ea29ff59af520001f40c9e7d45a578d0.jpg
         * meal_type : 1
         * rank : 3
         * res_id : 1
         * res_name : 哈尔的移动城堡
         * scene_id :
         * scene_name :
         * sub_cate_id :
         */

        private String avg_cost;
        private String is_bookable;
        private String logo_path;
        private String meal_type;
        private String rank;
        private String res_id;
        private String res_name;
        private String scene_id;
        private String scene_name;
        private String sub_cate_id;

        public String getAvg_cost() {
            return avg_cost;
        }

        public void setAvg_cost(String avg_cost) {
            this.avg_cost = avg_cost;
        }

        public String getIs_bookable() {
            return is_bookable;
        }

        public void setIs_bookable(String is_bookable) {
            this.is_bookable = is_bookable;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getMeal_type() {
            return meal_type;
        }

        public void setMeal_type(String meal_type) {
            this.meal_type = meal_type;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getRes_id() {
            return res_id;
        }

        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getRes_name() {
            return res_name;
        }

        public void setRes_name(String res_name) {
            this.res_name = res_name;
        }

        public String getScene_id() {
            return scene_id;
        }

        public void setScene_id(String scene_id) {
            this.scene_id = scene_id;
        }

        public String getScene_name() {
            return scene_name;
        }

        public void setScene_name(String scene_name) {
            this.scene_name = scene_name;
        }

        public String getSub_cate_id() {
            return sub_cate_id;
        }

        public void setSub_cate_id(String sub_cate_id) {
            this.sub_cate_id = sub_cate_id;
        }
    }
}
