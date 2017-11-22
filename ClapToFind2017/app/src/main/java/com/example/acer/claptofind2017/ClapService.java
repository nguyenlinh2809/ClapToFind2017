package com.example.acer.claptofind2017;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;

/**
 * Created by ACER on 11/5/2017.
 */

public class ClapService extends Service {

    NotificationManager notificationManager;
    public static int NOTIFICATION_ID = 1234;

    AudioDispatcher audio;
    PercussionOnsetDetector detector;
    Thread mThread;

    ConfirmReceiver receiver;
    ShareReferencesManager shareReferencesManager;
    double m_sensitivity = 0;

    Timer timer;
    CustomTimerTask customTimerTask;
    public static boolean clapDetect = false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        shareReferencesManager = new ShareReferencesManager(getApplicationContext());
        m_sensitivity = Double.parseDouble(shareReferencesManager.getSensityStatus());
        if(m_sensitivity == 0){
            m_sensitivity = m_sensitivity + 5;
        }
        m_sensitivity = m_sensitivity/2;

        audio = startListen(m_sensitivity);

        initReceiver();
    }

    private void initReceiver() {
        receiver = new ConfirmReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationActivity.MY_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mThread = new Thread(audio);
        mThread.start();
        Log.d("count", "");
        showNotification();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        stopListen();
        notificationManager.cancel(NOTIFICATION_ID);
    }


    public AudioDispatcher startListen(double sensitivity) {
        AudioDispatcher mDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
        double threshold = 8;

        PercussionOnsetDetector mPercussionDetector = new PercussionOnsetDetector(22050, 1024,
                new OnsetHandler() {

                    @Override
                    public void handleOnset(double time, double salience) {

                        if(!clapDetect){
                            if(timer != null){
                                timer.cancel();
                            }
                            timer = new Timer();
                            customTimerTask = new CustomTimerTask();
                            timer.schedule(customTimerTask, 1000, 1000);
                            clapDetect = true;
                        }else if(clapDetect){
                            if(timer != null){
                                timer.cancel();
                                timer.purge();
                                timer = null;
                            }
                            if(CustomTimerTask.count <= 2){
                                Log.d("Clap", "Clap detected!");
                                stopListen();
                                m_sensitivity = Double.parseDouble(shareReferencesManager.getSensityStatus());
                                m_sensitivity = m_sensitivity/2;

                                Intent intent = new Intent(ClapService.this, NotificationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);
                            }
                        }

                    }
                }, sensitivity, threshold);
        mDispatcher.addAudioProcessor(mPercussionDetector);
        this.detector = mPercussionDetector;
        return mDispatcher;
    }


    public void stopListen() {
        if (audio != null) {
            audio.removeAudioProcessor(detector);
            if (mThread != null) {
                mThread.interrupt();
            }
            audio.stop();
            Log.d("Stop", "Stop");
        } else {
            Toast.makeText(this, "Already stop", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public class ConfirmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NotificationActivity.MY_ACTION)) {
                boolean receive = intent.getBooleanExtra(NotificationActivity.MY_RECEIVER, false);
                if (receive) {
                    clapDetect = false;
                    CustomTimerTask.count = 0;
                    audio = startListen(m_sensitivity);
                    mThread = new Thread(audio);
                    mThread.start();

                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentText("Clap to find your phone!");
        builder.setContentTitle("Clap to find");

        Intent intent = new Intent(ClapService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

}
class CustomTimerTask extends TimerTask{
    public static int count =0;
    @Override
    public void run() {
        count ++;
        Log.d("count", count+"");
        if(count > 2){
            this.cancel();
            count = 0;
            ClapService.clapDetect = false;
        }

    }
}
