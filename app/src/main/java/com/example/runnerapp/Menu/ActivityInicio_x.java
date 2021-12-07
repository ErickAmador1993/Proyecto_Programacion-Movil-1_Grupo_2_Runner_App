package com.example.runnerapp.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runnerapp.Activity_login;
import com.example.runnerapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityInicio_x extends AppCompatActivity {

    Button cerrar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_x);

        cerrar_sesion = (Button) findViewById(R.id.btnInicio);
        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Sesión Cerrada", Toast.LENGTH_SHORT).show();
                to_back();
            }
        });

    }

    private void to_back() {
        Intent i = new Intent(this, Activity_login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}