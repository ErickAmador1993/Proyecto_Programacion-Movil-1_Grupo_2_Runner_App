package com.example.runnerapp.Menu;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.runnerapp.R;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {
    View Vista;
    TextView emailuser;
    FirebaseAuth firebaseAuth;



    public InicioFragment() {

    }

    public static InicioFragment newInstance() {

        Bundle args = new Bundle();
        InicioFragment fragment = new InicioFragment();
        fragment.setArguments(args);
        return fragment;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FirebaseAuth mauth  = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();

        Vista=inflater.inflate(R.layout.fragment_inicio, container, false);
        emailuser = Vista.findViewById(R.id.EmailUser);


       emailuser.setText("Bienvenido: "+ user.getEmail());
        return Vista;

    }

}