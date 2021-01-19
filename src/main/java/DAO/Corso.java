package dao;

public class Corso {
    private String titulo;

    public Corso(String t){
        this.titulo=t;
    }

    public String getTitulo() {
        return titulo;
    }

    //Don't know if will be usefull
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString(){
        return this.titulo;
    }
}
