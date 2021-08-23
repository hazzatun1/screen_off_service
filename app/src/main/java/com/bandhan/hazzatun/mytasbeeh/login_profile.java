package com.bandhan.hazzatun.mytasbeeh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class login_profile extends AppCompatActivity {
TextView email;
    String email2;
LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                // String name = profile.getDisplayName();
                email2 = profile.getEmail();
                // Uri photoUrl = profile.getPhotoUrl();
            }
            email=findViewById(R.id.email_logo);
            email.setText(email2);

        }
layout=findViewById(R.id.prof);
        SharedPreferences set_back=getSharedPreferences("set_back", Context.MODE_PRIVATE);
        String back = set_back.getString("pic_name", "");
        if (back != null && !back.equals("")){

            switch(back) {
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



    }
}