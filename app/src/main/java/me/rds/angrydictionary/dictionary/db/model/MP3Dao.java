package me.rds.angrydictionary.dictionary.db.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import me.rds.angrydictionary.dictionary.db.generators.DaoDefaultBehavior;
import me.rds.angrydictionary.dictionary.db.generators.DaoSession;
import me.rds.angrydictionary.dictionary.model.MP3Phrase;

public class MP3Dao extends AbstractDao<MP3Phrase, Long> {

    public static final String TABLENAME = "mp3";


    public MP3Dao(DaoConfig config, DaoSession daoSession) {
        super(config);
    }

    ;

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + " '" + TABLENAME + "' (" +
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "'word' TEXT, " +
                "'mp3' TEXT );");

    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MP3'";
        db.execSQL(sql);
    }

    @Override
    protected MP3Phrase readEntity(Cursor cursor, int offset) {
        MP3Phrase entity = new MP3Phrase();
        entity.id = cursor.getLong(offset + 0);
        entity.wordOrSentences = cursor.getString(offset + 1);
        entity.mp3 = cursor.getString(offset + 2);
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return (Long) DaoDefaultBehavior.readKey(cursor, offset);
    }

    @Override
    protected void readEntity(Cursor cursor, MP3Phrase entity, int offset) {
        entity.id = cursor.getLong(offset + 0);
        entity.wordOrSentences = cursor.getString(offset + 1);
        entity.mp3 = cursor.getString(offset + 2);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, MP3Phrase entity) {
        stmt.clearBindings();
        stmt.bindString(2, entity.wordOrSentences);
        stmt.bindString(3, entity.mp3);
    }

    @Override
    protected Long updateKeyAfterInsert(MP3Phrase entity, long rowId) {
        entity.id = rowId;
        return entity.id;
    }

    @Override
    protected Long getKey(MP3Phrase entity) {
        return entity.id;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Word = new Property(1, String.class, "wordOrSentences", false, "word");
        public final static Property MP3 = new Property(2, String.class, "mp3", false, "mp3");
    }
}
