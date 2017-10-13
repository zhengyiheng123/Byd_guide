package com.example.asus.xyd_order.base;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.NetworkState;
import com.example.asus.xyd_order.utils.SharedPreferenceUtils;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import me.leefeng.promptlibrary.PromptDialog;


public abstract class BaseFragment extends Fragment implements View.OnClickListener{

	protected View rootView;

    public static final int STATE_NO_NETWORK=0;
    public static final int STATE_LOAD_ERROR=2;
    public static final int STATE_CONTENT=1;
	private int resultCode;

	public Context context;
	private PromptDialog dialog;
	public int user_id;
	public String apitoken;

	@Override
	public void onClick(View v) {
		if (NetworkState.isNetworkAvailable(getActivity())){
			myOnclick(v);
		}else {
			ToastUtils.show(getActivity(),"请检查网络连接",0);
		}
	}
public abstract void myOnclick(View view);
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (rootView != null) {
			ViewGroup viewGroup = (ViewGroup) rootView.getParent();
			viewGroup.removeView(rootView);
		}
	}
	public void showDialog(){
		dialog.showLoading("请稍后",false);
	}
	public void dismissDialog(){
		dialog.dismissImmediately();
	}
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		context=getActivity();
		user_id = (int) SharedPreferenceUtils.getParam(context,"user_id",0);
		apitoken = (String) SharedPreferenceUtils.getParam(context,"apitoken","");
		Log.e("zyh",apitoken);
		dialog = new PromptDialog(getActivity());

		rootView =inflater.inflate(getResource(),null);
			try {
				resultCode = initData();
			}catch (Exception e){
				ToastUtils.show(context,e.getMessage(),0);
			}

				initView(rootView);
				setEvent();
		return rootView;
	}


	/**
	 * 加载布局控件
	 * @param view
     */
	public abstract void initView(View view);

	/**
	 * 获取资源文件
	 * @return
     */
	public abstract int getResource();

	/**
	 * 获取网络数据
	 * @return
     */
	public abstract int initData() throws Exception;
//	private void setTitle(){
//		rl_parent = (RelativeLayout) rootView.findViewById(R.id.rl_parent);
//		tv_title = (TextView) rootView.findViewById(R.id.tv_Title);
//		tv_secondTitle= (TextView) rootView.findViewById(R.id.tv_secondTitle);
//		third_title= (TextView) rootView.findViewById(R.id.third_title);
//		setTitle(rl_parent,tv_title,tv_secondTitle,third_title);
//	}
//	public abstract void setTitle(RelativeLayout rl_parent,TextView tv_title,TextView tv_secondTitle,TextView third_title);
//	//初始化为空提示布局
//	private View initEmptyView(LayoutInflater inflater){
//		View view=inflater.inflate(R.layout.empty_view,null);
//		return view;
//	}

	public abstract void setEvent();
	public String getTime() {
		return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
	}

	/**
	 * 解决listview和scrollview的bug
	 * @return
     */
	public int measureHeight(ListView lv_catch) {
		// get ListView adapter
		ListAdapter adapter = lv_catch.getAdapter();
		if (null == adapter) {
			return 0;
		}

		int totalHeight = 0;

		for (int i = 0, len = adapter.getCount(); i < len; i++) {
			View item = adapter.getView(i, null, lv_catch);
			if (null == item) continue;
			// measure each item width and height
			item.measure(0, 0);
			// calculate all height
			totalHeight += item.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = lv_catch.getLayoutParams();

		if (null == params) {
			params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		// calculate ListView height
		params.height = totalHeight + (lv_catch.getDividerHeight() * (adapter.getCount() - 1));

		lv_catch.setLayoutParams(params);

		return params.height;
	}

    public void toastShow(String text){
		ToastUtils.show(context,text,0);
	}
}
