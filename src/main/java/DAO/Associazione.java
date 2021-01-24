package DAO;

public class Associazione {
    private Docente teacher;

    private Corso corso;
    public Associazione(Docente doc, Corso c){
        teacher=doc;
        corso=c;
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
        return corso;
    }

}
