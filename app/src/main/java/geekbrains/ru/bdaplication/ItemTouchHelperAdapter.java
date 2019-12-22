package geekbrains.ru.bdaplication;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
