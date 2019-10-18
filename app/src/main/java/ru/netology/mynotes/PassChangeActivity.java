package ru.netology.mynotes;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PassChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);

        initViews();
    }

    public void initViews() {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final EditText editNewPassword = findViewById(R.id.doYouPass);

        FloatingActionButton btnSavePassword = findViewById(R.id.next);
        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editNewPassword.length() == 4) {
                    App.getKeystore().saveNewPassword(editNewPassword.getText().toString());

                    editNewPassword.setText("");
                    Toast.makeText(PassChangeActivity.this, "Пароль сохранен", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(PassChangeActivity.this, "Пароль должен состоять из 4 цифр", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

}
