package geekbrains.ru.bdaplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;

import geekbrains.ru.bdaplication.db.DataSource;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter adapter;
    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO:init db
        dataSource = new DataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.note_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //todo
        adapter = new NoteAdapter(dataSource.getReader());
        adapter.setOnMenuItemClickListener(new NoteAdapter.OnMenuItemClickListener() {
            @Override
            public void onItemEditClick(Note note) {
                editElement(note);
            }

            @Override
            public void onItemDeleteClick(Note note) {
                deleteElement(note);
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addElement();
                return true;
            case R.id.menu_clear:
                clearList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearList() {
        dataSource.deleteAll();
        refreshData();
    }

    private void refreshData() {
        dataSource.getReader().refresh();
        adapter.notifyDataSetChanged();
    }

    private void addElement() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertView = factory.inflate(R.layout.add_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alertView);
        builder.setTitle(R.string.alert_title_add);
        builder.setNegativeButton(R.string.alert_cancel, null);
        builder.setPositiveButton(R.string.menu_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dataSource.add(
                        ((TextView)alertView.findViewById(R.id.editTextNoteTitle)).getText().toString(),
                        ((TextView)alertView.findViewById(R.id.editTextNote)).getText().toString()
                );
                refreshData();
            }
        });
        builder.show();
    }

    private void editElement(Note note) {
        dataSource.edit( note, "edit title", "edit desc");
        refreshData();
    }

    private void deleteElement(Note note) {
        dataSource.delete(note);
        refreshData();
    }
}

