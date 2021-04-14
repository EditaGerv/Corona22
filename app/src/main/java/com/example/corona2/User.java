package com.example.corona2;

import android.content.Context;
import android.content.SharedPreferences;

public class User { //klase visada bus public ir is didziosios, nes viena
    //1. klases kintamieji (argumentai, pozymiai, argument)
    private String username;
    private String password;
    private String email;

    private SharedPreferences sharedPreferences;
    private static final String PREFERENCES_PACKAGE_NAME = "com.example.corona2";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String REMEMBERME_KEY = "rememberMe";

    //2. konstruktorius, atitinka klasės pavadinimą irgi iš didžiosios raidės
    public User() {
        //bevardis konstruktorius (galima jo ir nekurti - sukuriamas automatiškai)
    }

    //šis konstruktorius skirtas prisijungimo langui
    public User(String username, String password) {
        this.username = username; //this visalaik bus prie požymio, per konstruktoriu perduosime tam tikrus pozymius
        this.password = password;
    }

    //šis konstruktorius skirtas registracijos langui
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //konstruktorius skirtas vartotojo prisijungimui
    public User(Context context) {
        this.sharedPreferences = context.getSharedPreferences(User.PREFERENCES_PACKAGE_NAME,
                Context.MODE_PRIVATE);
    }
    //3. get'eriai ir set'eriai, metodai (bazine sintakse yra funkcijos, bet vadinasi metodais)
    //get'eris atitinka grąžinančią funkciją be parametrų

    public String getUsernameForRegistration() {
        return username;
    }

    public String getPasswordForRegistration() {
        return password;
    }

    //set'eris atitinka negrąžinančią funkciją su parametrais
    public void setPasswordForRegistration(String password) {
        this.password = password; // pakeisime požymį nauja reikšme perduota per parametrą, sena reikšmė lygu naujai reikšmei
    }

    public String getEmailForRegistration() {
        return email;
    }

    public void setEmailForRegistration(String email) {
        this.email = email;
    }

    public String getUsernameForLogin() {
        return this.sharedPreferences.getString(USERNAME_KEY, "");
    }

    public void setUsernameForLogin(String username) {
        this.sharedPreferences.edit().putString(USERNAME_KEY, username).commit();
    }

    public String getPasswordForLogin() {
        return this.sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public void setPasswordForLogin(String password) {
        this.sharedPreferences.edit().putString(PASSWORD_KEY, password).commit();
    }

    public boolean isRememberedForLogin() {
        return this.sharedPreferences.getBoolean(REMEMBERME_KEY, false);
    }

    public void setRemembermeKeyForLogin(boolean remembermeKey) {
        this.sharedPreferences.edit().putBoolean(REMEMBERME_KEY, remembermeKey).commit();
    }

}
