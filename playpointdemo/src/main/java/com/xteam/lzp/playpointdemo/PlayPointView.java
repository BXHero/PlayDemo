package com.xteam.lzp.playpointdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PlayPointView extends View {


    public PlayPointView(Context context) {
        this(context,null);
    }

    public PlayPointView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PlayPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public PlayPointView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;

    }

    private Context mContext;

    //圆的半径
    private int r = 0;

    //圆的半径微调-用于适配间距
    private int r_spacing = 5;

    //圆的默认颜色
    private int defaultStartColors = Color.parseColor("#FFA500");
    private int defaultEndColors = Color.parseColor("#FF4500");

    //圆选中的颜色
    private int clickColors = Color.GREEN;

    //字体距离圆的间距
    private int text_spacing = r_spacing*10;

    //SPot的画笔
    private Paint spotSpaint;

    //text的画笔
    private Paint txtSpaint;

    //显示的Title
    private String txt_Title = "";

    //Title大小
    private int txt_Size = 40;

    //字体的点击范围区域
    private float txtClickStartX = 0;
    private float txtClickEndX = 0;
    private float txtClickStartY = 0;
    private float txtClickEndY = 0;

    //初始化圆心的list
    SpotList mSpotList;

    //预计一条斜线最多画几个圆
    int spotSum = 12;

    //圆的间距X
    int xSpots1 = 0;

    //圆的间距y
    int ySpots1 = 0;

    @Override
    protected void onDraw(Canvas canvas) {

        if(mSpotList==null) initSpotXY();

        for(Spot item : mSpotList){
            Shader mShader = new LinearGradient(item.x-r,0,item.x+r,item.y,new int[] {item.defaultStartColor,item.defaultEndColor},null,Shader.TileMode.CLAMP);
            spotSpaint.setShader(mShader);
            canvas.drawCircle(item.x, item.y, r-r_spacing, spotSpaint);
        }

        canvas.drawText(txt_Title,getWidth()/2-txtSpaint.measureText(txt_Title)/2,getHeight()/4,txtSpaint);
    }


    public void initSpotXY(){
        synchronized (this){
            if(mSpotList == null) mSpotList = new SpotList();

            //初始化画笔
            spotSpaint = new Paint();
            spotSpaint.setStyle(Paint.Style.FILL);
            spotSpaint.setStrokeWidth(1);

            txtSpaint = new Paint();
            txtSpaint.setStrokeWidth(1);
            txtSpaint.setColor(Color.WHITE);
            txtSpaint.setTextSize(txt_Size);

            txt_Title = mContext.getResources().getString(R.string.title);
            //初始化字体点击区域
            txtClickStartX = getWidth()/2-txtSpaint.measureText(txt_Title)/2;
            txtClickEndX = getWidth()/2+txtSpaint.measureText(txt_Title)/2;
            txtClickStartY = getHeight()/4-txtSpaint.getTextSkewX()-txt_Size;
            txtClickEndY = getHeight()/4+txtSpaint.getTextSkewX()+txt_Size;

            //斜边的长度
            double ll = Math.sqrt((getWidth()*getWidth() +getHeight()*getHeight()));
            //假设的直径-根据斜边
            int itemlength = (int)ll/spotSum;
            //对应的半径
            r = itemlength/2;

            //根据直径确定画X位置的圆个数
            int xSpots = getWidth() / itemlength;
            //根据直径确定画y位置的圆个数
            int ySpots = getHeight() / itemlength;
            //圆的间距X
            xSpots1 = (getWidth() - xSpots*itemlength);
            xSpots1 = xSpots1 > 0 ? xSpots1/(xSpots-1) : 0;
            //圆的间距y
            ySpots1 = (getHeight() - ySpots*itemlength);
            ySpots1 = ySpots1 > 0 ? ySpots1/(ySpots-1) : 0;

            //确认角度--下角度
            double sin = Math.sin(getWidth()/ll);
            double cos = Math.cos(getHeight()/ll);

            for(int x = 0; xSpots > x; x++){
                for(int y = 0; ySpots > y; y++){
                    //判断圆是否超过了边界方位  这里的x和y不是代表圆心.
                    if(r*2*x+r > getWidth() || r*2*y+r >getHeight()) continue;
                    if(x > 0 && x < (xSpots-1)){
                        if(y <= 0 || y == (ySpots-1)){
                            mSpotList.add(new Spot(r*2*x+r+xSpots1*x,r*2*y+r+ySpots1*y,defaultStartColors,defaultEndColors));
                        }
                    }else{
                        mSpotList.add(new Spot(r*2*x+r+xSpots1*x,r*2*y+r+ySpots1*y,defaultStartColors,defaultEndColors));
                    }
                }
            }

            //绘制斜线的圆心
            for(int i = 1;spotSum >= i;i++){
                int x = (int)((getWidth()/ll)  * itemlength*i);
                int y = (int)((getHeight()/ll) * itemlength*i);
                //圆心加半径判定是否超过了屏幕范围
                if(x+r > getWidth() || y+r >getHeight()) continue;
                //判定是否圆重叠了
                if(!clickContains((int)x,(int)y,true)){
                    mSpotList.add(new Spot(x,y,defaultStartColors,defaultEndColors));
                    //加个判定.保证中心点不会重叠或者两个斜交的圆不会重叠
                    if(Math.abs(getWidth()-2*x)>r){
                        mSpotList.add(new Spot(getWidth()-x,y,defaultStartColors,defaultEndColors));
                    }
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

         if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            if((x>=txtClickStartX && x<=txtClickEndX) && (y>=txtClickStartY && y<=txtClickEndY)){
                if(mOnTestClickListener!=null){
                    mOnTestClickListener.finishView(this,false);
                }
            }
        }else if (event.getAction() != MotionEvent.ACTION_CANCEL){
            clickContains((int)event.getX(),(int)event.getY(),false);
            if(isFinishTets() && mOnTestClickListener!=null){
                mOnTestClickListener.finishView(this,true);
            }
        }
        return true;
    }

    class SpotList extends ArrayList<Spot> {

        @Override
        public boolean add(Spot spot) {
            if(this.contains(spot)){
                return false;
            }
            return super.add(spot);
        }

        @Override
        public boolean contains(Object o) {
            if(super.contains(o)){
            }
            return false;
        }
    }

    //判断是否点击了这个圆和判断这个圆是否重叠了
    private boolean clickContains(int x,int y,boolean overlap){
        for(int i = 0;mSpotList.size() > i;i++){
            Spot item = mSpotList.get(i);
            int lx = Math.abs(x-item.x);
            int ly = Math.abs(y-item.y);
            int ll = (int)Math.sqrt((lx*lx + ly*ly));

            if(ll>0 && (ll<=(r+xSpots1) || ll<=(r+ySpots1))){
                if(overlap){
                    return true;
                }else{
                    mSpotList.get(i).setColor(clickColors,clickColors);
                    postInvalidate();
                }
                return false;
            }
        }
        return false;
    }

    private boolean isFinishTets(){
        for(Spot item : mSpotList){
            if(!item.click){
                return false;
            }
        }
        return true;
    }

    class Spot{
        int x;
        int y;
        int defaultStartColor;
        int defaultEndColor;
        boolean click = false;

        public Spot(int x, int y,int defaultStartColor,int defaultEndColor){
            this.x = x;
            this.y = y;
            this.defaultStartColor = defaultStartColor;
            this.defaultEndColor = defaultEndColor;
        }

        private void setColor(int defaultStartColor,int defaultEndColor){
            this.defaultStartColor = defaultStartColor;
            this.defaultEndColor = defaultEndColor;
            click = true;
        }

        @Override
        public boolean equals(Object obj) {
            Spot mSpot = (Spot) obj;
            if(this.x != mSpot.x || this.y != mSpot.y) return false;
            return true;
        }
    }

    OnTestListener mOnTestClickListener;

    interface OnTestListener{
        void finishView(PlayPointView view, boolean result);
    }

    public void setOnTestListener(OnTestListener onTestClickListener){
        this.mOnTestClickListener = onTestClickListener;
    }
}
