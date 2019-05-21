package com.xteam.lzp.hencodeview02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.xteam.lzp.hencodeview02.utils.Util;

public class CameraView extends View {
    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    final float IMAGE_PADDING = Util.setdp2px(100);
    final float IMAGE_WIDTH = Util.setdp2px(200);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap image;
    Camera camera = new Camera();

    {
        image = Util.getAvatar(getResources(), (int) IMAGE_WIDTH);
        camera.rotateX(40);
        //设置camera的z坐标位置.
        camera.setLocation(0,0,Util.getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //camera的x y z坐标和平时的x y坐标有区别.
        //平时的x y 坐标.是x 横线是正值,y对下是正值.
        //而空间的坐标是,x横线是正值,y对上是正值,z对内是正值.
/**
        canvas.save();
        //所以下面的代码是把图片往上移动,而不是往下移动...这个切上面
        canvas.translate(IMAGE_PADDING+IMAGE_WIDTH/2,IMAGE_PADDING+IMAGE_WIDTH/2);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-IMAGE_PADDING/2,-IMAGE_PADDING/2,IMAGE_PADDING/2,0);
        canvas.translate(-(IMAGE_PADDING+IMAGE_WIDTH/2),-(IMAGE_PADDING+IMAGE_WIDTH/2));
        canvas.drawBitmap(image,IMAGE_PADDING,IMAGE_PADDING,paint);
        canvas.restore();


        canvas.save();
        //所以下面的代码是把图片往上移动,而不是往下移动...  这个切下面
        canvas.translate(IMAGE_PADDING+IMAGE_WIDTH/2,IMAGE_PADDING+IMAGE_WIDTH/2);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-IMAGE_PADDING/2,0,IMAGE_PADDING/2,IMAGE_PADDING/2);
        canvas.translate(-(IMAGE_PADDING+IMAGE_WIDTH/2),-(IMAGE_PADDING+IMAGE_WIDTH/2));
        canvas.drawBitmap(image,IMAGE_PADDING,IMAGE_PADDING,paint);
        canvas.restore();
      **/


        canvas.save();
        //所以下面的代码是把图片往上移动,而不是往下移动...这个切上面
        canvas.translate(IMAGE_PADDING+IMAGE_WIDTH/2,IMAGE_PADDING+IMAGE_WIDTH/2);
        canvas.rotate(-30);
        canvas.clipRect(-IMAGE_WIDTH,-IMAGE_WIDTH,IMAGE_WIDTH,0);
        canvas.rotate(30);
        canvas.translate(-(IMAGE_PADDING+IMAGE_WIDTH/2),-(IMAGE_PADDING+IMAGE_WIDTH/2));
        canvas.drawBitmap(image,IMAGE_PADDING,IMAGE_PADDING,paint);
        canvas.restore();

        canvas.save();
        //所以下面的代码是把图片往上移动,而不是往下移动...这个切上面
        canvas.translate(IMAGE_PADDING+IMAGE_WIDTH/2,IMAGE_PADDING+IMAGE_WIDTH/2);
        canvas.rotate(-30);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-IMAGE_WIDTH,0,IMAGE_WIDTH,IMAGE_WIDTH);
        canvas.rotate(30);
        canvas.translate(-(IMAGE_PADDING+IMAGE_WIDTH/2),-(IMAGE_PADDING+IMAGE_WIDTH/2));
        canvas.drawBitmap(image,IMAGE_PADDING,IMAGE_PADDING,paint);
        canvas.restore();

    }
}
