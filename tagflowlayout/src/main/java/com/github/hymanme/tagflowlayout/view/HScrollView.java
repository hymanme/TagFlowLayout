package com.github.hymanme.tagflowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/3 0003
 * Description:
 */
public class HScrollView extends ScrollView {
    //上一次滑动的坐标
    private int mLastY;
    //是否向下滑动
    private boolean scrollDown;

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

    public boolean dispatchTouchEvent(MotionEvent e) {
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!canScroll) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastY = y;
        return super.dispatchTouchEvent(e);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("T=" + t, "oldT=" + oldt);
        int height = getChildAt(0).getHeight() - getHeight();
        if (t <= 0 && oldt >= 0 || t == height && oldt >= 0) {
            canScroll = false;
        } else {
            canScroll = true;
        }
        Log.i("T=" + t, "|height=" + height);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
