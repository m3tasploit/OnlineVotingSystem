package com.example.onlinevotingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.editText2);
        password=(EditText)findViewById(R.id.editText3);
        b1=(Button)findViewById(R.id.button2);
    }
}
