package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserChoose extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String email;
    String admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choose);
        this.getSupportActionBar().setTitle("KGMU");
        sharedPreferences=getSharedPreferences("session",MODE_PRIVATE);
        email=sharedPreferences.getString("user","");
        admin = sharedPreferences.getString("userA","");

    }

    public void adlogin (View view) {
        if (admin.equals("loggedout")) {
            Intent intent = new Intent(UserChoose.this, LogIn.class);
            startActivity(intent);
        } else {
            startActivity(new Intent(UserChoose.this, AdminChoose.class));
            finish();
        }
    }

    public void tclogin (View view)
    {
        if (email.equals("loggedout"))
        {
            Intent intent = new Intent(UserChoose.this,LogInT.class);
            startActivity(intent);
        }
        else
        {
            startActivity(new Intent(UserChoose.this,TeacherChoose.class));
            finish();
        }


    }
}
