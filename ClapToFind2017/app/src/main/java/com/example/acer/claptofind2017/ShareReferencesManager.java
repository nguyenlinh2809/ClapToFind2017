package com.example.acer.claptofind2017;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

/**
 * Created by ACER on 11/13/2017.
 */

public class ShareReferencesManager {

    public static final String SHAREREFERENCES_NAME = "settings";
    public static final String RINGTONE = "ringtone";
    public static final String FLASH = "flash";
    public static final String VIBRATE = "vibrate";
    public static final String IS_START = "is_start";
    public static final String RINGTONE_URI = "ringtone_uri";
    public static final String SENSITY_STATUS = "sensity_status";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ShareReferencesManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHAREREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveSettings(boolean isRingtone, boolean isFlash, boolean isVibrate, String sensityStatus){
        editor = sharedPreferences.edit();
        editor.putBoolean(RINGTONE, isRingtone);
        editor.putBoolean(FLASH, isFlash);
        editor.putBoolean(VIBRATE, isVibrate);
        editor.putString(SENSITY_STATUS, sensityStatus);
        editor.apply();
    }
    public void saveSensity(String sensityStatus){
        editor = sharedPreferences.edit();
        editor.putString(SENSITY_STATUS, sensityStatus);
        editor.apply();
    }
    public void saveRingtoneURI(String ringtoneURI){
        editor = sharedPreferences.edit();
        editor.putString(RINGTONE_URI, ringtoneURI);
        editor.apply();
    }
    public void saveStartStatus(boolean isStart){
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_START, isStart);
        editor.apply();
    }
    public void saveRingtone(boolean isRingtone){
        editor = sharedPreferences.edit();
        editor.putBoolean(RINGTONE, isRingtone);
        editor.apply();
    }
    public void saveFlash(boolean isFlash){
        editor = sharedPreferences.edit();
        editor.putBoolean(FLASH, isFlash);
        editor.apply();
    }
    public void saveVibrate(boolean isVibrate){
        editor = sharedPreferences.edit();
        editor.putBoolean(VIBRATE, isVibrate);
        editor.apply();
    }

    public boolean getRingtoneStatus(){
        return sharedPreferences.getBoolean(RINGTONE, true);
    }
    public boolean getFlashStatus(){
        return sharedPreferences.getBoolean(FLASH, false);
    }
    public boolean getVibrateStatus(){
        return sharedPreferences.getBoolean(VIBRATE, true);
    }
    public boolean getStartStatus(){
        return sharedPreferences.getBoolean(IS_START, false);
    }
    public String getRingtoneURI(){
        return sharedPreferences.getString(RINGTONE_URI, String.valueOf(Settings.System.DEFAULT_RINGTONE_URI));
    }
    public String getSensityStatus(){
        return sharedPreferences.getString(SENSITY_STATUS, "40");
    }
}
