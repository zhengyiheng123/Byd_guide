package com.example.asus.xyd_order.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

import com.example.asus.xyd_order.activity.Activity_Register_confirm;

/**
 * Created by Zheng on 2017/10/11.
 */

public class ConfirmDialog extends AlertDialog.Builder {

    public ConfirmDialog(@NonNull Activity context) {
        super(context);
        initDialog(context);
    }

    private void initDialog(Activity context) {
        setMessage("请填写认证信息");
        setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(context, Activity_Register_confirm.class) );

            }
        });
        create().show();
    }
}
