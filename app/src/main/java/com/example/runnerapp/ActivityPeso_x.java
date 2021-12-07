package com.example.runnerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.runnerapp.Menu.MainActivity;

public class ActivityPeso_x extends AppCompatActivity {

    private TextView txtResultado,descripcion;
    Button btnSiguienteMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_x);

        //Button btnSigiente1 = (Button) findViewById(R.id.btnSiguienteAltura);
        descripcion = (TextView)findViewById(R.id.Descrpcion);
        btnSiguienteMenu = (Button) findViewById(R.id.btnSiguienteMenu);

        btnSiguienteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seguienteMenu();
            }
        });


            Intent i = getIntent();
            float p = i.getExtras().getFloat("peso");
            float a = i.getExtras().getFloat("altura");

            float Imc=p/(float) Math.pow(a,2);
            txtResultado =(TextView)findViewById(R.id.txtIMC);
            txtResultado.setText("\n"+String.valueOf(Imc));

            if(Imc<18.5)
                descripcion.setText("Bajo Peso");

            if(Imc>=18.5f && Imc<=25)
                descripcion.setText("Peso Normal");

            if(Imc>=25 && Imc<=30 )
                descripcion.setText("Sobre peso");


            }

    private void seguienteMenu() {
        Intent intentMenu = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentMenu);
    }


}