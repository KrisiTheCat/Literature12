package com.krisi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

public class UnscrollableViewPager extends ViewPager {

    public UnscrollableViewPager(Context context) {
        super(context);
    }

    public UnscrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Prevent touch events from being intercepted
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Ignore touch events
        return false;
    }
}
