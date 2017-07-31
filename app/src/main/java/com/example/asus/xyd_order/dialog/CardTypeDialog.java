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
import com.example.asus.xyd_order.utils.ActivityFactory;

/**
 * Created by Zheng on 2017/2/27.
 */
public class CardTypeDialog extends Dialog implements View.OnClickListener{
    Context context;
    private View view;
    private Display display;
    private TextView tv_cancel;
    private TextView tv_idcard;
    private TextView tv_huzhao;

    public CardTypeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_cardtype,null);
        tv_idcard = (TextView) view.findViewById(R.id.tv_idcard);
        tv_huzhao = (TextView) view.findViewById(R.id.tv_huzhao);
        tv_huzhao.setOnClickListener(this);
        tv_idcard.setOnClickListener(this);
        setContentView(view,new LinearLayout.LayoutParams((int) (display.getWidth() * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_huzhao:
                break;
            case R.id.tv_idcard:
                break;
        }
    }
}
