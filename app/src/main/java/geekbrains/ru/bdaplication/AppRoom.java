package geekbrains.ru.bdaplication;

import android.app.Application;

import androidx.room.Room;

import geekbrains.ru.bdaplication.room.AppDBRoom;

public class AppRoom extends Application {

    public static AppRoom instance;

    private AppDBRoom database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDBRoom.class, "database")
                .allowMainThreadQueries() //fixme work in UI thread
                .build();
    }

    public static AppRoom getInstance() {
        return instance;
    }

    public AppDBRoom getDatabase() {
        return database;
    }
}
