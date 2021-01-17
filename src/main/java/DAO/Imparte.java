package DAO;

public class Imparte {
    private String name;
    private String surname;
    private String course;
    private boolean attiva;


    public Imparte(String name, String surname, String course,boolean attiva) {
        this.name = name;
        this.surname = surname;
        this.course = course;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCourse() {
        return course;
    }

    public boolean getAttiva() {
        return attiva;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + course;
    }
}
