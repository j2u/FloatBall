package com.example.imchen.myfloatball;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.imchen.myfloatball.Service.FloatService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSDKVersion();
    }

    public void startService(View view){
        Intent intent=new Intent(MainActivity.this, FloatService.class);
        Toast.makeText(getApplicationContext(),"启动悬浮球",Toast.LENGTH_SHORT).show();
        startService(intent);
    }

    public  void hideFloatBall(View view){
        Intent intent = new Intent(MainActivity.this,FloatService.class);
        stopService(intent);
    }

    //android 6.0必须申请权限，否则出错
    public void checkSDKVersion(){
        if (Build.VERSION.SDK_INT>=23){
            if (!Settings.canDrawOverlays(this)){
                Intent intent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return;
            }
        }

    }
}
