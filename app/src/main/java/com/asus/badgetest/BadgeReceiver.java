package com.asus.badgetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BadgeReceiver extends BroadcastReceiver {

    public static final String ACTION_TEST = "action_test";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("mingchun", intent.getAction());
    }

}
