package com.example.acer.claptofind2017;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by ACER on 10/29/2017.
 */

public class PlayRingtone {
    MediaPlayer mediaPlayer;
    public PlayRingtone(Context context, String ringtoneURI){
        mediaPlayer = MediaPlayer.create(context, Uri.parse(ringtoneURI));
        mediaPlayer.setLooping(true);
    }
    public void playSong(){
        if(mediaPlayer!=null && (!mediaPlayer.isPlaying())){
            mediaPlayer.start();
        }
    }
    public void stopSong(){
        if(mediaPlayer.isPlaying() && mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }
}
