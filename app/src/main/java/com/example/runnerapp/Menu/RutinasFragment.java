package com.example.runnerapp.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.runnerapp.R;
import com.example.runnerapp.Rutinas.Rutina1;
import com.example.runnerapp.Rutinas.Rutina2;
import com.example.runnerapp.Rutinas.Rutina3;

/**
 * A simple {@link Fragment} subclass.

 */
public class RutinasFragment extends Fragment {
    private CardView card1,card2,card3;
    View vista;


    public RutinasFragment() {
        // Required empty public constructor
    }

    public static RutinasFragment newInstance() {

        Bundle args = new Bundle();

        RutinasFragment fragment = new RutinasFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_rutinas, container, false);
        card1 =(CardView)vista.findViewById(R.id.card1);
        card2 =(CardView)vista.findViewById(R.id.card2);
        card3 =(CardView)vista.findViewById(R.id.card3);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vista.getContext().getApplicationContext(), Rutina1.class);
                startActivity(intent);

            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent card = new Intent(vista.getContext().getApplicationContext(), Rutina2.class);
                startActivity(card);

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vista.getContext().getApplicationContext(), Rutina3.class);
                startActivity(intent);
            }
        });


        return vista;
    }
}