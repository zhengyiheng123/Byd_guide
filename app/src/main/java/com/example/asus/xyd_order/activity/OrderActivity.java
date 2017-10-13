package com.example.asus.xyd_order.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.TuancanHolder;
import com.example.asus.xyd_order.net.BaseApi;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;
import com.example.asus.xyd_order.net.result.TuancanBean;
import com.example.asus.xyd_order.net.result.ZhongcanOrderSuccessBean;
import com.example.asus.xyd_order.ui.CircleImageView;
import com.example.asus.xyd_order.ui.MyListView;
import com.example.asus.xyd_order.utils.ActivityFactory;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.TimeUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zheng on 2017/2/24.
 */
public class OrderActivity extends BaseActivity {
    //时间选择弹出框
    private Dialog dateDialog, timeDialog;
    private MyListView mylistview;
    private List<RestaurantDetailsBean.SingleMealBean> mList=new ArrayList<>();
    private TextView tv_timepicker;
    private TextView btn_order,tv_re_name,tv_meal_info,tv_single_price,tv_total_Price,tv_all_money,tv_paytype,tv_single_jiage;
    private EditText tv_num,et_gname,et_g_phone,et_group_num,et_message;
    private RelativeLayout rl_single,rl_caipin;
    private final static int DATA_MODE=1;
    private int mHour;
    private int mMinute;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String date;
    private android.app.TimePickerDialog pickerDialog;
    private String mode;
    private ImageView iv_add,iv_release;
    private String numString;
    private cn.qqtheme.framework.picker.TimePicker timePicker;
    private String res_name,meal_name,img_path;
    private TuancanBean.PriceListBean priceListBean;
    private ImageView iv_img_meal;

    //到店时间
    private String time="";

    //支付方式
    private String pay_type="";

    //预约人手机号
    private String order_phone;

    //预约人姓名
    private String order_name;
    private OptionPicker picker;
    private String mer_id;
    private List<RestaurantDetailsBean.SingleMealBean> restList;
    private String singleCaidan;
    private Double price;
    private TimePickerView pvTime;
    private CheckBox cb_check;

