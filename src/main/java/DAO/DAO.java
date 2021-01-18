package DAO;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Queue;


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

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM Utente WHERE account='" + username + "' and pass=" + pass + ";");
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

    public static boolean getRole(String username){
        Connection conn1 = null;
        boolean role = false;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null){
                System.out.println("Connected to db from getRole");
            }
            Statement st = conn1.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM utente WHERE account? '" + username + "';");
            rs.next();
            role = rs.getBoolean("admin");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null){
                try{
                    conn1.close();
                    System.out.println("Connection closed successfully");
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                    role=false;
                }
            }
        }
        return role;
    }

    public static boolean addDocenteToDB(Docente d){
        Connection conn1 = null;
        boolean added = false;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null){
                System.out.println("Connected to db from addDocenteToDB");
            }
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO 'docente' ('nome', 'cognome', 'attiva' ) VALUES ('" + d.getName() + "', '"  + d.getSurname() + "', '1');" );
            added = true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (conn1 != null){
                try{
                    conn1.close();
                    System.out.println("Connection closed successfully");
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return added;
    }

    public static ArrayList<Prenotazione> getReserves(){
        ArrayList<Prenotazione> reserves = new ArrayList<>();
        Connection conn1 = null;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null){
                System.out.println("Connected to db from getReserves");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONE" );
            while (rs.next()){
                String u = rs.getString("usuario");
                String t = rs.getNString("nome") + rs.getString("cognome");
                String c = rs.getString("corso");
                String g = rs.getString("giorno");
                int h = rs.getInt("ora");
                String s = rs.getString("stato");

                reserves.add(new Prenotazione(h, g, t, c, u, s));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }

        return reserves;
    }


    public static ArrayList<Prenotazione> getReserves(String username){
        ArrayList<Prenotazione> reserves = new ArrayList<>();
        Connection conn1 = null;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null){
                System.out.println("Connected to db from getReserves");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONE where usuairo='" + username + "';" );
            while (rs.next()){
                String u = rs.getString("usuario");
                String t = rs.getNString("nome") + rs.getString("cognome");
                String c = rs.getString("corso");
                String g = rs.getString("giorno");
                int h = rs.getInt("ora");
                String s = rs.getString("stato");

                reserves.add(new Prenotazione(h, g, t, c, u, s));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }

        return reserves;
    }

    public static boolean addCoursetoDB(Corso c) {
        Connection conn1 = null;
        boolean created = false;
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from addCoursetoDB");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("select * from Corso where titulo='"+c.getTitulo()+"';");
            if(rs.next()) {
                if (!rs.getBoolean("attiva")) {
                    st.executeUpdate("update Corso set attiva=1 where titulo='"+c.getTitulo()+"';");
                    created = true;
                }
            }
            else{
                st.executeUpdate("INSERT INTO `Corso` (`titulo`, `attiva`) VALUES ('" + c.getTitulo() + "', '1');");
                created = true;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return created;
    }

}
