package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/12.
 */

public class TuishuiBean {

    /**
     * drawback_desc : 1.退税说明;2.退税说明;3.退税说明.4.test
     * drawback_name : 慕尼黑机场退税说明
     * drawback_point : [{"dp_id":1,"dp_point":"T3航站楼"},{"dp_id":2,"dp_point":"T2航站楼"}]
     */

    private String drawback_desc;
    private String drawback_name;
    private List<DrawbackPointBean> drawback_point;

    public String getDrawback_desc() {
        return drawback_desc;
    }

    public void setDrawback_desc(String drawback_desc) {
        this.drawback_desc = drawback_desc;
    }

    public String getDrawback_name() {
        return drawback_name;
    }

    public void setDrawback_name(String drawback_name) {
        this.drawback_name = drawback_name;
    }

    public List<DrawbackPointBean> getDrawback_point() {
        return drawback_point;
    }

    public void setDrawback_point(List<DrawbackPointBean> drawback_point) {
        this.drawback_point = drawback_point;
    }

    public static class DrawbackPointBean {
        /**
         * dp_id : 1
         * dp_point : T3航站楼
         */

        private int dp_id;
        private String dp_point;

        public int getDp_id() {
            return dp_id;
        }

        public void setDp_id(int dp_id) {
            this.dp_id = dp_id;
        }

        public String getDp_point() {
            return dp_point;
        }

        public void setDp_point(String dp_point) {
            this.dp_point = dp_point;
        }
    }
}
