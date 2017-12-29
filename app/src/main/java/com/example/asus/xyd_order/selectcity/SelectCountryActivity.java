package com.example.asus.xyd_order.selectcity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.base.BaseArrayAdapter;
import com.example.asus.xyd_order.db.DBAdapter;
import com.example.asus.xyd_order.dialog.SelectPopwindow;
import com.example.asus.xyd_order.holder.AutoTextHolder;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.HttpService;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RegionsBean;
import com.example.asus.xyd_order.ui.FlowLayout;
import com.example.asus.xyd_order.ui.SelectPopWindow;
import com.example.asus.xyd_order.utils.Myutils;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SelectCountryActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog,tv_search;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<CitySortModel> SourceDateList;
    private List<String> sourceDataList=new ArrayList<>();
    private ImageView iv_back,iv_delete;
    private String apitoken;
    private LinearLayout ll_title;

    private CityListBean cityListBean1;
    FlowLayout flow_history;
    EditText auto_text;
    private DBAdapter db;

    private List<RegionsBean> cityList;
    private List<RegionsBean> regionsList=new ArrayList<>();
    private SelectPopwindow selectPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNetData();
        setContentView(R.layout.activitycountry);
        initViews();
    }

    private void initViews() {
        auto_text= (EditText) findViewById(R.id.auto_text);
        ll_title= (LinearLayout) findViewById(R.id.ll_title);
        initHistory();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        characterParser = CharacterParser.getInstance();
        tv_search= (TextView) findViewById(R.id.tv_search);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        iv_delete= (ImageView) findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(this);
        sideBar.setTextView(dialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position + 1);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        tv_search.setOnClickListener(this);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0){
                    APP.getApplication().setCityBean(null);
                    finish();
                    return;
                }
                String countryName=((CitySortModel) adapter.getItem(position -1)).getName();
                for (int i=0; i<cityListBean1.getRegions().size();i++){
                    if (countryName.equals(cityListBean1.getRegions().get(i).getOriginal_name())){
                        APP.getApplication().setCityBean(cityListBean1.getRegions().get(i));
                        finish();
                    }
                }
            }
        });

    }
private void initCountryList(){
    SourceDateList = filledData(sourceDataList.toArray(new String[sourceDataList.size()]));
    Collections.sort(SourceDateList, new PinyinComparator());
    adapter = new SortAdapter(this, SourceDateList);
    sortListView.addHeaderView(initHeaderView());
    sortListView.setAdapter(adapter);
}
//        if (!db.insert(regionsList.get(i).getRegion_id(),regionsList.get(i).getOriginal_name())){
//            db.deleteContact(regionsList.get(i).getRegion_id());
//            db.insert(regionsList.get(i).getRegion_id(),regionsList.get(i).getOriginal_name());
//        }

    private void initHistory() {
        flow_history= (FlowLayout) findViewById(R.id.flow_history);
        db = new DBAdapter(this);
        db.open();

        addView();
    }

    private  View initHeaderView(){
        View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.header_country,null,false);
        return view;
    }
    private List<CitySortModel> filledData(String[] date) {
        List<CitySortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            CitySortModel sortModel = new CitySortModel();
            sortModel.setName(date[i]);
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {

                //对重庆多音字做特殊处理
                if (pinyin.startsWith("zhongqing")) {
                    sortString = "C";
                    sortModel.setSortLetters("C");
                } else {
                    sortModel.setSortLetters(sortString.toUpperCase());
                }

                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }else {
                sortString = "#";
                sortModel.setSortLetters("#");
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }

            mSortList.add(sortModel);
        }

        Collections.sort(indexString);

        if (indexString.contains("#")){
            String indexFirst=indexString.get(indexString.indexOf("#"));
            indexString.remove("#");
            indexString.add(indexFirst);
        }
        Log.e("cinyida","标签数量："+indexString.size());
        sideBar.setIndexText(indexString);
        return mSortList;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String country_name=auto_text.getText().toString();
                if (!TextUtils.isEmpty(country_name)){
                    queryCountry(country_name);
                }
                break;
            case R.id.iv_delete:
                db.deleteAll();
                break;
        }
    }
    /**
     * 获取城市列表
     */
    private void getNetData(){
        apitoken = (String) SharedPreferenceUtils.getParam(getApplicationContext(),"apitoken","");
        Observable<HttpResult<CityListBean>> result= ServiceApi.getInstance().getServiceContract().cityList(apitoken);
        result.map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CityListBean cityListBean) {
                        cityListBean1 = cityListBean;
                        for (int i=0;i<cityListBean.getRegions().size();i++){
                            sourceDataList.add(cityListBean.getRegions().get(i).getOriginal_name());
                        }
                        initCountryList();
                    }
                });
    }
    //循环添加子控件
    private void addView(){
        for (int i=0;i<(db.getAllContacts().size()>=9?10:db.getAllContacts().size());i++){
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(Myutils.Dp2Px(getApplicationContext(),8),Myutils.Dp2Px(getApplicationContext(),8),Myutils.Dp2Px(getApplicationContext(),8),Myutils.Dp2Px(getApplicationContext(),8));
            TextView textView=new TextView(getApplicationContext());
            int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        APP.getApplication().setCityBean(db.getAllContacts().get(finalI));
                        finish();
                }
            });
            textView.setPadding(Myutils.Dp2Px(getApplicationContext(),12),Myutils.Dp2Px(getApplicationContext(),4),Myutils.Dp2Px(getApplicationContext(),12),Myutils.Dp2Px(getApplicationContext(),4));
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setText(db.getAllContacts().get(i).getOriginal_name());
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_next));
            flow_history.addView(textView,lp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db!=null){
            db.close();
        }
    }


    //查询地址
    private void queryCountry(String mer_name){
        apitoken = (String) SharedPreferenceUtils.getParam(getApplicationContext(),"apitoken","");
        ServiceApi.getInstance().getServiceContract().merQuery(apitoken,mer_name)
                .map(new ResultFilter<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CityListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CityListBean cityListBean) {
                        regionsList.clear();
                        regionsList = cityListBean.getRegions();
                        selectPopWindow = new SelectPopwindow(SelectCountryActivity.this, regionsList, new SelectPopwindow.OnItemClick() {
                            @Override
                            public void onItemClick(int position) {
                                APP.getApplication().setCityBean(regionsList.get(position));
                                        if (!db.insert(regionsList.get(position).getRegion_id(),regionsList.get(position).getOriginal_name())){
                                             db.deleteContact(regionsList.get(position).getRegion_id());
                                             db.insert(regionsList.get(position).getRegion_id(),regionsList.get(position).getOriginal_name());
                                        }
                                finish();
                            }
                        });
                        selectPopWindow.showPop(ll_title,0,10);
                    }
                });
    }
}
