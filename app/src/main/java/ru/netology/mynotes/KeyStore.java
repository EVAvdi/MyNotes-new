package ru.netology.mynotes;

public interface KeyStore {

    boolean hasPassword();

    boolean checkPassword(String password);

    void saveNewPassword(String password);
}
