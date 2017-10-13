package com.example.asus.xyd_order.selectcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.net.result.CityListBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class SelectCityActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private List<CitySortModel> SourceDateList;
    private List<String> sourceDataList;
    public static String CITY_LIST="city_list";
    public static String COUNTRY_INFO="country_info";
    private Toolbar toolbar;
    private ImageView iv_back;
    //城市名称临时集合
    private List<CityListBean.RegionsBean> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        countryList = (List<CityListBean.RegionsBean>) getIntent().getSerializableExtra(CITY_LIST);
        sourceDataList=new ArrayList<>();
        for (int i = 0; i< countryList.size(); i++){
            sourceDataList.add(countryList.get(i).getRegion_name());
        }
        initViews();
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
//                Toast.makeText(getApplication(),
//                        ((CitySortModel) adapter.getItem(position - 1)).getName(),
//                        Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra(COUNTRY_INFO,((CitySortModel) adapter.getItem(position)).getName());
                setResult(1,intent);
                finish();
            }
        });

        SourceDateList = filledData(sourceDataList.toArray(new String[sourceDataList.size()]));
        Collections.sort(SourceDateList, new PinyinComparator());
        adapter = new SortAdapter(this, SourceDateList);
//        sortListView.addHeaderView(initHeadView());
        sortListView.setAdapter(adapter);
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
}
