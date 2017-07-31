package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/27.
 */

public class NoticeBean {

    private List<NoticesBean> notices;

    public List<NoticesBean> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticesBean> notices) {
        this.notices = notices;
    }

    public static class NoticesBean {
        /**
         * add_time : 1500966495
         * msg : 您团号为 1492849283 的需求订单已被 郑郑一横 接单，请及时查看
         * opm_id : 78
         * ord_id : 152
         * ord_type : -1
         * title :
         */

        private int add_time;
        private String msg;
        private int opm_id;
        private int ord_id;
        private int ord_type;
        private String title;

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getOpm_id() {
            return opm_id;
        }

        public void setOpm_id(int opm_id) {
            this.opm_id = opm_id;
        }

        public int getOrd_id() {
            return ord_id;
        }

        public void setOrd_id(int ord_id) {
            this.ord_id = ord_id;
        }

        public int getOrd_type() {
            return ord_type;
        }

        public void setOrd_type(int ord_type) {
            this.ord_type = ord_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
