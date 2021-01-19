package DAO;

import java.util.ArrayList;

public class Cell {
    private String day;
    private int hour;
    private ArrayList<Imparte> table;


    public Cell(String day,int hour,ArrayList<Imparte> table){
        this.day=day;
        this.hour=hour;
        this.table=table;
    }

    public int getHour() {
        return hour;
    }

    public String getDay() {
        return day;
    }

    public ArrayList<Imparte> getTable() {
        return table;
    }
}

