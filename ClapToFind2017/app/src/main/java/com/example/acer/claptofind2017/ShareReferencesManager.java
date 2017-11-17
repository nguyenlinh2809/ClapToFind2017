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
    public static final String RINGTONE_POSITION = "ringtone_position";
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
    public void saveRingtonePosition(int position){
        editor = sharedPreferences.edit();
        editor.putInt(RINGTONE_POSITION, position);
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
    public int getRingtonePosition(){
        return sharedPreferences.getInt(RINGTONE_POSITION, 0);
    }
    public String getSensityStatus(){
        return sharedPreferences.getString(SENSITY_STATUS, "40");
    }
}
