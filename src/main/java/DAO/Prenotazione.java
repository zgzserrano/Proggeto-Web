package DAO;

public class Prenotazione {
    private int hour;
    private String day;
    private Docente teacher;
    private Corso course;
    private Utente user;
    private String state;

    Prenotazione(int h, String d, Docente t, Corso c, Utente u, String s){
        this.hour = h;
        this.day = d;
        this.teacher = t;
        this.course = c;
        this.user = u;
        this.state = s;
    }

    public Corso getCourse() {
        return course;
    }

    public Docente getTeacher() {
        return teacher;
    }

    public int getHour() {
        return hour;
    }

    public String getDay() {
        return day;
    }

    public String getState() {
        return state;
    }

    public Utente getUser() {
        return user;
    }
}
