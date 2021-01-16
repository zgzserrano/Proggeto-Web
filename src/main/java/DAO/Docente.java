package DAO;

public class Docente {
    private String name;
    private String surname;

    public Docente(String n, String s){
        this.name = n;
        this.surname=s;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @Override
    public String toString(){
        return this.name + " " + this.surname;
    }
}
