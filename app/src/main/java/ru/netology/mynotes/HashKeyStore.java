package ru.netology.mynotes;

import android.content.SharedPreferences;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashKeyStore implements KeyStore{
    private final String TAG_MESSAGE_DIGEST = "MD5";

    HashKeyStore() {
    }

    @Override
    public boolean hasPassword() {
        if (MainActivity.mySharedPreferences.contains(MainActivity.SHARED_PREFERENCES_APP_PASSWORD)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkPassword(String password) {

        String hashPassword = md5Custom(password + md5Custom(password));
        String sharePrefPassword = MainActivity.mySharedPreferences.getString(MainActivity.SHARED_PREFERENCES_APP_PASSWORD, "");
        if (sharePrefPassword.equals(hashPassword))
            return true;
        else
            return false;
    }

    @Override
    public void saveNewPassword(String password) {
        SharedPreferences.Editor editor;
        editor = MainActivity.mySharedPreferences.edit();
        editor.putString(MainActivity.SHARED_PREFERENCES_APP_PASSWORD, md5Custom(password + md5Custom(password)));
        editor.apply();
    }

    private String md5Custom(String password) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance(TAG_MESSAGE_DIGEST);
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);

        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }


        return md5Hex;
    }
}
