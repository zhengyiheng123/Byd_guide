package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.SiteViewpagerAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.base.BaseFragment;
import com.example.asus.xyd_order.fragment.FragmentSiteFragment1;
import com.example.asus.xyd_order.holder.AttractionDetailsHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AddressBean;
import com.example.asus.xyd_order.net.result.BaseTicketBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RoutDetailsBean;
import com.example.asus.xyd_order.net.result.ZhongcanOrderSuccessBean;
import com.example.asus.xyd_order.ui.ChildViewPager;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/3/20.
 */
public class Activity_Order_Nomal extends BaseActivity {

    public static Activity_Order_Nomal instance;

    private ChildViewPager child_viewpager;
    private View v_nine;
    private View v_ten;
    private RelativeLayout rl_nine;
    private RelativeLayout rl_ten;
    private LinearLayout ll_address;
    private TextView btn_order_now;
    private String route_id;
    private TextView tv_driver_free;
    private TextView tv_guide_free,tv_group,tv_nomal,tv_paytype
            ,et_group_num,tv_total_price,tv_name,tv_phoneNum,tv_address,tv_all_money;
    private MyListView lv_ticket;

    private List<RoutDetailsBean.NormalTicketBean> nomalList=new ArrayList<>();
    private List<RoutDetailsBean.GroupTicketBean> groupList=new ArrayList<>();

    private List<BaseTicketBean> mList=new ArrayList<>();
    private BaseArrayAdapter adapter;
    private OptionPicker picker;

    //支付方式
    private String pay_type="";

    //团体票Json数据
    private String groupString="";

    //单人票票Json数据
    private String nomalString="";
    //取票方式
    private String post_type="";
    private RadioGroup rg_get_ways;

    //收货地址id
    private String ua_id="";
    private double totalPrice=0.0f;
    private RoutDetailsBean routeBean;
    private EditText et_user_phone;
    private EditText et_user_name;
    private LinearLayout ll_user_info;
    private AddressBean.AddressesBean addressesBean;
    private int groupNum;
    private int nomalNum;
    private ImageView iv_driver_free;
    private ImageView iv_guide_free;


