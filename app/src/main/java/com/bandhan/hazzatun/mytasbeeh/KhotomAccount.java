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
import com.google.firebase.database.ChildEventListener;
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
import java.util.Objects;


public class KhotomAccount extends AppCompatActivity {
    String k_count_name="", myCount="0", tCount="0";
    FirebaseUser user;
    String email="", target="", count_name="", date="", email1="", mail2="";
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


        database.child("Group").child(count_name).addValueEventListener(new ValueEventListener() {
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



    public void add_k_name(View view) {
        count_name=et_k_name.getText().toString();

        if(!count_name.trim().matches("") &&  !targt_et.getText().toString().trim().matches("")) {
            database.child("Group").child(count_name)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                Toast.makeText(KhotomAccount.this, "this group exists", Toast.LENGTH_SHORT).show();

                            } else {
                                writeNewGroup();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
        }
        else{
            Toast.makeText(KhotomAccount.this, "Group Data is empty", Toast.LENGTH_SHORT).show();
        }

    }

    public void writeNewGroup() {

        count_name=et_k_name.getText().toString();
        target=targt_et.getText().toString();
        email=email1;
        date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        KhatamUser helperClass = new KhatamUser(email, target, count_name, date, myCount, tCount);
        database.child("Group").child(count_name).push().setValue(helperClass);
        Toast.makeText(KhotomAccount.this, "success to insert", Toast.LENGTH_SHORT).show();
    }



    public void addItemToList(View view) {

            count_name = et_k_name.getText().toString();
            mail2 = email2.getText().toString();
        if(!count_name.trim().matches("") && !mail2.trim().matches("")) {
            auth.fetchSignInMethodsForEmail(mail2)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                            boolean isNewUser = Objects.requireNonNull(task.getResult().getSignInMethods()).isEmpty();

                            if (isNewUser) {
                                Toast.makeText(KhotomAccount.this, "unregistered email", Toast.LENGTH_SHORT).show();
                            } else {

                                //Toast.makeText(KhotomAccount.this, "this member existed", Toast.LENGTH_SHORT).show();
                                writenewMember();

                            }
                        }

                    });
        }
        else{
            Toast.makeText(KhotomAccount.this, "Group Data is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void writenewMember(){
        count_name = et_k_name.getText().toString();
        mail2 = email2.getText().toString();
        target=targt_et.getText().toString();

        Query q= database.child("Group").child(count_name)
                .orderByChild("k_acc_email")
                .equalTo(mail2);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.getChildrenCount()>0) {
                //username found
                Toast.makeText(KhotomAccount.this, "this member existed", Toast.LENGTH_SHORT).show();

            }else{
                // username not found
                newMember();
            }

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }


    public void newMember(){
        count_name=et_k_name.getText().toString();
        target=targt_et.getText().toString();
        email=email2.getText().toString();
        date=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        KhatamUser helperClass = new KhatamUser(email, target, count_name, date, myCount, tCount);
        database.child("Group").child(count_name).push().setValue(helperClass);
        Toast.makeText(KhotomAccount.this, "success to insert", Toast.LENGTH_SHORT).show();
    }

    //public void delItemfromList(View view) {
   // }

    public void goGroup(View view) {
        if(!et_k_name.getText().toString().trim().matches("") &&
                !targt_et.getText().toString().trim().matches("")) {
            Intent i = new Intent(this, khatam_list.class);
            i.putExtra("name", et_k_name.getText().toString());
            i.putExtra("tgt", targt_et.getText().toString());
            startActivity(i);
            this.finish();
        }
        else{
            Toast.makeText(KhotomAccount.this, "Please assure your group", Toast.LENGTH_SHORT).show();
        }
    }
    public void go_main(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }


}

