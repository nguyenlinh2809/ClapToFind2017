package com.example.acer.claptofind2017;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    public static String MY_ACTION = "my_action";
    //public static String MY_RECEIVER = "my_receiver";

    public static String MY_ACTION_VOLUME_UP = "my_action_volume_up";
    public static String MY_ACTION_VOLUME_DOWN = "my_action_volume_down";
    public static String MY_ACTION_ON_DESTROY = "my_action_on_destroy";

    PlayRingtone playRingtone;
    Vibration vibration;
    TurnOnFlash turnOnFlash;

    boolean receiver = false;
    boolean checkFlash = false;

    boolean checkReceiver = false;

    ArrayList<Integer> listSong;
    ShareReferencesManager shareReferencesManager;

    Button btnYes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        addControls();
        awakeScreen();
        addEvents();

    }

    private void awakeScreen(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON );
    }

    private void addEvents() {
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkReceiver = true;
                receiver = true;
                checkFlash = true;
                /*if(shareReferencesManager.getRingtoneStatus()){
                    turnOnFlash.blinkFlash(checkFlash);
                }*/
                stopNotification(shareReferencesManager.getRingtoneStatus(), shareReferencesManager.getVibrateStatus(), shareReferencesManager.getFlashStatus());
                Intent intent = new Intent();
                intent.setAction(MY_ACTION);
                //intent.putExtra(MY_RECEIVER, receiver);
                sendBroadcast(intent);
                finish();
            }
        });
    }

    private void addControls() {
        shareReferencesManager = new ShareReferencesManager(getApplicationContext());
        listSong = new ArrayList<>();
        listSong.add(R.raw.ek_villain_sad);
        listSong.add(R.raw.miss_pooja);
        listSong.add(R.raw.preman);
        listSong.add(R.raw.ringtone);
        listSong.add(R.raw.sumon);
        listSong.add(R.raw.voice_bawa);
        listSong.add(R.raw.in_the_end);
        listSong.add(R.raw.numb);
        listSong.add(R.raw.until_you);


        btnYes = (Button) findViewById(R.id.btnYes);

        if(shareReferencesManager.getFlashStatus()){
            turnOnFlash = new TurnOnFlash(getApplicationContext());
        }


        if(shareReferencesManager.getRingtoneStatus()){
            playRingtone = new PlayRingtone(getApplicationContext(), listSong.get(shareReferencesManager.getRingtonePosition()));
        }
        vibration = new Vibration(getApplicationContext());
        startNotification(shareReferencesManager.getRingtoneStatus(), shareReferencesManager.getFlashStatus(), shareReferencesManager.getVibrateStatus());
    }

    public void startNotification(boolean isRingTone, boolean isFlash, boolean isVibration) {
        if (isRingTone) {
            playRingtone.playSong();
        }
        if (isFlash) {
            turnOnFlash.blinkFlash(checkFlash);

        }
        if (isVibration) {
            vibration.vibrate();
        }

    }

    public void stopNotification(boolean isRingTone, boolean isVibration, boolean isFlash) {
        if (isRingTone) {
            playRingtone.stopSong();
        }
        if (isVibration) {
            vibration.stopVibrate();
        }
        if (isFlash) {
            turnOnFlash.m_turnOff();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        checkReceiver = true;
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            stopNotification(shareReferencesManager.getRingtoneStatus(), shareReferencesManager.getVibrateStatus(), shareReferencesManager.getFlashStatus());
            Intent intent = new Intent();
            intent.setAction(MY_ACTION_VOLUME_DOWN);
            //intent.putExtra(MY_RECEIVER, receiver);
            sendBroadcast(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        checkReceiver = true;
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            stopNotification(shareReferencesManager.getRingtoneStatus(), shareReferencesManager.getVibrateStatus(), shareReferencesManager.getFlashStatus());
            Intent intent = new Intent();
            intent.setAction(MY_ACTION_VOLUME_UP);
            //intent.putExtra(MY_RECEIVER, receiver);
            sendBroadcast(intent);
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if(!checkReceiver){
            stopNotification(shareReferencesManager.getRingtoneStatus(), shareReferencesManager.getVibrateStatus(), shareReferencesManager.getFlashStatus());
            Intent intent = new Intent();
            intent.setAction(MY_ACTION_ON_DESTROY);
            //intent.putExtra(MY_RECEIVER, receiver);
            sendBroadcast(intent);
            finish();
        }
        super.onDestroy();
    }
}
