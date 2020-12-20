package com.example.ado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ThingsActivity extends AppCompatActivity {


    private List<Thing> thingsList;
    private RecyclerView thingsRecyclerView;
    private ThingsAdapter thingsAdapter;

    private Button addThingButton;
    private EditText editText;
    private TextView text;
    private TextView nothingToDo;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference doDatabaseReference;
    private DatabaseReference userDatabaseReference;


    private String type;
    private String userId;
    private String textThing;
    String lastKey;
    int count = 123;



//    private SimpleItemTouchHelper callback;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNoActionBar);
        setContentView(R.layout.activity_things);




        thingsRecyclerView = findViewById(R.id.thingsRecycler);
        addThingButton = findViewById(R.id.floatingActionButton);
        editText = findViewById(R.id.editText);
        text = findViewById(R.id.textUp);
        nothingToDo = findViewById(R.id.nothingToDo);



        thingsList = new ArrayList<>();


        thingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        thingsAdapter = new ThingsAdapter(thingsList, userId, type);
        thingsRecyclerView.setAdapter(thingsAdapter);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(thingsRecyclerView);


        firebaseDatabase = FirebaseDatabase.getInstance();
        doDatabaseReference = firebaseDatabase.getReference().child("do");
        userDatabaseReference = firebaseDatabase.getReference().child("user");



        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        type = intent.getStringExtra("type");

        if (type.equals("1")) {
            text.setText("Important, urgent");
        }
        if (type.equals("2")) {
            text.setText("Important, not urgent");
        }
        if (type.equals("3")) {
            text.setText("Not important, not urgent");
        }
        if (type.equals("4")) {
            text.setText("Not important, urgent");
        }



        addThingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textThing = editText.getText().toString();
                editText.setText("");

                if (!textThing.isEmpty()) {
                    /*
                     * Firebase - Send message
                     */
                    Thing thing = new Thing(userId, textThing, type);
                    count += (int)(Math.random() * 60 + 123 * Math.random());
                    thing.setId(textThing + count);
                    doDatabaseReference.child(textThing + count).setValue(thing);

                    count += (int)(Math.random() * 60 + 123 * Math.random());
                    nothingToDo.setVisibility(View.GONE);




                    doDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> keysIn = new ArrayList<>();
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String key = childSnapshot.getKey();
                                keysIn.add(key);
                            }

                            lastKey = keysIn.get(keysIn.size()-1);
                            Log.d("MYTAG", "" + lastKey);

                            /*keys.add(lastKey);
                            Log.d("MYTAG", "" + keys);*/
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    /*Log.d("MYTAG", "" + keys);*/


                }

            }
        });

        doDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Thing thing = dataSnapshot.getValue(Thing.class);

                if((thing.getUser().equals(userId) &&
                        thing.getType().equals(type))) {
                    thingsList.add(thing);
                    thingsRecyclerView.scrollToPosition(thingsList.size() - 1);
                    thingsAdapter.notifyItemInserted(thingsList.size() - 1);

                    if (thingsList.isEmpty()) {
                        nothingToDo.setVisibility(View.VISIBLE);
                    } else {
                        nothingToDo.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        callback = new SimpleItemTouchHelper(thingsAdapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(thingsRecyclerView);



    }


    public void addThing(View view) {

        Thing thing = new Thing();
        thing.setText("test");
        thing.setType(type);
        thing.setUser(userId);


    }


//    public static void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        mItemTouchHelper.startDrag(viewHolder);
//    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            return false;
        }

        

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Thing thing = thingsList.get(position);


            thingsList.remove(viewHolder.getAdapterPosition());
            thingsAdapter.notifyDataSetChanged();

            if (thingsList.isEmpty()) {
                nothingToDo.setVisibility(View.VISIBLE);
            } else {
                nothingToDo.setVisibility(View.GONE);
            }

            doDatabaseReference.child(thing.getId()).removeValue();
            thingsRecyclerView.setBackgroundColor(getColor(R.color.colorPrimaryDark));

            Toast toast = Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        @Override
        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            recyclerView.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            return super.getSwipeDirs(recyclerView, viewHolder);
        }
    };




}
