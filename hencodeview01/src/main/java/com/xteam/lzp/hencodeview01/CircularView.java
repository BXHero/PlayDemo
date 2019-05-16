package com.xteam.lzp.hencodeview01;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CircularView extends View {

    public CircularView(Context context) {
        super(context);
    }

    public CircularView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircularView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int[] angles = {60,120,100,80};
    private int[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.parseColor("#3c3c3c")};

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF rectF;
    final float RADIUS = setdp2px(100);
    int indexCircular = 1;
    int PULLED_LENGTH = 30;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(getWidth()/2-RADIUS,getHeight()/2-RADIUS,getWidth()/2+RADIUS,getHeight()/2+RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            if(indexCircular == i){
                canvas.save();
                canvas.translate((float)(Math.cos(Math.toRadians(startAngle+angles[i]/2))*PULLED_LENGTH),
                        (float)(Math.sin(Math.toRadians(startAngle+angles[i]/2))*PULLED_LENGTH));
            }
            canvas.drawArc(rectF,startAngle,angles[i],true,paint);
            startAngle += angles[i];
            if(indexCircular == i){
                canvas.restore();
            }
        }

    }

    private float setdp2px(int value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value, Resources.getSystem().getDisplayMetrics());
    }
}
