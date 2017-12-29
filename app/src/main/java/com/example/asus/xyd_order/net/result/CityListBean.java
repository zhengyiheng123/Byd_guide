package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/12.
 */

public class CityListBean implements Serializable{

    private List<RegionsBean> regions;

    public List<RegionsBean> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionsBean> regions) {
        this.regions = regions;
    }

}
