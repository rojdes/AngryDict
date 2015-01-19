package me.rds.angrydictionary.services.media;

import android.app.Service;
import android.media.AudioManager;

import java.util.concurrent.atomic.AtomicInteger;

import me.rds.angrydictionary.app.WidgetApp;

/**
 * Created by D1m11n on 17.01.2015.
 */
public class VolumeHelper {

    private static final int MUSIC_STREAM = AudioManager.STREAM_MUSIC;
    private static int mLastVolume = -1;
    private static AtomicInteger mCount = new AtomicInteger(0);
    private static AudioManager mAmManager;
    private VolumeHelper() {
    }

    public static void setVolume(int level) {
        if (level < 0) return;
        mCount.incrementAndGet();
        initAudioManagerIfNeeded();
        if (mLastVolume == -1) {
            mLastVolume = mAmManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        mAmManager.setStreamVolume(MUSIC_STREAM, level > getMax() ? getMax() : level, 0);
    }

    public static void restoreVolume() {
        if (mLastVolume == -1) return;
        if (mCount.decrementAndGet() <= 0) {
            mAmManager.setStreamVolume(MUSIC_STREAM, mLastVolume, 0);
            mLastVolume = -1;
        }
    }

    public static int getMax() {
        initAudioManagerIfNeeded();
        return mAmManager.getStreamMaxVolume(MUSIC_STREAM);
    }

    private static void initAudioManagerIfNeeded() {
        if (mAmManager == null)
            mAmManager = (AudioManager) WidgetApp.getInstance().getSystemService(Service.AUDIO_SERVICE);
    }

}
