package com.bandhan.hazzatun.mytasbeeh;

import com.bandhan.hazzatun.mytasbeeh.khatam_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class KhotomAccount extends AppCompatActivity {
    String k_count_name="", myCount="0", tCount="0";
    FirebaseUser user;
    String email="", target="", count_name="", date="", email1="";
    DatabaseReference database;
    khatam_adapter kAdapter;
    ArrayList<KhatamUser> list_khatam;
    EditText email2, et_k_name, targt_et;
    RecyclerView recyclerView;
    FirebaseAuth auth;
   // String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khotom_account);
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("MyDigitalCounter");
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        email1=user.getEmail();
        list_khatam=new ArrayList<KhatamUser>();
        email2=findViewById(R.id.edit_text);
        et_k_name=findViewById(R.id.edit_text1);
        targt_et=findViewById(R.id.edit_text2);
        if (getIntent().hasExtra("cName") && getIntent().hasExtra("target")){
            k_count_name=getIntent().getStringExtra("cName");
            et_k_name.setText(k_count_name);
            target=getIntent().getStringExtra("target");
            targt_et.setText(target);
       }
        recyclerView = findViewById(R.id.khatamList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        database.child("Group").orderByKey().addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_khatam.clear();
                for (DataSnapshot booksSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot bSnapshot : booksSnapshot.getChildren()) {
                        if (bSnapshot.exists()) {
                            KhatamUser us = bSnapshot.getValue(KhatamUser.class);
                            list_khatam.add(us);
                        }
                        kAdapter = new khatam_adapter(KhotomAccount.this, list_khatam);
                        recyclerView.setAdapter(kAdapter);
                        kAdapter.notifyDataSetChanged();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void writeNewGroup() {

        count_name=et_k_name.getText().toString();
        target=targt_et.getText().toString();
        email=email1;
        date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        KhatamUser helperClass = new KhatamUser(email, target, count_name, date, myCount, tCount);
        database.child("Group").child(String.valueOf(helperClass.getK_count_name())).push().setValue(helperClass);
    }
    public void add_k_name(View view) {

        writeNewGroup();

    }


    public void go_main(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    public void writeNewMember() {
        count_name=et_k_name.getText().toString();
        email=email2.getText().toString();
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            Toast.makeText(KhotomAccount.this, "invalid email", Toast.LENGTH_SHORT).show();
                        } else {
                            date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            KhatamUser helperClass = new KhatamUser(email, target, count_name, date, myCount, tCount);
                            database.child("Group").child(helperClass.getK_count_name()).push().setValue(helperClass);
                        }

                    }
                });
    }
    public void addItemToList(View view) {
        writeNewMember();
    }

    public void goGroup(View view) {
        Intent i = new Intent(this, khatam_list.class);
        i.putExtra("name",et_k_name.getText().toString());
        i.putExtra("tgt",targt_et.getText().toString());
        startActivity(i);
        this.finish();
    }
}
