package com.github.hymanme.tagflowlayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.hymanme.tagflowlayout.utils.DensityUtils;
import com.github.hymanme.tagflowlayout.view.FlowLayout;
import com.github.hymanme.tagflowlayout.view.HScrollView;

/**
 * /**
 * Author   :hyman
 * Email    :hymanme@163.com
 * Create at 2016/9/4
 * Description:可折叠和展开的标签控件
 */
public class TagFlowLayout extends NestedScrollView {
    //默认最小最大布局高度，单位dp
    private static final float DEFAULT_MIN_LAYOUT_HEIGHT = 90;
    private static final float DEFAULT_MAX_LAYOUT_HEIGHT = 200;
    //默认标签之间的间距
    private static final float DEFAULT_TAGS_SPACE = 12;
    //默认控件背景颜色
    private static final int DEFAULT_LAYOUT_BACKGROUND_COLOR = 0Xdef2f2f2;
    //默认标题颜色
    private static final int DEFAULT_TITLE_TEXT_COLOR = 0XFF727272;
    //默认展开收回提示字体颜色
    private static final int DEFAULT_HINT_TEXT_COLOR = 0Xde000000;
    //默认分割线颜色
    private static final int DEFAULT_DIVIDER_COLOR = 0X1f000000;
    public static final int DEFAULT_TITLE_TEXT_SIZE = 14;
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
    //控件背景颜色
    private int backGroundColor;
    //查看更多文字颜色
    private int hintTextColor;
    //分割线颜色
    private int dividerColor;
    //标签之间的横向间距
    private int tagsHorizontalSpace;
    //标签之间的纵向间距
    private int tagsVerticalSpace;
    //查看更多前面显示的小图标
    private Drawable indicateImage;
    //内容区域最少显示高度(px)
    private int minVisibleHeight;
    //内容区域最大显示高度
    private int maxVisibleHeight;
    //标题字体大小
    private float titleTextSize;
    //提示字体大小
    private float hintTextSize;
    //展开和折叠动画持续时间
    private int animationDuration;

    //点击监听事件
    private OnTagClickListener mListener;
    private AdapterDataSetObserver mDataSetObserver;
    private TagAdapter mTagAdapter;
    private FlowLayout mLayout;

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
//        setOrientation(VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        title = a.getString(R.styleable.TagFlowLayout_titleText);
        foldHint = a.getString(R.styleable.TagFlowLayout_foldHint);
        expandHint = a.getString(R.styleable.TagFlowLayout_expandHint);

        backGroundColor = a.getColor(R.styleable.TagFlowLayout_backGroundColor, DEFAULT_LAYOUT_BACKGROUND_COLOR);
        titleTextColor = a.getColor(R.styleable.TagFlowLayout_textTitleColor, DEFAULT_TITLE_TEXT_COLOR);
        hintTextColor = a.getColor(R.styleable.TagFlowLayout_hintTextColor, DEFAULT_HINT_TEXT_COLOR);
        dividerColor = a.getColor(R.styleable.TagFlowLayout_dividerColor, DEFAULT_DIVIDER_COLOR);
        minVisibleHeight = (int) a.getDimension(R.styleable.TagFlowLayout_minVisibleHeight, DensityUtils.dp2px(mContext, DEFAULT_MIN_LAYOUT_HEIGHT));
        maxVisibleHeight = (int) a.getDimension(R.styleable.TagFlowLayout_maxVisibleHeight, DensityUtils.dp2px(mContext, DEFAULT_MAX_LAYOUT_HEIGHT));
        tagsHorizontalSpace = (int) a.getDimension(R.styleable.TagFlowLayout_tagsHorizontalSpace, DensityUtils.dp2px(mContext, DEFAULT_TAGS_SPACE));
        tagsVerticalSpace = (int) a.getDimension(R.styleable.TagFlowLayout_tagsVerticalSpace, DensityUtils.dp2px(mContext, DEFAULT_TAGS_SPACE));
        indicateImage = a.getDrawable(R.styleable.TagFlowLayout_indicateImage);
        titleTextSize = a.getDimension(R.styleable.TagFlowLayout_titleTextSize, DensityUtils.sp2px(mContext, DEFAULT_TITLE_TEXT_SIZE));
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
        mLayout = new FlowLayout(mContext);
        hsv_tag_content.removeAllViews();
        hsv_tag_content.addView(mLayout);

        ViewGroup.LayoutParams layoutParams = hsv_tag_content.getLayoutParams();
        layoutParams.height = minVisibleHeight;
        hsv_tag_content.setLayoutParams(layoutParams);
        mLayout.setSpace(tagsHorizontalSpace, tagsVerticalSpace);
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
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    private void reloadData() {
        mLayout.clearAllView();
        for (int i = 0; i < mTagAdapter.getCount(); i++) {
            final View view = mTagAdapter.getView(i, null, mLayout);
            final int j = i;
            if (mListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onClick(TagFlowLayout.this, v, j);
                    }
                });
                view.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mListener.onLongClick(TagFlowLayout.this, v, j);
                        return true;
                    }
                });
            }
            mLayout.addView(view);
        }
    }

    public TagAdapter getTagAdapter() {
        return mTagAdapter;
    }

    public void setTagAdapter(TagAdapter mTagAdapter) {
        if (mTagAdapter != null && mDataSetObserver != null) {
            this.mTagAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        this.mTagAdapter = mTagAdapter;
        if (this.mTagAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            this.mTagAdapter.registerDataSetObserver(mDataSetObserver);
            reloadData();
        }
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

    public void setTitleTextColor(@ColorInt int titleTextColor) {
        this.titleTextColor = titleTextColor;
        this.tv_title.setTextColor(titleTextColor);
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(@ColorInt int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getHintTextColor() {
        return hintTextColor;
    }

    public void setHintTextColor(@ColorInt int hintTextColor) {
        this.hintTextColor = hintTextColor;
        this.tv_more_hint.setTextColor(hintTextColor);
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(@ColorInt int dividerColor) {
        this.dividerColor = dividerColor;
        this.divider.setBackgroundColor(dividerColor);
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
        int minPx = DensityUtils.dp2px(mContext, minVisibleHeight);
        if (!isFolded) {
            animate(this.minVisibleHeight, minPx);
        }
        this.minVisibleHeight = minPx;
    }

    public int getMaxVisibleHeight() {
        return maxVisibleHeight;
    }

    public void setMaxVisibleHeight(int maxVisibleHeight) {
        int maxPx = DensityUtils.dp2px(mContext, maxVisibleHeight);
        if (isFolded) {
            animate(this.maxVisibleHeight, maxPx);
        }
        this.maxVisibleHeight = maxPx;
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
        this.tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.titleTextSize);
    }

    public float getHintTextSize() {
        return hintTextSize;
    }

    public void setHintTextSize(float hintTextSize) {
        this.hintTextSize = DensityUtils.sp2px(mContext, hintTextSize);
        this.tv_more_hint.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.hintTextSize);
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public OnTagClickListener getTagListener() {
        return mListener;
    }

    public void setTagListener(OnTagClickListener mListener) {
        this.mListener = mListener;
        if (mTagAdapter != null) {
            reloadData();
        }
    }

    public boolean isCanScroll() {
        return hsv_tag_content.isCanScroll();
    }

    public void setCanScroll(boolean can) {
        hsv_tag_content.setCanScroll(can);
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
}
