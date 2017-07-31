package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.ExpandableAttractListviewAdapter;
import com.example.asus.xyd_order.adapter.ExpandableListviewAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.dialog.NoticeDialogDialog;
import com.example.asus.xyd_order.holder.SiteDisplayHolder;
import com.example.asus.xyd_order.holder.SiteDisplayHolder2;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.AddressBean;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RouteDetails;
import com.example.asus.xyd_order.net.result.ZhongcanOrderSuccessBean;
import com.example.asus.xyd_order.ui.MyExpandListView;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.ToastUtils;

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
 * Created by Zheng on 2017/3/3.
 */
public class AttractionsOrderActivity extends BaseActivity {

    List<BaseTicketRouteBean> groupList=new ArrayList<>();
    List<BaseTicketRouteBean> nomalList=new ArrayList<>();
    private TextView btn_order_now,
    tv_name,tv_phoneNum,tv_address,tv_paytype
            ,tv_all_money,tv_nomal_count,tv_group_count;
    private RadioGroup rg_get_ways;
    private RadioButton rb_sent,rb_getbyself;
    private LinearLayout ll_address,ll_user_info;
    private MyListView myly_group,myly_nomal;
    private BaseArrayAdapter groupAdapter,nomalAdapter;
    private List<BaseTicketRouteBean.TicketsBean> tempGroupList;
    private List<BaseTicketRouteBean.TicketsBean> tempNomalList;
    private String totalPrice;
    private int groupCount;
    private int nomalCount;
    private EditText et_user_name,et_user_phone,et_group_num;

