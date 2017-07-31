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
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.utils.ActivityFactory;

/**
 * Created by Zheng on 2017/2/27.
 */
public class CatchSeccessDialog extends Dialog {
    Context context;
    private View view;
    private Display display;
    private CircleImageView iv_head;
    private int resourceId;

    public CatchSeccessDialog(Context context, int themeResId,int resourceId) {
        super(context, themeResId);
        this.context=context;
        this.resourceId=resourceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_catch_result,null);
        iv_head = (CircleImageView) view.findViewById(R.id.iv_head);
        iv_head.setImageResource(resourceId);
        setContentView(view,new LinearLayout.LayoutParams((int) (display.getWidth() * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
