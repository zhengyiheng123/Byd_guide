package com.example.asus.xyd_order.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.adapter.CityListAdapter;
import com.example.asus.xyd_order.adapter.ResultListAdapter;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.db.DBManager;
import com.example.asus.xyd_order.mode.City;
import com.example.asus.xyd_order.mode.LocateState;
import com.example.asus.xyd_order.ui.SideLetterBar;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.util.List;

/**
 * Created by Zheng on 2017/2/22.
 */
public class LocationActivity extends BaseActivity {

    private ImageView iv_back,iv_clear;
    private TextView overlay;
    private AutoCompleteTextView searchBox;
    private ListView mListView,mResultListView;
    private LinearLayout emptyView;
    private SideLetterBar mLetterBar;

    //private ViewGroup emptyView;
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City>mAllCities;
    private DBManager dbManager;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_city;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        back();
        overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        searchBox = (AutoCompleteTextView) findViewById(R.id.et_search);
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mResultListView= (ListView) findViewById(R.id.listview_search_result);
        emptyView = (LinearLayout) findViewById(R.id.empty_view);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        iv_clear= (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(myOnclickListener);
        initData();
        processView();
    }

    private void processView() {
        mListView.setAdapter(mCityAdapter);

        //右侧的栏
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangeListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position=mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show(mResultAdapter.getItem(position).getName());
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword=s.toString();
                if(TextUtils.isEmpty(keyword)){
                    iv_clear.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                }else{
                    iv_clear.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City>result=dbManager.searchCity(keyword);
                    if(result==null||result.size()==0){
                        emptyView.setVisibility(View.VISIBLE);
                    }else{
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }

                }

            }
        });
    }

    private void initData() {
        dbManager=new DBManager(this);
        dbManager.copyDBFile();
        mAllCities=dbManager.getAllCities();
        mCityAdapter=new CityListAdapter(this,mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                show(name);
            }

            @Override
            public void onLocateClick() {
                Log.e("OnLocateClick","重新定位....");
                mCityAdapter.updateLocateState(LocateState.LOCATING,null);
//                mLocationClient.startAssistantLocation();
            }
        });
        mResultAdapter=new ResultListAdapter(this,null);
    }

    private void show(String name) {
        ToastUtils.show(this,"点击的城市："+name,0);
    }

    private void back() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(myOnclickListener);
    }

    @Override
    public void setEvent() {

    }
    View.OnClickListener myOnclickListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_clear:
                    searchBox.setText("");
                    break;
                case R.id.iv_back:
                    onBackPressed();
                    break;
            }
        }
    } ;
}
