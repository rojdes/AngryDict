package me.rds.angrydictionary.dictionary.db.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import me.rds.angrydictionary.dictionary.db.generators.DaoDefaultBehavior;
import me.rds.angrydictionary.dictionary.db.generators.DaoSession;
import me.rds.angrydictionary.dictionary.model.Language;
import me.rds.angrydictionary.dictionary.model.Word;
import me.rds.angrydictionary.dictionary.model.WordType;

public class WordDao extends AbstractDao<Word, Long> {

    /**
     * NEED ADD, OTHERWISE DAOCONFIG ERROR
     */
    public static final String TABLENAME = "word";


    public WordDao(DaoConfig config, DaoSession daoSession) {
        super(config);
    }

    ;

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + " '" + TABLENAME + "' (" +
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                "'word_type' TEXT, " +
                "'primary_lang' TEXT, " +
                "'secondary_lang' TEXT, " +
                "'word' TEXT, " +
                "'translates' TEXT );");

    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'WORD'";
        db.execSQL(sql);
    }

    /**
     * Reads the key from the current position of the given cursor, or returns null if there's no single-value key.
     */
    @Override
    protected Long readKey(Cursor cursor, int offset) {
        //return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
        return (Long) DaoDefaultBehavior.readKey(cursor, offset);
    }

    @Override
    protected Long getKey(Word entity) {
        return entity.id;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    //Reads the values from the current position of the given cursor into an existing entity.
    @Override
    protected void readEntity(Cursor cursor, Word entity, int offset) {
        entity.id = cursor.getLong(offset + 0);
        entity.wordType = WordType.getFrom(cursor.getString(offset + 1).charAt(0));
        entity.primaryLang = Language.getFrom(cursor.getString(offset + 2).charAt(0));
        entity.secondaryLang = Language.getFrom(cursor.getString(offset + 3).charAt(0));
        entity.word = cursor.getString(offset + 4);
        entity.setTranslatesTakeFromFromDB(cursor.getString(offset + 5));
    }

    //Reads the values from the current position of the given cursor and create new entity.
    @Override
    protected Word readEntity(Cursor cursor, int offset) {
        Word entity = new Word();
        entity.id = cursor.getLong(offset + 0);
        entity.wordType = WordType.getFrom(cursor.getString(offset + 1).charAt(0));
        entity.primaryLang = Language.getFrom(cursor.getString(offset + 2).charAt(0));
        entity.secondaryLang = Language.getFrom(cursor.getString(offset + 3).charAt(0));
        entity.word = cursor.getString(offset + 4);
        entity.setTranslatesTakeFromFromDB(cursor.getString(offset + 5));
        return entity;
    }

    //Binds the entity's values to the statement. Make sure to synchronize the statement outside of the method.
    @Override
    protected void bindValues(SQLiteStatement stmt, Word entity) {
        stmt.clearBindings();
        stmt.bindString(2, String.valueOf(entity.wordType.getKey()));
        stmt.bindString(3, String.valueOf(entity.primaryLang.getKey()));
        stmt.bindString(4, String.valueOf(entity.secondaryLang.getKey()));
        stmt.bindString(5, entity.word);
        stmt.bindString(6, entity.getTranslatesAsString());
    }

    /**
     * Updates the entity's key if possible (only for Long PKs currently). This method must always return the entity's
     * key regardless of whether the key existed before or not.
     *
     * @param entity
     * @param rowId
     */
    @Override
    protected Long updateKeyAfterInsert(Word entity, long rowId) {
        entity.id = rowId;
        return entity.id;
    }

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");

        public final static Property WordType = new Property(1, String.class, "wordType", false, "word_type");
        public final static Property PrimaryLang = new Property(2, String.class, "primaryLang", false, "primary_lang");
        public final static Property SecondaryLang = new Property(3, String.class, "secondaryLang", false, "secondary_lang");
        public final static Property Word = new Property(4, String.class, "word", false, "word");
        public final static Property Translates = new Property(5, String.class, "translates", false, "translates");

    }
}
