package com.xteam.lzp.hencodeview02.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.xteam.lzp.hencodeview02.R;

public class Util {

    public static float setdp2px(int value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getAvatar(Resources resources,int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.mipmap.rabbi,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(resources,R.mipmap.rabbi,options);
    }

    public static int getZForCamera(){
        return (int) (-6 * Resources.getSystem().getDisplayMetrics().density);
    }
}
