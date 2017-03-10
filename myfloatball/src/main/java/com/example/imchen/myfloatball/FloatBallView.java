package com.example.imchen.myfloatball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by imchen on 2017/3/10.
 */

public class FloatBallView extends View{

    public int width=350;
    public int height=350;
    public String text="hello";
    public Bitmap logo;
    private Paint circlePaint;
    private Paint textPaint;

    private  boolean mDrag=false;

    public FloatBallView(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.GREEN);
        circlePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);

        Bitmap src= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        logo=Bitmap.createScaledBitmap(src,width,height,true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mDrag){
            canvas.drawCircle(width/2,height/2,width/2,circlePaint);
            float textWidth=textPaint.measureText(text);
            Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
            float dy = -(fontMetrics.descent + fontMetrics.ascent) / 2;
            canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + dy, textPaint);
        }else {
            Log.d("imc", "onDraw: logo");
            canvas.drawBitmap(logo,0,0,null);
        }
    }

    public void setDragStatus(boolean isDrag){
        Log.d("imc", "setDragStatus: "+isDrag);
        mDrag=isDrag;
    }

}
