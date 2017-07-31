package com.example.asus.xyd_order.entity;

/**
 * Created by Zheng on 2017/2/23.
 */
public class ZhongcanItemEntity  {
    public String name;
    public String stars;
    public String range;
    public String perMoney;
    public String img;
    public String type;
    public String miles;

    public ZhongcanItemEntity(String name, String stars, String range, String perMoney, String img, String type, String miles) {
        this.name = name;
        this.stars = stars;
        this.range = range;
        this.perMoney = perMoney;
        this.img = img;
        this.type = type;
        this.miles = miles;
    }
}
