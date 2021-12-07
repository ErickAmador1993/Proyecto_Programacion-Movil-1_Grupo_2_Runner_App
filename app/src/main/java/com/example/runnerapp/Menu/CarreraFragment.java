package com.example.runnerapp.Menu;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.runnerapp.Clases.Datos;
import com.example.runnerapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class CarreraFragment extends Fragment {

    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private int REQUEST_CODE = 111;
    View Vista;
    Button btn_start, btn_stop, btn_reset, btnfinalizar;
    Chronometer chronometro;
    Boolean correr = false;
    TextView mensaje;
    long detenerse;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    AwesomeValidation awesomeValidation;

    Button btnguardar;
    Double longitudOrigen, latitudOrigen, longitudFinal, latitudFinal;
    boolean dibujar = false;
    boolean finalizar = false;
    GoogleMap map;
    Boolean actualPosition = true;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarreraFragment() {
        // Required empty public constructor
    }

    public static CarreraFragment newInstance() {

        Bundle args = new Bundle();
        CarreraFragment fragment = new CarreraFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inicialisarFirebase();
        Vista=inflater.inflate(R.layout.fragment_carrera, container, false);
        FirebaseAuth mauth  = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        btn_start = Vista.findViewById(R.id.btn_start);
        btn_stop = Vista.findViewById(R.id.btn_stop);
        btn_reset = Vista.findViewById(R.id.btn_reset);
        chronometro = Vista.findViewById(R.id.chronometro);
        btnguardar = Vista.findViewById(R.id.BtnRegistrar);
        btnfinalizar = Vista.findViewById(R.id.btnFinalizar);
        mensaje = Vista.findViewById(R.id.txtmensaje);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mauth  = FirebaseAuth.getInstance();
                FirebaseUser user = mauth.getCurrentUser();
                String mail = user.getEmail();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                String min = chronometro.getContentDescription().toString();

                Datos d = new Datos();
                d.setUid(UUID.randomUUID().toString());
                d.setEmail(mail);
                d.setDatenow(strDate);
                d.setMinutes(min);
                databaseReference.child("Datos").child(d.getUid()).setValue(d);
                Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                resetChronometro();
            }
        });




        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(CarreraFragment.this.getContext());

        if (ActivityCompat.checkSelfPermission(CarreraFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            startChronometro();
            getFirstLocation();
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions((Activity) CarreraFragment.this.getContext(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometro();
                btn_start.setVisibility(View.INVISIBLE);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                btn_start.setVisibility(View.VISIBLE);
                stopChronometro();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.INVISIBLE);
                resetChronometro();
            }
        });

        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocation();
                stopChronometro();
                mensaje.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
                btn_stop.setVisibility(View.INVISIBLE);
                btnguardar.setVisibility(View.VISIBLE);
                btnfinalizar.setVisibility(View.INVISIBLE);
            }
        });
        return Vista;
    }
    public void Guardar(){


    }

    private void getFirstLocation() {

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            //Toast.makeText(getContext(), " correctamente", Toast.LENGTH_SHORT).show();
                            latitudFinal = location.getLatitude();
                            longitudFinal = location.getLongitude();

                            LatLng posicionFinal = new LatLng(latitudFinal,longitudFinal);

                            //googleMap.addMarker(new MarkerOptions().position(posicionFinal).title("Aquí terminaste"));

                            MarkerOptions markerOptionsF = new MarkerOptions().position(posicionFinal).title("Comenzaste Aquí");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionFinal,17));

                            googleMap.addMarker(markerOptionsF).showInfoWindow();
                        }
                    });

                }
            }
        });
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            map = googleMap;
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                        Manifest.permission.ACCESS_COARSE_LOCATION) ){

                                }
                                else
                                {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            1);
                                }

                                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                        Manifest.permission.ACCESS_FINE_LOCATION) ){

                                }
                                else
                                {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            1);
                                }


                                return;
                            }
                            map.setMyLocationEnabled(true);
                            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                                @Override
                                public void onMyLocationChange(Location location) {

                                    if(actualPosition)
                                    {
                                        latitudOrigen = location.getLatitude();
                                        longitudOrigen = location.getLongitude();
                                        actualPosition = true;

                                        LatLng miPosicion = new LatLng(latitudOrigen,longitudOrigen);


                                        //map.addMarker(new MarkerOptions().position(miPosicion).title("Aqui estoy yo"));

                                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                                .target(new LatLng(latitudOrigen,longitudOrigen))      // Sets the center of the map to Mountain View
                                                .zoom(17)
                                                .bearing(1)// Sets the zoom
                                                .build();                   // Creates a CameraPosition from the builder
                                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    }
                                }
                            });
                        }
                    });

                }
            }
        });
    }


    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            //Toast.makeText(getContext(), " correctamente", Toast.LENGTH_SHORT).show();
                            latitudFinal = location.getLatitude();
                            longitudFinal = location.getLongitude();

                            LatLng posicionFinal = new LatLng(latitudFinal,longitudFinal);

                            //googleMap.addMarker(new MarkerOptions().position(posicionFinal).title("Aquí terminaste"));

                            MarkerOptions markerOptionsL = new MarkerOptions().position(posicionFinal).title("Terminaste Aquí");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionFinal,17));

                            googleMap.addMarker(markerOptionsL).showInfoWindow();
                        }
                    });

                }
            }
        });
    }

    private void inicialisarFirebase() {
        FirebaseApp.initializeApp(this.getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void resetChronometro() {
        chronometro.setBase(SystemClock.elapsedRealtime());
        detenerse=0;
    }

    private void stopChronometro() {
        if (correr){
            chronometro.stop();
            detenerse = SystemClock.elapsedRealtime() - chronometro.getBase();
            correr=false;
        }
    }

    private void startChronometro() {
        if(!correr){
            chronometro.setBase(SystemClock.elapsedRealtime() - detenerse);
            chronometro.start();
            correr=true;
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==  REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }else{
                Toast.makeText(this.getContext(),"Permiso Denegado",Toast.LENGTH_LONG).show();
            }
        }
    }

}