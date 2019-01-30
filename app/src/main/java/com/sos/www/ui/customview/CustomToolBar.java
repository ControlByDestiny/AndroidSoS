package com.sos.www.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sos.www.R;

public class CustomToolBar extends Toolbar {
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
    //item中的控件
    private TextView mTextView;
    private ImageView mLeftImageView, mRightImageView;


    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initCustomStyle(context, attrs);
        initCustomAttrs();
    }


    private void init(Context context) {
        mView = ViewGroup.inflate(context, R.layout.toolbar, this);
        mTextView = mView.findViewById(R.id.custom_title);
        mLeftImageView = mView.findViewById(R.id.custom_left_icon);
        mRightImageView = mView.findViewById(R.id.custom_right_icon);
    }

    private void initCustomAttrs() {
        mView.setBackgroundColor(mBackgroundColor);
        mTextView.setText(mText);
        mTextView.setTextSize(mTextSize);
        mTextView.setTextColor(mTextColor);
        mLeftImageView.setBackground(mLeftIcon);
        mLeftImageView.setVisibility(mLeftIcon == null ? ImageView.GONE : ImageView.VISIBLE);
        mRightImageView.setBackground(mRightIcon);
    }

    private void initCustomStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar);
        mText = typedArray.getText(R.styleable.CustomToolBar_title);
        mTextSize = px2sp(context, typedArray.getDimensionPixelSize(R.styleable.CustomToolBar_titleSize, 20));
        mTextColor = typedArray.getColor(R.styleable.CustomToolBar_titleColor, Color.BLACK);
        mLeftIcon = typedArray.getDrawable(R.styleable.CustomToolBar_leftIcon);
        mRightIcon = typedArray.getDrawable(R.styleable.CustomToolBar_rightIcon);
        mBackgroundColor = typedArray.getColor(R.styleable.CustomToolBar_backgroundColor, Color.WHITE);
        typedArray.recycle();
    }

    public void setOnLeftIconClickListener(OnClickListener onClickListener) {
        mLeftImageView.setOnClickListener(onClickListener);
    }

    public void setOnRightIconClickListener(OnClickListener onClickListener) {
        mRightImageView.setOnClickListener(onClickListener);
    }

    private static int px2sp(Context context, int pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}
