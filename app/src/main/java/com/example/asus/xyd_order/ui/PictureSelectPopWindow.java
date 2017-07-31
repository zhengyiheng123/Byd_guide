package com.example.asus.xyd_order.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.Myutils;

/**
 * Created by Zheng on 2017/2/23.
 */
public class PictureSelectPopWindow extends PopupWindow {
    private Window window;

    public PictureSelectPopWindow(Activity activity, View view) {
        this.window=activity.getWindow();
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth((int)(Myutils.getScreenWidth(activity)*0.9));
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0000000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        setWindowAlph(0.7f);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlph(1);
            }
        });
    }

    public void showPop(View view,int xoff,int yoff){
        showAsDropDown(view,xoff,yoff);
    }

    private void setWindowAlph(float alphaNumb) {
        WindowManager.LayoutParams params = this.window.getAttributes();
        params.alpha = alphaNumb;
        this.window.setAttributes(params);
    }
}
