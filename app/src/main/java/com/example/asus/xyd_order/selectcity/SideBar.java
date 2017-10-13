package com.example.asus.xyd_order.selectcity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.utils.Myutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SideBar extends View {

    public static String[] INDEX_STRING = {"A","B","C","D","E","F","G","H","I","J","K","L"
    ,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private List<String> indexStrings;
    private int choose = -1;
    private Paint paint = new Paint();
    private TextView mTextDialog;
    private Context mContext;

    public SideBar(Context context) {
        this(context, null);
        mContext=context;
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext=context;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.transparent));
        indexStrings = Arrays.asList(INDEX_STRING);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / indexStrings.size();

        for (int i = 0; i < indexStrings.size(); i++) {
            paint.setColor(Color.GRAY);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(Myutils.Dp2Px(mContext, 10));
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }

            float xPos = width / 2 - paint.measureText(indexStrings.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(indexStrings.get(i), xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * indexStrings.size());

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.GONE);
                }
                break;
            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < indexStrings.size()) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(indexStrings.get(c));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(indexStrings.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    public void setIndexText(ArrayList<String> indexStrings) {
        this.indexStrings = indexStrings;
        invalidate();
    }


    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

}