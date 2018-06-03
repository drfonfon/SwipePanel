package com.fonfon.swipepanel.panel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public final class DragRecyclerView extends RecyclerView {

    private int touchSlop;
    private float motionEventDownX;
    private float motionEventDownY;
    private boolean canScrollUp;
    private boolean canScrollDown;

    public DragRecyclerView(Context context) {
        super(context);
        init();
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                motionEventDownX = ev.getRawX();
                motionEventDownY = ev.getRawY();
                canScrollUp = canScrollingUp();
                canScrollDown = canScrollingDown();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float xDistance = Math.abs(motionEventDownX - ev.getRawX());
                float yDistance = Math.abs(motionEventDownY - ev.getRawY());

                if (yDistance > xDistance && yDistance > touchSlop) {
                    if (motionEventDownY > ev.getRawY() && canScrollDown) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }

                    if (motionEventDownY < ev.getRawY() && canScrollUp) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean canScrollingUp() {
        return !canScrollVertically(-1);
    }

    private boolean canScrollingDown() {
        return !canScrollVertically(1);
    }
}
