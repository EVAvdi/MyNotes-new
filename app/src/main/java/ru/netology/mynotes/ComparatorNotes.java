package ru.netology.mynotes;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ComparatorNotes implements Comparator <Note> {
    private Context context;

    ComparatorNotes(Context context) {
        this.context = context;
    }

    @Override
    public int compare(Note o1, Note o2) {

        Date datedeadline1 = null, datedeadline2 = null, dateUpdate1 = null, dateUpdate2 = null;
        int result = 0;

        try {
            datedeadline1 = new SimpleDateFormat("dd.mmm.yyyy").parse(o1.getDateDeadline());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            datedeadline2 = new SimpleDateFormat("dd.mmm.yyyy").parse(o2.getDateDeadline());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            dateUpdate1 = new SimpleDateFormat("dd.mmm.yyyy").parse(o1.getDateUpdateNote());
            dateUpdate2 = new SimpleDateFormat("dd.mmm.yyyy").parse(o2.getDateUpdateNote());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((datedeadline1 != null) && (datedeadline2 != null)) {
            result = datedeadline1.compareTo(datedeadline2);

            if (result == 0) {
                result = dateUpdate2.compareTo(dateUpdate1);
            }
        } else if ((datedeadline1 == null) && (datedeadline2 == null)) {

            result = dateUpdate2.compareTo(dateUpdate1);
        } else if (datedeadline1 == null) {
            return 1;
        } else if (datedeadline2 == null) {
            return -1;
        }
        return result;
    }
}
