package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TeacherRegistration extends AppCompatActivity {

    EditText[] ets = new EditText[5];
    int[] ids = {R.id.officer_name, R.id.officer_code, R.id.officer_email, R.id.officer_pass, R.id.officer_mob};
    String[] values = new String[ets.length];
    int i;
    ConnectionHelper ch;
    Connection con;
    Statement start;
    Spinner spinofficer;
    ResultSet rs;
    ArrayAdapter AD;
    ArrayList<String> al_dept = new ArrayList<String>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);
        this.getSupportActionBar().setTitle("Nodal Registration");
        ch = new ConnectionHelper();
        con = ch.getConnection();
        if (con == null) {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            TeacherRegistration.super.onBackPressed();
        }
        else
            {
            try {
                start = con.createStatement();
                String query = "select*from kgmu_dept";
                rs = start.executeQuery(query);
                while (rs.next()) {
                    al_dept.add(rs.getString("department_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            spinofficer = (Spinner) findViewById(R.id.dlist);
            AD = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, al_dept);
            spinofficer.setAdapter(AD);
            for (i = 0; i < ets.length; i++)
            {
                ets[i] = (EditText) findViewById(ids[i]);
            }
        }
    }

    public void add_nodal(View view)
    {

        for (i = 0; i < ets.length; i++)
        {
            if(!ets[2].getText().toString().matches("[a-zA-z0-9._-]+@+[a-z]+\\.+[a-z]+"))
            {
                ets[2].setError("Invalid");
                break;
            }
            if (ets[i].getText().toString().trim().isEmpty())
            {
                ets[i].setError("Required");
                break;

            }

        }

        if (i == ets.length) {
            for (i = 0; i < ets.length; i++) {
                values[i] = ets[i].getText().toString().trim();
            }


//            Toast.makeText(this, "" + values[0] + values[1] + values[2]
//                    + values[3] + values[4] + values[5], Toast.LENGTH_SHORT).show();

            con = ch.getConnection();

            if (con == null) {
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    start = con.createStatement();
                    String query = "insert into kgmu_nodal values('" + values[0] + "','" + values[1] + "','" + spinofficer.getSelectedItem().toString() + "','" + values[2] + "','" + values[3] + "','" + values[4] + "')";
                    start.execute(query);
                    Toast.makeText(this, " Successful Registered ", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    // Logout Button

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_logout:
            {
                sharedPreferences = getSharedPreferences("session",MODE_PRIVATE);
                SharedPreferences.Editor ed=sharedPreferences.edit();
                ed.putString("userA","loggedout");
                ed.commit();

                startActivity(new Intent(TeacherRegistration.this,UserChoose.class));
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