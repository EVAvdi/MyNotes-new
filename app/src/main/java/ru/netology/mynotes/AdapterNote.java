package ru.netology.mynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterNote extends BaseAdapter {

    private List<Note> notes;

    private LayoutInflater inflater;

    private Context context;

    private Comparator<Note> comparator;

    AdapterNote(Context context, List<Note> notes) {
        this.context = context;
        comparator = new ComparatorNotes(context);

        if (notes == null) {
            this.notes = new ArrayList<>();
        } else {
            Collections.sort(notes, comparator);
            this.notes = notes;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addNote(Note note) {
        this.notes.add(note);
        notifyDataSetChanged();
    }

    void removeNote(int position) {
        notes.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        if (position < notes.size()) {
            return notes.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        Note Note = notes.get(position);

        TextView titleNote = view.findViewById(R.id.titleNote);
        TextView textNote = view.findViewById(R.id.textNote);
        TextView dateDeadline = view.findViewById(R.id.dateDeadline);
        TextView dateUpdateNote = view.findViewById(R.id.dateUpdateNote);

        if (Note.getTitleNote().length() == 0) {
            titleNote.setVisibility(View.GONE);
        } else {

            titleNote.setVisibility(View.VISIBLE);
            titleNote.setText(Note.getTitleNote());
        }
        if (Note.getTextNote().length() == 0) {
            textNote.setVisibility(View.GONE);
        } else {
            textNote.setVisibility(View.VISIBLE);
            textNote.setText(Note.getTextNote());
        }
        if (Note.getDateDeadline().length() == 0) {
            dateDeadline.setVisibility(View.GONE);
        } else {
            dateDeadline.setVisibility(View.VISIBLE);
            dateDeadline.setText(Note.getDateDeadline());
        }
        dateUpdateNote.setText(Note.getDateUpdateNote());

        return view;
    }

    void refresh(List<Note> listNotes) {
        Collections.sort(listNotes, comparator);
        notes = listNotes;
        notifyDataSetChanged();
    }
}
