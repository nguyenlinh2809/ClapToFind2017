package com.example.acer.claptofind2017;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class MyBootComplete extends BroadcastReceiver {
    ShareReferencesManager shareReferencesManager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        shareReferencesManager = new ShareReferencesManager(context);
        if(shareReferencesManager.getStartStatus()){
            Intent service = new Intent(context, ClapService.class);
            context.startService(service);
        }
    }
}
