package com.example.acer.claptofind2017;

import android.content.Context;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by ACER on 10/29/2017.
 */

public class Vibration {
    Vibrator vibrator=null;
    Context context;

    public Vibration(Context context){
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate(){

        long[] pattern = new long[] { 100, 1000, 1000 };
        if(hasVibration()){
            vibrator.vibrate(pattern, 1);
        }else{
            Toast.makeText(context, "This device does not support Vibration!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public void stopVibrate(){
        if(vibrator != null){
            vibrator.cancel();
        }else return;
    }
    public boolean hasVibration(){
        if(vibrator.hasVibrator()){
            return true;
        }else return false;
    }
}
