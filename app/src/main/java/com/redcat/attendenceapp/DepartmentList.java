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
import java.sql.SQLException;
import java.sql.Statement;

public class DepartmentList extends AppCompatActivity {

    EditText adddept;
    ConnectionHelper ch;
    Statement stmt;
    Connection conn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);
        this.getSupportActionBar().setTitle("Add Department");
        adddept = (EditText) findViewById(R.id.adddept);
        ch = new ConnectionHelper();
    }

    public void deptadd(View view) {

        String deptName = adddept.getText().toString().trim();
        if (deptName.isEmpty()) {
            adddept.setError("Add Department");
            adddept.requestFocus();
            // Toast.makeText(this, "" + deptName, Toast.LENGTH_SHORT).show();

        } else {

            conn = ch.getConnection();
            if (conn == null) {
                Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    stmt = conn.createStatement();
                    String query = "insert into kgmu_dept values('" + deptName + "')";
                    stmt.execute(query);
                    Toast.makeText(this, " Successful Add ", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
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

                startActivity(new Intent(DepartmentList.this, UserChoose.class));
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
