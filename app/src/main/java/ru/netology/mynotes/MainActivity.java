package ru.netology.mynotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public final String SHARED_PREFERENCES_APP_NAME = "mySharePref";
    public static final String SHARED_PREFERENCES_APP_PASSWORD = "AppPassword";
    public static final String POSITION_LISTVIEW = "Position";
    public static SharedPreferences mySharedPreferences;
    private Intent intent;
    private AdapterNote adapter;
    private static long back_pressed;
    private NoteRepository fileNoteRepository;
    public static final String FILE_NAME = "data.file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mySharedPreferences = getSharedPreferences(SHARED_PREFERENCES_APP_NAME, MODE_PRIVATE);

        if (App.getKeystore().hasPassword()) {
            // Вывод окна с вводом пароля
            intent = new Intent(MainActivity.this, PasswordActivity.class);

            if (intent != null) {
                startActivity(intent);
            }
        }

        initViews();
    }

    public void displayNotes() {

        List<Note> notes = fileNoteRepository.getNotes();

        if (notes != null) {

            adapter.refresh(notes);
        }
    }

    public void initViews() {
        fileNoteRepository = App.getNoteRepository();

        final ListView listView = findViewById(R.id.listView);
        adapter = new AdapterNote(this, null);
        listView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoteActivity(-1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openNoteActivity(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getString(R.string.note_delete))
                        .setMessage(getString(R.string.question_to_delete_note))
                        .setPositiveButton(getString(R.string.yes_i_sure), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.removeNote(position);

                                fileNoteRepository.deleteById(String.valueOf(position));

                                displayNotes();
                            }
                        }).setNegativeButton(getString(R.string.no_i_not_sure), null).show();
                return true;
            }
        });

        if (!fileNoteRepository.connection()) {
            fileNoteRepository.createDefaultNotes();
            Toast.makeText(this, "Создание заметки", Toast.LENGTH_SHORT).show();
        }

        displayNotes();
    }

    public void openNoteActivity(int position) {

        intent = new Intent(MainActivity.this, AddNoteActivity.class);

        intent.putExtra(POSITION_LISTVIEW, position);

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        displayNotes();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            intent = new Intent(MainActivity.this, PassChangeActivity.class);

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.click_again), Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
