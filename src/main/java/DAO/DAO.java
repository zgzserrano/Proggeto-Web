package DAO;

import java.sql.*;


public class DAO {
    private static final String url1 = "jdbc:mysql://localhost:3306/applicazione";
    private static final String user = "root";
    private static final String password = "";

    public  static void registerDriver(){
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correctamente registrato");
        } catch (SQLException e) {
            System.out.println("errore " + e.getMessage());
        }
    }

}
