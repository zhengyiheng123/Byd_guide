package com.example.asus.xyd_order.selectcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.app.APP;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.CityListBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SelectCountryActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<CitySortModel> SourceDateList;
    private List<String> sourceDataList=new ArrayList<>();
    private ImageView iv_back;
    private String apitoken;
    private CityListBean cityListBean1;
    //城市名称临时集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNetData();
        setContentView(R.layout.activitycountry);

    }

    private void initViews() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        characterParser = CharacterParser.getInstance();
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("");
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
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
                    if (countryName.equals(cityListBean1.getRegions().get(i).getRegion_name())){
                        APP.getApplication().setCityBean(cityListBean1.getRegions().get(i));
                        finish();
                    }
                }
            }
        });

        SourceDateList = filledData(sourceDataList.toArray(new String[sourceDataList.size()]));
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.addHeaderView(initHeaderView());
        sortListView.setAdapter(adapter);
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
                            sourceDataList.add(cityListBean.getRegions().get(i).getRegion_name());
                        }
                        initViews();
                    }
                });
    }
}
