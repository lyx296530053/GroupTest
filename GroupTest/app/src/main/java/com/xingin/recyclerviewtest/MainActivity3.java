package com.xingin.recyclerviewtest;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.xingin.recyclerviewtest.Services.MyService;

public class MainActivity3 extends AppCompatActivity {

    private IInterface mMainService;
    private boolean mIsConnected;
    private boolean mIsBind = false;

    private final int NOTIFICATION_ID = 98;
    private boolean mIsForegroundService = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = new Intent(this, MyService.class);

        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMainService = (IInterface) service;
                if (mMainService != null){
                    MainActivity3.this.mIsConnected = true;
                }else{
                    new Throwable("服务绑定失败");
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                MainActivity3.this.mIsConnected = false;
                Log.i("zyq","ServiceConnection:onServiceDisconnected");
            }
        }, BIND_AUTO_CREATE);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private Notification getNotification(){
        Notification.Builder mBuilder = new Notification.Builder(MainActivity3.this);
        mBuilder.setShowWhen(false);
        mBuilder.setAutoCancel(false);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setLargeIcon(((BitmapDrawable)getDrawable(R.drawable.ic_launcher_foreground)).getBitmap());
        mBuilder.setContentText("thisiscontent");
        mBuilder.setContentTitle("this is title");
        return mBuilder.build();
    }

}