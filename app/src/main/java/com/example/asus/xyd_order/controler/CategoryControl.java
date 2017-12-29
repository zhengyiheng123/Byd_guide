package com.example.asus.xyd_order.controler;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.activity.OverViewActivity;
import com.example.asus.xyd_order.activity.ZhongCanActivity;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.holder.Category_Home_Holder;
import com.example.asus.xyd_order.holder.CountryHolder;
import com.example.asus.xyd_order.holder.MonthHolder;
import com.example.asus.xyd_order.holder.MonthSelectHolder;
import com.example.asus.xyd_order.holder.PopAllCategoryHolder;
import com.example.asus.xyd_order.mode.City;
import com.example.asus.xyd_order.net.result.CategoryBean;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.RegionsBean;
import com.example.asus.xyd_order.net.result.YearAndMonth;
import com.example.asus.xyd_order.ui.FilterButton;
import com.example.asus.xyd_order.ui.FlowLayout;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/2/23.
 */
public class CategoryControl implements View.OnClickListener{
    Activity activity;
    private ListView lv_pop_auto,lv_pop_all;
    private SelectPopWindow popWindow;
    private FrameLayout rl_all_category,rl_all_auto;
    private BaseArrayAdapter arrayAdapter;
    private List<CategoryBean.SubCategoriesBean> autoList;
    private List<CategoryBean.SubCategoriesBean> allCategoryList;
    private final View view;
    private final TextView tv_text,tv_confirm,tv_reset;
    private EditText et_minprice,et_maxprice;
    private final LinearLayout linearLayout;
    private final FrameLayout pop_orderbytime;
    private final ListView lv_pop_orderbytime;
    private final LinearLayout pop_month;
    private final ListView lv_pop_orderbymonth;

    //最大价格
    private String maxPrice="";
    //最低价格
    private String minPrice="";


    //智能排序参数 1:距离优先  2：好评优先  3：人均最低  4：可停大巴
    private String autoParam="";

    //全部分类参数
    private String sub_cate_id="";
    private  FilterButton tv_nolimit,tv_temp,tv_daba,tv_parking;

