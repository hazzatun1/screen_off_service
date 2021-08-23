package com.bandhan.hazzatun.mytasbeeh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
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
    String email1 ="", providerId="", cname="";
LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
layout=findViewById(R.id.ul_back);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                 providerId = profile.getProviderId();
                String uid = profile.getUid();
                String acc_name = profile.getDisplayName();
                 email1 = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }

           SharedPreferences set_back = getSharedPreferences("set_back", Context.MODE_PRIVATE);
            String back = set_back.getString("pic_name", "");
            if (back != null && !back.equals("")) {

                switch (back) {
                    case "bk":
                        layout.setBackgroundResource(R.drawable.bk);
                        break;
                    case "pic_1":
                        layout.setBackgroundResource(R.drawable.pic_1);
                        break;
                    case "pic_2":
                        layout.setBackgroundResource(R.drawable.pic_2);
                        break;
                    default:
                        break;
                }

            }


        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("MyDigitalCounter");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        User us=new User(cname);


            database.child(user.getUid()).child(us.get_name()).addValueEventListener(new ValueEventListener() {
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
                            // Don't exist!
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