package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/17.
 */

public class ActAttractionsBean {

    private List<ShowsBean> shows;

    public List<ShowsBean> getShows() {
        return shows;
    }

    public void setShows(List<ShowsBean> shows) {
        this.shows = shows;
    }

    public static class ShowsBean {
        /**
         * date : 2017.09.10
         * route_id : 6
         * route_name : 歌剧魅影
         * time : 18:00-20:00
         */

        private String date;
        private int route_id;
        private String route_name;
        private String time;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
