package com.xteam.lzp.hencodeview01;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class MTextView extends View {

    //画笔工具.附上抗锯齿属性
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    //路径.dash用于绘制短的仪表刻度.path用于计算仪表的弧线长度
    Path mPath,dash,mPath2;
    //用于测绘仪表的弧线长度
    PathMeasure pathMeasure;
    //设定仪表的半径
    private final float RADIUS = setRadius(100);
    //路径的影响因子操作.给画笔赋能.
    PathDashPathEffect pathDashPathEffect;

    Path circularPath;

    PathDashPathEffect pathDashPathEffectForCircular;

    DashPathEffect dashPathEffect;

    private final float RADIUS2 = setRadius(10);

    public MTextView(Context context) {
        super(context);
    }

    public MTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public MTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MTextView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100, Resources.getSystem().getDisplayMetrics());
    }

    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);

        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(10);

        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setStrokeWidth(10);
        //dash
        dash = new Path();
        dash.addRect(0,0,setdp2px(2),setdp2px(10),Path.Direction.CCW);
        mPath = new Path();
        mPath2 = new Path();
        circularPath = new Path();
        circularPath.addCircle(0,setdp2px(4),setdp2px(4),Path.Direction.CCW);

    }


    /**
     * 测量结果不同的时候会被调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //利用path 来绘制一些图形
//        mPath.addCircle(getWidth()/2,getHeight()/2,RADIUS,Path.Direction.CCW);
//        mPath.addRect(getWidth()/2-RADIUS,getHeight()/2,
//                getWidth()/2+RADIUS,getHeight()/2+RADIUS*2,
//                Path.Direction.CCW);
//        mPath.setFillType(Path.FillType.EVEN_ODD);

        mPath.addArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,
                getWidth()/2+RADIUS,getHeight()/2+RADIUS,
                120,300);
        pathMeasure = new PathMeasure(mPath,false);
        pathDashPathEffect = new PathDashPathEffect(dash,(pathMeasure.getLength()-setdp2px(2))/20,0, PathDashPathEffect.Style.ROTATE);


        pathDashPathEffectForCircular = new PathDashPathEffect(circularPath,50,0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GREEN);
        mPaint2.setColor(Color.RED);
//        canvas.drawPath(mPath,mPaint);
        //绘制仪表
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,
                getWidth()/2+RADIUS,getHeight()/2+RADIUS,
                120,300,false,mPaint);
        //绘制刻度
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,
                getWidth()/2+RADIUS,getHeight()/2+RADIUS,
                120,300,false,mPaint);
        mPaint.setPathEffect(null);


        //绘制红色的虚线
        //flaot里的第一个参数是长度 第二个参数是间距  而函数的第二个参数正数是左移的意思.会让左边的图消失 左移多少消失多少
        DashPathEffect dashPathEffect2 = new DashPathEffect(new float[]{100, 10}, 0);
        mPath2.moveTo(0, getHeight()/10);
        mPath2.lineTo(getWidth(), getHeight()/10);

        mPaint2.setPathEffect(dashPathEffect2);
        canvas.drawPath(mPath2,mPaint2);
        mPaint2.setPathEffect(null);

        //绘制斜线圆试试-而如果使用
        //canvas.drawLine(0,0,getWidth(), getHeight(),mPaint3); 是无法进行这样的绘制的
        mPaint3.setPathEffect(pathDashPathEffectForCircular);
        mPath2.reset();
        mPath2.moveTo(0,0);
        mPath2.lineTo(getWidth(), getHeight()/5);
        canvas.drawPath(mPath2,mPaint3);
        mPaint3.setPathEffect(null);


        //绘制指针
        canvas.drawLine(getWidth()/2,getHeight()/2,
                getWidth()/2+(float)Math.cos(Math.toRadians(getAngle(5)))*RADIUS,
                getHeight()/2+(float)Math.sin(Math.toRadians(getAngle(5)))*RADIUS,
                mPaint);
    }

    private double getAngle(int index){
        return (90+30)+(300/20*index);
    }


    private float setRadius(int value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,Resources.getSystem().getDisplayMetrics());
    }

    private float setdp2px(int value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,Resources.getSystem().getDisplayMetrics());
    }
}
