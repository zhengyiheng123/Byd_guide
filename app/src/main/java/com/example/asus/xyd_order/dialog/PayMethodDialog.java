package com.example.asus.xyd_order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;

/**
 * Created by Zheng on 2017/2/27.
 */
public abstract class PayMethodDialog extends Dialog implements View.OnClickListener{
    Context context;
    private View view;
    private Display display;
    private TextView tv_cancel;
    private TextView tv_yinlian,tv_webchat;

    public PayMethodDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_pay_method,null);
        tv_yinlian = (TextView) view.findViewById(R.id.tv_yinlian);
        tv_webchat= (TextView) view.findViewById(R.id.tv_webchat);
        tv_yinlian.setOnClickListener(this);
        tv_webchat.setOnClickListener(this);
        setContentView(view,new LinearLayout.LayoutParams((int) (display.getWidth() * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_yinlian:
                showCardList();
                break;
            case R.id.tv_webchat:
                gotoSuccessed();
                break;
        }
    }

    protected abstract void gotoSuccessed();

    public abstract void showCardList();
}
