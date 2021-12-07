package com.example.runnerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityGenero_x extends AppCompatActivity {

    Button btnSiguienteAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero_x);
        btnSiguienteAltura = (Button) findViewById(R.id.btnSiguienteMenu);

        btnSiguienteAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAltura = new Intent(getApplicationContext(), ActivityAltura_x.class);
                startActivity(intentAltura);
            }
        });
    }
}