package me.rds.angrydictionary.dictionary.db.generators;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import me.rds.angrydictionary.dictionary.db.model.MP3Dao;
import me.rds.angrydictionary.dictionary.db.model.UsageDao;
import me.rds.angrydictionary.dictionary.db.model.WordDao;




/*
DaoMaster: The entry point for using greenDAO.
DaoMaster holds the database object (SQLiteDatabase) and manages DAO classes (not objects) for a specific schema.
It has static methods to create the tables or drop them.
Its inner classes OpenHelper and DevOpenHelper are SQLiteOpenHelper implementations that create the schema in the SQLite database.

 */


public class DaoMaster extends AbstractDaoMaster {

    private static final int DB_VERSION = 2;

    public DaoMaster(SQLiteDatabase db, int dbVersion) {
        super(db, dbVersion);
        registerDaoClass(WordDao.class);
        registerDaoClass(MP3Dao.class);
        registerDaoClass(UsageDao.class);
    }

    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        WordDao.createTable(db, ifNotExists);
        UsageDao.createTable(db, ifNotExists);
        MP3Dao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        WordDao.dropTable(db, ifExists);
        UsageDao.dropTable(db, ifExists);
        MP3Dao.dropTable(db, ifExists);
    }

    /**
     * You can add version of db to manifest in "meta-data" with name "db_version"
     * <p/>
     * <b><"meta-data  android:name="db_version"   android:value="1" / ></b>
     *
     * @param context
     * @return 1 if default or number  from manifest
     */
    public static int getDbVersionFromManifest(Context context) {
        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            Log.e("BUNDLE", "VERSION IS  " + bundle.getInt("db_version", 1));
            return bundle.getInt("db_version", 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }

    }

//    public DaoMaster(SQLiteDatabase db, Context context) {
//        super(db, DB_VERSION);
//        registerDaoClass(WordDao.class);
//        registerDaoClass(MP3Dao.class);
//        registerDaoClass(UsageDao.class);
//    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    private static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createAllTables(db, false);
        }
    }

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            dropAllTables(db, true);
            onCreate(db);
        }
    }


}
