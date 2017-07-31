package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.dialog.PayMethodDialog;
import com.example.asus.xyd_order.dialog.SelectCardDialog;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.ActivityFactory;

/**
 * Created by Zheng on 2017/3/3.
 */
public class AttractionsOrderConfirm extends BaseActivity {

    private TextView btn_order_now;
    private PayMethodDialog dialog;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_order_now:
                dialog = new PayMethodDialog(AttractionsOrderConfirm.this, R.style.MyDialog) {

                    @Override
                    protected void gotoSuccessed() {
                        ActivityFactory.gotoSuccessed(context,"0");
                    }

                    @Override
                    public void showCardList() {
                        dialog.dismiss();
                        SelectCardDialog cardDialog = new SelectCardDialog(AttractionsOrderConfirm.this,R.style.MyDialog);
                        cardDialog.show();
                    }
                };
                dialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null)
        {
            dialog=null;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attractions_order_confirm;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        btn_order_now = (TextView) findViewById(R.id.btn_order_now);
    }

    @Override
    public void setEvent() {
        btn_order_now.setOnClickListener(this);
    }
}
