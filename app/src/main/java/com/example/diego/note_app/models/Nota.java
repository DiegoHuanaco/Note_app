package com.example.diego.note_app.models;

import com.example.diego.note_app.application.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Nota extends RealmObject {

    @PrimaryKey
    private int id;
    private String nota;


    public Nota() {} // Only for Realm

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Nota(String nota) {
        this.id = MyApplication.NotaID.incrementAndGet();
        this.nota = nota;

    }

}