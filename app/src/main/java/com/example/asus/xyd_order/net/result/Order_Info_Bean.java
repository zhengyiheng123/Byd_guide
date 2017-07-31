package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/11.
 */

public class Order_Info_Bean {

    /**
     * add_time : 1499823236
     * book_time : 1499823180
     * cancel_condition : 0
     * group_meal : {}
     * group_num : jjkmn
     * meal_type : 2
     * merchant : {"address":"北京市丰台区丰台科技园","logo_path":"/uploads/20170623/d44f30aec36da5b087a8984e135034a9.jpg","mer_name":"哈尔的移动城堡","mobile":"12938293812"}
     * message : 我9嗯YY
     * mobile : 18518158917
     * ord_id : 72
     * ord_num : RS1383593284
     * ord_status : -2
     * pay_type : 1
     * price : 716.00
     * seat_cost : 5
     * single_meal : [{"img_path":"/uploads/20170421/9c4cd4441a22010eb169479977cd2c39.png","meal_id":2,"meal_name":"番茄意大利面","meal_price":666,"meal_weight":200,"nums":1},{"img_path":"/uploads/20170613/606015484a0e665673b34b225401bdf7.jpg","meal_id":6,"meal_name":"虾仁芝士烩饭","meal_price":50,"meal_weight":300,"nums":1}]
     * user_name : 噢www
     */

    private String mer_id;
    private int add_time;
    private int book_time;
    private int cancel_condition;
    private GroupMealBean group_meal;
    private String group_num;
    private String meal_type;
    private MerchantBean merchant;
    private String message;
    private String mobile;
    private int ord_id;
    private String ord_num;
    private int ord_status;
    private int pay_type;
    private String price;
    private int seat_cost;
    private String user_name;
    private List<SingleMealBean> single_meal;

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getBook_time() {
        return book_time;
    }

    public void setBook_time(int book_time) {
        this.book_time = book_time;
    }

    public int getCancel_condition() {
        return cancel_condition;
    }

    public void setCancel_condition(int cancel_condition) {
        this.cancel_condition = cancel_condition;
    }

    public GroupMealBean getGroup_meal() {
        return group_meal;
    }

    public void setGroup_meal(GroupMealBean group_meal) {
        this.group_meal = group_meal;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public MerchantBean getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantBean merchant) {
        this.merchant = merchant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(int ord_id) {
        this.ord_id = ord_id;
    }

    public String getOrd_num() {
        return ord_num;
    }

    public void setOrd_num(String ord_num) {
        this.ord_num = ord_num;
    }

    public int getOrd_status() {
        return ord_status;
    }

    public void setOrd_status(int ord_status) {
        this.ord_status = ord_status;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSeat_cost() {
        return seat_cost;
    }

    public void setSeat_cost(int seat_cost) {
        this.seat_cost = seat_cost;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<SingleMealBean> getSingle_meal() {
        return single_meal;
    }

    public void setSingle_meal(List<SingleMealBean> single_meal) {
        this.single_meal = single_meal;
    }

    public static class GroupMealBean {
        String meal_name;
        String img_path;
        String meal_price;
        String nums;

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

        public String getMeal_price() {
            return meal_price;
        }

        public void setMeal_price(String meal_price) {
            this.meal_price = meal_price;
        }

        public String getNums() {
            return nums;
        }

        public void setNums(String nums) {
            this.nums = nums;
        }
    }

    public static class MerchantBean {
        /**
         * address : 北京市丰台区丰台科技园
         * logo_path : /uploads/20170623/d44f30aec36da5b087a8984e135034a9.jpg
         * mer_name : 哈尔的移动城堡
         * mobile : 12938293812
         */

        private String address;
        private String logo_path;
        private String mer_name;
        private String mobile;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getMer_name() {
            return mer_name;
        }

        public void setMer_name(String mer_name) {
            this.mer_name = mer_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public static class SingleMealBean {
        /**
         * img_path : /uploads/20170421/9c4cd4441a22010eb169479977cd2c39.png
         * meal_id : 2
         * meal_name : 番茄意大利面
         * meal_price : 666
         * meal_weight : 200
         * nums : 1
         */

        private String img_path;
        private int meal_id;
        private String meal_name;
        private int meal_price;
        private int meal_weight;
        private int nums;

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

        public int getMeal_price() {
            return meal_price;
        }

        public void setMeal_price(int meal_price) {
            this.meal_price = meal_price;
        }

        public int getMeal_weight() {
            return meal_weight;
        }

        public void setMeal_weight(int meal_weight) {
            this.meal_weight = meal_weight;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }
    }
}
