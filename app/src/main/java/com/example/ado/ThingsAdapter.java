package com.example.ado;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

class ThingsAdapter extends RecyclerView.Adapter<ThingsAdapter.ViewHolder>  {

    private List<Thing> athingList;
    private String auserId;
    private String atype;
    private TextView textView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference doDatabaseReference;


    ThingsAdapter(List<Thing> thingList, String userId, String type) {
        athingList = thingList;
        auserId = userId;
        atype = type;
    }

    @NonNull
    @Override
    public ThingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ThingsAdapter.ViewHolder holder, int position) {

            Thing thing = athingList.get(position);
            holder.mTextView.setText(thing.getText());
            holder.itemView.setLongClickable(true);

//            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
//                @SuppressLint("ClickableViewAccessibility")
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//                        ThingsActivity.onStartDrag(holder);
//                    }
//                    return false;
//                }
//
//            });
//            holder.mTextView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//                        ThingsActivity.onStartDrag(holder);
//                    }
//                    return false;
//                }
//            });


    }

    @Override
    public int getItemCount() {
        return athingList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.doTextView);

            }
        }
    }

//    public void onItemDismiss(int position) {
//        athingList.remove(position);
//        notifyItemRemoved(position);
//
//    }

//    public void onItemMove(int fromPosition, int toPosition) {
//        Thing prev = athingList.remove(fromPosition);
//        athingList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
//        notifyItemMoved(fromPosition, toPosition);
//    }





