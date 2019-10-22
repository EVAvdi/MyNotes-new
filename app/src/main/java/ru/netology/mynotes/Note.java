package ru.netology.mynotes;

import androidx.annotation.Nullable;

public class Note {

    private String titleNote;
    private String textNote;
    private String dateDeadline;
    private String dateUpdateNote;

    Note(@Nullable String titleNote, String textNote, @Nullable String dateDeadline, @Nullable String dateUpdateNote) {
        this.titleNote = titleNote;
        this.textNote = textNote;
        this.dateDeadline = dateDeadline;
        this.dateUpdateNote = dateUpdateNote;
    }

    String getTitleNote() {
        return titleNote;
    }

    public void setTitleNote(String titleNote) {
        this.titleNote = titleNote;
    }

    String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    String getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(String dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    String getDateUpdateNote() {
        return dateUpdateNote;
    }

    public void setDateUpdateNote(String dateUpdateNote) {
        this.dateUpdateNote = dateUpdateNote;
    }
}
