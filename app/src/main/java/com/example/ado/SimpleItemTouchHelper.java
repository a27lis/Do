//package com.example.ado;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class SimpleItemTouchHelper extends ItemTouchHelper.Callback {
//
//    private final ThingsAdapter mAdapter;
//
//    public SimpleItemTouchHelper(ThingsAdapter adapter) {
//        mAdapter = adapter;
//    }
//
//    @Override
//    public boolean isLongPressDragEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isItemViewSwipeEnabled() {
//        return true;
//    }
//    @Override
//    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//        return makeMovementFlags(dragFlags, swipeFlags);
//    }
//
//    @Override
//    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        return true;
//    }
//
//    @Override
//    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//        int position = viewHolder.getAdapterPosition();
//        mAdapter.onItemDismiss(position);
//    }
//
//}
