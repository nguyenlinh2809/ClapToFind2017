package com.example.acer.claptofind2017;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by ACER on 10/29/2017.
 */

public class PlayRingtone {
    MediaPlayer mediaPlayer;
    public PlayRingtone(Context context, int song){
        mediaPlayer = MediaPlayer.create(context, song);
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
