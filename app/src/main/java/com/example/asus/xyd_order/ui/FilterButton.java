package com.example.asus.xyd_order.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.xyd_order.utils.ToastUtils;

/**
 * Created by Zheng on 2017/7/10.
 */

@SuppressLint("AppCompatCustomView")
public class FilterButton extends TextView{
    private String state="";
    private Context context;
    public FilterButton(Context context) {
        super(context);
        this.context=context;
    }

    public FilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public FilterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state=state;
    }
}
