package com.example.asus.xyd_order.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangxingli on 2014/12/15.
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize= MeasureSpec.getSize(widthMeasureSpec);
        int widthMode= MeasureSpec.getMode(widthMeasureSpec);
        int heightSize= MeasureSpec.getSize(heightMeasureSpec);
        int heightMode= MeasureSpec.getMode(heightMeasureSpec);
        //上面求得的widthSize和heightSize是当mode为EXACT时，求得的，即当layout_width为fill_parent或者100dp等具体值时的宽高值



        //当为wrap_content时需要自己计算宽高
        //wrap_content时实际要显示的宽度和高度
        int width=0;
        int height=0;

        //wrap_content时 每行的宽度和高度
        int lineWidth=0;
        //每一行的高度为这一行所有view的高度中最高的那个view的高度
        int lineHeight=0;
        //计算的逻辑就是实际的宽度为所有行的宽度中最宽的哪行的宽度，高度为所有行高度的叠加

        int childCount=getChildCount();
        for (int i=0;i<childCount;i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            //每一个view占据的高度
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            //每一个view占据的宽度
            int childWidth=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            //注意getMeasureWidth和getMeasureHeight不包括其margin值

            if (childWidth+lineWidth>widthSize-getPaddingLeft()-getPaddingRight()){
                Log.e("TAG","===onmEADUSRE要换行了");
                width= Math.max(lineWidth,width);
                //注意此处加的都是上一行的行高，所以其本身的行高都没有加上，所以最后一个控件时要将其加上
                height +=lineHeight;
                lineHeight=childHeight;
                lineWidth=childWidth;

            }else{
                lineWidth +=childWidth;
                lineHeight= Math.max(lineHeight,childHeight);

            }
            if (i==childCount-1){
                width= Math.max(width,lineWidth);
                height +=lineHeight;
            }

        }
        Log.v("TAG","=-===ONMEASURE WIDTH IS "+width+"==onMeasure HERIGHT "+height);
        Log.v("TAG","===WIDTHYSIZE IS "+widthSize+"----HEIGHTSIZE IS ---"+heightSize);
        //注意此处记得要加上getPadding的各个值，因为padding值属于自定义控件的空间
        setMeasuredDimension(widthMode== MeasureSpec.AT_MOST?width+getPaddingLeft()+getPaddingRight():widthSize,heightMode== MeasureSpec.AT_MOST?height+getPaddingBottom()+getPaddingTop():heightSize);

    }
    ArrayList<List<View>> allViews=new ArrayList<List<View>>();
    ArrayList<Integer> heights=new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        allViews.clear();
        heights.clear();
        Log.v("TAG","---WIDTH ---"+getMeasuredWidth());
        Log.v("TAG","====HEIGHT IS 0-0-"+getMeasuredHeight());
        int childCount=getChildCount();
        int lineWidth=0;
        int lineHeight=0;
        int width=getWidth();
        ArrayList<View> views=new ArrayList<View>();
        for (int i=0;i<childCount;i++){
            View child=getChildAt(i);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+lp.rightMargin+lp.leftMargin;
            int childHeight=child.getMeasuredHeight()+lp.bottomMargin+lp.topMargin;

               if (lineWidth+childWidth>width-getPaddingLeft()-getPaddingRight()){
                   //注意此处加的也是上一行的高度，所以最后一个view时也要加上本行的高度
                   Log.e("TAG","==ONLAYOUT=要换行了");
               heights.add(lineHeight);
               allViews.add(views);
               views=new ArrayList<View>();
               views.add(child);
               lineWidth=childWidth;

               lineHeight=childHeight;

           }else{
               lineWidth +=childWidth;
               views.add(child);
               lineHeight= Math.max(lineHeight,childHeight);

           }
            if (i==childCount-1){
                lineHeight= Math.max(lineHeight,childHeight);
                heights.add(lineHeight);
                allViews.add(views);
            }

        }
        //注意此处用的是getPaddingLeft而不是getLeft，此处的getPaddingLeft是父控件的paddingLelf,是开始放置子view的地方
        //父控件的左边的padding，和上面的padding
        int left=getPaddingLeft();
        int top=getPaddingTop();
        Log.v("TAG","===ALLVIEWS SIZE IS "+allViews.size());
        for (int i=0;i<allViews.size();i++){
            ArrayList<View> childViews= (ArrayList<View>) allViews.get(i);
            int lc,rc,tc,bc;
            int childWidth;
            int childHeight=0;
            int elineHeight=heights.get(i);


            for (int j=0;j<childViews.size();j++){
                View child=childViews.get(j);
                childWidth=child.getMeasuredWidth();
                childHeight=child.getMeasuredHeight();

                MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
                lc=left+lp.leftMargin;
                rc=lc+child.getMeasuredWidth();
                tc=top+lp.topMargin;
                bc=tc+child.getMeasuredHeight();
                child.layout(lc,tc,rc,bc);
                left +=childWidth+lp.leftMargin+lp.rightMargin;


            }
            left=getPaddingLeft();
            top +=elineHeight;
            Log.v("TAG","====TOP IS "+top);



        }



    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
