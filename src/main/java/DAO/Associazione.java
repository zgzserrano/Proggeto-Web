package DAO;

public class Associazione {
    private Docente d;

    private Corso corso;
    public Associazione(Docente doc, Corso c){
        d=doc;
        corso=c;
    }
    public String getName(){
        return d.getName();
    }

    public String getSurname(){
        return d.getSurname();
    }

    public Corso getCourse(){
        return corso;
    }

}
