package com.example.asus.xyd_order.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.SingleHolder;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;
import com.example.asus.xyd_order.utils.ActivityFactory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Zheng on 2017/2/28.
 */
public class SingleChooseActivity extends BaseActivity {

    private ListView multiple;
    private ArrayList<RestaurantDetailsBean.SingleMealBean> mlist;
    private TextView btn_order,tv_total_price;
    private List<RestaurantDetailsBean.SingleMealBean> tempList=new ArrayList<>();
    private String res_name;
    private String logo;
    private String res_id;
    private TextView tv_empty;
    public static SingleChooseActivity instance;

    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_order:
                tempList.clear();
                for (int i=0;i<mlist.size();i++){
                    if (mlist.get(i).getNums()>0){
                        tempList.add(mlist.get(i));
                    }
                }
                if (tempList.size()>0){
                    ActivityFactory.gotoOrder(context,"0",res_name,"",null,logo,res_id,tempList);
                }else {
                    toastShow("请选择条目");
                }

                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("单点");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_single_choose;
    }

    @Override
    public int getData() throws Exception {
        res_name = getIntent().getStringExtra("res_name");
        logo = getIntent().getStringExtra("logo");
        res_id = getIntent().getStringExtra("res_id");
        mlist = (ArrayList<RestaurantDetailsBean.SingleMealBean>) getIntent().getSerializableExtra("singleList");
        return 0;
    }

    @Override
    public void initView() {
        multiple = (ListView) findViewById(R.id.multiple);
        btn_order = (TextView) findViewById(R.id.btn_order);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        multiple.setEmptyView(tv_empty);
        tv_total_price= (TextView) findViewById(R.id.tv_total_price);
        initAdapter();
    }

    @Override
    public void setEvent() {
        btn_order.setOnClickListener(this);
    }

    private void initAdapter() {
        multiple.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SingleHolder();
            }
        }, mlist));
    }
    public void countPrice(){
        Double totalPrice=0.0;
        for (int i=0;i<mlist.size();i++){
            totalPrice = totalPrice+mlist.get(i).getNums()*mlist.get(i).getMeal_price();
        }
        btn_order.setText("预定"+totalPrice);
    }
}
