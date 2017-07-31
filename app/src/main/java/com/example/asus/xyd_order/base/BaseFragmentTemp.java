package com.example.asus.xyd_order.base;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.NetworkState;
import com.example.asus.xyd_order.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public abstract class BaseFragmentTemp extends Fragment implements View.OnClickListener{

	protected View rootView;
	protected boolean isVisible;
	private TextView tv_title,third_title,tv_secondTitle;
	private RelativeLayout rl_parent;

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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			rootView =inflater.inflate(getResource(),null);
			initView(rootView);
			initData();
			setEvent();
		return rootView;
	}

	public abstract void initView(View view);
	public abstract int getResource();
	public abstract void initData();
	private void setTitle(){
		rl_parent = (RelativeLayout) rootView.findViewById(R.id.rl_parent);
		tv_title = (TextView) rootView.findViewById(R.id.tv_Title);
		tv_secondTitle= (TextView) rootView.findViewById(R.id.tv_secondTitle);
		third_title= (TextView) rootView.findViewById(R.id.third_title);
		setTitle(rl_parent,tv_title,tv_secondTitle,third_title);
	}
	public abstract void setTitle(RelativeLayout rl_parent,TextView tv_title,TextView tv_secondTitle,TextView third_title);
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
}
