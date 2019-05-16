package com.xteam.lzp.hencodeview01;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class XTransMode extends View {
    public XTransMode(Context context) {
        super(context);
    }

    public XTransMode(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XTransMode(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XTransMode(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap avatar;
    final float BITMAP_WIDTH = setdp2px(300);
    final float PADDING = setdp2px(20);
    RectF cut = new RectF();
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    {
        avatar = getAvatar((int)BITMAP_WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int tmpWidth = 0;
        int startX = 0;
        int startY = 0;
        if(avatar.getWidth() > avatar.getHeight()){
            tmpWidth = avatar.getHeight();
            startX = avatar.getWidth()/2 - tmpWidth/2;
        }else if(avatar.getWidth() < avatar.getHeight()){
            tmpWidth = avatar.getWidth();
            startY = avatar.getHeight()/2 - tmpWidth/2;
        }else{
            tmpWidth = avatar.getWidth();
        }
        System.out.println("PADDING:"+PADDING);
        System.out.println("startX:"+startX);
        System.out.println("startY:"+startY);
        System.out.println("tmpWidth:"+tmpWidth);
        cut.set(PADDING+startX,PADDING+startY,PADDING+startX+tmpWidth,PADDING+startY+tmpWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int saved = canvas.saveLayer(cut,paint);
        canvas.drawOval(cut,paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(avatar,PADDING,PADDING,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }

    //图片的加载先进行百分比压缩后进行输出.防止OOM异常
    Bitmap getAvatar(int width){
        System.out.println("1-width"+width);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.mipmap.rabbi,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(),R.mipmap.rabbi,options);
    }

    private float setdp2px(int value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value, Resources.getSystem().getDisplayMetrics());
    }
}
