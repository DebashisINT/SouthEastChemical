package com.southeastchemicalfsm.Customdialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.southeastchemicalfsm.BuildConfig;

public class PowerSaveModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {

        final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm.isPowerSaveMode()) {
            // do something
        } else {
            // do some else
        }
    }
}
