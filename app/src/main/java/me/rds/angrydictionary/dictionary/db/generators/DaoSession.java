package me.rds.angrydictionary.dictionary.db.generators;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import me.rds.angrydictionary.dictionary.db.model.MP3Dao;
import me.rds.angrydictionary.dictionary.db.model.UsageDao;
import me.rds.angrydictionary.dictionary.db.model.WordDao;
import me.rds.angrydictionary.dictionary.model.MP3Phrase;
import me.rds.angrydictionary.dictionary.model.PhraseUsage;
import me.rds.angrydictionary.dictionary.model.Word;

public class DaoSession extends AbstractDaoSession {

    private final DaoConfig wordDaoConfig;
    private final DaoConfig mp3DaoConfig;
    private final DaoConfig usageDaoConfig;


    private final WordDao wordDao;
    private final MP3Dao mp3Dao;
    private final UsageDao usageDao;
    /*

    Manages all available DAO objects for a specific schema, which you can acquire using one of the getter methods.
    DaoSession provides also some generic persistence methods like insert, load, update, refresh and delete for entities.
    Lastly, a DaoSession objects also keeps track of an identity scope. For more details, have a look at the session documentation.
    Sessions
The (generated) DaoSession class is one of the central interface to greenDAO.
   As a start, DaoSession provides developers access to basic entity operations and DAOs for a more complete set of operations.
   Sessions also mange an identity scope for entities.
     */

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);

        wordDaoConfig = daoConfigMap.get(WordDao.class).clone();
        wordDaoConfig.initIdentityScope(type);
        wordDao = new WordDao(wordDaoConfig, this);

        mp3DaoConfig = daoConfigMap.get(MP3Dao.class).clone();
        mp3DaoConfig.initIdentityScope(type);
        mp3Dao = new MP3Dao(mp3DaoConfig, this);

        usageDaoConfig = daoConfigMap.get(UsageDao.class).clone();
        usageDaoConfig.initIdentityScope(type);
        usageDao = new UsageDao(usageDaoConfig, this);

        registerDao(Word.class, wordDao);
        registerDao(MP3Phrase.class, mp3Dao);
        registerDao(PhraseUsage.class, usageDao);
    }

    public void clear() {

        wordDaoConfig.getIdentityScope().clear();
        mp3DaoConfig.getIdentityScope().clear();
        usageDaoConfig.getIdentityScope().clear();
    }

    public WordDao getWordDao() {
        return wordDao;
    }

    public MP3Dao getMp3Dao() {
        return mp3Dao;
    }

    public UsageDao getUsageDao() {
        return usageDao;
    }

}
