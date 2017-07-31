package com.example.asus.xyd_order.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.ToastUtils;
import com.example.asus.xyd_order.utils.common.PermissionManager;
import com.example.asus.xyd_order.utils.common.PermissionResult;

/**
 * Created by Zheng on 2017/2/27.
 */
public class ContactDialog extends Dialog implements View.OnClickListener{
    Activity context;
    private View view;
    private Display display;
    private TextView tv_mail,tv_mobile;
    private String mobile;
    private String email;
    private String ord_id;
    public ContactDialog(Activity context, int themeResId, String mobile, String email,String ord_id) {
        super(context, themeResId);
        this.context=context;
        this.mobile=mobile;
        this.email=email;
        this.ord_id=ord_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_contact,null);
        tv_mail = (TextView) view.findViewById(R.id.tv_mail);
        tv_mobile= (TextView) view.findViewById(R.id.tv_mobile);
        tv_mobile.setOnClickListener(this);
        tv_mobile.setText(mobile);
        TextView tv_leave_message= (TextView) view.findViewById(R.id.tv_leave_message);
        tv_leave_message.setOnClickListener(this);
        ClipboardManager clipboardManager=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        tv_mail.setOnLongClickListener(v -> {
            clipboardManager.setText(tv_mail.getText().toString());
            ToastUtils.show(context,"复制成功",0);
            return true;
        });
        setContentView(view,new LinearLayout.LayoutParams((int) (display.getWidth() * 0.8), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_leave_message:
                dismiss();
                ActivityFactory.gotoCatchMessage(context,ord_id);
                break;
            case R.id.tv_mobile:
                PermissionManager.with(context).request(new PermissionManager.Callback() {
                    @Override
                    public void call(PermissionResult result) {
                        if (result.isSuccessful()){
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" +mobile);
                            intent.setData(data);
                            context.startActivity(intent);
                        }else {
                            ToastUtils.show(context,"没有拨打电话权限",0);
                        }

                    }
                }, Manifest.permission.CALL_PHONE);
                break;
        }
    }
}
