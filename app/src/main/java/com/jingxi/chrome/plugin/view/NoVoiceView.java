package com.jingxi.chrome.plugin.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class NoVoiceView extends View {
    public NoVoiceView(Context context) {
        super(context);
    }

    public NoVoiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoVoiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return true;
    }
}
