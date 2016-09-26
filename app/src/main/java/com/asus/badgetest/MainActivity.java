package com.asus.badgetest;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "badgetest";

    int mCount = 0;
    int mCountVip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, Build.DEVICE);

    }

    public void addBadge(View view) {
        sendBadge(this, ++mCount, mCountVip);

        TextView mTv = (TextView)findViewById(R.id.textView);
        mTv.setText(mCount + " | " + mCountVip);
    }

    public void addVip(View view) {
        sendBadge(this, mCount, ++mCountVip);

        TextView mTv = (TextView)findViewById(R.id.textView);
        mTv.setText(mCount + " | " + mCountVip);
    }

    public void clearBadge(View view) {
        mCount = 0;
        mCountVip = 0;
        sendBadge(this, mCount, mCountVip);

        TextView mTv = (TextView)findViewById(R.id.textView);
        mTv.setText(mCount + " | " + mCountVip);
    }

    public void sendBadge(Context context, int count, int countVip) {

        String pkg = getComponentName().getPackageName();
        String cls = getComponentName().getClassName();

        Log.d(TAG, "sendBadge" + " pkg = " + pkg + " cls = " + cls + " count = " + count + " vip = " + countVip);

        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_vip_count", countVip);
        intent.putExtra("badge_count_package_name", pkg);
        intent.putExtra("badge_count_class_name", cls);
        context.sendBroadcast(intent);
    }

    public void auto(View view) {
        startActivity(new Intent(this, Main2Activity.class));
    }

}
