package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/13.
 */

public class JingdianBean {

    private List<ScenesBean> scenes;

    public List<ScenesBean> getScenes() {
        return scenes;
    }

    public void setScenes(List<ScenesBean> scenes) {
        this.scenes = scenes;
    }

    public static class ScenesBean {
        /**
         * avg_cost : 0.00
         * distance : 950247
         * logo_path : /uploads/20170623/d44f30aec36da5b087a8984e135034a9.jpg
         * scene_id : 9
         * scene_name : 天空之城(代理商)
         * sub_cate_id : 5
         */
        private int is_bookable;
        private String avg_cost;
        private String distance;
        private String logo_path;
        private int scene_id;
        private String scene_name;
        private int sub_cate_id;

        public int getIs_bookable() {
            return is_bookable;
        }

        public void setIs_bookable(int is_bookable) {
            this.is_bookable = is_bookable;
        }

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
