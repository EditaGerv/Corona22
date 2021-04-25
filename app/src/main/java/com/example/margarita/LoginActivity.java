package com.example.margarita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity { //klases pradzia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //funkcijos pradzia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_login); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu)

        final EditText username = findViewById(R.id.Username); //susiejamas elementas vaizde su kintamuotu
        final EditText password = findViewById(R.id.Password);
        Button login = findViewById(R.id.Login);
        final Button register = findViewById(R.id.Register);
        final CheckBox rememberMe = findViewById(R.id.remember_me);
        // Mes turesim patikrinti, ar vartotojas buvo pask karta pazymejes checkbox:
        final User user =new User(LoginActivity.this);  //turi duomenis, kas paskutini karta buvo padaryta,nes susietas su sharedPreferences; context rodo, nes konstruktoiuje apsirasem kaip context
        //kreipiames i checkbox:
        rememberMe.setChecked(user.isRememberedForLogin()); // kokia paskutini karta buvo suteikta reiksme (true arba false), sitoje vietoje grazina

        if (rememberMe.isChecked()){
            username.setText(user.getUsernameForLogin(), TextView.BufferType.EDITABLE); // editext viduje perduosime parametra is musu objekto
        //bus irasytas jo paskutinis vardas, bet ji galima bus redaguoti (pasikeisti) dėl EDITABLE
            password.setText(user.getPasswordForLogin(), TextView.BufferType.EDITABLE);
        }
        else{
            username.setText("",TextView.BufferType.EDITABLE);//nes is sharedpreferences interf. galima paimti visur
            password.setText("",TextView.BufferType.EDITABLE);
        }
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
                    //issaugoti SharedPreferences duomenis
                    user.setUsernameForLogin(usernameStr);
                    user.setPasswordForLogin(passwordStr);
                    if(rememberMe.isChecked()){ //ar pazymejo checkbox
                        user.setRemembermeKeyForLogin(true); // jei buvo pazymetas, kreipiames i user objekta, norime ji issaugoti kad irasytume jo busena "pazymetas" i sharedpreferences
                    }
                    else {
                        user.setRemembermeKeyForLogin(false); //pasakome, kad nebuvo pazymejes ir kad kai krausime langa kita karta nieko nebutu irasyta
                    }

//                    User user = new User (usernameStr, passwordStr);//sukonstruotas naujas objektas, po zodelio new rasosi konstruktoriaus pavadinimas
//                    System.out.println(user.getUsername() + "\n" + "Password: " + user.getPassword());
//                    Toast.makeText(LoginActivity.this, "Username: " +
//                            user.getUsername() + "\n" + "Password: " + user.getPassword(), Toast.LENGTH_LONG).show();
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