    public CategoryControl(Activity activity) {
        this.activity=activity;
        initList();
        LayoutInflater inflater=LayoutInflater.from(activity);

        rl_all_category = (FrameLayout) inflater.inflate(R.layout.pop_all_category,null,false);
        lv_pop_all = (ListView) rl_all_category.findViewById(R.id.lv_pop_all);


        view = inflater.inflate(R.layout.header_view_category,null,false);
        tv_text = (TextView) view.findViewById(R.id.tv_text);

        rl_all_auto=(FrameLayout) inflater.inflate(R.layout.pop_all_auto,null,false);
        lv_pop_auto = (ListView) rl_all_auto.findViewById(R.id.lv_pop_auto);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl_all_category.setLayoutParams(params);
        rl_all_auto.setLayoutParams(params);

        linearLayout = (LinearLayout) inflater.inflate(R.layout.pop_shaixuan,null,false);
        tv_nolimit = (FilterButton) linearLayout.findViewById(R.id.tv_nolimit);
        tv_daba= (FilterButton) linearLayout.findViewById(R.id.tv_daba);
        tv_parking= (FilterButton) linearLayout.findViewById(R.id.tv_parking);
        tv_temp= (FilterButton) linearLayout.findViewById(R.id.tv_temp);
        tv_confirm= (TextView) linearLayout.findViewById(R.id.tv_confirm);
        tv_reset= (TextView) linearLayout.findViewById(R.id.tv_reset);
        et_minprice= (EditText) linearLayout.findViewById(R.id.et_minprice);
        et_maxprice= (EditText) linearLayout.findViewById(R.id.et_maxprice);

        tv_confirm.setOnClickListener(this);
        tv_temp.setOnClickListener(this);
        tv_parking.setOnClickListener(this);
        tv_daba.setOnClickListener(this);
        tv_nolimit.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        LinearLayout.LayoutParams linearParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(linearParams);

        //景点门票
        pop_orderbytime = (FrameLayout) inflater.inflate(R.layout.pop_orderbytime,null,false);
        lv_pop_orderbytime = (ListView) pop_orderbytime.findViewById(R.id.lv_pop_orderbytime);

        //选择月份popwindow
        pop_month = (LinearLayout) inflater.inflate(R.layout.pop_montn,null,false);
        lv_pop_orderbymonth = (ListView) pop_month.findViewById(R.id.lv_pop_orderbymonth);
    }
/**
 * 月份选择弹出框
 */
public SelectPopWindow getMonth(List<YearAndMonth> mlist){
    popWindow=null;
    popWindow=new SelectPopWindow(activity,pop_month);
    lv_pop_orderbymonth.setAdapter(new BaseArrayAdapter<>(activity, ()->{return new MonthSelectHolder();},mlist));
    lv_pop_orderbymonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            OverViewActivity.instance.getNetData(mlist.get(i).getYear()+"-"+mlist.get(i).getMonth(),"");
            popWindow.dismiss();
        }
    });
    return popWindow;
}
    /**
     * 城市选择弹出框
     */
    public SelectPopWindow getCountry(List<RegionsBean> mlist){
        popWindow=null;
        popWindow=new SelectPopWindow(activity,pop_month);
        lv_pop_orderbymonth.setAdapter(new BaseArrayAdapter<>(activity, ()->{return new CountryHolder();},mlist));
        lv_pop_orderbymonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                countryItemClick.onItemClick(mlist.get(i));
            }
        });
        return popWindow;
    }
    CountryItemClick countryItemClick;
    public void setCountryItemClick(CountryItemClick countryItemClick){
        this.countryItemClick=countryItemClick;
    }

    public interface CountryItemClick {
        void onItemClick(RegionsBean bean);
    }

    private AllCategoryInterface allCategoryInterface;
    //全部分类点击事件
    public interface AllCategoryInterface{
        void AllCagetoryClick(int position,List<CategoryBean.SubCategoriesBean> beanList);
    }
    public void SetAllCategoryInterface(AllCategoryInterface allCategoryInterface){
        this.allCategoryInterface=allCategoryInterface;
    }
    public SelectPopWindow getAllCategoryPop(){
        popWindow=null;
        tv_text.setText("不限");
        lv_pop_all.removeHeaderView(view);
        lv_pop_all.addHeaderView(view);
            arrayAdapter=new BaseArrayAdapter<>(activity, ()->{ return new PopAllCategoryHolder();},allCategoryList);

        lv_pop_all.setAdapter(arrayAdapter);
        lv_pop_all.setOnItemClickListener((adapterView, view1, i, l) -> {
            setallCategoryList();
            allCategoryInterface.AllCagetoryClick(i,allCategoryList);
//            if (i==0){
//                sub_cate_id ="";
//                ZhongCanActivity.instance.onRefresh();
//                popWindow.dismiss();
//                return;
//            }
            if (i!=0){
                allCategoryList.get(i-1).setState(1);
                arrayAdapter.notifyDataSetChanged();
            }
//            sub_cate_id=allCategoryList.get(i-1).getSub_cate_id()+"";
//            ZhongCanActivity.instance.getNetData(autoParam,getSort_State(),sub_cate_id,getMinPrice(),getMaxPrice());
            popWindow.dismiss();
        });
        popWindow = new SelectPopWindow(activity, rl_all_category);
        return popWindow;
    }
    //智能排序点击事件
    public interface AutoCategoryInterface{
        void AutoCategoryClick(int position, List<CategoryBean.SubCategoriesBean> bean);
    }
    AutoCategoryInterface autoCategoryInterface;
    public void SetAutoInterface(AutoCategoryInterface autoCategoryInterface){
        this.autoCategoryInterface=autoCategoryInterface;
    }
    public SelectPopWindow getAutoPop(){
        popWindow=null;
        tv_text.setText("不限");
        lv_pop_auto.removeHeaderView(view);
        lv_pop_auto.addHeaderView(view);
            arrayAdapter=new BaseArrayAdapter<>(activity, ()->{return new PopAllCategoryHolder();},autoList);
        lv_pop_auto.setAdapter(arrayAdapter);
        popWindow = new SelectPopWindow(activity, rl_all_auto);
        lv_pop_auto.setOnItemClickListener((parent, view1, position, id) -> {
            autoCategoryInterface.AutoCategoryClick(position,autoList);
                    setAutoListState();
            if (position != 0){
                autoList.get(position-1).setState(1);
                arrayAdapter.notifyDataSetChanged();
                popWindow.dismiss();
            }
            popWindow.dismiss();
        }
        );
        return popWindow;
    }

    //设置自动集合state为0
    private void setallCategoryList(){
        for (int i=0;i<allCategoryList.size();i++){
            allCategoryList.get(i).setState(0);
        }
    }
    private void setAutoListState(){
        for (int i=0;i<autoList.size();i++){
            autoList.get(i).setState(0);
        }
    }
    private void initList(){
        allCategoryList = new ArrayList<>();
        autoList = new ArrayList<>();
        autoList.add(new CategoryBean.SubCategoriesBean("距离优先",1));
        autoList.add(new CategoryBean.SubCategoriesBean("好评优先",2));
        autoList.add(new CategoryBean.SubCategoriesBean("人均最低",3));
        autoList.add(new CategoryBean.SubCategoriesBean("可停大巴",4));
    }
    public SelectPopWindow getShaixuan(){
        popWindow=null;
        popWindow=new SelectPopWindow(activity,linearLayout);
        return popWindow;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        FilterButton button=null;
            switch (view.getId()){
                case R.id.tv_daba:
                    button= (FilterButton) view;
                    tv_nolimit.setState("1");
                    tv_nolimit.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
                    tv_nolimit.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
                    if (button.getState().equals("")){
                        button.setState("1");
                        button.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter_checked));
                        button.setTextColor(activity.getResources().getColor(R.color.white));
                    }else {
                        button.setState("");
                        button.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
                        button.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
                    }
                    break;
                case R.id.tv_nolimit:
                    button= (FilterButton) view;
                    tv_nolimit.setState("1");
                    tv_nolimit.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    tv_nolimit.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
                    setBgWhite();
                    button.setState("1");
                    button.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter_checked));
                    button.setTextColor(activity.getResources().getColor(R.color.white));
                    break;
                case R.id.tv_temp:
                    button= (FilterButton) view;
                    tv_nolimit.setState("1");
                    tv_nolimit.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
                    tv_nolimit.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
                    if (button.getState().equals("")){
                        button.setState("3");
                        button.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter_checked));
                        button.setTextColor(activity.getResources().getColor(R.color.white));
                    }else {
                        button.setState("");
                        button.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
                        button.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
                    }
                    break;
                case R.id.tv_parking:
                    button= (FilterButton) view;
                    tv_nolimit.setState("1");
                    tv_nolimit.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
                    tv_nolimit.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
                    if (button.getState().equals("")){
                        button.setState("2");
                        button.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter_checked));
                        button.setTextColor(activity.getResources().getColor(R.color.white));
                    }else {
                        button.setState("");
                        button.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
                        button.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
                    }
                    break;
                case R.id.tv_confirm:
                    ZhongCanActivity.instance.onRefresh();
