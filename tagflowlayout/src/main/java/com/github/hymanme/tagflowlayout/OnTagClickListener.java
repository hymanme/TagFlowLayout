package com.github.hymanme.tagflowlayout;

import android.view.View;

/**
 * /**
 * Author   :hyman
 * Email    :hymanme@163.com
 * Create at 2016/9/4
 * Description:
 */
public interface OnTagClickListener {
    void onClick(View view, int position, String name);

    void onLongClick(View view, int position, String name);
}
