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
import android.widget.ListView;
import android.widget.Toast;

import com.katepratik.msg91api.MSG91;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NoticeGen extends AppCompatActivity {
    ConnectionHelper ch;
    Connection con;
    Statement stmt;
    ResultSet rs;
    ArrayList<String> al_smob = new ArrayList<String>();
    ArrayList<String> al_gmob = new ArrayList<String>();
    ArrayAdapter AD1, AD2;
    MSG91 msg91;
    SimpleDateFormat sdf;
    Date dt;
    String date;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_gen);
        this.getSupportActionBar().setTitle("Notice");
        Calendar cd = Calendar.getInstance();

        int day = cd.get(Calendar.DAY_OF_MONTH);
        int month = cd.get(Calendar.MONTH);
        int year = cd.get(Calendar.YEAR);

        date = day + "/" + (month + 1) + "/" + year;


        AD1 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, al_smob);
        AD2 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, al_gmob);


        ch = new ConnectionHelper();
        con = ch.getConnection();

        if (con == null) {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        } else {
            try {
                stmt = con.createStatement();
                String query = "select srollno from kgmu_att where status='A' and attdate='" + date + "'";
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    al_gmob.add(getGmob(rs.getString("srollno")));
                    al_smob.add(getSmob(rs.getString("srollno")));
                }
                AD1.notifyDataSetChanged();
                AD2.notifyDataSetChanged();
            } catch (SQLException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(this, "" + al_gmob.size() + " " + al_smob.size(), Toast.LENGTH_SHORT).show();
        }


    }

    public String getGmob(String rno) {
        String gmob = "";
        try {
            stmt = con.createStatement();
            String query = "select gmob from kgmu_students where rollno='" + rno + "'";
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                gmob = gmob + res.getString("gmob");
            }
        } catch (SQLException e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return gmob;
    }


    public String getSmob(String rno) {
        String smob = "";
        try {
            stmt = con.createStatement();
            String query = "select mobile from kgmu_students where rollno='" + rno + "'";
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                smob = smob + res.getString("mobile");
            }
        } catch (SQLException e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return smob;
    }

    public void tostudent(View view) {


        for (String str : al_smob) {
            try {
                msg91 = new MSG91("196023AwNplgJ6qS5a722783");
                msg91.composeMessage("KGMUSM", "Accha baat ni hai.....aaye nhi na....papa ko bta re hain");
                msg91.to(str);
                msg91.send();
            } catch (Exception ex) {
                Toast.makeText(this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void toguardian(View view) {
        for (String str : al_gmob) {
            try {
                msg91 = new MSG91("196023AwNplgJ6qS5a722783");
                msg91.composeMessage("KGMUSM", "Accha baat ni hai.....aaye nhi na....papa ko bta re hain");
                msg91.to(str);
                msg91.send();
            } catch (Exception ex) {
                Toast.makeText(this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void todept(View view) {
        Intent intent = new Intent(NoticeGen.this, ToDepartment.class);
        startActivity(intent);
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

                startActivity(new Intent(NoticeGen.this, UserChoose.class));
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