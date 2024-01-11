package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SQLiteHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inserta("","",1,R.drawable.ic_launcher_background,1,1,1,1,1);
    }
    private void inserta(String nombre, String ciudad, int partidasGanadas,int foto,int fecha,int jugador1,int jugador2,int puntuacionJugador1,int puntuacionJugador2) {

        ContentValues values= new ContentValues();
        values.put("", nombre);
        values.put("", ciudad);
        values.put("", partidasGanadas);
        values.put("",foto);

        values.put("",fecha);
        values.put("",jugador1);
        values.put("",jugador2);
        values.put("",puntuacionJugador1);
        values.put("",puntuacionJugador2);
        db.insert("campeonatotorneo",null, values);

    }
}