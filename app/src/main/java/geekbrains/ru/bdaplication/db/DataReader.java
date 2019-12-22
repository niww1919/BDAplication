package geekbrains.ru.bdaplication.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;

import geekbrains.ru.bdaplication.Note;

public class DataReader implements Closeable {
    private final SQLiteDatabase database;
    private Cursor cursor;
    private String[] allColumn = {
            DataHelper.TABLE_ID,//0
            DataHelper.TABLE_TITLE,//1
            DataHelper.TABLE_DESC//2
    };

    public DataReader(SQLiteDatabase database) {
        this.database = database;
    }

    public void open() {
        query();
        cursor.moveToFirst();
    }

    private void query() {
        cursor = database.query(DataHelper.TABLE_NAME, allColumn,
                null, null, null, null,null);
    }

    public void refresh() {
        int pos = cursor.getPosition();
        query();
        cursor.moveToPosition(pos);
    }

    @Override
    public void close() throws IOException {
        cursor.close();

    }

    private Note cursorToNote() {
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setDescription(cursor.getString(2));
        return note;
    }

    public Note getPosition(int position) {
        cursor.moveToPosition(position);
        return cursorToNote();
    }

    public int getCount() {
        return cursor.getCount();
    }
}
