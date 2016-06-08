package com.asus.badgetest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;


public class MyService extends Service {
    int mCount;
    Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler.post(new Runnable() {
            @Override
            public void run() {

                if (mCount > 100) mCount = 0;
                Log.d("mingchun", "count = " + mCount);

                Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
                intent.putExtra("badge_count", mCount++);
                intent.putExtra("badge_count_package_name", getPackageName());
                intent.putExtra("badge_count_class_name", "com.asus.badgetest.Main2Activity");
                sendBroadcast(intent);

                mHandler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
