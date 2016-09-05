package com.github.hymanme.tagflowlayout.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.github.hymanme.tagflowlayout.R;
import com.github.hymanme.tagflowlayout.utils.DensityUtils;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/5
 * Description: 默认标签视图，可自定义
 */
public class DefaultTagView extends TextView {
    //默认标签内间距
    private static final float DEFAULT_TAG_PADDING = 5;
    //默认标签背景圆角大小
    private static final float DEFAULT_TAG_CORNER = 3;
    //默认标签背景正常颜色
    private static final int DEFAULT_TAG_BACKGROUND_COLOR = 0XFFFFFFFF;
    //默认标签背景按下颜色
    private static final int DEFAULT_TAG_BACKGROUND_PRESSED_COLOR = 0XFF97445C;
    private Context mContext;


    public DefaultTagView(Context context) {
        this(context, null);
    }

    public DefaultTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        int padding = DensityUtils.dp2px(mContext, DEFAULT_TAG_PADDING);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER);
        //设置字体颜色的选择器
        ColorStateList colorSateList = mContext.getResources().getColorStateList(R.color.secondary_text);
        setTextColor(colorSateList);

        GradientDrawable normal = new GradientDrawable();
        normal.setShape(GradientDrawable.RECTANGLE);
        normal.setCornerRadius(DensityUtils.dp2px(mContext, DEFAULT_TAG_CORNER));
        normal.setColor(getNormalColor());

        GradientDrawable pressed = new GradientDrawable();
        pressed.setShape(GradientDrawable.RECTANGLE);
        pressed.setCornerRadius(DensityUtils.dp2px(mContext, DEFAULT_TAG_CORNER));
        pressed.setColor(DEFAULT_TAG_BACKGROUND_PRESSED_COLOR);

        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
        selector.addState(new int[]{}, normal);
        setBackgroundDrawable(selector);
    }

    protected int getNormalColor() {
        return DEFAULT_TAG_BACKGROUND_COLOR;
    }
}
