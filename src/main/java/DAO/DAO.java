package DAO;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;


public class DAO {
    private static final String url1 = "jdbc:mysql://localhost:3306/applicazione";
    private static final String user = "root";
    private static final String password = "";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
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
        ArrayList<Prenotazione> reserves = new ArrayList<Prenotazione>();
        Connection conn1 = null;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null){
                System.out.println("Connected to db from getReserves");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONE" );
            while (rs.next()){

                Utente u = new Utente(rs.getString("usuario"));
                Docente t = new Docente(rs.getNString("nome"), rs.getString("cognome"));
                Corso c = new Corso(rs.getString("corso"));
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
        ArrayList<Prenotazione> reserves = new ArrayList<Prenotazione>();
        Connection conn1 = null;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null){
                System.out.println("Connected to db from getReserves");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONE where usuairo='" + username + "';" );
            while (rs.next()){
                Utente u = new Utente(rs.getString("usuario"));
                Docente t = new Docente(rs.getNString("nome"), rs.getString("cognome"));
                Corso c = new Corso(rs.getString("corso"));
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
    public static boolean deleteDocenteofDB(Docente d){
        Connection conn1 = null;
        boolean deleted = false;
        try{
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null){
                System.out.println("Connected to db from deleteDocenteToDB");
            }
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE Docente SET attiva=0 WHERE nome='" + d.getName() + "' AND cognome='" +  d.getSurname() + "';");
            st.executeUpdate("UPDATE Imparte SET attiva=0 WHERE nome='" + d.getName() + "' AND cognome='" + d.getSurname() + "';");
            st.executeUpdate("UPDATE Prenotazione SET stato='CANCELLATA' WHERE nome='" + d.getName() + "' AND cognome='" + d.getSurname() + "';");
            deleted = true;
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
        return deleted;
    }



    public static boolean deleteCoursetoDB(Corso c) {
        Connection conn1 = null;
        boolean deleted = false;
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from deleteCoursetoDB");
            }
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE Corso SET attiva=0 WHERE titulo='" + c.getTitulo() + "';");
            st.executeUpdate("UPDATE Imparte SET attiva=0 WHERE corso='" + c.getTitulo() + "';");
            st.executeUpdate("UPDATE  Prenotazione SET stato='CANCELLATA' WHERE  corso='" + c.getTitulo() + "';");
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
        return deleted;

    }
    public static boolean reservate(Prenotazione p){
        Connection conn1 = null;
        boolean reservated = false;
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from reservate");
            }
            Statement st = conn1.createStatement();
            Utente u = p.getUser();
            Docente d = p.getTeacher();
            Corso c = p.getCourse();
            st.executeUpdate("INSERT INTO 'prenotazione' ('usuario', 'nome', 'cognome', 'corso', 'giorno', 'ora', 'stato' ) VALUES ('" + u.getAccount() + "', '" + d.getName() + "', '"  + d.getSurname()  +"','"+c.getTitulo()+"','"+p.getDay()+"',"+p.getHour()+",'"+p.getState()+"');");
            reservated = true;

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
        return reservated;

    }
    public static boolean delete(Prenotazione p){
        Connection conn1 = null;
        boolean deleted = false;
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from delete");
            }
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE Prenotazione  set stato='CANCELLATA' where usuario='"+p.getUser()+"' and ora="+p.getHour()+" and giorno='"+p.getDay()+"' and nome="+p.getTeacher()+"''" );
             deleted = true;

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
        return deleted;

    }
    public static boolean achieve(Prenotazione p){
        Connection conn1 = null;
        boolean achieved = false;
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from achieve");
            }
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE Prenotazione  set stato='EFFETTUATA' where usuario='"+p.getUser()+"' and ora="+p.getHour()+" and giorno='"+p.getDay()+"' and nome="+p.getTeacher()+"''" );
            achieved = true;

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
        return achieved;

    }
    public static boolean create(Associazione a){
        Connection conn1 = null;
        boolean created = false;
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from createassociazone");
            }
            Statement st = conn1.createStatement();
            Docente d= new Docente(a.getName(),a.getSurname());
            Corso corso=a.getCourse();

            ResultSet rs=st.executeQuery("SELECT * FROM Imparte WHERE nome='"+d.getName()+"' and cognome='"+d.getSurname()+"' and corso='"+corso.getTitulo()+"';");
            if(rs.next()) {
                if (!rs.getBoolean("attiva")) {
                    st.executeUpdate("update Imparte set attiva=1 WHERE nome='"+d.getName()+"' and cognome='"+d.getSurname()+"' and corso='"+corso.getTitulo()+"';");
                    created = true;
                }
            }
           else{
                st.executeUpdate("Insert into Imparte(nome, cognome, corso, attiva) VALUES ('" + d.getName() + "','" + d.getSurname() + "', '" + corso.getTitulo() + "', 1);");
                created=true;
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
    public static Boolean eliminate(Associazione a){
        Connection conn1 = null;
        boolean deleted = false;
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from deleteassociazone");
            }
            Statement st = conn1.createStatement();
            Docente d= new Docente(a.getName(),a.getSurname());
            Corso corso=a.getCourse();
            st.executeUpdate("update Imparte set attiva=0 WHERE nome='"+d.getName()+"' and cognome='"+d.getSurname()+"' and corso='"+corso.getTitulo()+"';");
            st.executeUpdate("update Prenotazione set stato='CANCELLATA' where nome='"+d.getName()+"'and cognome='"+d.getSurname()+"' and corso='"+corso.getTitulo()+"';");
            deleted = true;

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
        return deleted;
    }
    public static ArrayList<Prenotazione> getPrenotazioni(){
        Connection conn1 = null;
        ArrayList<Prenotazione> lista = new ArrayList<Prenotazione>();
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from deleteassociazone");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Prenotazione ;");
            while(rs.next()) {
                Corso corso= new Corso(rs.getString("corso"));
                String day= rs.getString("giorno");
                int hour= rs.getInt("ora");
                Docente docente= new Docente(rs.getString("nome"),rs.getString("cognome"));
                Utente utente= new Utente(rs.getString("usuario"));
                String activity= rs.getString("stato");

                Prenotazione prenotazione = new Prenotazione(hour,day,docente,corso,utente,activity);
                lista.add(prenotazione);
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
        return lista;

    }
    public static ArrayList<Prenotazione> getPrenotazioni(String utente){
        Connection conn1 = null;
        ArrayList<Prenotazione> lista = new ArrayList<Prenotazione>();
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from deleteassociazone");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Prenotazione  WHERE usuario='" + utente + "';");

            while(rs.next()) {
                Corso corso= new Corso(rs.getString("corso"));
                String day= rs.getString("giorno");
                int hour= rs.getInt("ora");
                Docente docente= new Docente(rs.getString("nome"),rs.getString("cognome"));
                Utente utente_= new Utente(rs.getString("usuario"));
                String activity= rs.getString("stato");

                Prenotazione prenotazione = new Prenotazione(hour,day,docente,corso,utente_,activity);
                lista.add(prenotazione);
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
        return lista;
    }
    public static ArrayList<Docente> mostrareDoc(){
        Connection conn1 = null;
        ArrayList<Docente> lista = new ArrayList<Docente>();
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from deleteassociazone");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Docente WHERE attiva=1;");
            while(rs.next()) {

                Docente docente= new Docente(rs.getString("nome"),rs.getString("cognome"));
                lista.add(docente);
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
        return lista;

    }
    public static ArrayList<Corso> mostrareCor(){
        Connection conn1 = null;
        ArrayList<Corso> lista = new ArrayList<Corso>();
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from deleteassociazone");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Corso WHERE attiva=1;");
            while(rs.next()) {

                Corso corso= new Corso(rs.getString("corso"));
                lista.add(corso);
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
        return lista;

    }
    public static ArrayList<Imparte> showImpart(){
        Connection conn1 = null;
        ArrayList<Imparte> lista = new ArrayList<Imparte>();
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from showImport");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Docente WHERE attiva=1;");
            while(rs.next()) {

                Docente teacher= new Docente(rs.getString("nome"),rs.getString("cognome"));
                Statement s1=conn1.createStatement();
                Statement s2=conn1.createStatement();

                ResultSet r=s1.executeQuery("select corso from Imparte where nome='"+rs.getString("nome")+"' and cognome='"+rs.getString("cognome")+"' and attiva=1");
                ResultSet s=s2.executeQuery("select COUNT(*) from Imparte where nome='"+rs.getString("nome")+"' and cognome='"+rs.getString("cognome")+"' and attiva=1");

                ArrayList<Corso> courses= new ArrayList<Corso>();
                int n=0;
                if(s.next()) {
                    n = s.getInt("COUNT(*)");

                    while (r.next()) {
                        Corso c = new Corso(r.getString("corso"));
                        courses.add(c);
                    }
                    if (n != 0) {
                        Imparte i = new Imparte(teacher, courses);
                        lista.add(i);
                    }
                }
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
        return lista;

    }
    public static Cell gethourfree(int hour, String day){
        Connection conn1 = null;
        ArrayList<Imparte> lista = new ArrayList<Imparte>();
        try{
            conn1 = DriverManager.getConnection(url1,user,password);
            if (conn1 != null){
                System.out.println("Connected to database from deleteassociazone");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Docente WHERE attiva=1;");
            while(rs.next()) {

                Docente docente= new Docente(rs.getString("nome"),rs.getString("cognome"));
                Statement s2 = conn1.createStatement();
                ResultSet rs2;
                rs2=s2.executeQuery("SELECT COUNT(*) FROM Prenotazione where nome='" + docente.getName()+"' and cognome='"+ docente.getSurname()+"' and giorno='"+day+"' and ora="+hour+" and stato='ATTIVA';");
                rs2.next();
                int resul= rs2.getInt("COUNT(*)");
                if(resul==0){
                    ArrayList<Corso> c=new ArrayList<Corso>();
                    rs2=s2.executeQuery("SELECT corso from Imparte where nome='" + docente.getName()+"' and cognome='"+ docente.getSurname()+"' and attiva=1;");
                    boolean exist = false;
                    while(rs2.next()) {
                        Corso corso = new Corso(rs2.getString("corso"));
                        c.add(corso);
                        exist = true;
                        if (exist) {
                            Imparte imparte = new Imparte(docente, c);
                            lista.add(imparte);
                        }
                    }
                }
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
        Cell free= new Cell(day,hour,lista);
        return free;


    }
}