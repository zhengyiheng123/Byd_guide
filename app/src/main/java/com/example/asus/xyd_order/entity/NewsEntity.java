package com.example.asus.xyd_order.entity;

/**
 * Created by Zheng on 2017/2/22.
 */
public class NewsEntity {
    public String headUrl;
    public String name;
    public String time;
    //备选字段
    public String extra;
    public String content;

    public NewsEntity(String headUrl, String name, String time, String extra, String content) {
        this.headUrl = headUrl;
        this.name = name;
        this.time = time;
        this.extra = extra;
        this.content = content;
    }
}
