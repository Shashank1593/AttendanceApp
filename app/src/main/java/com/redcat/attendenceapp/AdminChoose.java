package com.redcat.attendenceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AdminChoose extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_choose);
        this.getSupportActionBar().setTitle("Admin Choice");
    }

    public void stureg(View view) {
        Intent intent = new Intent(AdminChoose.this, StudentRegistration.class);
        startActivity(intent);
    }

    public void officer(View view) {
        Intent intent = new Intent(AdminChoose.this, TeacherRegistration.class);

        startActivity(intent);
    }

    public void dplist(View view) {
        Intent intent = new Intent(AdminChoose.this, DepartmentList.class);
        startActivity(intent);
    }

    public void notice(View view) {
        Intent intent = new Intent(AdminChoose.this, NoticeGen.class);
        startActivity(intent);
    }


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

                Intent intent = new Intent (AdminChoose.this, UserChoose.class);
                startActivity(intent);
                finish();
                //Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();

                break;
            }

            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);

    }


    //Alert Box

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit ?")
                .setMessage("Press YES to Exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdminChoose.super.onBackPressed();
                    }
                })
                .setCancelable(true)
                .show();


    }
}
