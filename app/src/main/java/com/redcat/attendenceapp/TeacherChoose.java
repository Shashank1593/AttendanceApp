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

public class TeacherChoose extends AppCompatActivity {
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_choose);
        this.getSupportActionBar().setTitle("Nodal Choice");
    }

    public void attpannel(View view) {
        Intent intent = new Intent(TeacherChoose.this, AttendancePannel.class);
        startActivity(intent);
    }

    public void repgen(View view) {
        Intent intent = new Intent(TeacherChoose.this, ReportGen.class);
        startActivity(intent);
    }

    public void revgen(View view) {
        Intent intent = new Intent(TeacherChoose.this, ReviewStudent.class);
        startActivity(intent);
    }

    public void nodal_profile (View view)
    {
        Intent intent = new Intent(TeacherChoose.this, NodalProfile.class);
        startActivity(intent);
    }


    //Logout Button

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
                ed.putString("user", "loggedout");
                ed.commit();

                startActivity(new Intent(TeacherChoose.this, UserChoose.class));
                finish();
                //Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();

                break;
            }

            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit ?")
                .setMessage("Press YES to Exit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TeacherChoose.super.onBackPressed();
                    }
                })
                .setCancelable(true)
                .show();


    }


}


