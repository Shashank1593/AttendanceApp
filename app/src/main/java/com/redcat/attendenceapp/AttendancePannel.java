package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class AttendancePannel extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    String dept;
    String[] from = {"name", "rollno", "status"};
    int[] to = {R.id.stud_name, R.id.stud_roll, R.id.status};
    ArrayList<HashMap<String, String>> al_details = new ArrayList<HashMap<String, String>>();
    SimpleAdapter SA;
    ConnectionHelper connectionHelper;
    Connection connection;
    ResultSet resultSet;
    Statement statement;
    ListView studentlist;
    HashMap<String, String> hm;
    String[] monthname = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] weekday = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String day, cmonth, year, wkday, attdate;
    Calendar dt = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_pannel);
        this.getSupportActionBar().setTitle("Attendance");
        day = dt.get(Calendar.DAY_OF_MONTH) + "";
        cmonth = monthname[dt.get(Calendar.MONTH)];
        year = dt.get(Calendar.YEAR) + "";
        wkday = weekday[dt.get(Calendar.DAY_OF_WEEK) - 1];
        attdate = dt.get(Calendar.DAY_OF_MONTH) + "/" + (dt.get(Calendar.MONTH) + 1) + "/" + dt.get(Calendar.YEAR);

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        dept = sharedPreferences.getString("dept", "");

        // Toast.makeText(this, "" + dept, Toast.LENGTH_LONG).show();

        connectionHelper = new ConnectionHelper();
        connection = connectionHelper.getConnection();
        studentlist = (ListView) findViewById(R.id.lv_studata);
        SA = new SimpleAdapter(this, al_details, R.layout.attendance_list, from, to);
        studentlist.setAdapter(SA);
        if (connection == null) {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        } else {
            try {
                statement = connection.createStatement();
                String query = "select * from kgmu_students where department_name = '" + dept + "'";
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    hm = new HashMap<String, String>();
                    hm.put("name", resultSet.getString("name"));
                    hm.put("rollno", resultSet.getString("rollno"));
                    hm.put("status", "P");
                    al_details.add(hm);
                }
                SA.notifyDataSetChanged();
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }


    }

    public void test(View view) {
        CheckBox mybox = (CheckBox) view.findViewById(R.id.status);
        hm = new HashMap<String, String>();
        int position = studentlist.getPositionForView(view);
        HashMap<String, String> newmap = al_details.get(position);
        if (mybox.getText().toString().equals("P") == true) {
            mybox.setText("A");

        } else {
            mybox.setText("P");

        }
        hm.put("status", mybox.getText().toString());
        hm.put("rollno", newmap.get("rollno"));
        hm.put("name", newmap.get("name"));
        al_details.remove(position);
        al_details.add(position, hm);
        SA.notifyDataSetChanged();
        studentlist.invalidateViews();
        mybox.invalidate();

    }

    public void Att_submit(View view) {
        String record = "<DocumentElement>\t";
        for (HashMap<String, String> hashMap : al_details) {
            record = record + "<Test>\t";
            for (String key : hashMap.keySet()) {
                record = record + "<" + key + ">" + hashMap.get(key) + "</" + key + ">";
            }
            record = record + "</Test>";
        }

        record = record + "</DocumentElement>";
        Toast.makeText(this, "" + record, Toast.LENGTH_LONG).show();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document parse = documentBuilder.parse(new ByteArrayInputStream(record.getBytes()));
            for (int i = 0; i < parse.getElementsByTagName("Test").getLength(); i++) {
                String name = parse.getElementsByTagName("name").item(i).getTextContent();
                String rollno = parse.getElementsByTagName("rollno").item(i).getTextContent();
                String status = parse.getElementsByTagName("status").item(i).getTextContent();

                statement = connection.createStatement();
                String query = "insert into kgmu_att(sname,srollno,department,month,year,day,status,weekday,attdate) values('" + name + "','" + rollno + "','" + dept + "','" + cmonth + "','" + year + "','" + day + "','" + status + "','" + wkday + "','" + attdate + "')";
                statement.execute(query);
            }

            Toast.makeText(this, "Attendance Updated", Toast.LENGTH_SHORT).show();
            recreate();
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

                startActivity(new Intent(AttendancePannel.this, UserChoose.class));
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
