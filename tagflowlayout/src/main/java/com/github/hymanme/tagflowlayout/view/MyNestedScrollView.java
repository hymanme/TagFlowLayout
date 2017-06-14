package com.github.hymanme.tagflowlayout.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/3 0003
 * Description:
 */
public class MyNestedScrollView extends ScrollView {
    private boolean forceFixed;//强制固定
    private boolean canScroll;

    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean isCan) {
        canScroll = isCan;
    }

    public boolean isForceFixed() {
        return forceFixed;
    }

    public void setForceFixed(boolean forceFixed) {
        this.forceFixed = forceFixed;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!forceFixed && canScroll) {
            getParent().requestDisallowInterceptTouchEvent(true);

        }
        return super.dispatchTouchEvent(event);
    }

//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        int height = getChildAt(0).getHeight() - getHeight();
//        if (t <= 0 && oldt >= 0 && t >= height && oldt >= 0) {
//            //滑到顶或者滑到底，设置
//            canScroll = false;
//        } else {
//            canScroll = true;
//        }
//        super.onScrollChanged(l, t, oldl, oldt);
//    }
}
