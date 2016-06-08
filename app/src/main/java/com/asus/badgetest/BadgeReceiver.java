/*
 * Copyright (c) 2014 ASUSTek Computer Inc. All rights reserved.
 */

package com.asus.badgetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;


public class BadgeReceiver extends BroadcastReceiver {

    private static final HandlerThread sBadgeReceiverThread = new HandlerThread(
            "badge-receiver-thread", Process.THREAD_PRIORITY_BACKGROUND);
    static {
        sBadgeReceiverThread.start();
    }
    private static final Handler sThreadHandler = new Handler(sBadgeReceiverThread.getLooper());

    private class Info {
        String pkgName;
        String className;
        int count;
        int vip_count;
    }

    private static final String TAG = "BadgeReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        final Info info = new Info();
        String countMsg = null;

        try {
            if ("android.intent.action.BADGE_COUNT_UPDATE".equals(intent.getAction())) {
                Log.d("mingchun", "BadgeReceiver():: Asus badge update action");
                info.pkgName = intent.getStringExtra("badge_count_package_name");
                info.className = intent.getStringExtra("badge_count_class_name");
                info.count = intent.getIntExtra("badge_count", 0);
                info.vip_count = intent.getIntExtra("badge_vip_count", 0);
            } else if ("com.sonyericsson.home.action.UPDATE_BADGE".equals(intent.getAction())) {
                // For receive broadcast from Sony's devices, and Facebook's
                // unread message count.
                Log.d("mingchun", "BadgeReceiver():: Sony badge update action");
                info.pkgName = intent
                        .getStringExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME");
                info.className = intent
                        .getStringExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME");
                countMsg = intent
                        .getStringExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE");
                info.count = Integer.valueOf(countMsg);
            }
        } catch (Exception e) {
            Log.w(TAG, "Get intent extra error: " + e.getMessage());
        }
    }

    public static Handler getBadgeHandler() {
        return sThreadHandler;
    }

}
