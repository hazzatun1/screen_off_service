package com.bandhan.hazzatun.mytasbeeh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.net.Uri;
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
    String email1 ="", providerId="", name="";

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                 providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();
                // Name, email address, and profile photo Url
                 name = profile.getDisplayName();
                 email1 = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }


        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("MyDigitalCounter");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();



            database.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User value = dataSnapshot.getValue(User.class);
                        assert value != null;
                        String mail = value.get_email();
                       if (snapshot.exists() && mail.equals(email1)) {
                            // Exist! Do whatever.
                           list.add(value);
                           Toast.makeText(userlist.this, "only you!!!", Toast.LENGTH_SHORT).show();

                        } else {
                            // Don't exist! Do something.
                           Toast.makeText(userlist.this, "nothing to show", Toast.LENGTH_SHORT).show();

                        }

                     //   Toast.makeText(userlist.this, "success to show", Toast.LENGTH_SHORT).show();

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