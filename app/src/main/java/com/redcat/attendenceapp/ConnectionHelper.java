package com.redcat.attendenceapp;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by bornr on 13-Mar-18.
 */

public class ConnectionHelper {
    String IP,DB,DBUsername,DBPassword;

    public Connection getConnection()
    {
        IP="184.168.194.55";
        DB="AppDb";
        DBUsername="appdb";
        DBPassword="appdb@123";
        Connection con=null;
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:jtds:sqlserver://"+IP+";databasename="+DB+";user="+DBUsername+";password="+DBPassword+";");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}
