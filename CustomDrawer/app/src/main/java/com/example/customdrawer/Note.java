package com.example.customdrawer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Note implements Serializable {

    private String name;
    private String date;
    private String status;


    public Note(String name, String date, String done) {
        this.name = name;
        this.date = date;
        this.status = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", Status='" + status + '\'' +
                '}';
    }
    public static ArrayList<Note> generate4Notes(){
        ArrayList<Note> notes = new ArrayList<>();

        notes.add(new Note("Iphone 14", "24/12/2022", "Wait"));
        notes.add(new Note("Iphone 15", "24/12/2022", "Wait"));
        notes.add(new Note("Iphone 16", "24/12/2022", "Wait"));
        notes.add(new Note("Iphone 17", "24/12/2022", "Wait"));

        return notes;
    }

    public static ArrayList<Note> generateSingleNote(){
        ArrayList<Note> notes = new ArrayList<>();

        notes.add(new Note("IMacM2", "24/12/2022", "Wait"));
        return notes;
    }
}