    @Override
    protected void onResume() {
        super.onResume();
        instance=this;
    }

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.rl_ten:
                v_ten.setVisibility(View.VISIBLE);
                v_nine.setVisibility(View.GONE);
                mList.clear();
                mList.addAll(groupList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rl_nine:
                v_ten.setVisibility(View.GONE);
                v_nine.setVisibility(View.VISIBLE);
                mList.clear();
                mList.addAll(nomalList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.ll_address:
                Intent intent=new Intent(context,Activity_address_list.class);
                intent.putExtra("choose","1");
                startActivityForResult(intent,0);
                break;
            case R.id.btn_order_now:
                try {
                    allRight();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_paytype:
                picker = new OptionPicker(Activity_Order_Nomal.this,new String[]{"到店支付","支付宝","微信","银联"});
                picker.setCycleDisable(true);
                picker.setLineVisible(true);
                picker.setShadowVisible(false);
                picker.setTextSize(14);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        switch (index){
                            case 0:
                                pay_type="1";
                                break;
                            case 1:
                                pay_type="2";
                                break;
                            case 2:
                                pay_type="3";
                                break;
                            case 3:
                                pay_type="4";
                                break;
                        }
                        tv_paytype.setText(item);
                    }
                });
                picker.show();
                break;
        }
    }

    private void allRight() throws JSONException {
        if (TextUtils.isEmpty(et_group_num.getText().toString())){
            toastShow("请填写团号");
            return;
        }
        if (TextUtils.isEmpty(pay_type)){
            toastShow("请选择支付方式");
            return;
        }
        if (TextUtils.isEmpty(post_type)){
            toastShow("请选择取票方式");
            return;
        }
        if (post_type.equals("1")){
            if (addressesBean==null){
                toastShow("请选择收货地址");
                return;
            }
        }
        if (post_type .equals("2")){
            if (TextUtils.isEmpty(et_user_name.getText().toString())){
                toastShow("请填写收货人姓名");
                return;
            }
            if (TextUtils.isEmpty(et_user_phone.getText().toString())){
                toastShow("请填写收货人联系电话");
                return;
            }
        }
        //拼装单人票json
        JSONArray jsonArrayNomal=new JSONArray();
        JSONObject tmpObj=null;
        for (int i=0;i<nomalList.size();i++){
            if (nomalList.get(i).getNums()>0){
                tmpObj=new JSONObject();
                tmpObj.put("nums",nomalList.get(i).getNums());
                tmpObj.put("price",nomalList.get(i).getPrice());
                tmpObj.put("ticket_type",nomalList.get(i).getTicket_type());
                tmpObj.put("tp_id",nomalList.get(i).getTp_id());
                jsonArrayNomal.put(tmpObj);
            }
        }
        //拼装团票json
        JSONArray jsonArrayGroup=new JSONArray();
        for (int i=0;i<groupList.size();i++){
            if (groupList.get(i).getNums()>0){
                tmpObj=new JSONObject();
                tmpObj.put("nums",groupList.get(i).getNums());
                tmpObj.put("price",groupList.get(i).getPrice());
                tmpObj.put("ticket_type",groupList.get(i).getTicket_type());
                tmpObj.put("tp_id",groupList.get(i).getTp_id());
                jsonArrayGroup.put(tmpObj);
            }
        }
       if (groupNum==0){
           groupString=jsonArrayGroup.toString();
       }else if (groupNum>0 && groupNum<10){
           toastShow("团体票最低购买数量为10张");
           return;
       }else {
           groupString=jsonArrayGroup.toString();
       }
       if (nomalNum == 0){
           nomalString=jsonArrayNomal.toString();
       }else {
           nomalString=jsonArrayNomal.toString();
       }
       showDialog();
        order(et_group_num.getText().toString(),pay_type,post_type,routeBean.getMer_id()+"",route_id,totalPrice+"",et_user_name.getText().toString(),et_user_phone.getText().toString(),groupString,nomalString);
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("预定门票");
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_order_nomal;
    }


    @Override
    public int getData() throws Exception {
        route_id = getIntent().getStringExtra("route_id");
        getNetData();
        return 0;
    }

    @Override
    public void initView() {
        lv_ticket = (MyListView) findViewById(R.id.lv_ticket);
        adapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new AttractionDetailsHolder();
            }
        },mList);
        lv_ticket.setAdapter(adapter);
        v_nine = findViewById(R.id.v_nine);
        v_ten =findViewById(R.id.v_ten);
        rl_ten = (RelativeLayout) findViewById(R.id.rl_ten);
        rl_nine = (RelativeLayout) findViewById(R.id.rl_nine);
        rg_get_ways = (RadioGroup) findViewById(R.id.rg_get_ways);

        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        btn_order_now = (TextView) findViewById(R.id.btn_order_now);
        tv_driver_free= (TextView) findViewById(R.id.tv_driver_free);
        tv_guide_free= (TextView) findViewById(R.id.tv_guide_free);
        tv_nomal= (TextView) findViewById(R.id.tv_nomal);
        tv_group= (TextView) findViewById(R.id.tv_group);
        tv_paytype= (TextView) findViewById(R.id.tv_paytype);
        et_group_num= (TextView) findViewById(R.id.et_group_num);
        tv_total_price= (TextView) findViewById(R.id.tv_total_price);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_phoneNum= (TextView) findViewById(R.id.tv_phoneNum);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_all_money= (TextView) findViewById(R.id.tv_all_money);

        et_user_phone = (EditText) findViewById(R.id.et_user_phone);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        ll_user_info = (LinearLayout) findViewById(R.id.ll_user_info);

        iv_driver_free = (ImageView) findViewById(R.id.iv_driver_free);
        iv_guide_free = (ImageView) findViewById(R.id.iv_guide_free);

    }

    @Override
    public void setEvent() {
        rl_ten.setOnClickListener(this);
        rl_nine.setOnClickListener(this);

        ll_address.setOnClickListener(this);
        btn_order_now.setOnClickListener(this);
        tv_paytype.setOnClickListener(this);

        rg_get_ways.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_sent:
                        Intent intent=new Intent(context,Activity_address_list.class);
                        intent.putExtra("choose","1");
                        startActivityForResult(intent,0);
                        break;
                    case R.id.rb_getbyself:
                        post_type="2";
                        ll_user_info.setVisibility(View.VISIBLE);
                        ll_address.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }
    /**
     * 获取网络数据
     */
    public void upDataCounts(){
        groupNum = 0;
        double groupPrice=0.0f;
        for (int i=0;i<groupList.size();i++){
            groupNum = groupNum +groupList.get(i).getNums();
            if (groupList.get(i).getTotalPrice()!=null){
                groupPrice=groupPrice+groupList.get(i).getTotalPrice();
            }
        }
        tv_group.setText("10人以上"+"("+ groupNum +"张)");
        nomalNum = 0;
        double nomalPrice=0.0f;
        for (int i=0;i<nomalList.size();i++){
            nomalNum = nomalNum +nomalList.get(i).getNums();
            if (nomalList.get(i).getTotalPrice()!=null){
                nomalPrice=nomalPrice+nomalList.get(i).getTotalPrice();
            }
        }
        totalPrice=nomalPrice+groupPrice;
        tv_total_price.setText("总价："+totalPrice);
        tv_all_money.setText("总价："+totalPrice);
        tv_nomal.setText("0-9人"+"("+ nomalNum +"张)");
    }

    /**
     * 请求网络数据
     */
    private void getNetData(){
        Observable<HttpResult<RoutDetailsBean>> result= ServiceApi.getInstance().getServiceContract().routeDeTails(route_id,apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RoutDetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RoutDetailsBean routDetailsBean) {
                        routeBean = routDetailsBean;
                        if (routDetailsBean.getDriver_free() == 1){
                            tv_driver_free.setText("司机可免");
                            iv_driver_free.setImageDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }else {
                            tv_driver_free.setText("司机可免");
                            iv_driver_free.setImageDrawable(getResources().getDrawable(R.drawable.ic_false));
                        }
                        if (routDetailsBean.getGuide_free()== 1){
                            tv_guide_free.setText("导游可免");
                            iv_guide_free.setImageDrawable(getResources().getDrawable(R.drawable.ic_right));
                        }else {
                            tv_guide_free.setText("导游可免");
                            iv_guide_free.setImageDrawable(getResources().getDrawable(R.drawable.ic_false));
                        }
                        nomalList.clear();
                        groupList.clear();
                        nomalList.addAll(routDetailsBean.getNormal_ticket());
                        groupList.addAll(routDetailsBean.getGroup_ticket());
                        if (mList.size()>0){
                            mList.clear();
                        }
                        mList.addAll(groupList);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 预定
     */
    private void order(String group_num,String pay_type,String post_type,String mer_id,String route_id,String totalPrice
    ,String username,String mobile,String group_ticket,String normal_ticket
    ){
        Observable<HttpResult<ZhongcanOrderSuccessBean>> result=ServiceApi.getInstance().getServiceContract().saveOrderNomal(apitoken,group_num,pay_type,post_type,mer_id,route_id,
        totalPrice,ua_id,username,mobile,group_ticket,normal_ticket
                );
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhongcanOrderSuccessBean>() {
                    @Override
                    public void onCompleted() {
                        dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhongcanOrderSuccessBean zhongcanOrderSuccessBean) {
//                        Log.e("zyh","预定处成功,订单号："+zhongcanOrderSuccessBean.getOrd_id());
                        ActivityFactory.gotoSuccessed(context,zhongcanOrderSuccessBean.getOrd_id());
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (resultCode == 1){
                    addressesBean = (AddressBean.AddressesBean) data.getSerializableExtra("address");
                    ll_address.setVisibility(View.VISIBLE);
                    ll_user_info.setVisibility(View.GONE);
                    tv_name.setText(addressesBean.getUser_name());
                    tv_phoneNum.setText(addressesBean.getMobile());
                    tv_address.setText(addressesBean.getAddress());
                    ua_id= addressesBean.getUa_id()+"";
                    post_type="1";
                    rg_get_ways.check(R.id.rb_sent);
                }else {
                    rg_get_ways.check(R.id.rb_getbyself);
                }
                break;
        }
    }


}
