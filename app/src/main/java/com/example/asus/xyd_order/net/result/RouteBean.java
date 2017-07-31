package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/13.
 */

public class RouteBean {

    private List<RoutesBean> routes;

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public static class RoutesBean {
        /**
         * price : 100.00
         * route_id : 306
         * route_name : 线路一
         */

        private String price;
        private int route_id;
        private String route_name;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getRoute_id() {
            return route_id;
        }

        public void setRoute_id(int route_id) {
            this.route_id = route_id;
        }

        public String getRoute_name() {
            return route_name;
        }

        public void setRoute_name(String route_name) {
            this.route_name = route_name;
        }
    }
}
