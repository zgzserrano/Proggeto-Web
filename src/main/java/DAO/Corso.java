package DAO;

public class Corso {
    private String title;

    public Corso(String t){
        this.title=t;
    }

    public String getTitle() {
        return title;
    }

    //Don't know if will be usefull
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return this.title;
    }

    public Corso(){}
}
