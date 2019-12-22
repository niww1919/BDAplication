package geekbrains.ru.bdaplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "notes";
    public static final String TABLE_TITLE = "title";
    public static final String TABLE_DESC = "desc";
    public static final String TABLE_ID = "id";

    public DataHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_TITLE + " TEXT," +
                TABLE_DESC + " TEXT); " );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            String upgradeSrt = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + TABLE_TITLE + " TEXT DEFAULT Title";
            sqLiteDatabase.execSQL(upgradeSrt);
        }

    }
}
