package com.example.asus.xyd_order.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Zheng on 2017/8/7.
 */

public class StateButton extends android.support.v7.widget.AppCompatButton {
    private int state = 0;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public StateButton(Context context) {
        super(context);
    }

    public StateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
