package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {

    EditText admin_user, admin_pass;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        this.getSupportActionBar().setTitle("Admin Login");

        admin_user = (EditText) findViewById(R.id.admin_user);
        admin_pass = (EditText) findViewById(R.id.admin_pass);

    }

    public void login(View view) {
        String user, password;

        user = admin_user.getText().toString().trim();
        password = admin_pass.getText().toString().trim();

        if (user.isEmpty()) {
            admin_user.setError("Empty");
            admin_user.requestFocus();
        } else if (password.isEmpty()) {
            admin_pass.setError("Empty");
            admin_pass.requestFocus();
        } else {
            if (user.equals("KGMU") && password.equals("Admin"))  // for match
            {
                sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putString("userA", "admin");
                ed.commit();
                Intent intent = new Intent(LogIn.this, AdminChoose.class);   // Intent use for go to next activity (obj create)
                startActivity(intent);
                finish();
            } else {
                admin_user.setError("Invalid");
                admin_user.setText("");
                admin_pass.setError("Invalid");
                admin_pass.setText("");
            }

        }

    }
}
