package com.asus.badgetest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int mCount = 0;
    int mCountVip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("mingchun", Build.DEVICE);
    }

    public void addBadge(View view) {
        sendBadge(this, ++mCount, mCountVip);

        TextView mTv = (TextView)findViewById(R.id.textView);
        mTv.setText(String.valueOf(mCount));
    }

    public void sendBadge(Context context, int count, int countVip) {
        Log.d("mingchun", "sendBadge" +
                " pkg = " + getComponentName().getPackageName() +
                " cls = " + getComponentName().getClassName() +
                " count = " + count +
                " vip = " + countVip);

//        String launcherClassName = getLauncherClassName(context);
//        if (launcherClassName == null) { return; }

        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_vip_count", countVip);
        intent.putExtra("badge_count_package_name", getComponentName().getPackageName());
        intent.putExtra("badge_count_class_name", getComponentName().getClassName());
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if(pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }
}
