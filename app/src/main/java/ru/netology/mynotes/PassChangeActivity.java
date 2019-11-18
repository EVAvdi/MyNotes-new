package ru.netology.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
        final EditText editLastPassword = findViewById(R.id.doYouLastPass);
        FloatingActionButton btnSavePassword = findViewById(R.id.next);

        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lastPass;
                lastPass = editLastPassword.getText().toString();
                    if(App.getKeystore().checkPassword(lastPass)){
                      MainActivity.mySharedPreferences.edit().clear().commit();
                      final Intent intent = new Intent(PassChangeActivity.this, PasswordActivity.class);
                      startActivity(intent);
                   finish();
                }
                else {
                    Toast.makeText(PassChangeActivity.this, getString(R.string.password_not_right), Toast.LENGTH_LONG).show();
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
