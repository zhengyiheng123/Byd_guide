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
public class SelectCardDialog extends Dialog {
    Context context;
    private View view;
    private Display display;
    private TextView tv_cancel;
    private TextView tv_add_card;

    public SelectCardDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_card_list,null);
        tv_add_card = (TextView) view.findViewById(R.id.tv_add_card);
        tv_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityFactory.gotoAddCard(context);
            }
        });
        setContentView(view,new LinearLayout.LayoutParams((int) (display.getWidth() * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
