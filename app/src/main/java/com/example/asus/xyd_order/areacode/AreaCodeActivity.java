package com.example.asus.xyd_order.areacode;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.ToastUtils;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AreaCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private List<AreaCode> mCodeList;
    private List<String> mIndexList;
    private ImageView iv_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areacode);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("请选择国际区号");
        iv_back.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        IndexView indexView = (IndexView) findViewById(R.id.index_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        AreaCodeGroupDecoration groupDecoration = new AreaCodeGroupDecoration(100, 0xffF1F4F6, 32, getResources().getColor(R.color.material_grey_700));
        //实现group显示
        mRecyclerView.addItemDecoration(groupDecoration);
        //实现分割线显示
        mRecyclerView.addItemDecoration(new AreaCodeDividerDecoration(new ColorDrawable(ContextCompat.getColor(this, R.color.material_grey_200)), 24, 24));

        mCodeList = getAreaCodeList();
        indexView.setIndexList(mIndexList);
        AreaCodeAdapter areaCodeAdapter = new AreaCodeAdapter(mCodeList);
        areaCodeAdapter.setOnitemClick(new AreaCodeAdapter.ItemClickInterface() {
            @Override
            public void OnItemClick(AreaCode areaCode) {
//                ToastUtils.show(getApplicationContext(),areaCode.getCode(),0);
                Intent intent=new Intent();
                String areaCodeStr="";
                if (areaCode.getCode().startsWith("+")){
                    areaCodeStr=areaCode.getCode().substring(1);
                }else {
                    areaCodeStr=areaCode.getCode();
                }
                intent.putExtra("areacode",areaCodeStr);
                setResult(1,intent);
                finish();
            }
        });
        mRecyclerView.setAdapter(areaCodeAdapter);

        indexView.setOnSelectedListener(new IndexView.OnSelectedListener() {
            @Override
            public void onSelected(String index) {
                selectedIndex(index);
            }
        });
    }

    private void selectedIndex(String index) {
        int position = -1;
        for (int i = 0; i < mCodeList.size(); i++) {
            if (mCodeList.get(i).getGroupName().equals(index)) {
                position = i;
                break;
            }
        }

        if (-1 == position) {
            return;
        }

        mRecyclerView.smoothScrollToPosition(position);
    }


    private List<AreaCode> getAreaCodeList() {
        ArrayList<AreaCode> list = new ArrayList<>();
        String[] areaCodeList = getResources().getStringArray(R.array.area_code);

        for (int i = 0; i < areaCodeList.length; i++) {
            String[] info = areaCodeList[i].split(" ");
            if (0 == i) {
                list.add(new AreaCode(info[0], info[1]));
            } else {
                list.add(new AreaCode(info[0], info[1]));
            }
        }

        //按照字母排序，中文转换为拼音后按字母排序
        Collections.sort(list, new Comparator<AreaCode>() {
            @Override
            public int compare(AreaCode o1, AreaCode o2) {
                if (Pinyin.isChinese(o1.getArea().charAt(0))) {
                    return Pinyin.toPinyin(o1.getArea().charAt(0)).compareTo(Pinyin.toPinyin(o2.getArea().charAt(0)));
                } else {
                    return o1.getArea().compareTo(o2.getArea());
                }
            }
        });

        //填充groupName
        mIndexList = new ArrayList<>();
        String groupName;
        String oldName = null;
        for (int j = 0; j < list.size(); j++) {
            AreaCode code = list.get(j);
            if (Pinyin.isChinese(code.getArea().charAt(0))) {
                groupName = String.valueOf(Pinyin.toPinyin(code.getArea().charAt(0)).charAt(0));
            } else {
                groupName = code.getArea().substring(0, 1);
            }

            code.setGroupName(groupName);
            if (!TextUtils.equals(groupName, oldName)) {
                mIndexList.add(groupName);
                code.setTop(true);
            }

            oldName = groupName;
        }
        return list;
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
