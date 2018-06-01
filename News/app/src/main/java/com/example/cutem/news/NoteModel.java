package com.example.cutem.news;

public class NoteModel {
    public String noteTitle;
    public String noteTime;


    public NoteModel(String noteTitle, String noteTime) {
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }
}
