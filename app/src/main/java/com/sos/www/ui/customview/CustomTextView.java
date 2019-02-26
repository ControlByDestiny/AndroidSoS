package com.sos.www.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sos.www.R;


public class CustomTextView extends LinearLayout {
    private static final String TAG = "CustomTextView";
    //文本内容
    private CharSequence mText;
    //文本大小
    private int mTextSize;
    //文本颜色
    private int mTextColor;
    //背景颜色
    private int mBackgroundColor;
    //左侧图片
    private Drawable mLeftIcon;
    //右侧图片
    private Drawable mRightIcon;
    //整体根布局xml
    private View mView;
    private LinearLayout mRootView;
    private int mUnderLineColor;
    //item中的控件
    private TextView mTextView;
    private ImageView mLeftImageView, mRightImageView;
    private View mUnderLine;
    //item点击事件的监听
    private ItemClickListener mOnItemClickListener;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initCustomStyle(context, attrs);
        useConfig();
    }

    private void init(Context context) {
        mView = View.inflate(context, R.layout.text_item_layout, this);
        mRootView = mView.findViewById(R.id.item_root);
        mTextView = mView.findViewById(R.id.item_text);
        mLeftImageView = mView.findViewById(R.id.item_left_icon);
        mRightImageView = mView.findViewById(R.id.item_right_icon);
        mUnderLine = mView.findViewById(R.id.item_underLine);
        mRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"----click----");
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v);
            }
        });
    }

    private void initCustomStyle(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        mText = typedArray.getText(R.styleable.CustomTextView_text);
        mTextSize = px2sp(context, typedArray.getDimensionPixelSize(R.styleable.CustomTextView_textSize, 12));
        mTextColor = typedArray.getColor(R.styleable.CustomTextView_textColor, Color.BLACK);
        mLeftIcon = typedArray.getDrawable(R.styleable.CustomTextView_leftDrawable);
        mRightIcon = typedArray.getDrawable(R.styleable.CustomTextView_rightDrawable);
        mBackgroundColor = typedArray.getColor(R.styleable.CustomTextView_itemBackgroundColor, Color.WHITE);
        mUnderLineColor = typedArray.getColor(R.styleable.CustomTextView_underLineColor, Color.LTGRAY);
        typedArray.recycle();
    }

    //加载属性
    private void useConfig() {
        mView.setBackgroundColor(mBackgroundColor);
        mTextView.setText(mText);
        mTextView.setTextSize(mTextSize);
        mTextView.setTextColor(mTextColor);
        mLeftImageView.setImageDrawable(mLeftIcon);
        mRightImageView.setImageDrawable(mRightIcon);
        mUnderLine.setBackgroundColor(mUnderLineColor);
    }

    public void  setNoneDivider(){
        mUnderLine.setVisibility(View.INVISIBLE);
    }

    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private static int px2sp(Context context, int pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    public interface ItemClickListener {
        void onItemClick(View v);
    }
}
