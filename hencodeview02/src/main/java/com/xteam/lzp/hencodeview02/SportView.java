package com.xteam.lzp.hencodeview02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.xteam.lzp.hencodeview02.utils.Util;

public class SportView extends View {
    public SportView(Context context) {
        super(context);
    }

    public SportView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SportView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SportView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float RING_WIDTH = Util.setdp2px(10);
    private final float RADIUS = Util.setdp2px(100);
    private Rect textBounds = new Rect();
    private Paint.FontMetrics textMetrics = new Paint.FontMetrics();
    {
        textPaint.setTextSize(Util.setdp2px(30));
        textPaint.setStrokeWidth(RING_WIDTH);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //ring
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth()/2,getHeight()/2,RADIUS,paint);

        //seekbar
        paint.setColor(Color.YELLOW);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,
                getWidth()/2+RADIUS,getHeight()/2+RADIUS,
                0-90,270,false,paint);
        paint.setStrokeCap(Paint.Cap.BUTT);

        //text--这种测绘方式比较合适静态的文字测绘
        textPaint.setStyle(Paint.Style.FILL);
        //text--这种测绘方式比较合适静态的文字测绘
        textPaint.getTextBounds("ababg",0,"ababg".length(),textBounds);
        //text.这种测绘较为合适动态文字的测绘.不是根据top和bootom两条线,而是根据ascent,和descent这两条线去测绘
        textPaint.getFontMetrics(textMetrics);
        float offset = (textMetrics.ascent+textMetrics.descent)/2f;
        canvas.drawText("ababg",getWidth()/2,getHeight()/2-offset,textPaint);


        //贴边--顶部贴边
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.getTextBounds("asdfgh",0,"asdfgh".length(),textBounds);
        System.out.println("textBounds.top:"+textBounds.top+"--textBounds.bottom:"+textBounds.bottom);
//        canvas.drawText("asdfgh",0,-textBounds.top+textPaint.getFontSpacing(),textPaint);
        canvas.drawText("asdfgh",0,-textBounds.top,textPaint);

        //贴边--左边贴边
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.getTextBounds("asdfghss",0,"asdfghss".length(),textBounds);
        canvas.drawText("asdfghss",-textBounds.left,-textBounds.top+textPaint.getFontSpacing(),textPaint);
    }
}
