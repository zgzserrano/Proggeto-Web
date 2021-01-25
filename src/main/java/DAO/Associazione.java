package DAO;

public class Associazione {
    private Docente teacher;

    private Corso course;
    public Associazione(Docente doc, Corso c){
        teacher=doc;
        course=c;
    }

    public Docente getTeacher() {
        return teacher;
    }

    public String getName(){
        return teacher.getName();
    }

    public String getSurname(){
        return teacher.getSurname();
    }

    public Corso getCourse(){
        return course;
    }

}
