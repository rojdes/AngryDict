package me.rds.angrydictionary.dictionary.db.generators;

import android.database.Cursor;

/**
 * Created by D1m11n on 17.01.2015.
 */


/**
 * Class was created from example of Dao to simplify describing classes, that extends AbstractDao
 */
public class DaoDefaultBehavior {

    private DaoDefaultBehavior() {
    }

    public static Object readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

}
