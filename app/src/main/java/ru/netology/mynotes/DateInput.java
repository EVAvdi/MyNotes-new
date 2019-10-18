package ru.netology.mynotes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

public class DateInput implements TextWatcher {

    private String current = "";
    private final String formatDate = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    private EditText input;

    DateInput(EditText input) {
        this.input = input;
        this.input.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals(current)) {
            return;
        }

        String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
        String cleanC = current.replaceAll("[^\\d.]|\\.", "");

        int cl = clean.length();
        int sel = cl;
        for (int i = 1; i <= cl && i < 6; i += 2) {
            sel++;
        }
        //Fix for pressing delete next to a forward slash
        if (clean.equals(cleanC)) sel--;

        if (clean.length() < 8) {
            clean = clean + formatDate.substring(clean.length());
        } else {
            //This part makes sure that when we finish entering numbers
            //the date is correct, fixing it otherwise
            int day = Integer.parseInt(clean.substring(0, 2));
            int mon = Integer.parseInt(clean.substring(2, 4));
            int year = Integer.parseInt(clean.substring(4, 8));
            int getYear = Calendar.getInstance().get(Calendar.YEAR);
            int getMon = Calendar.getInstance().get(Calendar.MONTH)+ 1;
            int getDay = Calendar.getInstance().get(Calendar.DATE);

            year = (year < getYear) ? getYear : year;
            cal.set(Calendar.YEAR, year);

            if (year == getYear) {
                mon = mon < getMon ? getMon : mon;
            } else {
                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
            }
            cal.set(Calendar.MONTH, mon - 1);
            // ^ first set year for the line below to work correctly
            //with leap years - otherwise, date e.g. 29/02/2012
            //would be automatically corrected to 28/02/2012

            if ((year == getYear) && (mon == getMon)) {
                day = day < getDay ? getDay : (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
            } else {
                day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
            }
            clean = String.format("%02d%02d%02d", day, mon, year);
        }

        clean = String.format("%s.%s.%s", clean.substring(0, 2)
                , clean.substring(2, 4)
                , clean.substring(4, 8));

        sel = sel < 0 ? 0 : sel;
        current = clean;
        input.setText(current);
        input.setSelection(sel < current.length() ? sel : current.length());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
