package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogInT extends AppCompatActivity {

    EditText [] edts = new EditText[2];
    int []ids = {R.id.tech_user, R.id.tech_pass};
    String values[] = new String[edts.length];
    SharedPreferences sharedPreferences;
    ConnectionHelper connectionHelper;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_t);
        this.getSupportActionBar().setTitle("Nodal Login");

        connectionHelper = new ConnectionHelper();

        connection = connectionHelper.getConnection();

        for (i = 0; i < edts.length; i++)
        {

        edts[i] = (EditText) findViewById(ids[i]);
    }

    }

    public void logint (View view)
    {
        for (i=0; i<edts.length; i++)
        {
            if(edts[i].getText().toString().isEmpty())
            {
                edts[i].setError("Empty");
                edts[i].requestFocus();
                break;
            }
        }

        if (i==edts.length)
        {
            for (i=0; i<edts.length; i++)
            {
                values[i] = edts[i].getText().toString().trim();
            }
        }
        connection = connectionHelper.getConnection();
        if (connection==null)
        {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                statement = connection.createStatement();
                String query = "select * from kgmu_nodal where nodal_email LIKE '"+ values[0] +"' AND nodal_password LIKE '"+ values[1]+"'";

                resultSet = statement.executeQuery(query);
                if (resultSet.next())
                {
                   try {
                       sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                       SharedPreferences.Editor ed = sharedPreferences.edit();
                       ed.putString("user", resultSet.getString("nodal_email"));
                       ed.putString("email", resultSet.getString("nodal_password"));
                       ed.putString("dept", resultSet.getString("department_name"));
                       ed.putString("id",resultSet.getString("id"));
                       ed.apply();
                   }
                   catch (Exception e)
                   {
                       Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }

                   // Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInT.this,TeacherChoose.class);
                    startActivity(intent);
                    finish(); }
                else
                    Toast.makeText(this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                for (i=0; i<edts.length; i++)
                {
                    edts[i].setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}

