package com.bandhan.hazzatun.mytasbeeh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class userlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<User> list;
String email1 ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);


       // FirebaseApp.initializeApp(this);
      //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        if (getIntent().hasExtra("lang_code")) {
            String lang_code = getIntent().getStringExtra("lang_code");
            Settings.setLocale(this, lang_code);
        }


        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("MyDigitalCounter");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                email1 = profile.getEmail();
            }


            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User value = dataSnapshot.getValue(User.class);
                        String mail = value.get_email();
                        if (mail.equals(email1)) {

                            list.add(value);
                            Toast.makeText(userlist.this, "only you!!!", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(userlist.this, "success to show", Toast.LENGTH_SHORT).show();
                    }
                    myAdapter = new MyAdapter(userlist.this, list);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


    }}
