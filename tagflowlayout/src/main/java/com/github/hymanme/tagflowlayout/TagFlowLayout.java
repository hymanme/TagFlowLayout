package com.github.hymanme.tagflowlayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.hymanme.tagflowlayout.bean.TagBean;
import com.github.hymanme.tagflowlayout.utils.DensityUtils;
import com.github.hymanme.tagflowlayout.view.FlowLayout;
import com.github.hymanme.tagflowlayout.view.HScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * Author   :hyman
 * Email    :hymanme@163.com
 * Create at 2016/9/4
 * Description:可折叠和展开的标签控件
 */
public class TagFlowLayout extends LinearLayout {
    //默认最小最大布局高度，单位dp
    private static final float DEFAULT_MIN_LAYOUT_HEIGHT = 90;
    private static final float DEFAULT_MAX_LAYOUT_HEIGHT = 200;
    //默认标签之间的间距
    private static final float DEFAULT_TAGS_SPACE = 12;
    //默认标签内间距
    private static final float DEFAULT_TAG_PADDING = 5;
    //默认标签背景圆角大小
    private static final float DEFAULT_TAG_CORNER = 3;
    //默认控件背景颜色
    private static final int DEFAULT_LAYOUT_BACKGROUND_COLOR = 0Xdef2f2f2;
    //默认标题颜色
    private static final int DEFAULT_TITLE_TEXT_COLOR = 0XFF727272;
    //默认标签字体颜色
    private static final int DEFAULT_TAG_TEXT_COLOR = 0XFF727272;
    //默认展开收回提示字体颜色
    private static final int DEFAULT_HINT_TEXT_COLOR = 0Xde000000;
    //默认分割线颜色
    private static final int DEFAULT_DIVIDER_COLOR = 0X1f000000;
    //默认标签背景正常颜色
    private static final int DEFAULT_TAG_BACKGROUND_COLOR = 0XFFFFFFFF;
    //默认标签背景按下颜色
    private static final int DEFAULT_TAG_BACKGROUND_PRESSED_COLOR = 0XFF97445C;
    public static final int DEFAULT_TITLE_TEXT_SIZE = 14;
    public static final int DEFAULT_TAG_TEXT_SIZE = 14;
    public static final int DEFAULT_HINT_TEXT_SIZE = 12;
    //默认展开动画执行时间(毫秒)
    private static final int DEFAULT_ANIMATION_DURATION = 400;

    private Context mContext;
    //最外层布局
    private LinearLayout ll_tag_layout;
    //标题
    private TextView tv_title;
    //标签容器
    private HScrollView hsv_tag_content;
    //点击展开收回的容器
    private RelativeLayout rl_show_more;
    //分割线
    private View divider;
    //iv_arrow_more和tv_more_hint容器，主要用于填充颜色盖住分割线
    private LinearLayout ll_hint_layout;
    //down down down
    private ImageView iv_arrow_more;
    //hint
    private TextView tv_more_hint;

    //是否折叠起来
    //true:折叠起来了
    //false:展开了
    private boolean isFolded;

    //标题
    private String title;
    //展开后显示的提示文字
    private String foldHint;
    //折叠起来后显示的提示文字
    private String expandHint;
    //标题文字颜色
    private int titleTextColor;
    //标签文字颜色
    private int tagsTextColor;
    //控件背景颜色
    private int backGroundColor;
    //标签正常背景颜色
    private int tagsBackgroundColor;
    //标签不正常(按下)背景颜色
    private int tagsBackgroundPressedColor;
    //查看更多文字颜色
    private int hintTextColor;
    //分割线颜色
    private int dividerColor;
    //标签背景圆角度数
    private float tagsBackgroundCorners;
    //标签之间的横向间距
    private int tagsHorizontalSpace;
    //标签之间的纵向间距
    private int tagsVerticalSpace;
    //是否随机设置各个标签的背景颜色(设置true之后手动设置背景颜色将无效)
    private boolean randomBackground;
    //查看更多前面显示的小图标
    private Drawable indicateImage;
    //内容区域最少显示高度(px)
    private int minVisibleHeight;
    //内容区域最大显示高度
    private int maxVisibleHeight;
    //标题字体大小
    private float titleTextSize;
    //标签字体大小
    private float tagsTextSize;
    //提示字体大小
    private float hintTextSize;
    //展开和折叠动画持续时间
    private int animationDuration;

