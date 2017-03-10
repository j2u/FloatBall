package com.example.imchen.myfloatball.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.imchen.myfloatball.manager.FloatManager;

/**
 * Created by imchen on 2017/3/10.
 */

public class FloatService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FloatManager floatManager=FloatManager.getInstance(this);
        floatManager.showFloatBall();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        FloatManager floatManager=FloatManager.getInstance(this);
        floatManager.hideFloatBall(this);
    }
}
