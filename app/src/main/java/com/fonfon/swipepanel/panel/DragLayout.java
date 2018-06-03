package com.fonfon.swipepanel.panel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public final class DragLayout extends ViewGroup {

    private static final int VEL_THRESHOLD = 500;
    private static final int DISTANCE_THRESHOLD = 500;
    public View firstView, secondView;
    private ViewDragHelper viewDragHelper;
    private GestureDetectorCompat gestureDetectorCompat;
    private int firstHeight;

    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), new YScrollDetector());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstView = getChildAt(0);
        secondView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (firstView.getTop() == 0) {
            firstView.layout(l, 0, r, b - t);
            secondView.layout(l, 0, r, b - t);

            firstHeight = firstView.getMeasuredHeight();
            secondView.offsetTopAndBottom(firstHeight);
        } else {
            firstView.layout(l, firstView.getTop(), r, firstView.getBottom());
            secondView.layout(l, secondView.getTop(), r, secondView.getBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int finalTop = top;

            if (child == firstView) {
                if (top > 0) {
                    finalTop = 0;
                }
            } else if (child == secondView) {
                if (top < 0) {
                    finalTop = 0;
                }
            }

            return finalTop;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            if (changedView == firstView) {
                secondView.offsetTopAndBottom(dy);
            }
            else if (changedView == secondView) {
                firstView.offsetTopAndBottom(dy);
            }

            ViewCompat.postInvalidateOnAnimation(DragLayout.this);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            animTopOrBottom(releasedChild, yvel);
        }
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void animTopOrBottom(View releasedChild, float yvel) {
        int finalTop = 0;

        if (releasedChild == firstView) {
            if (yvel < -VEL_THRESHOLD || (releasedChild.getTop() < -DISTANCE_THRESHOLD)) {
                finalTop = -firstHeight;
                //open
            }
        } else
            if (releasedChild == secondView) {
            if (yvel > VEL_THRESHOLD || (releasedChild.getTop() > DISTANCE_THRESHOLD)) {
                finalTop = firstHeight;
                //closed
            }
        }
        if (viewDragHelper.smoothSlideViewTo(releasedChild, 0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (firstView.getTop() < 0 && firstView.getBottom() > 0) {
            return false;
        }

        boolean yScroll = gestureDetectorCompat.onTouchEvent(ev);
        boolean shouldIntercept = viewDragHelper.shouldInterceptTouchEvent(ev);

        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            viewDragHelper.processTouchEvent(ev);
        }
        return yScroll && shouldIntercept;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            viewDragHelper.processTouchEvent(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }
}
