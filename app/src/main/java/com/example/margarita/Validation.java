package com.example.margarita;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String USERNAME_REGEX_PATTERN = "^[a-zA-Z0-9]{3,20}$"; //globalus kintamasis matomas visur uz funkcijos ribų, nes yra klases, o ne funkcijos viduje, final - galutinis, nekeičiamas, static - matomas klasiu sarase
    public static final String PASSWORD_REGEX_PATTERN = "^[a-z0-9.!@_]{5,20}$"; //jei kintamasis turi public static final jis rasomas didziosiomis
    public static final String EMAIL_REGEX_PATTERN = "^([a-z0-9._-]+){10,50}@([a-z0-9._-]+){2,10}.([a-z]){2,}$"; //jei parasome public kintamasis bus matomos visur uz funkcijos ribu
//funkcijos antraste sudaro matomumas, grąžina ar negrąžina kažką, funkcijos pav., turi ar neturi parametrą, skliausteliuose rasome, jei aprasome metoda, jei kreipiames į funkciją - ne.
    public static boolean isUsernameValid(String username) { //naujas programavimo bazinis tipas boolean galimos tik 2 opcijos: true (1) arba false (0), jei void - funkcija nieko negrąžina, rekomenduoja pradeti zodziu "is", jeigu funkcija grazina true ar false, riestiniai skliausteliai nusako funkcijos pradžią ir pabaigą
        Pattern pattern = Pattern.compile(USERNAME_REGEX_PATTERN); //sukuriamos username validacijai taisykles pagal nurodyta sablona, String perduodamas bibliotekos metodui, kuris pagal nurodytas taisykles sukuria šabloną, sukuriamos griežtos taisyklės
        Matcher matcher = pattern.matcher(username); //pagal anksčiau sukurtas taisykles palyginami vartotojo įvesti duomenys (username), matcher viduryje yra svarstykles, is kaires puses pattern - taisykles, o desines - vartotojo ivesti duomenys
        boolean isUsernameValid = matcher.find(); //jeigu atitinka, grąžins true, jei ne - false
        return isUsernameValid; // galima tiesiog rasyti return=matcher.find()
    }
    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);
        boolean isPasswordValid = matcher.find();
        return isPasswordValid;
    }
    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(email);
        boolean isEmailValid = matcher.find();
        return isEmailValid;
    }
}
