package geekbrains.ru.bdaplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;

import geekbrains.ru.bdaplication.Note;

public class DataSource implements Closeable {
    private final DataHelper dbHelper;
    private SQLiteDatabase database;
    private DataReader reader;

    public DataSource(Context context) {
        dbHelper = new DataHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        reader = new DataReader(database);
        reader.open();

    }

    @Override
    public void close() throws IOException {
        database.close();
        reader.close();

    }

    public Note add(String title, String desc) {
        Note note = new Note();
        ContentValues values = new ContentValues();
        values.put(DataHelper.TABLE_TITLE,title);
        values.put(DataHelper.TABLE_DESC, desc);
        long id = database.insert(DataHelper.TABLE_NAME, null, values);
        note.setId(id);
        note.setTitle(title);
        note.setDescription(desc);
        return note;
    }

    public void edit(Note note, String title, String desc) {
        ContentValues values = new ContentValues();
        values.put(DataHelper.TABLE_TITLE,title);
        values.put(DataHelper.TABLE_DESC, desc);
        values.put(DataHelper.TABLE_ID, note.getId());
        database.update(DataHelper.TABLE_NAME, values,
                DataHelper.TABLE_ID + "=" + note.getId(), null);

    }

    public void delete(Note note) {
        database.delete(DataHelper.TABLE_NAME,
                DataHelper.TABLE_ID + "=" + note.getId(), null);
    }

    public void deleteAll() {
        database.delete(DataHelper.TABLE_NAME, null, null);
    }

    public DataReader getReader() {
        return reader;
    }
}
