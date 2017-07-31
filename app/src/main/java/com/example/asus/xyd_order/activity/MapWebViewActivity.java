package com.example.asus.xyd_order.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;

/**
 * Created by Zheng on 2017/7/27.
 */

public class MapWebViewActivity extends BaseActivity {

    private TextView tv_title;
    private WebView wv_test;
    private ProgressBar progressBar1;
    private String map_url;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("地址");
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_map_web;
    }

    @Override
    public int getData() throws Exception {
        map_url = getIntent().getStringExtra("map_url");
        return 0;
    }

    @Override
    public void initView() {
        wv_test = (WebView) findViewById(R.id.wv_test);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        WebSettings ws = wv_test.getSettings();
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        ws.setUseWideViewPort(false);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        wv_test.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progressBar1.setVisibility(View.GONE);
                }else {
                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar1.setProgress(newProgress);
                }
            }
        });
        wv_test.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
//        ws.setDefaultTextEncodingName("UTF -8");
//        wv_test.loadData(data, "text/html; charset=UTF-8", null);
        wv_test.loadUrl(map_url);
    }

    @Override
    public void setEvent() {

    }
}
