package me.rds.angrydictionary.dictionary.managers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import common.utils.ArraysUtils;
import common.utils.ListsUtils;
import de.greenrobot.dao.query.Query;
import me.rds.angrydictionary.AppConsts;
import me.rds.angrydictionary.communications.https.HttpsClient;
import me.rds.angrydictionary.communications.https.listeners.OnGetContentListener;
import me.rds.angrydictionary.dictionary.db.generators.DaoMaster;
import me.rds.angrydictionary.dictionary.db.generators.DaoSession;
import me.rds.angrydictionary.dictionary.db.model.WordDao;
import me.rds.angrydictionary.dictionary.listeners.DictionaryActionsCallBack;
import me.rds.angrydictionary.dictionary.model.MP3Phrase;
import me.rds.angrydictionary.dictionary.model.DBFileInfo;
import me.rds.angrydictionary.dictionary.model.Language;
import me.rds.angrydictionary.dictionary.model.PhraseUsage;
import me.rds.angrydictionary.dictionary.model.TrueWord;
import me.rds.angrydictionary.dictionary.model.Word;

public class DictionaryManager {


    private static final int INT = 4;

    private static final String TAG = "DICT_MANAGER";
    private static final String DB_NAME = "dictionary";
    private static ArrayList<Word> mEngList = new ArrayList<Word>();

    private static ArrayList<String> mWords = new ArrayList<>();




//    static {
//
//        //convincing  ������������
//        //responsive
//        //mention
//        //collision
//        //option
//        //experience
//    }
    private static DictionaryManager mInstance;

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context mContext;

    private DictionaryManager(Context context) {
        mContext = context.getApplicationContext();
        mDaoMaster = new DaoMaster(new DaoMaster.DevOpenHelper(mContext, DB_NAME, null).getWritableDatabase(), DaoMaster.getDbVersionFromManifest(context));
        Log.e(TAG, "VERSION OF DB IS =  " + mDaoMaster.getDatabase().getVersion());
        mDaoSession = mDaoMaster.newSession();
    }

    public static DictionaryManager getInstance(Context context) {
        if (mInstance == null){
            synchronized (DictionaryManager.class){
                if (mInstance == null)
                    mInstance = new DictionaryManager(context);
            }
        }
        return mInstance;
    }


    public void addNewUsages(PhraseUsage ... ph) {
        if (ArraysUtils.isEmpty(ph)) return;
        mDaoSession.getUsageDao().insertOrReplaceInTx(ph);
    }

    public void addNewMP3s(MP3Phrase... mp3) {
        if (ArraysUtils.isEmpty(mp3)) return;
        updateListIfNeeded();
        mDaoSession.getMp3Dao().insertOrReplaceInTx(mp3);
    }

    public void addNewWords(List<Word>  w) {
        if (ListsUtils.isEmpty(w)) return;
        updateListIfNeeded();
        for (int i=0;i<w.size(); i++)
            if (!mWords.contains(w.get(i).word))
               addUniqueWord(w.get(i));
    }

    private void addUniqueWord(Word w){
        mEngList.add(w);
        mWords.add(w.word);
        mDaoSession.getWordDao().insertOrReplaceInTx(w);
    }

    public List<Word> getAvailableList(){
        updateListIfNeeded();
        return mEngList;
    }

    public void addNewUsages(List<PhraseUsage>  ph) {
        if (ListsUtils.isEmpty(ph)) return;
        mDaoSession.getUsageDao().insertOrReplaceInTx(ph);
    }

    public void addNewMP3s(List<MP3Phrase>  mp3) {
        if (ListsUtils.isEmpty(mp3)) return;
        mDaoSession.getMp3Dao().insertOrReplaceInTx(mp3 );
    }


    public Word getWord(int id) {
        Query query = mDaoSession.getWordDao().queryBuilder().where(WordDao.Properties.Id.eq(id)).build();
        return (Word) query.list().get(0);
    }

    public Word getWord(String word) {
        Query query = mDaoSession.getWordDao().queryBuilder().where(WordDao.Properties.Word.eq(word)).build();
        return (Word) query.list().get(0);
    }

    public String getMP3For(String word) {
        Query query = mDaoSession.getMp3Dao().queryBuilder().where(WordDao.Properties.Word.eq(word)).build();
        List<MP3Phrase> list=query.list();
        return list.size()>0?list.get(0).mp3:"";
    }


    public TrueWord getWord(Language from) {
        switch (from) {
            case ENG:
                return getRandomEngWord();
            default:
                return null;
        }
    }

    private TrueWord getRandomEngWord() {
        updateListIfNeeded();
        TrueWord word = new TrueWord();
        Integer[] chain = getRandomChain(mEngList.size(), INT);
        word.trueWordNumber = new Random().nextInt(INT);
        Word w = mEngList.get(chain[word.trueWordNumber]);
        word.word = w.word;
        word.translates = new String[4];
        word.translates = new String[4];
        for (int i = 0; i < INT; i++) {
            if (word.trueWordNumber != i) {
                String[] ww = mEngList.get(chain[i]).translates;
                word.translates[i] = ww[new Random().nextInt(ww.length)];
            } else
                word.translates[i] = w.translates[new Random().nextInt(w.translates.length)];
        }
        return word;
    }


    private void clearList(){
        mEngList.clear();
        mEngList=null;
        mWords.clear();
        mWords=null;
    }

    private void updateListIfNeeded() {
        if(!ListsUtils.isEmpty(mEngList)&&!ListsUtils.isEmpty(mWords)) return;
        mEngList=new ArrayList<Word>(mDaoSession.getWordDao().loadAll());
        mWords= new ArrayList<String>();
        for (int i=0; i<mEngList.size(); i++)
            mWords.add(mEngList.get(i).word);
    }

    private Integer[] getRandomChain(int max, int size) {
        LinkedHashSet<Integer> chain = new LinkedHashSet<Integer>(size);
        do {
            chain.add(new Random().nextInt(max));
        } while (chain.size() < size);
        return chain.toArray(new Integer[size]);
    }
}
