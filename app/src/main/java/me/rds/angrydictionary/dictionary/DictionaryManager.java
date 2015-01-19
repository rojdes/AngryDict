package me.rds.angrydictionary.dictionary;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

import de.greenrobot.dao.query.Query;
import me.rds.angrydictionary.dictionary.db.generators.DaoMaster;
import me.rds.angrydictionary.dictionary.db.generators.DaoSession;
import me.rds.angrydictionary.dictionary.db.model.WordDao;
import me.rds.angrydictionary.dictionary.model.Language;
import me.rds.angrydictionary.dictionary.model.TrueWord;
import me.rds.angrydictionary.dictionary.model.Word;

public class DictionaryManager {


    private static final int INT = 4;

    private static final String TAG = "DICT_MANAGER";
    private static final String DB_NAME = "dictionary";
    private static ArrayList<Word> mEngList = new ArrayList<Word>();
    static {
        mEngList.add(new Word(Language.ENG, Language.UKR, "introduce", new String[]{"включати", "вводити", "знайомити", "представляти"}));
        mEngList.add(new Word(Language.ENG, Language.UKR, "estimate", new String[]{"оцінка", "кошторис", "зацінювати", "облікувати"}));
        mEngList.add(new Word(Language.ENG, Language.UKR, "affair", new String[]{"справа", "діло", "пригода", "роман", "сутичка"}));
        mEngList.add(new Word(Language.ENG, Language.UKR, "development", new String[]{"розвиток", "еволюція", "зростання", "результат", "виклад"}));
        mEngList.add(new Word(Language.ENG, Language.UKR, "identity", new String[]{"особистість", "ідентичність", "індивідуальність", "справжність", "тотожність"}));
        Word w = new Word(Language.ENG, Language.UKR, "offer", new String[]{"пропонувати", "траплятись", "попозиція", "пробувати", "висувати"});
        mEngList.add(w);

        //convincing  ������������
        //responsive
        //mention
        //collision
        //option
        //experience
    }
    private static DictionaryManager mInstance;

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context mContext;

    private DictionaryManager(Context context) {
        mContext = context;
        mDaoMaster = new DaoMaster(new DaoMaster.DevOpenHelper(mContext, DB_NAME, null).getWritableDatabase(), DaoMaster.getDbVersionFromManifest(context));
        Log.e(TAG, "VERSION OF DB IS =  " + mDaoMaster.getDatabase().getVersion());
        mDaoSession = mDaoMaster.newSession();
    }

    public static DictionaryManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new DictionaryManager(context);
        return mInstance;
    }

    public void addWord(Word w) {
        mDaoSession.getWordDao().insertOrReplace(w);
    }

    public Word getWord(int id) {
        Query query = mDaoSession.getWordDao().queryBuilder().where(WordDao.Properties.Id.eq(id)).build();
        return (Word) query.list().get(0);
    }

    public Word getWord(String word) {
        Query query = mDaoSession.getWordDao().queryBuilder().where(WordDao.Properties.Word.eq(word)).build();
        return (Word) query.list().get(0);
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

    private Integer[] getRandomChain(int max, int size) {
        LinkedHashSet<Integer> chain = new LinkedHashSet<Integer>(size);
        do {
            chain.add(new Random().nextInt(max));
        } while (chain.size() < size);
        return chain.toArray(new Integer[size]);
    }


}
