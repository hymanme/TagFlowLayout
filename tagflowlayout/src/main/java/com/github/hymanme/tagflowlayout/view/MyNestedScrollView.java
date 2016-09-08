package com.github.hymanme.tagflowlayout.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/3 0003
 * Description:
 */
public class MyNestedScrollView extends NestedScrollView {

    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean canScroll;

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean isCan) {
        canScroll = isCan;
    }

//    public boolean dispatchTouchEvent(MotionEvent e) {
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                setEnabled(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (!canScroll) {
//                    setEnabled(false);
//                } else {
//                    setEnabled(true);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                canScroll = true;
//                break;
//            default:
//                break;
//        }
//        return super.dispatchTouchEvent(e);
//    }

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

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
