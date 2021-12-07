package com.example.runnerapp.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.runnerapp.ActivityAltura_x;
import com.example.runnerapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalisisFragment extends Fragment {

    Button btnIMC;

    public AnalisisFragment() {
        // Required empty public constructor
    }

    public static AnalisisFragment newInstance() {

        Bundle args = new Bundle();

        AnalisisFragment fragment = new AnalisisFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View Vista = inflater.inflate(R.layout.fragment_analisis, container, false);

        btnIMC = Vista.findViewById(R.id.btnIMC);

        btnIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vista.getContext().getApplicationContext(), ActivityAltura_x.class);
                startActivity(intent);
            }
        });

        return Vista;
    }
}