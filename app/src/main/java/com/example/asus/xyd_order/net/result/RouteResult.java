package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/8/9.
 */

public class RouteResult {

    private List<RoutesBean> routes;
    private List<StationsBean> stations;

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public List<StationsBean> getStations() {
        return stations;
    }

    public void setStations(List<StationsBean> stations) {
        this.stations = stations;
    }

    public static class RoutesBean {
        /**
         * route_id : 2
         * route_name : 沃尔夫冈湖区游船
         */

        private int route_id;
        private String route_name;

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

    public static class StationsBean {
        /**
         * rs_id : 12
         * station_name : st.gilgen
         */

        private int rs_id;
        private String station_name;

        public int getRs_id() {
            return rs_id;
        }

        public void setRs_id(int rs_id) {
            this.rs_id = rs_id;
        }

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }
    }
}
