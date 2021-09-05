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
    String k_count_name="";
    FirebaseUser user;
    String email="", target="", count_name="", date="", email1="";
    DatabaseReference database;
    khatam_adapter kAdapter;
    ArrayList<KhatamUser> list_khatam;
    EditText email2, et_k_name, targt_et;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khotom_account);
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("MyDigitalCounter");
        user = FirebaseAuth.getInstance().getCurrentUser();
        email1=user.getEmail();
        list_khatam=new ArrayList<>();
        email2=findViewById(R.id.edit_text);
        et_k_name=findViewById(R.id.edit_text1);
        targt_et=findViewById(R.id.edit_text2);
        if (getIntent().hasExtra("cName") && getIntent().hasExtra("target")){
           et_k_name.setText(getIntent().getStringExtra("cName"));
            targt_et.setText(getIntent().getStringExtra("target"));
       }
        recyclerView = findViewById(R.id.khatamList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        database.child("Group").orderByValue().addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_khatam.clear();
                for (DataSnapshot booksSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot bSnapshot : booksSnapshot.getChildren()) {
                        try {
                            KhatamUser user = bSnapshot.getValue(KhatamUser.class);
                            list_khatam.add(user);
                        } catch (DatabaseException e) {
                            //Log the exception and the key
                            bSnapshot.getKey();
                        }
                    Toast.makeText(KhotomAccount.this, "only you!!!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(KhotomAccount.this, "success to show", Toast.LENGTH_SHORT).show();

                    kAdapter = new khatam_adapter(KhotomAccount.this, list_khatam);
                    recyclerView.setAdapter(kAdapter);
                    kAdapter.notifyDataSetChanged();

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
        KhatamUser helperClass = new KhatamUser(email, target, count_name, date, "0");
        database.child("Group").child(String.valueOf(helperClass.getK_count_name())).push().setValue(helperClass);
    }
    public void add_k_name(View view) {

        database.child("Group").child(k_count_name)
                .equalTo(et_k_name.getText().toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(KhotomAccount.this, "existing group: join here", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            list_khatam.clear();
                            writeNewGroup();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(KhotomAccount.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });


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
                            KhatamUser helperClass = new KhatamUser(email, target, count_name, date, "0");
                            database.child("Group").child(helperClass.getK_count_name()).push().setValue(helperClass);
                        }

                    }
                });
    }
    public void addItemToList(View view) {

        database.child("Group").child(k_count_name)
                .equalTo(et_k_name.getText().toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    Toast.makeText(KhotomAccount.this, "existing member", Toast.LENGTH_SHORT).show();

                                } else {
                                    list_khatam.clear();
                                    writeNewMember();
                                    Toast.makeText(KhotomAccount.this, "new member", Toast.LENGTH_SHORT).show();
                                }

                            }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(KhotomAccount.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void goGroup(View view) {
        Intent i = new Intent(this, khatam_list.class);
        i.putExtra("name",et_k_name.getText().toString());
        i.putExtra("tgt",targt_et.getText().toString());
        startActivity(i);
        this.finish();
    }
}
