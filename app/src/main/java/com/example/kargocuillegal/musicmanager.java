package com.example.kargocuillegal;

import android.content.Context;
import android.media.MediaPlayer;

public class musicmanager {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    public static void startMusic(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.oyun_muzigi);
            mediaPlayer.setLooping(true);
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public static void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    public static boolean isMusicPlaying() {
        return isPlaying;
    }
}
