package com.geekbrains.widgetexample2;

public class Note {
    private long id;
    private String note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return  note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // это нужно для ArrayAdapter, чтобы правильно отображался текст
    public String toString() {
        return note;
    }
}
