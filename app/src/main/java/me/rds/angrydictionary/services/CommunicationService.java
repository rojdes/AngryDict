package me.rds.angrydictionary.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.rds.angrydictionary.AppConsts;
import me.rds.angrydictionary.AppIntents;
import me.rds.angrydictionary.AppPrefs;
import me.rds.angrydictionary.communications.https.HttpsClient;
import me.rds.angrydictionary.communications.https.listeners.OnGetContentListener;
import me.rds.angrydictionary.dictionary.model.DBFileInfo;

/**
 * Created by D1m11n on 22.01.2015.
 */
public final class CommunicationService extends IntentService{


    private static final String TAG="LOAD_SERVICE";
    private final Intent intentAnswer= new Intent(AppIntents.Action.LOAD_ANSWER);


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * param name Used to name the worker thread, important only for debugging.
     */
    public CommunicationService() {
        super(TAG);
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               android.content.Context#startService(android.content.Intent)}.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
       if (intent==null||intent.getAction()==null||!intent.getAction().equals(AppIntents.Action.LOAD)) return;
       if (intent.hasExtra(AppIntents.Extra.UPDATE_DICT)){
           updateDictionary();
       }else if (intent.hasExtra(AppIntents.Extra.LINK))
           simpleLoad(intent.getStringExtra(AppIntents.Extra.LINK));
    }

    private void updateDictionary() {
        new HttpsClient().start(AppConsts.LINK_DB_LIST, false,new OnGetContentListener() {
            @Override
            public void onGetContent(String msg, Exception error) {
                if (msg==null){
                  intentAnswer.putExtra(AppIntents.Extra.SERVER_RESPONSE, "ERROR ON UPDATE " + error.toString());
                  LocalBroadcastManager.getInstance(CommunicationService.this).sendBroadcast(intentAnswer);
                }
                else try {
                    updateDBs(msg);
                }catch (Exception e) {
                    intentAnswer.putExtra(AppIntents.Extra.SERVER_RESPONSE, "ERROR ON UPDATE " + e.toString());
                    LocalBroadcastManager.getInstance(CommunicationService.this).sendBroadcast(intentAnswer);
                }
            }
        });
    }

    private void updateDBs(String msg) throws Exception {
        DBFileInfo.Array arr= new Gson().fromJson(msg, DBFileInfo.Array.class);
        long last= AppPrefs.getLastDictUpdate(CommunicationService.this);
        for (int i=0; i<arr.files.length; i++){
            if (last>arr.files[i].timestamp)
                continue;
            saveDB(arr.files[i].url);
        }

    }

    private void saveDB(String url) throws Exception {
        File f= new File(AppConsts.EXT_FOLDER_DB + url.substring(0, 5));
        f.createNewFile();
        OutputStream wrt= new  FileOutputStream(f);
        new HttpsClient().start(AppConsts.DROPBOX_HOST + url, wrt);
    }


    private void simpleLoad(final String url){
        new HttpsClient().start(url, false,new OnGetContentListener() {
            @Override
            public void onGetContent(String msg, Exception error) {

                intentAnswer.putExtra(AppIntents.Extra.SERVER_RESPONSE, (msg!=null?msg:error.toString()));
                intentAnswer.putExtra(AppIntents.Extra.LINK, url);
                LocalBroadcastManager.getInstance(CommunicationService.this).sendBroadcast(intentAnswer);
            }
        });
    }
}
