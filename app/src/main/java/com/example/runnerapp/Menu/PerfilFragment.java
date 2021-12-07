package com.example.runnerapp.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.runnerapp.Activity_login;
import com.example.runnerapp.R;
import com.example.runnerapp.Rutinas.Rutina1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.fragment.app.Fragment;

public class PerfilFragment extends Fragment
{
    View vista;
    Button btn_logout, btn_editar;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    public PerfilFragment()
    {
        //Requerido
    }

    public static PerfilFragment newInstance()
    {
        Bundle args = new Bundle();

        PerfilFragment fragment = new PerfilFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_perfil, container, false);

        btn_logout = vista.findViewById(R.id.btnLogout);
        btn_editar = vista.findViewById(R.id.btnEditar);


        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vista.getContext().getApplicationContext(), ActivityEditarperfil.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(vista.getContext(), "Sesión Cerrada", Toast.LENGTH_SHORT).show();
                to_back();
            }
        });

        return vista;
    }

    private void to_back() {
        Intent i = new Intent(vista.getContext().getApplicationContext(), Activity_login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}