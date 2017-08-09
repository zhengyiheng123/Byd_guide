package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/8/9.
 */

public class QueryData {

    /**
     * date : 1502254466
     * group_start : 20
     * route_id : 2
     * tickets : [{"end_station":"Strobl","group_price":65,"normal_price":77,"start_station":"Ried-falkenstein","time_end":"14:00","time_spend":"03:40","time_start":"10:20"},{"end_station":"Strobl","group_price":65,"normal_price":77,"start_station":"Ried-falkenstein","time_end":"15:25","time_spend":"03:40","time_start":"11:45"},{"end_station":"Strobl","group_price":65,"normal_price":77,"start_station":"Ried-falkenstein","time_end":"16:55","time_spend":"03:40","time_start":"13:15"},{"end_station":"Strobl","group_price":65,"normal_price":77,"start_station":"Ried-falkenstein","time_end":"18:25","time_spend":"03:40","time_start":"14:45"},{"end_station":"Strobl","group_price":65,"normal_price":77,"start_station":"Ried-falkenstein","time_end":"19:55","time_spend":"03:40","time_start":"16:15"},{"end_station":"Strobl","group_price":65,"normal_price":77,"start_station":"Ried-falkenstein","time_end":"21:25","time_spend":"03:40","time_start":"17:45"}]
     */

    private String date;
    private int group_start;
    private String route_id;
    private List<TicketsBean> tickets;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGroup_start() {
        return group_start;
    }

    public void setGroup_start(int group_start) {
        this.group_start = group_start;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public List<TicketsBean> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketsBean> tickets) {
        this.tickets = tickets;
    }

    public static class TicketsBean implements Serializable{
        /**
         * end_station : Strobl
         * group_price : 65
         * normal_price : 77
         * start_station : Ried-falkenstein
         * time_end : 14:00
         * time_spend : 03:40
         * time_start : 10:20
         */

        private String end_station;
        private double group_price;
        private double normal_price;
        private String start_station;
        private String time_end;
        private String time_spend;
        private String time_start;

        public String getEnd_station() {
            return end_station;
        }

        public void setEnd_station(String end_station) {
            this.end_station = end_station;
        }

        public double getGroup_price() {
            return group_price;
        }

        public void setGroup_price(double group_price) {
            this.group_price = group_price;
        }

        public double getNormal_price() {
            return normal_price;
        }

        public void setNormal_price(double normal_price) {
            this.normal_price = normal_price;
        }

        public String getStart_station() {
            return start_station;
        }

        public void setStart_station(String start_station) {
            this.start_station = start_station;
        }

        public String getTime_end() {
            return time_end;
        }

        public void setTime_end(String time_end) {
            this.time_end = time_end;
        }

        public String getTime_spend() {
            return time_spend;
        }

        public void setTime_spend(String time_spend) {
            this.time_spend = time_spend;
        }

        public String getTime_start() {
            return time_start;
        }

        public void setTime_start(String time_start) {
            this.time_start = time_start;
        }
    }
}