    /**
     * 日期选择框
     */
    private void datePicker(){
        cn.qqtheme.framework.picker.DatePicker datePicker=new cn.qqtheme.framework.picker.DatePicker(OrderActivity.this, DateTimePicker.YEAR_MONTH_DAY);
        datePicker.show();
        datePicker.setOnDatePickListener(new cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String date=year+"."+month+"."+day;
                timePicker(date);
            }
        });
    }
    /**
     * 时间选择框
     *
     */
    private void timePicker(String date){
        timePicker = new cn.qqtheme.framework.picker.TimePicker(OrderActivity.this, cn.qqtheme.framework.picker.TimePicker.HOUR_24);
        timePicker.show();
        timePicker.setOnTimePickListener(new cn.qqtheme.framework.picker.TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                tv_timepicker.setText(date+" "+hour+":"+minute);
                try {
                    time= TimeUtils.dateToStampsss(date+" "+hour+":"+minute).substring(0,10);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.tv_paytype:
                picker = new OptionPicker(OrderActivity.this,new String[]{"到店支付","在线支付"});
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
            case R.id.tv_timepicker:
//                showDateDialog(DateUtil.getDateForString("2017-02-28"));
//                showDialog(DATA_MODE);
                pvTime.show();
                break;
            case R.id.btn_order:
                //团餐
                if (!cb_check.isChecked()){
                    ToastUtils.show(context,"请阅读并同意免责声明",0);
                    return;
                }
                if (mode.equals("1")){
                    order_name=et_gname.getText().toString();
                    order_phone=et_g_phone.getText().toString();
                    if (!TextUtils.isEmpty(et_group_num.getText().toString())&&!TextUtils.isEmpty(time) && !TextUtils.isEmpty(order_name) && !TextUtils.isEmpty(order_phone) &&!TextUtils.isEmpty(pay_type)){
                        order("1");
                    }else {
                        toastShow("请完善信息");
                    }
                    //单点
                }else if (mode.equals("0")){
                    order_name=et_gname.getText().toString();
                    order_phone=et_g_phone.getText().toString();
                    if (!TextUtils.isEmpty(et_group_num.getText().toString())&&!TextUtils.isEmpty(time) && !TextUtils.isEmpty(order_name) && !TextUtils.isEmpty(order_phone) &&!TextUtils.isEmpty(pay_type)){
                        order("2");
                    }else {
                        toastShow("请完善信息");
                    }
                }

                break;
            case R.id.iv_add:
                int num;
                numString = tv_num.getText().toString();
                if (!android.text.TextUtils.isEmpty(numString)){
                    num= Integer.valueOf(numString);
                    tv_num.setText((num+1)+"");
                }else {
                    tv_num.setText("5");
                }

                break;
            case R.id.iv_release:
                numString=tv_num.getText().toString();
                if (!android.text.TextUtils.isEmpty(numString)){
                    num= Integer.valueOf(numString);
                    if (num>5){
                        tv_num.setText(""+(num-1));
                    }else {
                        return;
                    }
                }else {
                    return;
                }
                break;
        }
    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("预定订单");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_order;
    }


    @Override
    public int getData() throws Exception {
        mode = getIntent().getStringExtra("mode");
        if (mode.equals("1")){

        }else if(mode.equals("0")){
            restList = (List<RestaurantDetailsBean.SingleMealBean>) getIntent().getSerializableExtra("templist");
            mList.clear();
            mList.addAll(restList);
            price = 0.0;
            for (int i=0;i<restList.size();i++){
                Double tempPrice=0.0;
                tempPrice=restList.get(i).getMeal_price()*restList.get(i).getNums();
                price = price +tempPrice;
            }
//            Log.e("zyh",price+"价格");
            JSONArray jsonArray=new JSONArray();
            JSONObject tmpObj=null;
            for (int i=0;i<restList.size();i++){
                tmpObj=new JSONObject();
                tmpObj.put("img_path",restList.get(i).getImg_path());
                tmpObj.put("meal_id",restList.get(i).getMeal_id());
                tmpObj.put("meal_name",restList.get(i).getMeal_name());
                tmpObj.put("meal_price",restList.get(i).getMeal_price());
                tmpObj.put("meal_weight",restList.get(i).getMeal_weight());
                tmpObj.put("nums",restList.get(i).getNums());
                jsonArray.put(tmpObj);
            }
            singleCaidan = jsonArray.toString();
        }
        res_name = getIntent().getStringExtra("res_name");
        meal_name=getIntent().getStringExtra("meal_name");
        img_path=getIntent().getStringExtra("img_path");
        mer_id = getIntent().getStringExtra("mer_id");
        priceListBean = (TuancanBean.PriceListBean) getIntent().getSerializableExtra("price_bean");
        return 0;
    }

    @Override
    public void initView() {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //初始化时间选择器
        initTimePicker();


        mylistview = (MyListView) findViewById(R.id.mylistview);
        tv_timepicker = (TextView) findViewById(R.id.tv_timepicker);
        btn_order = (TextView) findViewById(R.id.btn_order);
        tv_re_name= (TextView) findViewById(R.id.tv_re_name);
        tv_meal_info= (TextView) findViewById(R.id.tv_meal_info);
        tv_single_jiage= (TextView) findViewById(R.id.tv_single_jiage);
        tv_single_price= (TextView) findViewById(R.id.tv_single_price);
        tv_total_Price= (TextView) findViewById(R.id.tv_total_Price);
        tv_all_money= (TextView) findViewById(R.id.tv_all_money);
        tv_paytype= (TextView) findViewById(R.id.tv_paytype);
        cb_check = (CheckBox) findViewById(R.id.cb_check);
        rl_single = (RelativeLayout) findViewById(R.id.rl_single);
        rl_caipin= (RelativeLayout) findViewById(R.id.rl_caipin);
        iv_img_meal = (ImageView) findViewById(R.id.iv_img_meal);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_release= (ImageView) findViewById(R.id.iv_release);
        tv_num= (EditText) findViewById(R.id.tv_num);
        et_g_phone= (EditText) findViewById(R.id.et_g_phone);
        et_g_phone.setText((String)SharedPreferenceUtils.getParam(context,LoginActivity.USER_MOBILE,""));
        et_gname= (EditText) findViewById(R.id.et_gname);
        et_gname.setText((String) SharedPreferenceUtils.getParam(context,LoginActivity.USER_NAME,""));
        //获取当前时间
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyyMMdd");
        String curDate=formatter.format(date);
        et_group_num= (EditText) findViewById(R.id.et_group_num);
        et_group_num.setText(curDate);
        et_message= (EditText) findViewById(R.id.et_message);
        tv_re_name.setText(res_name);
        Glide.with(context).load(BaseApi.getBaseUrl()+img_path).into(iv_img_meal);
        if (mode!=null && mode.equals("0")){
            rl_single.setVisibility(View.GONE);
            tv_meal_info.setText("单点");
            tv_single_jiage.setText("总价："+price);
            tv_all_money.setText(" 总价："+price);
            initListView();
        }
        else if (mode!=null && mode.equals("1")){
            rl_caipin.setVisibility(View.GONE);
            mylistview.setVisibility(View.GONE);
            tv_total_Price.setText("总价："+priceListBean.getMeal_price());
            tv_meal_info.setText(meal_name+"  "+priceListBean.getMeal_price()+"/人");
            tv_single_price.setText("单价："+priceListBean.getMeal_price());
            tv_all_money.setText("总价："+priceListBean.getMeal_price());
        }
    }

    private void initListView() {
        mylistview.setAdapter(new BaseArrayAdapter<>(context, new BaseArrayAdapter.OnCreateViewHolderListener() {
            @Override
            public Object onCreateViewHolder() {
                return new TuancanHolder();
            }
        },mList));
    }

    @Override
    public void setEvent() {
        tv_timepicker.setOnClickListener(this);
        btn_order.setOnClickListener(this);
        iv_release.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        tv_paytype.setOnClickListener(this);
    }
    private MultipartBody.Builder builder;
    public RequestBody getBody(String meal_type){
       return builder.addFormDataPart("apitoken",apitoken)
                .addFormDataPart("group_num",et_group_num.getText().toString())
                .addFormDataPart("seat_cost",tv_num.getText().toString())
                .addFormDataPart("book_time",time)
                .addFormDataPart("pay_type",pay_type)
                .addFormDataPart("mer_id",mer_id)
                .addFormDataPart("message",et_message.getText().toString())
                .addFormDataPart("price",priceListBean.getMeal_price())
                .addFormDataPart("user_name",et_gname.getText().toString())
                .addFormDataPart("mobile",et_g_phone.getText().toString())
                .addFormDataPart("meal_type",meal_type)
//                .addFormDataPart("nums",tv_num.getText().toString())
               .addFormDataPart("single_meal",TextUtils.isEmpty(singleCaidan) ?"":singleCaidan)
                .build();

    }
    /**
     * 预约
     */
    private void order(String meal_type){

        Observable<HttpResult<ZhongcanOrderSuccessBean>> result= ServiceApi.getInstance().getServiceContract().orderSuccess(getBody(meal_type));
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhongcanOrderSuccessBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toastShow(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhongcanOrderSuccessBean zhongcan) {
                        ActivityFactory.goToConfirmOrder(context,zhongcan.getOrd_id()+"");
                        finish();
//                        Log.e("zyh",zhongcan.getOrd_id()+"");
                    }
                });
    }


    //选择时间
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2016, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
//                    Button btn = (Button) v;
//                    btn.setText(getDateAndTime(date));
                tv_timepicker.setText(getDateAndTime(date));
                try {
                    time = TimeUtils.dateToStampssss(getDateAndTime(date));
                    time=time.substring(0,10);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "点", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(14)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();



        final Calendar ca = Calendar.getInstance();
        mHour =ca.get(Calendar.HOUR_OF_DAY);
        mMinute =ca.get(Calendar.MINUTE);
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }
    private String getDateAndTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
