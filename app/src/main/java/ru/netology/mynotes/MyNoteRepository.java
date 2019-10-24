package ru.netology.mynotes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class MyNoteRepository implements NoteRepository {
    private Context context;
    private String fileName;

    MyNoteRepository(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public boolean connection() {
        File file = context.getFileStreamPath(fileName);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    @Override
    public void createDefaultNotes() {
        String dateNow = new SimpleDateFormat(context.getString(R.string.data_format), Locale.getDefault()).format(new Date());

        saveNote(new Note(context.getString(R.string.title1), context.getString(R.string.text1), context.getString(R.string.deadline), dateNow));
        saveNote(new Note(null,context.getString(R.string.text2),null,context.getString(R.string.date_update)));
        saveNote(new Note(null,context.getString(R.string.text3),context.getString(R.string.deadline2),dateNow));
        saveNote(new Note(context.getString(R.string.title4), context.getString(R.string.text4), context.getString(R.string.deadline3), context.getString(R.string.data_update2)));
    }

    @Override
    public Note getNoteById(String id) {
        List<Note> notes = getNotes();
        return notes.get(Integer.parseInt(id));
    }

    @Override
    public List<Note> getNotes() {

        List<Note> noteList = new ArrayList<>();
        BufferedReader br = null;
        try {
            // открываем поток для чтения
            br = new BufferedReader(new InputStreamReader(
                    context.openFileInput(fileName)));
            String str;

            // читаем содержимое
            while ((str = br.readLine()) != null) {

                String[] arrayContent = str.split("\n");

                for (int i = 0; i < arrayContent.length; i++) {
                    String[] masStr = arrayContent[i].split(";");
                    noteList.add(new Note(masStr[0], masStr[1], masStr[2], masStr[3]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // закрываем поток
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(noteList, new ComparatorNotes(context));
        return noteList;
    }

    @Override
    public void saveNote(Note note) {
        BufferedWriter bw = null;
        try {
            if (connection()) {
                // отрываем поток для записи
                bw = new BufferedWriter(new OutputStreamWriter(
                        context.openFileOutput(fileName, MODE_APPEND)));
            } else {
                bw = new BufferedWriter(new OutputStreamWriter(
                        context.openFileOutput(fileName, MODE_PRIVATE)));
            }

            // пишем данные
            bw.write((note.getTitleNote() == null ? "" : note.getTitleNote()) + ";" +
                    (note.getTextNote() == null ? "" : note.getTextNote()) + ";" +
                    (note.getDateDeadline() == null ? "" : note.getDateDeadline()) + ";" +
                    note.getDateUpdateNote() + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // закрываем поток
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteById(String id) {
        List<Note> listNotes = getNotes();
        listNotes.remove(Integer.parseInt(id));

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(fileName, MODE_PRIVATE)));
            bw.write("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Note note : listNotes) {
            saveNote(note);
        }
    }
}
