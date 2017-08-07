package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/6/27.
 */

public class CountryBean implements Serializable{

    private List<CountriesBean> countries;

    public List<CountriesBean> getCountries() {
        return countries;
    }

    public void setCountries(List<CountriesBean> countries) {
        this.countries = countries;
    }

    public static class CountriesBean  implements Serializable{
        /**
         * region_id : 2
         * region_name : 法国
         */
        private int state;
        private int region_id;
        private String region_name;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

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
