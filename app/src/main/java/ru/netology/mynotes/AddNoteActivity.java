package ru.netology.mynotes;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText dataDeadline;
    private Bundle bundle;
    private EditText textNote, titleNote;
    private NoteRepository myNoteRepository;
    private final String TAG_DATE_PICKER = "date picker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        ++month;
        dataDeadline.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "." + (month < 10 ? "0" + month : month) + "." + year);
    }

    public void initViews() {

        myNoteRepository = App.getNoteRepository();
        myNoteRepository = new MyNoteRepository(this, MainActivity.FILE_NAME);

        myNoteRepository = App.getNoteRepository();

        titleNote = findViewById(R.id.userToDoEditText);
        textNote = findViewById(R.id.userToDoDescription);
        dataDeadline = findViewById(R.id.newTodoDateEditText);
        final SwitchCompat switchCompat = findViewById(R.id.toDoHasDateSwitchCompat);

        {
            bundle = getIntent().getExtras();
            if (bundle == null) {
                return;
            }

            if (bundle.getInt(MainActivity.POSITION_LISTVIEW) != -1) {

                Note note = myNoteRepository.getNoteById(Integer.toString(bundle.getInt(MainActivity.POSITION_LISTVIEW)));

                if (note.getTitleNote().length() != 0) {
                    titleNote.setText(note.getTitleNote());
                }
                if (note.getTextNote().length() != 0) {
                    textNote.setText(note.getTextNote());
                }

                if (note.getDateDeadline().length() != 0) {
                    switchCompat.setChecked(true);
                    dataDeadline.setEnabled(true);
                    dataDeadline.setText(note.getDateDeadline());
                } else {
                    switchCompat.setChecked(false);
                }
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        switchCompat.setOnCheckedChangeListener(checkedChangeListener);

        ImageButton buttonRemind = findViewById(R.id.userToDoReminderIconImageButton);
        buttonRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompat.isChecked()) {

                    DialogFragment datePicker = new DataPickerFragment();
                    datePicker.show(getSupportFragmentManager(), TAG_DATE_PICKER);
                }
            }
        });

        dataDeadline.addTextChangedListener(new DateInput(dataDeadline));
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                dataDeadline.setEnabled(true);
                dataDeadline.requestFocus();
            } else {
                dataDeadline.setEnabled(false);
                dataDeadline.setText("");
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:

                if (bundle.getInt(MainActivity.POSITION_LISTVIEW) == -1) {
                    if (textNote.getText().length() == 0) {
                        Toast.makeText(this, "Для сохранения, заполните тело заметки", Toast.LENGTH_LONG).show();
                    } else {
                        myNoteRepository.saveNote(new Note((titleNote.getText().length() == 0 ? null : titleNote.getText().toString())
                                , textNote.getText().toString()
                                , (dataDeadline.getText().length() == 0 ? null : dataDeadline.getText().toString())
                                , new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault()).format(new Date())));

                        Toast.makeText(this, "Заметка сохранена", Toast.LENGTH_LONG).show();

                    }
                } else {
                    myNoteRepository.deleteById(String.valueOf(bundle.getInt(MainActivity.POSITION_LISTVIEW)));
                    myNoteRepository.saveNote(new Note((titleNote.getText().length() == 0 ? null : titleNote.getText().toString())
                            , textNote.getText().toString()
                            , (dataDeadline.getText().length() == 0 ? null : dataDeadline.getText().toString())
                            , new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault()).format(new Date())));

                    Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_LONG).show();
                }

                finish();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
