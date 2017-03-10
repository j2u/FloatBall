package com.example.imchen.myfloatball.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.imchen.myfloatball.FloatBallView;


/**
 * Created by imchen on 2017/3/10.
 */

public class FloatManager {

    private Context mContext;
    private static FloatManager instance;
    private static WindowManager mWindowManager;
    private FloatBallView mFloatBallView;

    private float startY;
    private float startX;
    private static float screenWidth;
    private WindowManager.LayoutParams params;

    private  final  static String  TAG="imc";

    private FloatManager(Context context) {
        this.mContext=context;
    }

    public static FloatManager getInstance(Context context){
        if (instance==null){
            instance=new FloatManager(context);
        }
        return instance;
    }

    public static WindowManager getWindowManager(Context context){
        if (mWindowManager==null){
            mWindowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public void showFloatBall(){
        if (mFloatBallView==null){
            mFloatBallView=new FloatBallView(mContext);
            getWindowManager(mContext);
            params = new WindowManager.LayoutParams();
            //宽高
            params.width=mFloatBallView.width;
            params.height=mFloatBallView.height;
            //坐标
            params.x=mWindowManager.getDefaultDisplay().getWidth()-mFloatBallView.width;
            params.y=mWindowManager.getDefaultDisplay().getHeight()/2;
            //其他属性
            params.gravity= Gravity.LEFT|Gravity.TOP;
            params.format= PixelFormat.RGBA_8888;
            params.type= WindowManager.LayoutParams.TYPE_PHONE;
            params.flags= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            mFloatBallView.setLayoutParams(params);
            mWindowManager.addView(mFloatBallView, params);

            mFloatBallView.setOnTouchListener(floatBallTouchListener);
        }
    }

    public View.OnTouchListener floatBallTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX=event.getRawX();
                    startY=event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mFloatBallView.setDragStatus(true);
                    float x=event.getRawX()-startX;
                    float y=event.getRawY()-startY;
                    params.x+=x;
                    params.y+=y;
                    Log.d(TAG, "onTouch: "+params.x);
                    Log.d(TAG, "onTouch: "+params.y);
                    mWindowManager.updateViewLayout(mFloatBallView,params);
                    startX=event.getRawX();
                    startY=event.getRawY();
                    Log.d(TAG, "onTouch: "+startX);
                    Log.d(TAG, "onTouch: "+startY);
                    break;
                case MotionEvent.ACTION_UP:
                    //在这里把悬浮球沾边
                    mFloatBallView.setDragStatus(false);
                    float centerLine=getScreenWidth()/2;
                    if (startX>=centerLine){
                        params.x= (int) getScreenWidth();
                        params.y= (int) startY;
                        mWindowManager.updateViewLayout(mFloatBallView,params);
                        startX=event.getRawX();
                        startY=event.getRawY();
                        Log.d(TAG, "up: "+startX);
                        Log.d(TAG, "up: "+startY);
                    }else {
                        params.x=0;
                        params.y= (int) startY;
                        mWindowManager.updateViewLayout(mFloatBallView,params);
                        startX=event.getRawX();
                        startY=event.getRawY();
                        Log.d(TAG, "up: "+startX);
                        Log.d(TAG, "up: "+startY);
                    }
                    mFloatBallView.setDragStatus(false);
                    break;
            }

            return false;
        }


    };

    public void hideFloatBall(Context context){
        if (mFloatBallView!=null){
            mWindowManager=getWindowManager(context);
            mWindowManager.removeView(mFloatBallView);
            mFloatBallView=null;
        }
    }

    public static float getScreenWidth(){
        screenWidth=mWindowManager.getDefaultDisplay().getWidth();
        return  screenWidth;
    }
}
