package com.fonfon.swipepanel.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class InterceptButton extends android.support.v7.widget.AppCompatButton {

    public InterceptButton(Context context) {
        super(context);
    }

    public InterceptButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }
}
