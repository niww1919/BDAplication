package geekbrains.ru.bdaplication.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM DataToDo")
    List<DataToDo> getAll();

    @Query("SELECT * FROM DataToDo WHERE id = :id")
    DataToDo getById(int id);

    @Insert
    void insert(DataToDo dataToDO);

    @Update
    void update(DataToDo dataToDO);

    @Delete
    void delete(DataToDo dataToDO);

}
