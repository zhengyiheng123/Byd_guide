package com.example.asus.xyd_order.utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2016/11/29.
 */
public class Myutils {
    private static int statusBarHeight;
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight != 0)
            return statusBarHeight;

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    public static int getScreenWidth(Activity context){
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }
    public static int getScreenHeight(Activity activity){
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }
}
