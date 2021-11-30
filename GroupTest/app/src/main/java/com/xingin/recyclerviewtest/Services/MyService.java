package com.xingin.recyclerviewtest.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.xingin.recyclerviewtest.IInterface;
import com.xingin.recyclerviewtest.MainActivity;
import com.xingin.recyclerviewtest.R;

public class MyService extends Service {
    String ID = "com.example.service1";	//这里的id里面输入自己的项目的包的路径
    String NAME = "Channel One";
    private static final String TAG = "MyService";

    public class A extends Binder implements IInterface {
        @Override
        public void show() {
            Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
        }

    }

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setForeground();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new A();
    }

    private void setForeground() {
        Log.i(TAG, "setForeground: ");
        NotificationCompat.Builder notification; //创建服务对象
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID, NAME, manager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(MyService.this).setChannelId(ID);
        } else {
            notification = new NotificationCompat.Builder(MyService.this);
        }
        Intent intent = new Intent(MyService.this, MainActivity.class);
        notification.setContentTitle("标题")
                .setContentText("内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //.setContentIntent(PendingIntent.getActivity(this,0,intent,0))
                .build();
        Notification notification1 = notification.build();
        startForeground(1,notification1);
    }




}