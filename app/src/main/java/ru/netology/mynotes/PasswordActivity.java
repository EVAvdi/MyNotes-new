package ru.netology.mynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordActivity extends AppCompatActivity {
    private String enteredUserPassword = "";
    private int[] images;
    public final String SHARED_PREFERENCES_APP_NAME = "mySharePref";
    public static SharedPreferences mySharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_password);
        mySharedPreferences = getSharedPreferences(SHARED_PREFERENCES_APP_NAME, MODE_PRIVATE);
        initViews();

    }

    private void initViews() {

        NumberButtonClickListener numberListener = new NumberButtonClickListener();

        int[] buttonIdNumber = new int[]{R.id.buttonNumberOne, R.id.buttonNumberTwo
                , R.id.buttonNumberThree, R.id.buttonNumberFour
                , R.id.buttonNumberFive, R.id.buttonNumberSix
                , R.id.buttonNumberSeven, R.id.buttonNumberEight
                , R.id.buttonNumberNine, R.id.buttonNumberZero
                , R.id.buttonNumberBackspace};

        for (int buttonId : buttonIdNumber) {
            findViewById(buttonId).setOnClickListener(numberListener);
        }

        images = new int[]{R.id.profile_image1, R.id.profile_image2
                , R.id.profile_image3, R.id.profile_image4};


    }

    @Override
    public void onBackPressed() {
        //эмулируем нажатие на HOME, сворачивая приложение
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    class NumberButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int idButtonClicked;

            if (enteredUserPassword.length() < 4) {
                idButtonClicked = v.getId();
            } else if (v.getId() == R.id.buttonNumberBackspace) {
                idButtonClicked = v.getId();
            } else {
                idButtonClicked = 0; // когда пароль превышает 4 символов
            }

            switch (idButtonClicked) {
                case R.id.buttonNumberZero:
                    enteredUserPassword += "0";
                    break;
                case R.id.buttonNumberOne:
                    enteredUserPassword += "1";
                    break;
                case R.id.buttonNumberTwo:
                    enteredUserPassword += "2";
                    break;
                case R.id.buttonNumberThree:
                    enteredUserPassword += "3";
                    break;
                case R.id.buttonNumberFour:
                    enteredUserPassword += "4";
                    break;
                case R.id.buttonNumberFive:
                    enteredUserPassword += "5";
                    break;
                case R.id.buttonNumberSix:
                    enteredUserPassword += "6";
                    break;
                case R.id.buttonNumberSeven:
                    enteredUserPassword += "7";
                    break;
                case R.id.buttonNumberEight:
                    enteredUserPassword += "8";
                    break;
                case R.id.buttonNumberNine:
                    enteredUserPassword += "9";
                    break;
                case R.id.buttonNumberBackspace:
                    if (enteredUserPassword.length() != 0) {
                        enteredUserPassword = enteredUserPassword.substring(0, enteredUserPassword.length() - 1);
                        }
                    break;
            }

            for (int i = 0; i < images.length; i++) {
                if (i < enteredUserPassword.length()) {
                    findViewById(images[i]).setBackgroundColor(Color.rgb(255, 193, 7));
                } else {
                    findViewById(images[i]).setBackgroundColor(Color.rgb(80, 80, 80));
                }
            }

               if (enteredUserPassword.length() == 4){
                   if(App.getKeystore().hasPassword()){
                       if(App.getKeystore().checkPassword(enteredUserPassword)){
                           Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                           startActivity(intent);
//                           finish();
                       }else {
                           Toast.makeText(PasswordActivity.this, getString(R.string.password_not_right), Toast.LENGTH_SHORT).show();
                       }
                   }else {
                       App.getKeystore().saveNewPassword(enteredUserPassword);
                       Toast.makeText(PasswordActivity.this, getString(R.string.password_saved), Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                       startActivity(intent);
//                       finish();
                   }
               }
        }
    }
}
