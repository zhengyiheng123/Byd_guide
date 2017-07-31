package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/7/12.
 */

public class TuiShuiDetails {

    /**
     * dp_address : 慕尼黑机场
     * dp_point : T3航站楼
     * dp_desc : 还退不退了
     * stamp_img : /uploads/20170519/ce42cd7bde9c40c2bc555543eca56f20.jpg
     * drawback_img : /uploads/20170519/dd03f0df95ff4981476a165e1190fb18.png
     */

    private String dp_address;
    private String dp_point;
    private String dp_desc;
    private String stamp_img;
    private String drawback_img;

    public String getDp_address() {
        return dp_address;
    }

    public void setDp_address(String dp_address) {
        this.dp_address = dp_address;
    }

    public String getDp_point() {
        return dp_point;
    }

    public void setDp_point(String dp_point) {
        this.dp_point = dp_point;
    }

    public String getDp_desc() {
        return dp_desc;
    }

    public void setDp_desc(String dp_desc) {
        this.dp_desc = dp_desc;
    }

    public String getStamp_img() {
        return stamp_img;
    }

    public void setStamp_img(String stamp_img) {
        this.stamp_img = stamp_img;
    }

    public String getDrawback_img() {
        return drawback_img;
    }

    public void setDrawback_img(String drawback_img) {
        this.drawback_img = drawback_img;
    }
}
