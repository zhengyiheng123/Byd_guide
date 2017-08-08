package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/8/7.
 */

public class ShaiXuanBean {
    private String id;
    private String name;

    public ShaiXuanBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
