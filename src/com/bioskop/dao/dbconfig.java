package com.bioskop.dao;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class dbconfig {
    public static Connection con() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost:1433;database=Bioskop;integratedSecurity=true;";
        return DriverManager.getConnection(connectionUrl);
    }
}