    //标签文本数据
    private List<TagBean> tagBeans;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOrientation(VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        title = a.getString(R.styleable.TagFlowLayout_titleText);
        foldHint = a.getString(R.styleable.TagFlowLayout_foldHint);
        expandHint = a.getString(R.styleable.TagFlowLayout_expandHint);

        backGroundColor = a.getColor(R.styleable.TagFlowLayout_backGroundColor, DEFAULT_LAYOUT_BACKGROUND_COLOR);
        titleTextColor = a.getColor(R.styleable.TagFlowLayout_textTitleColor, DEFAULT_TITLE_TEXT_COLOR);
        tagsTextColor = a.getColor(R.styleable.TagFlowLayout_tagsTextColor, DEFAULT_TAG_TEXT_COLOR);
        tagsBackgroundColor = a.getColor(R.styleable.TagFlowLayout_tagsBackgroundColor, DEFAULT_TAG_BACKGROUND_COLOR);
        tagsBackgroundPressedColor = a.getColor(R.styleable.TagFlowLayout_tagsBackgroundPressedColor, DEFAULT_TAG_BACKGROUND_PRESSED_COLOR);
        hintTextColor = a.getColor(R.styleable.TagFlowLayout_hintTextColor, DEFAULT_HINT_TEXT_COLOR);
        dividerColor = a.getColor(R.styleable.TagFlowLayout_dividerColor, DEFAULT_DIVIDER_COLOR);
        minVisibleHeight = (int) a.getDimension(R.styleable.TagFlowLayout_minVisibleHeight, DensityUtils.dp2px(mContext, DEFAULT_MIN_LAYOUT_HEIGHT));
        maxVisibleHeight = (int) a.getDimension(R.styleable.TagFlowLayout_maxVisibleHeight, DensityUtils.dp2px(mContext, DEFAULT_MAX_LAYOUT_HEIGHT));
        tagsBackgroundCorners = a.getDimension(R.styleable.TagFlowLayout_tagsBackgroundCorners, DensityUtils.dp2px(mContext, DEFAULT_TAG_CORNER));
        tagsHorizontalSpace = (int) a.getDimension(R.styleable.TagFlowLayout_tagsHorizontalSpace, DensityUtils.dp2px(mContext, DEFAULT_TAGS_SPACE));
        tagsVerticalSpace = (int) a.getDimension(R.styleable.TagFlowLayout_tagsVerticalSpace, DensityUtils.dp2px(mContext, DEFAULT_TAGS_SPACE));
        randomBackground = a.getBoolean(R.styleable.TagFlowLayout_randomBackground, false);
        indicateImage = a.getDrawable(R.styleable.TagFlowLayout_indicateImage);
        titleTextSize = a.getDimension(R.styleable.TagFlowLayout_titleTextSize, DensityUtils.sp2px(mContext, DEFAULT_TITLE_TEXT_SIZE));
        tagsTextSize = a.getDimension(R.styleable.TagFlowLayout_tagsTextSize, DensityUtils.sp2px(mContext, DEFAULT_TAG_TEXT_SIZE));
        hintTextSize = a.getDimension(R.styleable.TagFlowLayout_hintTextSize, DensityUtils.sp2px(mContext, DEFAULT_HINT_TEXT_SIZE));
        animationDuration = a.getInt(R.styleable.TagFlowLayout_animationDuration, DEFAULT_ANIMATION_DURATION);
        a.recycle();
        initViews();
        init();
    }

