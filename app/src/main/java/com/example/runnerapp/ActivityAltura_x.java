package com.example.runnerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import static java.lang.Float.parseFloat;


public class ActivityAltura_x extends AppCompatActivity {
    EditText valor_peso;
    EditText valor_altura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altura_x);
        Button btnCalcular = (Button) findViewById(R.id.btnCalcularaIMC);
        valor_peso=(EditText)findViewById(R.id.txtpeso);
        valor_altura=(EditText)findViewById(R.id.txtaltura);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(getApplicationContext(), ActivityPeso_x.class);
                i.putExtra("peso", parseFloat(valor_peso.getText().toString()));
                i.putExtra("altura", parseFloat(valor_altura.getText().toString()));
                startActivity(i);

            }
        });
    }
}