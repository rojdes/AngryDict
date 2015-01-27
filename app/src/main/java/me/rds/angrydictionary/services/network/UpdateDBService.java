package me.rds.angrydictionary.services.network;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import me.rds.angrydictionary.AppConsts;
import me.rds.angrydictionary.AppIntents;
import me.rds.angrydictionary.AppPrefs;
import me.rds.angrydictionary.communications.https.HttpsClient;
import me.rds.angrydictionary.communications.https.listeners.OnGetContentListener;
import me.rds.angrydictionary.dictionary.db.generators.DaoMaster;
import me.rds.angrydictionary.dictionary.db.generators.DaoSession;
import me.rds.angrydictionary.dictionary.managers.DictionaryManager;
import me.rds.angrydictionary.dictionary.model.DBFileInfo;

/**
 * Created by D1m11n on 22.01.2015.
 */
public final class UpdateDBService extends IntentService{


    private static final String TAG="LOAD_SERVICE";
    private final Intent intentAnswer= new Intent(AppIntents.Action.UPDATE_RESULT);




    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * param name Used to name the worker thread, important only for debugging.
     */
    public UpdateDBService() {
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
       if (intent==null||intent.getAction()==null||!intent.getAction().equals(AppIntents.Action.UPDATE_DICT)) return;
           updateDictionary();
    }

    private void updateDictionary() {
        new HttpsClient().start(AppConsts.LINK_DB_LIST, false,new OnGetContentListener() {
            @Override
            public void onGetContent(String msg, Exception error) {
                if (msg==null){
                  intentAnswer.putExtra(AppIntents.Extra.SERVER_RESPONSE, "ERROR ON UPDATE " + error.toString());
                  LocalBroadcastManager.getInstance(UpdateDBService.this).sendBroadcast(intentAnswer);
                }
                else try {
                    updateDBs(msg);
                }catch (Exception e) {
                    Log.e("DB_UPDATE", e.toString());
                    intentAnswer.putExtra(AppIntents.Extra.SERVER_RESPONSE, "ERROR ON UPDATE " + e.toString());
                    LocalBroadcastManager.getInstance(UpdateDBService.this).sendBroadcast(intentAnswer);
                }
            }
        });
    }

    private void updateDBs(String msg) throws RuntimeException, Exception {
        DBFileInfo.Array arr= new Gson().fromJson(msg, DBFileInfo.Array.class);
        long last= AppPrefs.getLastDictUpdate(UpdateDBService.this);
        for (int i=0; i<arr.files.length; i++){
            if (last>arr.files[i].timestamp)
                continue;
            saveDB(arr.files[i].url);
        }

    }

    private void saveDB(String url) throws RuntimeException, Exception {
        Log.e("UPDATE_DB", "=====SAVE===");
        String path=AppConsts.EXT_FOLDER_DB + url.substring(0, 5);
        File f= new File(path);
        f.createNewFile();
        OutputStream wrt= new  FileOutputStream(f);
        new HttpsClient().start(AppConsts.DROPBOX_HOST + url, wrt);
        wrt.flush();
        wrt.close();
        Log.e("UDAPTE_DB", "1");
        SQLiteDatabase db=SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        DaoMaster m=  new DaoMaster(db,1);
        DaoSession s=m.newSession();
        Log.e("UDAPTE_DB", "3");
        DictionaryManager mm= DictionaryManager.getInstance(UpdateDBService.this);
        Log.e("UDAPTE_DB", "4");
        mm.addNewWords(s.getWordDao().loadAll());
        //mm.addNewUsages(s.getUsageDao().loadAll());
        //mm.addNewMP3s(s.getMp3Dao().loadAll());
        s.clear();
        f.delete();
        mm=null;
    }


//    private void simpleLoad(final String url){
//        new HttpsClient().start(url, false,new OnGetContentListener() {
//            @Override
//            public void onGetContent(String msg, Exception error) {
//
//                intentAnswer.putExtra(AppIntents.Extra.SERVER_RESPONSE, (msg!=null?msg:error.toString()));
//                intentAnswer.putExtra(AppIntents.Extra.LINK, url);
//                LocalBroadcastManager.getInstance(UpdateDBService.this).sendBroadcast(intentAnswer);
//            }
//        });
//    }
}
