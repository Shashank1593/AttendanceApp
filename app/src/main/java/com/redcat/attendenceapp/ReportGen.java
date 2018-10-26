package com.redcat.attendenceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.RowsExceededException;

public class ReportGen extends AppCompatActivity {
    ConnectionHelper connectionHelper;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    SimpleDateFormat simpleDateFormat;
    Date date;
    String datee;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_gen);
        this.getSupportActionBar().setTitle("Export Table");
        simpleDateFormat = new SimpleDateFormat("yyy/MM/dd");
        date = new Date();
        datee = simpleDateFormat.format(date);
    }

    public void export(View view) {
        connectionHelper = new ConnectionHelper();
        connection = connectionHelper.getConnection();

        if (connection == null) {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        } else {
            try {
                statement = connection.createStatement();
                String query = "select * from kgmu_att";
                resultSet = statement.executeQuery(query);
                File sd = Environment.getExternalStorageDirectory();
                String excelFile = "Attendance.xls";

                File directory = new File(sd.getAbsolutePath());
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }

                try {
                    File file = new File(directory, excelFile);

                    WorkbookSettings wbSettings = new WorkbookSettings();

                    wbSettings.setLocale(new Locale("en", "EN"));
                    WritableWorkbook workbook;
                    workbook = Workbook.createWorkbook(file, wbSettings);

                    WritableSheet sheet = workbook.createSheet("AttendanceData", 0);

                    sheet.addCell(new Label(0, 0, "id"));
                    sheet.addCell(new Label(1, 0, "sname"));
                    sheet.addCell(new Label(2, 0, "srollno"));
                    sheet.addCell(new Label(3, 0, "department"));
                    sheet.addCell(new Label(4, 0, "month"));
                    sheet.addCell(new Label(5, 0, "year"));
                    sheet.addCell(new Label(6, 0, "day"));
                    sheet.addCell(new Label(7, 0, "status"));
                    sheet.addCell(new Label(8, 0, "weekday"));
                    sheet.addCell(new Label(9, 0, "attdate"));

                    while (resultSet.next()) {
                        String id = resultSet.getString("id");
                        String sname = resultSet.getString("sname");
                        String srollno = resultSet.getString("srollno");
                        String department = resultSet.getString("department");
                        String month = resultSet.getString("month");
                        String year = resultSet.getString("year");
                        String day = resultSet.getString("day");
                        String status = resultSet.getString("status");
                        String weekday = resultSet.getString("weekday");
                        String attdate = resultSet.getString("attdate");

                        int i = resultSet.getRow() + 1;
                        sheet.addCell(new Label(0, i, id));
                        sheet.addCell(new Label(1, i, sname));
                        sheet.addCell(new Label(2, i, srollno));
                        sheet.addCell(new Label(3, i, department));
                        sheet.addCell(new Label(4, i, month));
                        sheet.addCell(new Label(5, i, year));
                        sheet.addCell(new Label(6, i, day));
                        sheet.addCell(new Label(7, i, status));
                        sheet.addCell(new Label(8, i, weekday));
                        sheet.addCell(new Label(9, i, attdate));
                    }
                    resultSet.close();
                    workbook.write();
                    workbook.close();
                    Toast.makeText(getApplicationContext(), "Data Exported", Toast.LENGTH_SHORT).show();
                } catch (IOException e1) {
                    Toast.makeText(this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (jxl.write.WriteException e) {

                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } catch (SQLException e) {
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

                startActivity(new Intent(ReportGen.this, UserChoose.class));
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