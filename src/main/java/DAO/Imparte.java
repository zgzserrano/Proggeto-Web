package dao;

public class Imparte {
    private Docente docente;
    private Corso course;
    private String attiva;


    public Imparte(Docente docente, Corso course,String attiva) {
        this.docente=docente;
        this.course = course;
        this.attiva= attiva;
    }


    public String getName() {
        return docente.getName();
    }

    public String getSurname() {
        return docente.getSurname();
    }

    public Corso getCourse() {
        return course;
    }

    public String getAttiva() {
        return attiva;
    }
}