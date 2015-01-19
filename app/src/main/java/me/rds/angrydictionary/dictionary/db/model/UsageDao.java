package me.rds.angrydictionary.dictionary.db.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import me.rds.angrydictionary.dictionary.db.generators.DaoDefaultBehavior;
import me.rds.angrydictionary.dictionary.db.generators.DaoSession;
import me.rds.angrydictionary.dictionary.model.PhraseUsage;

public class UsageDao extends AbstractDao<PhraseUsage, Long> {


    public static final String TABLENAME = "usage";

    public UsageDao(DaoConfig config, DaoSession daoSession) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + " '" + TABLENAME + "' (" +
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'word' TEXT, " +
                "'verb_forms' TEXT, " +
                "'example' TEXT, " +
                "'description' TEXT);");

    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USAGE'";
        db.execSQL(sql);
    }

    @Override
    protected PhraseUsage readEntity(Cursor cursor, int offset) {
        PhraseUsage entity = new PhraseUsage();
        entity.id = cursor.getLong(offset + 0);
        entity.wordOrSentences = cursor.getString(offset + 1);
        entity.setVerbFormsFromDB(cursor.getString(offset + 2));
        entity.example = cursor.getString(offset + 3);
        entity.description = cursor.getString(offset + 4);
        return entity;
    }

    @Override
    protected Long readKey(Cursor cursor, int offset) {
        return (Long) DaoDefaultBehavior.readKey(cursor, offset);
    }

    @Override
    protected void readEntity(Cursor cursor, PhraseUsage entity, int offset) {
        entity.id = cursor.getLong(offset + 0);
        entity.wordOrSentences = cursor.getString(offset + 1);
        entity.setVerbFormsFromDB(cursor.getString(offset + 2));
        entity.example = cursor.getString(offset + 3);
        entity.description = cursor.getString(offset + 4);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, PhraseUsage entity) {
        stmt.clearBindings();
        stmt.bindString(2, entity.wordOrSentences);
        stmt.bindString(3, entity.getVerbFormsAsString());
        stmt.bindString(4, entity.example);
        stmt.bindString(5, entity.description);
    }

    @Override
    protected Long updateKeyAfterInsert(PhraseUsage entity, long rowId) {
        entity.id = rowId;
        return entity.id;
    }

    @Override
    protected Long getKey(PhraseUsage entity) {
        return entity.id;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Word = new Property(1, String.class, "wordOrSentences", false, "word");
        public final static Property VerbForms = new Property(2, String.class, "verbForms", false, "verb_forms");
        public final static Property Example = new Property(3, String.class, "example", false, "example");
        public final static Property Description = new Property(4, String.class, "description", false, "description");
    }
}
