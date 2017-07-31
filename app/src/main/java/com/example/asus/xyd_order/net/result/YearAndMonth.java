package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/7/17.
 */

public class YearAndMonth {
    private String year;
    private String month;

    public YearAndMonth(String year, String month) {
        this.year = year;
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
