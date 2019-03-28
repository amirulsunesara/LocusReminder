package com.example.locusreminder.model;

public class ReminderModel {

    private String note_title;
    private String note_text;

    public ReminderModel(String note_title, String note_text) {
        this.note_title = note_title;
        this.note_text = note_text;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_text() {
        return note_text;
    }

    public void setNote_text(String note_text) {
        this.note_text = note_text;
    }
}
