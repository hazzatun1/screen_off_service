package com.bandhan.hazzatun.mytasbeeh;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

    public class Login extends AppCompatActivity {
        EditText mEmail, mPassword;
        Button mLoginBtn;
        TextView forgotTextLink;
        FirebaseAuth fAuth;
        String email, password;
        private FirebaseAuth auth;
        SharedPreferences set_back;
        LinearLayout layout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(Login.this, SplashActivity.class));
                this.finish();
            }

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            mEmail = findViewById(R.id.email_login);
            mPassword = findViewById(R.id.pass_login);
            fAuth = FirebaseAuth.getInstance();
            mLoginBtn = findViewById(R.id.loginBtn);
            forgotTextLink = findViewById(R.id.forgot_pass);
            layout=(LinearLayout)findViewById(R.id.login_back);

            set_back=getSharedPreferences("set_back", Context.MODE_PRIVATE);
            String back = set_back.getString("pic_name", "");
            if (back != null && !back.equals("")){

                switch(back) {
                    case "bk":
                        layout.setBackgroundResource(R.drawable.bk);
                        break;
                    case "pic1":
                        layout.setBackgroundResource(R.drawable.pic1);
                        break;
                    case "pic2":
                        layout.setBackgroundResource(R.drawable.pic2);
                        break;
                    default:
                        break;
                }

            }



            mLoginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                    if (netInfo == null) {
                        Toast.makeText(Login.this, "No Internet", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        email = mEmail.getText().toString().trim();
                        password = mPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        mEmail.setError("Email is Required.");
                        return;
                    }
//
                    if (TextUtils.isEmpty(password)) {
                        mPassword.setError("Password is Required.");
                        return;
                    }
//
//                    if (password.length() < 6) {
//                        mPassword.setError("Password Must be >= 6 Characters");
//                        return;
//                    }
                        // authenticate the user

                        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(Login.this,SplashActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });



        forgotTextLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){

            final EditText resetMail = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
            passwordResetDialog.setView(resetMail);
            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // extract the email and send reset link
                    String mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure( Exception e) {
                            Toast.makeText(Login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // close the dialog
                }
            });
            passwordResetDialog.create().show();
        }
        });


    }
    public void gotoreg(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    }