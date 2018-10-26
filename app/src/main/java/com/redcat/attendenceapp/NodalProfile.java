package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class NodalProfile extends AppCompatActivity {

    String pid;
    ConnectionHelper connectionHelper;
    Statement statement;
    Connection connection;
    SharedPreferences sharedPreferences;
    EditText nodalname, nodalcode, nodaldepartment, nodalemail, nodalpass, nodalmobile;
    ResultSet resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodal_profile);
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        pid = sharedPreferences.getString("id", "");
        //Toast.makeText(this, ""+pid, Toast.LENGTH_SHORT).show();

        connectionHelper = new ConnectionHelper();
        connection = connectionHelper.getConnection();

        nodalname = (EditText) findViewById(R.id.profile_name);
        nodalcode = (EditText) findViewById(R.id.profile_code);
        nodaldepartment = (EditText) findViewById(R.id.profile_department);
        nodalemail = (EditText) findViewById(R.id.profile_email);
        nodalpass = (EditText) findViewById(R.id.profile_pass);
        nodalmobile = (EditText) findViewById(R.id.profile_mob);

        if (connection == null) {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        } else {
            try {
                statement = connection.createStatement();
                String query = "select * from kgmu_nodal  where id = '" + pid + "'";
                resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    nodalname.setText(resultSet.getString("nodal_name"));
                    nodalcode.setText(resultSet.getString("nodal_code"));
                    nodaldepartment.setText(resultSet.getString("department_name"));
                    nodalemail.setText(resultSet.getString("nodal_email"));
                    nodalpass.setText(resultSet.getString("nodal_password"));
                    nodalmobile.setText(resultSet.getString("nodal_phone"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void profile_Edit(View view) {
        nodalname.setEnabled(true);
        nodalemail.setEnabled(true);
        nodalmobile.setEnabled(true);
        nodalpass.setEnabled(true);
    }

    public void profile_Update(View view) {
        String name = nodalname.getText().toString().trim();
        String mobile = nodalmobile.getText().toString().trim();
        String email = nodalemail.getText().toString().trim();
        String password = nodalpass.getText().toString().trim();

        try {
            statement = connection.createStatement();
            String query = "update kgmu_nodal set nodal_name='" + name + "', nodal_email='" + email + "', nodal_phone='" + mobile + "', nodal_password='" + password + "' where id='" + pid + "' ";
            statement.execute(query);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void profile_cancle(View view)
    {
        startActivity(new Intent(NodalProfile.this,NodalProfile.class));
        finish();
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

                startActivity(new Intent(NodalProfile.this, UserChoose.class));
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
