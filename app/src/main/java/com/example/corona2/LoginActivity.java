package com.example.corona2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity { //klases pradzia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //funkcijos pradzia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_login); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu)

        EditText username = findViewById(R.id.Username); //susiejamas elementas vaizde su kintamuotu
        EditText password = findViewById(R.id.Password) ;
        Button login = findViewById(R.id.Login);

        // kodas susijes su mygtuko paspaudimu
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //pradzia
                //cia bus rasomas kodas, kuris bus vykdomas, kai bus paspaustas mygtukas
                String usernameStr = username.getText().toString(); // String visada is didziosios raides
                String passwordStr = password.getText().toString();

                Toast.makeText(LoginActivity.this, "prisijungimo vardas: " +
                        usernameStr + "\n" + "slaptazodis: " + passwordStr, Toast.LENGTH_LONG).show();

                Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class); // iš kur (pirmas parametras) į kur (antras parametras)
                startActivity(goToSearchActivity);

            } //pabaiga
        }); //mygtuko paleidimo funkcijos pabaiga
    } //funkcijos pabaiga

} //klases pabaiga
