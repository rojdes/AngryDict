package me.rds.angrydictionary.services.media;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

import me.rds.angrydictionary.LocalConsts;
import me.rds.angrydictionary.LocalIntents;

public class MediaIntentService extends IntentService {


    private static final String THREAD_NAME_FOR_DEBUG = "MEDIA";

    private final CustomMediaPlayer mMediaPlayer;

    public MediaIntentService() {
        super(THREAD_NAME_FOR_DEBUG);
        mMediaPlayer = new CustomMediaPlayer();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) return;
        if (intent.getAction().equals(LocalIntents.ACTION_PLAY_ERROR)) {
            Log.e(THREAD_NAME_FOR_DEBUG, "LocalIntents.ACTION_PLAY_ERROR");
            startPlayError();
        }
        if (intent.getAction().equals(LocalIntents.ACTION_PLAY_WORD)) {
            startPlayWord(intent.getStringExtra(LocalIntents.EXTRA_PLAY_WORD));
        }
    }


    private void startPlayError() {
        int r = new Random().nextInt(5);
        try {
            Log.e(THREAD_NAME_FOR_DEBUG, "r = " + r);
            mMediaPlayer.playAudio(MediaIntentService.this, "mp3/error" + r + ".mp3");
        } catch (IOException e) {
            Log.e("MAIN_ACTIVITY", "eception = " + e.toString());
        }
    }

    private void startPlayWord(String mp3) {
        if (TextUtils.isEmpty(mp3)) return;
        Log.e(THREAD_NAME_FOR_DEBUG, mp3);
        mMediaPlayer.playAudio(LocalConsts.EXT_FOLDER_MP3 + mp3);
    }
}
