package com.asus.badgetest;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;


public class MyService extends Service {

    private static final String TAG = "badgetest";
    private static final Uri BADGE_URI = Uri.parse("content://com.android.launcher2.asus.settings/badge");

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    private MyInterface mMyInterface;

    boolean mRunning = false;
    static int mCount;
    Handler mHandler = new Handler();

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            if (mCount > 1000) mCount = 0;
            Log.d(TAG, "count = " + mCount);

            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", mCount++);
            intent.putExtra("badge_count_package_name", getPackageName());
            intent.putExtra("badge_count_class_name", "com.asus.badgetest.Main2Activity");
            sendBroadcast(intent);
            sendBroadcast(intent);

            if (mMyInterface != null) mMyInterface.updateCount(mCount);

            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void startService() {
        if (!mRunning) {
            mHandler.post(mRunnable);
            mRunning = true;
        }
    }

    public void stopService() {
        if (mRunning) {
            mHandler.removeCallbacks(mRunnable);
            mRunning = false;
        }
    }

    void impl(MyInterface myInterface) {
        mMyInterface = myInterface;
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }

    public interface MyInterface {
        public void updateCount(int count);
    }

}
