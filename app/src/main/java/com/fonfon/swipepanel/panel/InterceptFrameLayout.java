package com.fonfon.swipepanel.panel;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class InterceptFrameLayout extends FrameLayout {

    public TouchInterceptor interceptor;
    private GestureDetector gestureDetector;
    public OnClickListener onClickListener;

    public InterceptFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public InterceptFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InterceptFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InterceptFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        gestureDetector = new GestureDetector(getContext(), new SingleTapConfirm());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).getParent().requestDisallowInterceptTouchEvent(false);
        }
        if (gestureDetector.onTouchEvent(event)) {
            onClickListener.onClick(this);
        }
        return interceptor.onTouch(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).getParent().requestDisallowInterceptTouchEvent(false);
        }
        interceptor.onInterceptTouch(ev);
        return super.onInterceptTouchEvent(ev);
    }

    private final class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

}
