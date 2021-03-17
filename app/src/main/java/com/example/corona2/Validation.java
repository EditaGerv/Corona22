package com.example.corona2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String USERNAME_REGEX_PATTERN = "^[a-zA-Z0-9]{3,20}$"; //globalus kintamasis matomas visur uz funkcijos ribų

    public static boolean isUsernameValid(String username) { //true (1) arba false (0)
        Pattern pattern = Pattern.compile(USERNAME_REGEX_PATTERN);//sukuriamos username validacijai taisykles pagal nurodyta sablona
        Matcher matcher = pattern.matcher(username); //pagal anksčiau sukurtas taisykles palyginami vartotojo įvesti duomenys (username)
        boolean isUsernameValid = matcher.find(); //jeigu atitinka, grąžins true, jei ne - false
        return isUsernameValid;
    }
}

