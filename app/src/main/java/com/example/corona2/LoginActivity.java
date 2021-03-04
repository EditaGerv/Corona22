package com.example.corona2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    EditText this.username = (EditText) findViewById(R.id.username);
    EditText this.password = (EditText) findViewById(R.id.password);
    Button this.submit = (Button) findViewById(R.id.submit);

    }

}