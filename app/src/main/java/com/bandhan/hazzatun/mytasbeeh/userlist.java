package com.bandhan.hazzatun.mytasbeeh;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class userlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<User> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);


        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        if (getIntent().hasExtra("lang_code")){
            String lang_code = getIntent().getStringExtra("lang_code");
            Settings.setLocale(this, lang_code);
        }


        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("MyDigitalCounter");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                myAdapter = new MyAdapter(userlist.this,list);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}