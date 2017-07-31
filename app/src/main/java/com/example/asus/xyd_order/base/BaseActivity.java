package com.example.asus.xyd_order.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.app.ExitApplication;
import com.example.asus.xyd_order.utils.NetworkState;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by ASUS on 2017/2/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int STATE_NO_NETWORK=0;
    public static final int STATE_LOAD_ERROR=2;
    public static final int STATE_CONTENT=1;

    private int resultCode;
    public Context context;
    public LayoutInflater inflater;
    private PromptDialog dialog;
    public int user_id;
    public String apitoken;

    @Override
    public void onClick(View v) {
        if(NetworkState.isNetworkAvailable(getApplicationContext())){
            myOnclick(v);
        }else {
            ToastUtils.show(getApplicationContext(),"请检查网络连接",0);
        }

    }
    public void showDialog(){
        dialog.showLoading("请稍后",false);
    }
    public void dismissDialog(){
        dialog.dismissImmediately();
    }
    public abstract void myOnclick(View view);
    public abstract void setToolbar();
    public void toastShow(String text){
        ToastUtils.show(context,text,0);
    }
    // --------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        ButterKnife.bind(this);
        context=getApplicationContext();
        user_id = (int) SharedPreferenceUtils.getParam(context,"user_id",0);
        apitoken = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
        dialog = new PromptDialog(BaseActivity.this);
        inflater = LayoutInflater.from(context);
        ExitApplication.getInstance().addActivity(this);
        try {
            resultCode = getData();
        }catch (Exception e){
            ToastUtils.show(context,e.getMessage(),0);
        }
            initView();
            setEvent();
            setToolbar();
    }

    /**
     * 获取布局ID
     * @return
     */
    protected abstract int getResourceId();


    public abstract int getData() throws Exception;
    public abstract void initView();

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract void setEvent();

    /**
     * 获取当前时间
     * @return
     */
    public String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    /**
     * 解决listview和scrollview的bug
     * @return
     */
    public int measureHeight(ListView lv_catch) {
        // get ListView adapter
        ListAdapter adapter = lv_catch.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, lv_catch);
            if (null == item) continue;
            // measure each item width and height
            item.measure(0, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = lv_catch.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // calculate ListView height
        params.height = totalHeight + (lv_catch.getDividerHeight() * (adapter.getCount() - 1));

        lv_catch.setLayoutParams(params);

        return params.height;
    }

}