    //取票方式
    private String post_type="";
    private AddressBean.AddressesBean addressesBean;
    //地址id
    private String ua_id;
    private OptionPicker picker;
    //支付方式
    private String pay_type;
    private String mer_id;
    private String route_id;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_order_now:
                //弹出提示框
//                NoticeDialogDialog dialogDialog=new NoticeDialogDialog(AttractionsOrderActivity.this,R.style.MyDialog);
//                dialogDialog.show();
                allright();
//                ActivityFactory.gotoAttractionsConfirm(context);
                break;
            case R.id.ll_address:
                Intent intent=new Intent(context,Activity_address_list.class);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_paytype:
                picker = new OptionPicker(AttractionsOrderActivity.this,new String[]{"到店支付","支付宝","微信","银联"});
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
    //提交参数
    private void allright() {
        if (TextUtils.isEmpty(et_group_num.getText().toString())){
            toastShow("请填写团号");
            return;
        }
        if (TextUtils.isEmpty(pay_type)){
            toastShow("请选择付款方式");
            return;
        }
        if (TextUtils.isEmpty(post_type)){
            toastShow("请选择取票方式");
            return;
        }
        if (post_type.equals("2")){
            if (TextUtils.isEmpty(et_user_name.getText().toString())){
                toastShow("请填写收货人姓名");
                return;
            }
            if (TextUtils.isEmpty(et_user_phone.getText().toString())){
                toastShow("请填写收货人联系方式");
                return;
            }
        }
        if (groupCount>0 && groupCount<10){
            toastShow("团体票最低购买10张");
            return;
        }
        String groupJson=pojoToJson(groupList).toString();
        String nomalJson=pojoToJson(nomalList).toString();
        order(et_group_num.getText().toString(),pay_type,post_type,mer_id,route_id,totalPrice+"",ua_id,et_user_name.getText().toString(),et_user_phone.getText().toString(),groupJson,nomalJson);

    }
    //类转成json
    private JSONArray pojoToJson(List<BaseTicketRouteBean> mlist) {
        JSONArray jsonArrayGroup=new JSONArray();
        try {
            JSONObject tempJson=null;
            for (int i=0;i<mlist.size();i++){
                tempJson=new JSONObject();
                tempJson.put("ticket_id",mlist.get(i).getTicket_id()+"");
                tempJson.put("ticket_name",mlist.get(i).getTicket_name());
                JSONArray ticketsArray=new JSONArray();
                for (int y=0;y<mlist.get(i).getTickets().size();y++){
                    JSONObject tem=new JSONObject();
                    tem.put("nums",mlist.get(i).getTickets().get(y).getNums());
                    tem.put("price",mlist.get(i).getTickets().get(y).getPrice());
                    tem.put("ticket_type",mlist.get(i).getTickets().get(y).getTicket_nums());
                    tem.put("tp_id",mlist.get(i).getTickets().get(y).getTp_id());
                    ticketsArray.put(tem);
                }
                tempJson.put("tickets",ticketsArray);
                jsonArrayGroup.put(tempJson);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return jsonArrayGroup;
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("预定");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_attract_order;
    }


    @Override
    public int getData() throws Exception {
        totalPrice = getIntent().getStringExtra("totalPrice");
        mer_id = getIntent().getStringExtra("mer_id");
        route_id = getIntent().getStringExtra("route_id");
        groupList = (List<BaseTicketRouteBean>) getIntent().getSerializableExtra("groupList");
        nomalList= (List<BaseTicketRouteBean>) getIntent().getSerializableExtra("nomalList");
        //初始化团体票数据
        for (int i=0;i<groupList.size();i++){
            List<BaseTicketRouteBean.TicketsBean> ticketsList=groupList.get(i).getTickets();
            tempGroupList = new ArrayList<>();
            for (int y=0;y<ticketsList.size();y++){
                if (ticketsList.get(y).getNums()>0){
                    tempGroupList.add(ticketsList.get(y));
                }
            }
            groupList.get(i).setTickets(tempGroupList);
        }
        //初始化单人票数据
        for (int i=0;i<nomalList.size();i++){
            List<BaseTicketRouteBean.TicketsBean> ticketsList=nomalList.get(i).getTickets();
            tempNomalList = new ArrayList<>();
            for (int y=0;y<ticketsList.size();y++){
                if (ticketsList.get(y).getNums()>0){
                    tempNomalList.add(ticketsList.get(y));
                }
            }
            nomalList.get(i).setTickets(tempNomalList);
        }
        groupCount = 0;
        //团体票总数
        for (int i=0;i<groupList.size();i++){
            groupCount = groupCount +groupList.get(i).getAllCount();
        }
        //单人票总数
        nomalCount = 0;
        for (int i=0;i<nomalList.size();i++){
            nomalCount = nomalCount +nomalList.get(i).getAllCount();
        }
        return 0;
    }

    @Override
    public void initView() {
        inalizeView();
        initExpandListView();
    }

    /**
     * 初始化所有空间
     */
    private void inalizeView() {
        btn_order_now = (TextView) findViewById(R.id.btn_order_now);
        tv_group_count= (TextView) findViewById(R.id.tv_group_count);
        tv_nomal_count= (TextView) findViewById(R.id.tv_nomal_count);
        tv_group_count.setText(groupCount+"张");
        tv_nomal_count.setText(nomalCount+"张");
        rb_sent = (RadioButton) findViewById(R.id.rb_sent);
        rb_getbyself= (RadioButton) findViewById(R.id.rb_getbyself);
        tv_all_money= (TextView) findViewById(R.id.tv_all_money);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_phoneNum= (TextView) findViewById(R.id.tv_phoneNum);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_paytype= (TextView) findViewById(R.id.tv_paytype);


        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_phone= (EditText) findViewById(R.id.et_user_phone);
        et_group_num= (EditText) findViewById(R.id.et_group_num);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_user_info= (LinearLayout) findViewById(R.id.ll_user_info);
        rg_get_ways= (RadioGroup) findViewById(R.id.rg_get_ways);
        tv_all_money.setText("总价:"+totalPrice);
    }

    //初始化ExpandListView
    private void initExpandListView() {
        myly_group = (MyListView) findViewById(R.id.myly_group);
        groupAdapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SiteDisplayHolder2();
            }
        },groupList);
        myly_group.setAdapter(groupAdapter);
        myly_nomal= (MyListView) findViewById(R.id.myly_nomal);
        nomalAdapter = new BaseArrayAdapter(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new SiteDisplayHolder2();
            }
        },nomalList);
        myly_nomal.setAdapter(nomalAdapter);
    }

    @Override
    public void setEvent() {
        btn_order_now.setOnClickListener(this);
        rb_getbyself.setOnClickListener(this);
        rb_sent.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        tv_paytype.setOnClickListener(this);
        rg_get_ways.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_sent:
                        Intent intent=new Intent(context,Activity_address_list.class);
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
                    ua_id = addressesBean.getUa_id()+"";
                    post_type="1";
                    rg_get_ways.check(R.id.rb_sent);
                }else {
                    rg_get_ways.check(R.id.rb_getbyself);
                }
                break;
        }
    }

    //包存新增表演类订单
    private void order(String groupNum,String paytype,String post_type,String mer_id,String route_id,String price,String ua_id,String username,String mobile,String groupStr,String nomalStr){
        showDialog();
        Observable<HttpResult<ZhongcanOrderSuccessBean>> result= ServiceApi.getInstance().getServiceContract().showOrder(apitoken,groupNum,paytype,post_type,
                mer_id,route_id,price,ua_id,username,mobile,groupStr,nomalStr
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
                        toastShow(e.getMessage());
                        dismissDialog();
                    }

                    @Override
                    public void onNext(ZhongcanOrderSuccessBean zhongcanOrderSuccessBean) {
                        toastShow("预约成功");
                        Intent intent =new Intent(context,AttractionsOrderSuccessActivity.class);
                        intent.putExtra("order_id",zhongcanOrderSuccessBean.getOrd_id());
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
