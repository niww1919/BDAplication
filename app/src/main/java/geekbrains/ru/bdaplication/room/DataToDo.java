/***https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/529-urok-5-room-osnovy.html
 * */

package geekbrains.ru.bdaplication.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataToDo {

    @PrimaryKey
    int id;
    String note;
}
