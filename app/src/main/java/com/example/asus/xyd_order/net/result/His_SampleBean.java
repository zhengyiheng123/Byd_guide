package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/5.
 */

public class His_SampleBean {

    /**
     * company :
     * countries : ["法国","英国","德国","俄罗斯"]
     * dmd_area : 3
     * dmd_desc :
     * ds_id : 16
     * ds_name : 33333333
     * end_time : 1481126399
     * group_num : 1492849283
     * group_type : 3
     * level_req : 3
     * mobile : 18212392010
     * pay_type : 1
     * price : 2000.00
     * route_desc :
     * route_img_path :
     * service_type : 2
     * start_time : 1481111111
     * user_name : Bloodmage
     */

    private String company;
    private int dmd_area;
    private String dmd_desc;
    private int ds_id;
    private String ds_name;
    private int end_time;
    private String group_num;
    private int group_type;
    private int level_req;
    private String mobile;
    private int pay_type;
    private String price;
    private String route_desc;
    private String route_img_path;
    private int service_type;
    private int start_time;
    private String user_name;
    private List<String> countries;
    private List<CountryListBean> country_list;

    public List<CountryListBean> getCountry_list() {
        return country_list;
    }

    public void setCountry_list(List<CountryListBean> country_list) {
        this.country_list = country_list;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getDmd_area() {
        return dmd_area;
    }

    public void setDmd_area(int dmd_area) {
        this.dmd_area = dmd_area;
    }

    public String getDmd_desc() {
        return dmd_desc;
    }

    public void setDmd_desc(String dmd_desc) {
        this.dmd_desc = dmd_desc;
    }

    public int getDs_id() {
        return ds_id;
    }

    public void setDs_id(int ds_id) {
        this.ds_id = ds_id;
    }

    public String getDs_name() {
        return ds_name;
    }

    public void setDs_name(String ds_name) {
        this.ds_name = ds_name;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public int getGroup_type() {
        return group_type;
    }

    public void setGroup_type(int group_type) {
        this.group_type = group_type;
    }

    public int getLevel_req() {
        return level_req;
    }

    public void setLevel_req(int level_req) {
        this.level_req = level_req;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getRoute_desc() {
        return route_desc;
    }

    public void setRoute_desc(String route_desc) {
        this.route_desc = route_desc;
    }

    public String getRoute_img_path() {
        return route_img_path;
    }

    public void setRoute_img_path(String route_img_path) {
        this.route_img_path = route_img_path;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
    public static class CountryListBean {
        /**
         * region_id : 1
         * region_name : 德国
         */

        private int region_id;
        private String region_name;

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }
    }
}