    private void initViews() {
        View.inflate(mContext, R.layout.tag_flow_layout, this);
        ll_tag_layout = (LinearLayout) findViewById(R.id.ll_tag_layout);
        tv_title = (TextView) findViewById(R.id.tv_title);
        hsv_tag_content = (HScrollView) findViewById(R.id.hsv_tag_content);
        rl_show_more = (RelativeLayout) findViewById(R.id.rl_show_more);
        divider = findViewById(R.id.divider);
        ll_hint_layout = (LinearLayout) findViewById(R.id.ll_hint_layout);
        iv_arrow_more = (ImageView) findViewById(R.id.iv_arrow_more);
        tv_more_hint = (TextView) findViewById(R.id.tv_more_hint);
        rl_show_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFolded) {
                    ObjectAnimator.ofFloat(iv_arrow_more, "rotation", 0, 180).start();
                    animate(minVisibleHeight, maxVisibleHeight);
                    tv_more_hint.setText(foldHint);//点击收回
                    //设置可滑动
                    hsv_tag_content.setCanScroll(true);
                } else {
                    ObjectAnimator.ofFloat(iv_arrow_more, "rotation", -180, 0).start();
                    animate(maxVisibleHeight, minVisibleHeight);
                    tv_more_hint.setText(expandHint);//点击展开
                    //设置不可滑动
                    hsv_tag_content.setCanScroll(false);

                }
                isFolded = !isFolded;
            }
        });
    }

    private void init() {
        FlowLayout mLayout = new FlowLayout(mContext);
        hsv_tag_content.removeAllViews();
        hsv_tag_content.addView(mLayout);
        tagBeans = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            tagBeans.add(new TagBean(i, "tag" + i));
        }
        ViewGroup.LayoutParams layoutParams = hsv_tag_content.getLayoutParams();
        layoutParams.height = minVisibleHeight;
        hsv_tag_content.setLayoutParams(layoutParams);
        mLayout.setSpace(tagsHorizontalSpace, tagsVerticalSpace);
        if (tagBeans == null) {
            return;
        }
        for (int i = 0; i < tagBeans.size(); i++) {
            TagBean tagBean = tagBeans.get(i);
            final TextView tv = new TextView(mContext);
            tv.setText(tagBean.getName());
            tagBean.setTag(tv);
            int padding = DensityUtils.dp2px(mContext, DEFAULT_TAG_PADDING);
            tv.setPadding(padding, padding, padding, padding);
            tv.setGravity(Gravity.CENTER);
            //设置字体颜色的选择器
            ColorStateList colorSateList = mContext.getResources().getColorStateList(R.color.secondary_text);
//            ColorStateList colorSateList = new ColorStateList(new int[][]{}, new int[]{tagsTextColor});
            tv.setTextColor(colorSateList);

            GradientDrawable normal = new GradientDrawable();
            normal.setShape(GradientDrawable.RECTANGLE);
            normal.setCornerRadius(tagsBackgroundCorners);
            normal.setColor(tagsBackgroundColor);

            GradientDrawable pressed = new GradientDrawable();
            pressed.setShape(GradientDrawable.RECTANGLE);
            pressed.setCornerRadius(tagsBackgroundCorners);
            pressed.setColor(tagsBackgroundPressedColor);

            StateListDrawable selector = new StateListDrawable();
            selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
            selector.addState(new int[]{}, normal);
            tv.setBackgroundDrawable(selector);

            mLayout.addView(tv);
        }
    }

    private void animate(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(animationDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = hsv_tag_content.getLayoutParams();
                layoutParams.height = value;
                hsv_tag_content.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.tv_title.setText(title);
    }

    public String getFoldHint() {
        return foldHint;
    }

    public void setFoldHint(String foldHint) {
        this.foldHint = foldHint;
        if (isFolded) {
            this.tv_more_hint.setText(foldHint);
        }
    }

    public String getExpandHint() {
        return expandHint;
    }

    public void setExpandHint(String expandHint) {
        this.expandHint = expandHint;
        if (!isFolded) {
            this.tv_more_hint.setText(expandHint);
        }
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        this.tv_title.setTextColor(titleTextColor);
    }

    public int getTagsTextColor() {
        return tagsTextColor;
    }

    public void setTagsTextColor(int tagsTextColor) {
        this.tagsTextColor = tagsTextColor;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getTagsBackgroundColor() {
        return tagsBackgroundColor;
    }

    public void setTagsBackgroundColor(int tagsBackgroundColor) {
        this.tagsBackgroundColor = tagsBackgroundColor;
    }

    public int getTagsBackgroundPressedColor() {
        return tagsBackgroundPressedColor;
    }

    public void setTagsBackgroundPressedColor(int tagsBackgroundPressedColor) {
        this.tagsBackgroundPressedColor = tagsBackgroundPressedColor;
    }

    public int getHintTextColor() {
        return hintTextColor;
    }

    public void setHintTextColor(int hintTextColor) {
        this.hintTextColor = hintTextColor;
        this.tv_more_hint.setTextColor(hintTextColor);
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        this.divider.setBackgroundColor(dividerColor);
    }

    public float getTagsBackgroundCorners() {
        return tagsBackgroundCorners;
    }

    public void setTagsBackgroundCorners(float tagsBackgroundCorners) {
        this.tagsBackgroundCorners = tagsBackgroundCorners;
    }

    public int getTagsHorizontalSpace() {
        return tagsHorizontalSpace;
    }

    public void setTagsHorizontalSpace(int tagsHorizontalSpace) {
        this.tagsHorizontalSpace = tagsHorizontalSpace;
    }

    public int getTagsVerticalSpace() {
        return tagsVerticalSpace;
    }

    public void setTagsVerticalSpace(int tagsVerticalSpace) {
        this.tagsVerticalSpace = tagsVerticalSpace;
    }

    public boolean isRandomBackground() {
        return randomBackground;
    }

    public void setRandomBackground(boolean randomBackground) {
        this.randomBackground = randomBackground;
    }

    public Drawable getIndicateImage() {
        return indicateImage;
    }

    public void setIndicateImage(Drawable indicateImage) {
        this.indicateImage = indicateImage;
    }

    public int getMinVisibleHeight() {
        return minVisibleHeight;
    }

    public void setMinVisibleHeight(int minVisibleHeight) {
        if (isFolded) {
            animate(this.minVisibleHeight, minVisibleHeight);
        }
        this.minVisibleHeight = minVisibleHeight;
    }

    public int getMaxVisibleHeight() {
        return maxVisibleHeight;
    }

    public void setMaxVisibleHeight(int maxVisibleHeight) {
        if (!isFolded) {
            animate(this.maxVisibleHeight, maxVisibleHeight);
        }
        this.maxVisibleHeight = maxVisibleHeight;
    }

    public float getTitleTextSize() {
        return titleTextSize;
    }

    /**
     * 设置标题字体大小
     *
     * @param titleTextSize sp
     */
    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = DensityUtils.sp2px(mContext, titleTextSize);
        this.tv_title.setTextSize(this.titleTextSize);
    }

    public float getTagsTextSize() {
        return tagsTextSize;
    }

    public void setTagsTextSize(float tagsTextSize) {
        this.tagsTextSize = DensityUtils.sp2px(mContext, tagsTextSize);
    }

    public float getHintTextSize() {
        return hintTextSize;
    }

    public void setHintTextSize(float hintTextSize) {
        this.hintTextSize = DensityUtils.sp2px(mContext, hintTextSize);
        this.tv_more_hint.setTextSize(this.hintTextSize);
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public List<TagBean> getTagBeans() {
        return tagBeans;
    }

    public void setTagBeans(List<TagBean> tagBeans) {
        this.tagBeans = tagBeans;
    }

    public boolean isCanScroll() {
        return hsv_tag_content.isCanScroll();
    }

    public void setCanScroll(boolean can) {
        hsv_tag_content.setCanScroll(can);
    }
}
