package me.rds.angrydictionary.services.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class CustomMediaPlayer {


    private MediaPlayer mMediaPlayer = new MediaPlayer();


    /**
     * This method use simple file like in sdcard
     *
     * @param context
     * @param pathInAsset
     * @throws IOException
     */
    public void playAudio(String file) {
        play(file, false);
    }

    /**
     * This method use asset folder
     *
     * @param context
     * @param pathInAsset
     * @throws IOException
     */
    public void playAudio(Context context, String pathInAsset) throws IOException {
        AssetFileDescriptor ff = context.getAssets().openFd(pathInAsset);
        mMediaPlayer.reset();
        play(ff, true);
    }

    public void playUrl(String url) {
        try {
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            Log.v("AUDIOHTTPPLAYER", e.getMessage());
        }
    }


    private void play(Object file, boolean isAsset) {
        prepareResourse(file, isAsset);
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e("MEDIA", " prepare exception =" + e.toString());
            if (mMediaPlayer != null)
                mMediaPlayer.reset();
        } catch (IllegalStateException e) {
            Log.e("MEDIA", " prepare Illegal state=" + e.toString());
            if (mMediaPlayer != null)
                mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.start();
            VolumeHelper.setVolume(VolumeHelper.getMax());
        } catch (IllegalStateException e) {
            Log.e("MEDIA", "start illegal =" + e.toString());
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                VolumeHelper.restoreVolume();
            }
        }
        mMediaPlayer
                .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.i("MEDIA", "completion ");
                        mp.reset();
                        VolumeHelper.restoreVolume();
                    }
                });
    }


    //http://stackoverflow.com/questions/3289038/play-audio-file-from-the-assets-directory
    private void prepareResourse(Object file, boolean isAsset) {
        try {
            if (isAsset) {
                AssetFileDescriptor d = (AssetFileDescriptor) file;
                mMediaPlayer.setDataSource(d.getFileDescriptor(), d.getStartOffset(), d.getLength());
            } else {
                String f = (String) file;
                if (f.contains("http")) {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setDataSource(f);
                } else {
                    mMediaPlayer.setDataSource(f);
                }
            }
        } catch (IllegalArgumentException e) {
            Log.e("MEDIA", "setdata Illegal Arg=" + e.toString());
        } catch (IllegalStateException e) {
            Log.e("MEDIA", "setdata Illegal State=" + e.toString());
        } catch (IOException e) {
            Log.e("MEDIA", "setdata IO =" + e.toString());
        }
    }


    public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

}
