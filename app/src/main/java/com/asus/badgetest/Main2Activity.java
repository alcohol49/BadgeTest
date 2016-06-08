package com.asus.badgetest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    int mCount;
    int mCountVip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void addBadge(View view) {
        startService(new Intent(this, MyService.class));
//        sendBadge(this, ++mCount, mCountVip);
//        Log.d("mingchun", "mCount = " + mCount + " vip = " + mCountVip);
//
//        TextView mTv = (TextView)findViewById(R.id.textView);
//        mTv.setText(String.valueOf(mCount));
    }

    public void setBadge(Context context, int count, int countVip) {
        Log.d("mingchun", "sendBadge " +
                "pkg " + getComponentName().getPackageName() +
                "cls " + getComponentName().getClassName());

        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count_package_name", getComponentName().getPackageName());
        intent.putExtra("badge_count_class_name", getComponentName().getClassName());
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_vip_count", countVip);
        context.sendBroadcast(intent);
    }
}
