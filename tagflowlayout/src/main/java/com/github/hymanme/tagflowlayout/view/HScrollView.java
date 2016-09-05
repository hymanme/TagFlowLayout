package com.github.hymanme.tagflowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/3 0003
 * Description:
 */
public class HScrollView extends ScrollView {

    public HScrollView(Context context) {
        super(context);
    }

    public HScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean canScroll;

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean isCan) {
        canScroll = isCan;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (canScroll) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        if (t <= 0 & oldt > 0) {
//            canScroll = false;
//        } else if (t > 0 && oldt < 0) {
//            canScroll = true;
//        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
