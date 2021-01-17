package DAO;

public class Prenotazione {
    private int hour;
    private String day;
    private String teacher;
    private String course;
    private String  user;
    private String state;

    Prenotazione(int h, String d, String t, String c, String u, String s){
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

    @Override
    public String toString() {
        return "Prenotazione{" +
                "hour=" + hour +
                ", day='" + day + '\'' +
                ", teacher=" + teacher +
                ", course=" + course +
                ", user=" + user +
                ", state='" + state + '\'' +
                '}';
    }
}
