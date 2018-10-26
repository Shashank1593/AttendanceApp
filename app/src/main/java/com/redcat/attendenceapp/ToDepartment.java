package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ToDepartment extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_department);
        this.getSupportActionBar().setTitle("To Department");
    }


    // Logout Button

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout: {
                sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putString("userA", "loggedout");
                ed.commit();

                startActivity(new Intent(ToDepartment.this, UserChoose.class));
                finish();
                //Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();

                break;
            }

            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);

    }
}
