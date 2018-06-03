package com.fonfon.swipepanel.panel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InterceptPager extends ViewPager implements TouchInterceptor {

    public InterceptPager(@NonNull Context context) {
        super(context);
    }

    public InterceptPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouch(MotionEvent ev) {
        return onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouch(MotionEvent ev) {
        return onInterceptTouchEvent(ev);
    }
}
