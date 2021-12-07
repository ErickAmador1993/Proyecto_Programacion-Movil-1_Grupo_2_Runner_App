package com.example.runnerapp.Menu;

import androidx.annotation.FractionRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.runnerapp.Clases.Usuario;
import com.example.runnerapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ActivityEditarperfil extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference mDatabase;


    DatabaseReference datos;
    AwesomeValidation awesomeValidation;


    EditText nom, pais, edad, email, pass;
    TextView prueba;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);
        inicialisarFirebase();
        FirebaseAuth mauth  = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();

        String mail = user.getEmail();

        nom = (EditText) findViewById(R.id.txtNombrePerfil);
        pais = (EditText) findViewById(R.id.txtPaisPerfil);
        edad = (EditText) findViewById(R.id.txtEdadPerfil);
        email = (EditText) findViewById(R.id.txtEmailPerfil);
        prueba = (TextView) findViewById(R.id.Txtviewprueba);


        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    private void inicialisarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}