package com.example.asus.xyd_order.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.utils.ActivityFactory;

/**
 * Created by Zheng on 2017/3/3.
 */
public class AddCardActivity extends BaseActivity {

    private EditText et_cardnum;
    private TextView tv_next;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_next:
                ActivityFactory.gotoCardDetails(context);
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
        return R.layout.activity_addcard;
    }


    @Override
    public int getData() throws Exception {
        return 0;
    }

    @Override
    public void initView() {
        et_cardnum = (EditText) findViewById(R.id.et_cardnum);
        tv_next = (TextView) findViewById(R.id.tv_next);
    }

    @Override
    public void setEvent() {
        tv_next.setOnClickListener(this);
        et_cardnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence.toString())){
                    tv_next.setEnabled(false);
                }else {
                    tv_next.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
