package com.example.asus.xyd_order.eventbus;

import com.example.asus.xyd_order.net.result.PushEntity;

/**
 * Created by Zheng on 2017/10/19.
 */

public class PushData {
    private PushEntity entity;

    public PushEntity getEntity() {
        return entity;
    }

    public PushData(PushEntity entity) {
        this.entity = entity;
    }
}
