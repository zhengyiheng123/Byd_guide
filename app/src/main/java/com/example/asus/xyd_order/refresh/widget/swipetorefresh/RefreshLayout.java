package com.example.asus.xyd_order.refresh.widget.swipetorefresh;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xyd_order.R;


/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 * 
 * @author mrsimple
 */
public class RefreshLayout extends SwipeRefreshLayout implements
		OnScrollListener ,View.OnClickListener{

	/**
	 * 滑动到最下面时的上拉操作
	 */

	private int mTouchSlop;
	/**
	 * listview实例
	 */
	private ListView mListView;

	/**
	 * 上拉监听器, 到了最底部的上拉加载操作
	 */
	private OnLoadListener mOnLoadListener;

	/**
	 * ListView的加载中footer
	 */
	private View mListViewFooter;

	/**
	 * 按下时的y坐标
	 */
	private int mYDown;
	/**
	 * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
	 */
	private int mLastY;
	/**
	 * 是否在加载中 ( 上拉加载更多 )
	 */
	private boolean isLoading = false;
	/**
	 * 是否加载完成（没有数据可以加载）
	 */
	private boolean isFinish = false;

	private TextView mFinishLoadFooterTv;

	private static final int DELAY = 150;
    private static final int DURATION = 1500;

    private int size;
    private AnimatedView[] spots;
	private TextView tv_footer_content;

	//底部是否显示
	private boolean isBottomVisible=true;
	//    private AnimatorPlayer animator;
    
	/**
	 * @param context
	 */
	public RefreshLayout(Context context) {
		this(context, null);
	}

	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setColorSchemeColors(context.getResources().getColor(R.color.material_blue_600),
                context.getResources().getColor(R.color.tool_bar_color));
        setProgressBackgroundColorSchemeResource(R.color.background_color);
//		mListViewFooter = LayoutInflater.from(context).inflate(
//				R.layout.layout_footer, null, false);

//		initProgress();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		// 初始化ListView对象
//		if (mListView == null) {
//			getListView();
//		}
	}

	/**
	 * 获取ListView对象
	 */
	private void getListView() {
		int childs = getChildCount();
		if (childs > 0) {
			View childView = getChildAt(0);
		Log.e("滚动", "childs: " + childs);
		if(childView != null) Log.e("滚动", "childView is null");
			if (childView instanceof ListView) {
				mListView = (ListView) childView;
				Log.i("滚动","getListView 2");
				// 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
				mListView.setOnScrollListener(this);
//				Log.d(VIEW_LOG_TAG, "### 找到listview");
			}
		}
	}

	public void setAdapter(ListAdapter adapter, ListView listView) {
		if(adapter != null) {
			mListView = listView;
			if(mListViewFooter == null) {
				mListViewFooter = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null, false);
//				initProgress();
				tv_footer_content = (TextView) mListViewFooter.findViewById(R.id.tv_footer_content);
				mListViewFooter.setOnClickListener(this);
				mListView.addFooterView(mListViewFooter);
			}

			mListView.setAdapter(adapter);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			mYDown = (int) event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			// 移动
			mLastY = (int) event.getRawY();
			break;

		case MotionEvent.ACTION_UP:
			// 抬起
			if (canLoad()) {
				loadData();
			}
			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	/**
	 * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
	 * 
	 * @return
	 */
	private boolean canLoad() {
//		Log.i("refresh", "canLoad isBottom: " + isBottom());
//		Log.i("refresh", "canLoad: " + (isBottom() && !isLoading));
		return isBottom()   && !isRefreshing();
	}

	/**
	 * 判断是否到了最底部
	 */
	private boolean isBottom() {

		if(mListView == null) {
//			Log.e("加载", "ListView is null!");
			getListView();
		}
		return mListView.getLastVisiblePosition() >= (mListView.getAdapter().getCount() - 2);
	}

	/**
	 * 是否是上拉操作
	 * 
	 * @return
	 */
	private boolean isPullUp() {
		return (mYDown - mLastY) >= mTouchSlop;
	}

	/**
	 * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
	 */
	private void loadData() {
		if (mOnLoadListener != null && !isFinish) {
			// 设置状态
			setLoading(true);
			//
			mOnLoadListener.onLoad();
		}
	}
	/**
	 * @param loading
	 */
	public void setLoading(boolean loading) {
		isLoading = loading;
		if (isLoading) {
//			Log.e("加载", "setLoading()");
//			mListView.addFooterView(mListViewFooter);
			mListViewFooter.setVisibility(View.VISIBLE);
//			animator = new AnimatorPlayer(createAnimations());
//	        animator.play();
		} else {
//			mListView.removeFooterView(mListViewFooter);
//			if(animator != null)
//			animator.stop();
			mListViewFooter.setVisibility(View.GONE);
			TextView tv_footer_content= (TextView) mListViewFooter.findViewById(R.id.tv_footer_content);
			mYDown = 0;
			mLastY = 0;
		}
	}

	/**
	 * 加载完成没有更多的数据了
	 */
	public void onLoadFinish() {
		if(!isFinish) {
			isFinish = true;
			mListView.removeFooterView(mListViewFooter);
			mFinishLoadFooterTv = new TextView(getContext());
			mFinishLoadFooterTv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			mFinishLoadFooterTv.setPadding(0, 16, 0, 32);
			mFinishLoadFooterTv.setGravity(Gravity.CENTER);
			mFinishLoadFooterTv.setText("没有更多数据了！");
			mFinishLoadFooterTv.setTextColor(getResources().getColor(R.color.material_grey_700));
			mListView.addFooterView(mFinishLoadFooterTv);
		}
	}

	/**
	 * 回复加载更多
	 */
	public void recoveryLoad() {
		if(isFinish) {
			isFinish = false;
			mListView.removeFooterView(mFinishLoadFooterTv);
			mListViewFooter = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null, false);
//			initProgress();
			mListView.addFooterView(mListViewFooter);
		}
	}

	/**
	 * @param loadListener
	 */
	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		// 滚动时到了最底部也可以加载更多
		if (canLoad()) {
			loadData();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId())
		{
			case R.id.ll_refresh:
				mOnLoadListener.onLoad();
				break;
		}
	}

	/**
	 * 加载更多的监听器
	 * 
	 * @author mrsimple
	 */
	public static interface OnLoadListener {
		public void onLoad();
	}
}