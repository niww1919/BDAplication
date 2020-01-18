package geekbrains.ru.bdaplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataToDo.class}, version = 1)
public abstract class AppDBRoom extends RoomDatabase {
    public abstract DataDao dataDao();

}
