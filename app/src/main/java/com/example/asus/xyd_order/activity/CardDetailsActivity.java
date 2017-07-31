package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.dialog.CardTypeDialog;
import com.example.asus.xyd_order.utils.ActivityFactory;

/**
 * Created by Zheng on 2017/3/3.
 */
public class CardDetailsActivity extends BaseActivity {

    private TextView tv_card_type,tv_next;
    private CardTypeDialog cardTypeDialog;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_card_type:
                cardTypeDialog = new CardTypeDialog(CardDetailsActivity.this, R.style.MyDialog);
                cardTypeDialog.show();
                break;
            case R.id.tv_next:
                ActivityFactory.gotoCheckCard(context);
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_card_details;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        tv_card_type = (TextView) findViewById(R.id.tv_card_type);
        tv_next= (TextView) findViewById(R.id.tv_next);
    }

    @Override
    public void setEvent() {
        tv_card_type.setOnClickListener(this);
        tv_next.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cardTypeDialog!=null){
            cardTypeDialog=null;
        }
    }
}
