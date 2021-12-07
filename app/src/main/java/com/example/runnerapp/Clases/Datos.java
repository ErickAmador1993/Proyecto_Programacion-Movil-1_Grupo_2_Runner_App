package com.example.runnerapp.Clases;

import android.widget.Chronometer;

import java.time.DateTimeException;
import java.util.Date;

public class Datos {

    private String uid;
    private String email;
    private String datenow;
    private String minutes;


    public Datos(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatenow() {
        return datenow;
    }

    public void setDatenow(String datenow) {
        this.datenow = datenow;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
}
