package me.rds.angrydictionary.services.media;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

import me.rds.angrydictionary.AppConsts;
import me.rds.angrydictionary.AppIntents;

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
        if (intent.getAction().equals(AppIntents.Action.PLAY_ERROR)) {
            Log.e(THREAD_NAME_FOR_DEBUG, "LocalIntents.ACTION_PLAY_ERROR");
            startPlayError();
        }
        if (intent.getAction().equals(AppIntents.Action.PLAY_WORD)) {
            startPlayWord(AppConsts.EXT_FOLDER_MP3 + intent.getStringExtra(AppIntents.Extra.PLAY_FILE));
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
        Log.e("MEDIA_INTENT_SERVICE", "PLAY WORD IS  = " + mp3);
        if (TextUtils.isEmpty(mp3)) return;
        Log.e(THREAD_NAME_FOR_DEBUG, mp3);
        mMediaPlayer.playAudio(mp3);
    }
}
