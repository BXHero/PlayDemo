package com.xteam.lzp.hencodeview02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xteam.lzp.hencodeview02.utils.Util;

public class ImageTextView extends View {

    private String strs = "威尔逊(J. T. Wilson 1963)提出，指通常伴有大规模火山活动的地表高热流区。全球火山" +
            "大部分沿板块边界，特别是洋中脊分布。可是板块内部有些地方也有强烈的火山活动，而且它们下面并没有海" +
            "底扩张作用发生。太平洋中部的夏威夷群岛及其西延的天皇海岭，呈线形延伸，长度超过6000千米，由最东端" +
            "的冒纳罗亚活火山(时代为零)，向西依次变老，到最西北毗邻千岛海沟的底特律海山(约80百万年)。相应地貌" +
            "上也由活火山岛变为休眠火山岛，高度降低、破坏程度加大，最后成为海下截顶山。这一现象被解释为太平洋" +
            "板块向西北运移过程中通过下伏固定热点留下的迹线。摩根(W. J. Morgan，1971)明确提出这些热点是下地" +
            "幔对流的表现，即热点是地幔柱的地表表现。洋底高原即海台多由热点火山产生，如世界上规模最大的翁通爪" +
            "哇海台。有些热点也出现在大陆内部，如美国黄石公园。中国的峨眉山玄武岩也是热点火山的产物。";

    TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float[] mfloat = new float[1];
    Paint.FontMetrics fm = new Paint.FontMetrics();
    final float IMAGE_PADDING = Util.setdp2px(100);
    final float IMAGE_WIDTH = Util.setdp2px(200);
    float bitmapHeight = 0;
    public ImageTextView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Util.setdp2px(20));
        mPaint.getFontMetrics(fm);
        bitmap = Util.getAvatar(getResources(),(int) IMAGE_WIDTH);
        bitmapHeight = bitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //直接用于输入一串文字.
//        StaticLayout staticLayout = new StaticLayout(strs,paint,getWidth(), Layout.Alignment.ALIGN_NORMAL,
//                1,0,false);
//        staticLayout.draw(canvas);

        canvas.drawBitmap(bitmap,getWidth()-IMAGE_WIDTH,IMAGE_PADDING,mPaint);

        int yOffset = (int) paint.getFontSpacing();
        int usableWidth;
        boolean flag = false;
        for (int start = 0, count; start < strs.length();start += count,yOffset +=(int)paint.getFontSpacing()) {
            float textTop = fm.top + yOffset;
            float textBottom = fm.bottom + yOffset;
            //textTop-2 这个操作的原因是因为中文字符的paint.getFontSpacing()会有那么些微的误差.判定在这个误差范围内的话,就要做出规避
            if( (textTop > IMAGE_PADDING && (textTop < IMAGE_PADDING+bitmapHeight || textTop-2 < IMAGE_PADDING+bitmapHeight )) ||
                    (textBottom > IMAGE_PADDING && textBottom < IMAGE_PADDING+bitmapHeight)){
                usableWidth = (int) (getWidth() - IMAGE_WIDTH);
            }else{
                usableWidth = getWidth();
            }
            count = paint.breakText(strs,start,strs.length(),true,usableWidth,mfloat);
            canvas.drawText(strs,start,start+count,0,yOffset,paint);

        }
    }
}
