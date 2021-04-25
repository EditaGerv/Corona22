package com.example.margarita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_register); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu)

        EditText username = findViewById(R.id.Username); //susiejamas kintamasis vaizde su kintamuotu kode
        EditText password = findViewById(R.id.Password);
        EditText email = findViewById(R.id.Email);
        Button register = findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cia rasomas kodas, kuris bus vykdomas, kai paspausime mygtuka
                String usernameStr = username.getText().toString(); //getText - issitraukiame eilute is EditText, kreipiames i kintamaji, kad nuskaitytu vartotojo suvesta teksta
                String passwordStr = password.getText().toString();
                String emailStr = email.getText().toString();

                username.setError(null); //nulis yra skaicius, o null reiskia nieko nera, tuscia
                password.setError(null); //pries validacija issivalome klaidu zurnala
                email.setError(null);

                if (Validation.isUsernameValid(usernameStr) && Validation.isEmailValid(emailStr) && Validation.isPasswordValid(passwordStr)) { //jeigu bus validus duomenys, pereisim is vieno lango i kita
                    Toast.makeText(RegisterActivity.this, "Username: " +
                            usernameStr + "\n" + "Email: " + emailStr + "\n" + "Password: " + passwordStr, Toast.LENGTH_LONG).show();

                    Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(goToLoginActivity);

                } else { //nesirašo skliaustelis sąlygos nurodymui, nes bus vykdomas visais kitais atvejais
                    if (!Validation.isUsernameValid(usernameStr)) { //jeigu nebus validus ismes i ekrana klaida
                        username.setError(getResources().getString(R.string.register_invalid_username));
                        username.requestFocus(); //sufokusuoja ties klaida
                    }
                    if (!Validation.isEmailValid(emailStr)) {
                        email.setError(getResources().getString(R.string.register_invalid_email));
                        email.requestFocus();
                    }
                    if (!Validation.isPasswordValid(passwordStr)) {
                        password.setError(getResources().getString(R.string.register_invalid_password));
                        password.requestFocus();
                    }
                }
            } //onclick funkcijos pabaiga
        });//mygtuko paleidimo funkcijos pabaiga
    }
}