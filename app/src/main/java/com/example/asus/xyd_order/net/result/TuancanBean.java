package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/10.
 */

public class TuancanBean {

    private int meal_id;
    private int mer_id;
    private String meal_name;
    private String img_path;
    private List<PriceListBean> price_list;

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
        this.mer_id = mer_id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public List<PriceListBean> getPrice_list() {
        return price_list;
    }

    public void setPrice_list(List<PriceListBean> price_list) {
        this.price_list = price_list;
    }

    public static class PriceListBean implements Serializable{
        /**
         * mp_id : 10
         * meal_price : 222.00
         * meal_detail : 很好
         */
        private String img_path;
        private boolean isChecked;
        private String meal_name;
        private int mp_id;
        private String meal_price;
        private String meal_detail;

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setMeal_name(String meal_name) {
            this.meal_name = meal_name;
        }

        public String getMeal_name() {
            return meal_name;
        }

        public int getMp_id() {
            return mp_id;
        }

        public void setMp_id(int mp_id) {
            this.mp_id = mp_id;
        }

        public String getMeal_price() {
            return meal_price;
        }

        public void setMeal_price(String meal_price) {
            this.meal_price = meal_price;
        }

        public String getMeal_detail() {
            return meal_detail;
        }

        public void setMeal_detail(String meal_detail) {
            this.meal_detail = meal_detail;
        }
    }
}
