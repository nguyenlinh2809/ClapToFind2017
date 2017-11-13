package com.example.acer.claptofind2017;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ACER on 11/13/2017.
 */

public class ShareReferencesManager {

    public static final String SHAREREFERENCES_NAME = "settings";
    public static final String RINGTONE = "ringtone";
    public static final String FLASH = "flash";
    public static final String VIBRATE = "vibrate";
    public static final String IS_START = "is_start";
    public static final String RINGTONE_NAME = "ringtone_name";
    public static final String SENSITY_STATUS = "sensity_status";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ShareReferencesManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHAREREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveSettings(boolean isRingtone, boolean isFlash, boolean isVibrate, String ringtoneName, String sensityStatus){
        editor = sharedPreferences.edit();
        editor.putBoolean(RINGTONE, isRingtone);
        editor.putBoolean(FLASH, isFlash);
        editor.putBoolean(VIBRATE, isVibrate);
        editor.putString(RINGTONE_NAME, ringtoneName);
        editor.putString(SENSITY_STATUS, sensityStatus);
        editor.apply();
    }
    public void saveStartStatus(boolean isStart){
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_START, isStart);
        editor.apply();
    }

    public boolean getRingtoneStatus(){
        return sharedPreferences.getBoolean(RINGTONE, false);
    }
    public boolean getFlashStatus(){
        return sharedPreferences.getBoolean(FLASH, false);
    }
    public boolean getVibrateStatus(){
        return sharedPreferences.getBoolean(VIBRATE, false);
    }
    public boolean getStartStatus(){
        return sharedPreferences.getBoolean(IS_START, false);
    }
    public String getRingtoneName(){
        return sharedPreferences.getString(RINGTONE_NAME, "Song");
    }
    public String getSensityStatus(){
        return sharedPreferences.getString(SENSITY_STATUS, "40");
    }
}
