package com.example.diego.note_app.models;

import com.example.diego.note_app.application.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Nota extends RealmObject {

    @PrimaryKey
    private int id;
    private String nota;
    private int color;


    public Nota() {} // Only for Realm


    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Nota(String nota) {
        this.id = MyApplication.NotaID.incrementAndGet();
        this.nota = nota;
        this.color = 0;

    }

}