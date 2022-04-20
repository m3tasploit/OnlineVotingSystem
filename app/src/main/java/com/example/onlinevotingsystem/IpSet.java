package com.example.onlinevotingsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IpSet extends AppCompatActivity implements View.OnClickListener {
    EditText ed;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_set);
        ed = (EditText) findViewById(R.id.editText4);
        ed.setText("192.168.43.");
        b1 = (Button) findViewById(R.id.button3);
        b1.setOnClickListener(this);
        // b1.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        // Date dateobj = new Date();
        // System.out.println(df.format(dateobj));
        //
        //
        //
        // Calendar calobj = Calendar.getInstance();
        // System.out.println(df.format(calobj.getTime()));
        //
        // Toast.makeText(getApplicationContext(),
        // df.format(calobj.getTime()).toString(), Toast.LENGTH_LONG).show();
        //
        //
        // }
        // });

    }

    @Override
    public void onClick(View view) {
        String ipval = ed.getText().toString();
        if (ipval.equals("")) {
            ed.setError("enter ip..");
        } else {
            SharedPreferences sh = getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", ipval);
            ed.commit();
            Intent ii = new Intent(getApplicationContext(), Capturephoto.class);
            startActivity(ii);
        }
    }
}
