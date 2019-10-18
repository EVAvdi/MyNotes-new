package ru.netology.mynotes;

import androidx.annotation.Nullable;

public class Note {

    private String headline;
    private String textNote;
    private String dateDeadline;
    private String dateUpdateNote;

    Note(@Nullable String headline, String textNote, @Nullable String dateDeadline, @Nullable String dateUpdateNote) {
        this.headline = headline;
        this.textNote = textNote;
        this.dateDeadline = dateDeadline;
        this.dateUpdateNote = dateUpdateNote;
    }

    String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
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
