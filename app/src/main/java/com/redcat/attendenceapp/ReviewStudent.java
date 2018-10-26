package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ReviewStudent extends AppCompatActivity {

    ListView datalist;
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;

    String[] from = {"sname", "srollno", "department", "month", "year", "day", "status", "weekday", "attdate"};
    int[] to = {R.id.tv_sname, R.id.tv_srollno, R.id.tv_department, R.id.tv_month, R.id.tv_year, R.id.tv_day, R.id.tv_status, R.id.tv_weekday, R.id.tv_attdate};

    ArrayList<HashMap<String, String>> al_students = new ArrayList<HashMap<String, String>>();
    SimpleAdapter SA;
    HashMap<String, String> hm;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_student);
        this.getSupportActionBar().setTitle("Student Review");
        datalist = (ListView) findViewById(R.id.stu_review);

        SA = new SimpleAdapter(this, al_students, R.layout.review_list, from, to);
        datalist.setAdapter(SA);


        ch = new ConnectionHelper();
        con = ch.getConnection();


        if (con == null) {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        } else {
            try {
                stmt = con.createStatement();
                String query = "select * from kgmu_att";
                rs = stmt.executeQuery(query);


                while (rs.next()) {
                    hm = new HashMap<String, String>();
                    hm.put("sname", rs.getString("sname"));
                    hm.put("srollno", rs.getString("srollno"));
                    hm.put("department", rs.getString("department"));
                    hm.put("month", rs.getString("month"));
                    hm.put("year", rs.getString("year"));
                    hm.put("day", rs.getString("day"));
                    hm.put("status", rs.getString("status"));
                    hm.put("weekday", rs.getString("weekday"));
                    hm.put("attdate", rs.getString("attdate"));


                    al_students.add(hm);
                }


                SA.notifyDataSetChanged();


            } catch (Exception e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Logout Menu

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

                startActivity(new Intent(ReviewStudent.this, UserChoose.class));
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