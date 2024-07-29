package com.jingxi.chrome.plugin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RoundView extends View {
    private int bgColor = Color.WHITE;
    private Paint paint;
    private int width,height,radius;

    public RoundView(Context context) {
        this(context,null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public RoundView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(bgColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        radius = Math.min(width,height) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2,height / 2,radius,paint);
    }
}
