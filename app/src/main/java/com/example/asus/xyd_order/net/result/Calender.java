package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/21.
 */

public class Calender {

    private List<CalendarBean> calendar;

    public List<CalendarBean> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<CalendarBean> calendar) {
        this.calendar = calendar;
    }

    public static class CalendarBean {
        /**
         * month : 1970-01
         * nodes : [{"date":"01","stamp":-28800,"count":43}]
         */

        private String month;
        private List<NodesBean> nodes;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public List<NodesBean> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodesBean> nodes) {
            this.nodes = nodes;
        }

        public static class NodesBean {
            /**
             * date : 01
             * stamp : -28800
             * count : 43
             */

            private String date;
            private int stamp;
            private int count;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getStamp() {
                return stamp;
            }

            public void setStamp(int stamp) {
                this.stamp = stamp;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
