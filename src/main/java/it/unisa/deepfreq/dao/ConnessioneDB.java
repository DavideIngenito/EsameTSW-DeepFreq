package it.unisa.deepfreq.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {


    private static final String URL = "jdbc:mysql://localhost:3306/deepfreq?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Da01092004";

    private static Connection connection = null;


    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("Driver MySQL non trovato. Hai aggiunto il Connector/J al Build Path?");
                e.printStackTrace();
            }
        }
        return connection;
    }
}