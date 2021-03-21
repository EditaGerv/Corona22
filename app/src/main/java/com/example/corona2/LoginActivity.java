package com.example.corona2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity { //klases pradzia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //funkcijos pradzia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_login); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu)

        EditText username = findViewById(R.id.Username); //susiejamas elementas vaizde su kintamuotu
        EditText password = findViewById(R.id.Password);
        Button login = findViewById(R.id.Login);
        Button register = findViewById(R.id.Register);

        // kodas susijes su mygtuko paspaudimu
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //pradzia
                //cia bus rasomas kodas, kuris bus vykdomas, kai bus paspaustas mygtukas
                String usernameStr = username.getText().toString(); // String bazinis elementas, eilute, simbolių seka, visada is didziosios raides, eilute rasosi tarp dvigubu kabuciu
                String passwordStr = password.getText().toString(); //kintamaji galime pavadinti kaip norime, bet geriau, kad taip jog suprastume, ką jis savyje saugoja

                username.setError(null); //issivalome klaidu zurnala
                password.setError(null);

                if (Validation.isUsernameValid(usernameStr) && Validation.isPasswordValid(passwordStr)) {
                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class); // iš kur (pirmas parametras) į kur (antras parametras)
                    startActivity(goToSearchActivity);
                } else { //nesirašo skliaustelis sąlygos nurodymui, nes bus vykdomas visais kitais atvejais
                    username.setError(getResources().getString(R.string.login_invalid_credentials));
                    username.requestFocus();
                }

                //Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_ivalid_username), Toast.LENGTH_LONG).show(); //parametrai atskirti kableliais, pliusas sujungia eilutes, teksta tarp kabuciu mato vartotojas
                //Toast.makeText(LoginActivity.this, "prisijungimo vardas: " +
                //usernameStr + "\n" + "slaptazodis: " + passwordStr, Toast.LENGTH_LONG).show(); // nebus vykdomas

            } //pabaiga

        }); //mygtuko paleidimo funkcijos pabaiga

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegisterActivity);
            }
        }); //jei yra skliausteliai prie kintamojo pavadinimo, tai yra funkcija, kabliataškis reiškia sakinio pabaigą
    } //funkcijos pabaiga

} //klases pabaiga
