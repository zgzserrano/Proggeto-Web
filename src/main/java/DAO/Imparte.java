package DAO;

import java.util.ArrayList;

public class Imparte {
    private Docente teacher;
    private ArrayList<Corso> course;


    public Imparte(Docente docente, ArrayList<Corso> course) {
        this.teacher=docente;
        this.course = course;
    }


    public Docente getTeacher(){
        return teacher;
    }

    public ArrayList<Corso> getCourse() {
        return course;
    }

    public void addCourse(Corso c){
        course.add(c);
    }


}