package DAO;

import javax.swing.plaf.nimbus.State;
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

    public static boolean correctPass(String username, String pass) {

        boolean correct = false;
        //Have to create and initialize before try, else error
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to database for login");
            }
            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM Utente WHERE account=\'" + username + "\' and pass=" + pass + ";");
            rs.next();
            correct = rs.getInt("COUNT(*)") > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null){
                try {
                    conn1.close();
                    System.out.println("Connection closed successfully");
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                    correct = false;
                }
            }
        }
        return correct;
    }

}
