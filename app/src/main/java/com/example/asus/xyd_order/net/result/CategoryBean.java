package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/10.
 */

public class CategoryBean {

    private List<SubCategoriesBean> sub_categories;

    public List<SubCategoriesBean> getSub_categories() {
        return sub_categories;
    }

    public void setSub_categories(List<SubCategoriesBean> sub_categories) {
        this.sub_categories = sub_categories;
    }

    public static class SubCategoriesBean {
        /**
         * cate_name : 湘菜
         * sub_cate_id : 10
         * state : 选中状态
         */
        private int state;
        private String cate_name;
        private int sub_cate_id;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public int getSub_cate_id() {
            return sub_cate_id;
        }

        public void setSub_cate_id(int sub_cate_id) {
            this.sub_cate_id = sub_cate_id;
        }

        public SubCategoriesBean(String cate_name, int sub_cate_id) {
            this.cate_name = cate_name;
            this.sub_cate_id = sub_cate_id;
        }
    }
}
