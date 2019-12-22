package geekbrains.ru.bdaplication;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import geekbrains.ru.bdaplication.db.DataReader;
import geekbrains.ru.bdaplication.db.DataSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private final DataReader reader;
    private DataSource dataSource;
    private OnMenuItemClickListener itemMenuClickListener;
//    private EnumMap mItems;//fixme
    private final List<String> mItems = new ArrayList<>();


    //    private Note note;
    public NoteAdapter(DataReader reader) {
        //TODO:add db provider
        this.reader = reader;
//        note = new Note();
//        note.setTitle("TEST");
//        note.setDescription("TEST DESCRIPTION");
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        //TODO:get item by position from db
//        Note note1 = new Note();
//        note1.setTitle("TEST" + position);
//        note1.setDescription("TEST DESCRIPTION");
        holder.bind(reader.getPosition(position));
    }

    @Override
    public int getItemCount() {
        //TODO:get count from db
//        return 10;
        return reader.getCount();
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.itemMenuClickListener = onMenuItemClickListener;
    }


    public interface OnMenuItemClickListener {
        void onItemEditClick(Note note);

        void onItemDeleteClick(Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleNote;
        private Note note;

        public ViewHolder(View itemView) {
            super(itemView);
            titleNote = itemView.findViewById(R.id.itemText);
            titleNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemMenuClickListener != null) {
                        showPopupMenu(titleNote);
                    }
                }
            });
        }

        public void bind(Note note) {
            this.note = note;
            titleNote.setText(note.getTitle());
        }

        private void showPopupMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.context_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            itemMenuClickListener.onItemEditClick(note);
                            return true;
                        case R.id.menu_delete:
                            itemMenuClickListener.onItemDeleteClick(note);
                            return true;
                    }
                    return false;
                }
            });
            popup.show();
        }
    }
    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);//fixme
//        dataSource.deleteAll();
        notifyItemRemoved(position);
    }

    //adapter

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}

