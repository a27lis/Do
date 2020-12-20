package com.example.ado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DoActivity extends AppCompatActivity {



    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabaseReference;
    private FirebaseAuth auth;

    private String userId;
    private String textThing;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.things_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DoActivity.this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_do);

        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabaseReference = firebaseDatabase.getReference().child("user");

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        if (userId != auth.getCurrentUser().getUid()) {
            Intent intent = new Intent(DoActivity.this, SignInActivity.class);
            startActivity(intent);
        }




    }

    public void goToThings2(View view) {

        Intent intent = new Intent(DoActivity.this, ThingsActivity.class);
        intent.putExtra("type", "2");
        intent.putExtra("userId", userId);
        startActivity(intent);



    }

    public void goToThings1(View view) {
        Intent intent = new Intent(DoActivity.this, ThingsActivity.class);
        intent.putExtra("type", "1");
        intent.putExtra("userId", userId);
        startActivity(intent);

    }


    public void goToThings3(View view) {
        Intent intent = new Intent(DoActivity.this, ThingsActivity.class);
        intent.putExtra("type", "3");
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void goToThings4(View view) {
        Intent intent = new Intent(DoActivity.this, ThingsActivity.class);
        intent.putExtra("type", "4");
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