//                    ZhongCanActivity.instance.getNetData(autoParam,getSort_State(),sub_cate_id,getMinPrice(),getMaxPrice());
                    popWindow.dismiss();
                    break;
                case R.id.tv_reset:
                    setBgWhite();
                    et_maxprice.setText("");
                    et_minprice.setText("");
                    break;
            }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setBgWhite(){

        tv_daba.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
        tv_daba.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
        tv_daba.setState("");

        tv_temp.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
        tv_temp.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
        tv_temp.setState("");

        tv_parking.setBackground(activity.getResources().getDrawable(R.drawable.shape_filter));
        tv_parking.setTextColor(activity.getResources().getColor(R.color.material_grey_800));
        tv_parking.setState("");
    }
    //获取排序参数
    public String getAutoParam(){
        return autoParam;
    }

    //获取筛选参数
    public String getSort_State(){
        if (tv_nolimit.getState().equals("")){
            return "";
        }else {
            String state="";
            if (!tv_daba.getState().equals("")){
                state=state+"1,";
            }
            if (!tv_parking.getState().equals("")){
                state=state+"2,";
            }
            if (!tv_temp.getState().equals("")){
                state =state+"3";
            }
            return state;
        }
    }
    //给全部分类列表绑定数据
    public void setList(List<CategoryBean.SubCategoriesBean>  calist){
        allCategoryList.addAll(calist);
    }
    //获取全部分类条目id
    public String  getSub_cate_id(){
        return sub_cate_id;
    }
    //获取最大价格
    public String getMaxPrice() {
        return et_maxprice.getText().toString();
    }
    //获取最低价格
    public String getMinPrice() {
        return et_minprice.getText().toString();
    }
}
