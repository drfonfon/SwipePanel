package com.fonfon.swipepanel.panel;

import android.view.MotionEvent;

public interface TouchInterceptor {

    boolean onTouch(MotionEvent ev);

    boolean onInterceptTouch(MotionEvent ev);
}
