package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IngresoDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_datos);

//ERROR
        Button botonIrAJugadores = findViewById(R.id.ingreso_datos_jugadores);
        Button botonIrAPartidas = findViewById(R.id.ingreso_datos_partidas);
        botonIrAJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IngresoDatos.this, IngresoJugadores.class);
                startActivity(intent);
            }
        });

        botonIrAPartidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IngresoDatos.this, IngresoPartidas.class);
                startActivity(intent);
            }
        });
    }
}