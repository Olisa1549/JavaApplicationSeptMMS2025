package com.mycompany.hotel_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
        + "databaseName=HotelManagement;"
        + "encrypt=true;"
        + "trustServerCertificate=true;";

    private static final String USERNAME = "mainhotel";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
